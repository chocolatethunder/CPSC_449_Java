package parser;

import java.io.*;
import java.util.*;
import java.lang.*;
import java.lang.reflect.*;
import java.text.*;

import static parser.Utilities.*;

/**
 * Class which contains the methods which evaluated a completed parse tree.
 */
public class Evaluator {
    ParseTreeConstructor parseTreeConstructor;
    Node<Token> parseTree;
    Object result = null;   // holds the result of the parse

    /**
     * @param parseTreeConstructor - Represents the parse tree created from the command-line input
     */
	public Evaluator(ParseTreeConstructor parseTreeConstructor) {
        this.parseTreeConstructor = parseTreeConstructor;
        try {
            this.parseTree = parseTreeConstructor.createParseTree ();
        } catch (Exception e) { /*handled in parse tree constructor*/}
	}
	
	/**
	 * @return - Node<Token> representing the root of the parse tree
	 */
	public Node<Token> getParseTree() {
		return this.parseTree;
	}
    
    /**
	 * Evaluates the parse tree from the bottom up (most embedded method call).
	 *  
	 * @param rootNode - Represents the root node of the parse tree
	 * @param jarLoad - Represents the class under consideration
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @return - Node<Token> representing the root node of the parse tree
	 */
	public Node<Token> parse( Node<Token> rootNode, Class jarLoad ) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		
		// If the node has no children and is a method
		if (rootNode.isLeaf() && rootNode.getData().getStringType().equals("identifier")) {
			// list of methods
			Method[] methods = jarLoad.getMethods(); 
			// index for methods
			ArrayList<Integer> indices = getMethodIndices ( jarLoad,  rootNode);	
			
			// root node does not match any method in the class
			if (indices.size() == 0)	
			{
				// TODO add error handling here, method not valid!
			}
			
			// Invoke the method, and store the result
			// Will only have one index if it has no children
			result = methods[indices.get(0)].invoke(rootNode.getData().getName());		
			
			return setRootNodeData(rootNode, result);
			
		// If the node has no children and is not a method
		} else if ( rootNode.isLeaf()) {
			return rootNode;}
		
		// If the node has children
		else {
			for (Node<Token> c : rootNode.getChildren()) {
				if(!c.isLeaf()) {
					parse(c, jarLoad);		
				}
			}
			// list of methods
			Method[] methods = jarLoad.getMethods(); 
			// index for methods
			ArrayList<Integer> indices = getMethodIndices ( jarLoad,  rootNode);	
		
			// root node does not match any method in the class
			if (indices.size() == 0)	
			{
				// TODO add error handling here, method not valid!
			}
			
			// Get all children of root node, and store them in a arraylist
			ArrayList<Node<Token>> children = rootNode.getChildren();
			
			// gets method index that contains the valid params 
			int methodIndex = validParamType(methods,children, indices );		
			
			if (methodIndex == -1) {
				// node children does not match any method parameters.
				//TODO, throw exception here
			}
			
			else {
				// store arguments from node to an array
				Object[] args = new Object[children.size()]; 

				// convert children from string to their actual type and store in args list
				for (int i = 0; i < rootNode.getChildren().size(); i++) {		
						args[i] = convert(children.get(i).getData().getName(), children.get(i).getData().getType());
				}
				
				// delete all children
				for (Node<Token> c : rootNode.getChildren()) { c.deleteNode(); }
				
				// invoke the method, and store the result
				result = methods[methodIndex].invoke(rootNode.getData().getName(), args);
			}
			return setRootNodeData(rootNode, result);
		}
	}

	/**
	 * Returns the index of the method with the correct parameter types, or will return -1 if no parameters match.
	 * @param methods - Represents the list of methods for the class
	 * @param children - Represents a list of the children for the node
	 * @param methodIndex - Represents the indices of the methods
	 * @return - Integer representing if the parameters of the method were found to be valid
	 */
    public int validParamType(Method[] methods, ArrayList<Node<Token>> children, ArrayList<Integer> methodIndex) {
    	int validParam = 0;
    	
    	for (Integer i : methodIndex) {
    		// store types of parameters into an array
    		Class<?>[] paramTypes = methods[i].getParameterTypes(); 
    		// store the count of required parameters
			int leng = methods[i].getParameterCount();
			
			if (leng != children.size()) {
				// if size not the same, go to next iteration
				continue;		
			}
			
			for (int j = 0; j < paramTypes.length; j ++) { 
				// if param is Integer class then converts it to int.class
				if (paramTypes[j].equals(Integer.class)) {
					paramTypes[j] = int.class;
				}
				
				// if param is Float.class then converts it to float.class
				if (paramTypes[j].equals(Float.class)) {
					paramTypes[j] = float.class;
				}
				
				// compare params to children
				if (paramTypes[j].equals(children.get(j).getData().getType())) {
					validParam++;
				}
			}
			// returns the valid param
			if (validParam == paramTypes.length) { return i; }	
			else { validParam = 0; }
    	}
    	return -1;
    }
    
    /**
     * Returns a list of indices of the methods in the class
     * @param obj - Represents the class under consideration
     * @param rootNode - Represents the root node of the parse tree
     * @return - ArrayList<Integer> representing the indices of the methods
     */
	public ArrayList<Integer> getMethodIndices (Class obj, Node<Token> rootNode) {
		ArrayList<Integer> index = new ArrayList<Integer>();
		// list of methods
		Method[] methods = obj.getMethods(); 
		if (isMethod(rootNode.getData().getName(), obj))
		{
			for (int idk = 0; idk < methods.length; idk++)
			{
				if (rootNode.getData().getName().equals(methods[idk].getName())) {
					// add method index
					{index.add(idk);}		
				}
			}
		}
		return index;
	}
    
	/**
	 * Takes an argument and attempts to convert it into the desired datatype.
	 * @param valueType - Represents the desired datatype for the argument
	 * @param s - Represents the string to convert
	 * @return - Object representing the result of the conversion, or null if it was unsuccessful
	 */
    public Object convert(String s, Class valueType) {
        Class testSub = valueType;
        
        if (valueType == String.class)
        {
            String result = s.substring(1, s.length() - 1);
            return result;
        }
        
        if (valueType == int.class || valueType == Integer.class )
        {
        	try{
        		int result;
        		result = (int) Integer.parseInt(s); 
        		return result;
            }catch (NumberFormatException e)
        	{System.out.println("Int out of range!");}
        }
        
        if (valueType == float.class || valueType == Float.class)
        {
        	try {
        		float result;
        		result = (float)Float.parseFloat(s); 
        		return result;
        	}catch (NumberFormatException e) {
        		System.out.println("Float out of range!");}
        }
        return null;
    }
        
    /**
     * Sets the root node data to the result of the evaluated method call.
     * @param rootNode - Represents the root node of the parse tree
     * @param data - Represents the result of the evaluated method call
     * @return - Node representing the updated root node
     */
	public Node<Token> setRootNodeData (Node<Token> rootNode, Object data ) {
		// store the result into the root node
		rootNode.getData().setName(data+"");
		
		if (data.getClass().equals(String.class)) { 
			rootNode.getData().setType(String.class);
			rootNode.getData().setStringType("string");
		}else if (data.getClass().equals(Float.class) || data.getClass().equals(float.class)) {
			rootNode.getData().setType(float.class);
			rootNode.getData().setStringType("float");
		}else if (data.getClass().equals(Integer.class) || data.getClass().equals(int.class)) {
			rootNode.getData().setType(int.class);
			rootNode.getData().setStringType("int");
		}
		return rootNode;
	}
	
	/**
	 * @return - String representing the result of the method evaluations
	 */
	public String toString() {
		return result + "";
	}
}