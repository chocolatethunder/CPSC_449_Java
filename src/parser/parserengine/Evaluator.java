/************************************************************************************
 * CPSC 449 - Winter 2017															*
 * Prof: Rob Kremer																	*
 * Assignment: Java																	*
 * Group #: 32																		*
 * Members: Saurabh Tomar, Kaylee Stelter, Kowther Hassan, Matthew Mullins, Tsz Lam	*
 * Description:																		*
 * 		- Class takes a parse tree of type Node<Token and evaluates it. The result  *
 *        is stored as an object.                                                   *
 *  																				*
 * Contain methods:																	*
 *      + Evaluator(Tokenizer)                                                      *
 *      + getParseTree():Node<Token>                                                *
 *      + parse( Node<Token> rootNode, Class jarLoad ):Node<Token>                  *
 *      + validParamType(Method[],ArrayList<Node<Token>>,ArrayList<Integer>): int   *
 *      + hasParameters(Method[], ArrayList<Integer>):int                           *
 *      + getMethodIndices(Class,Node<Token>):ArrayList<Integer>                    *
 *      + convert(String,Class):Object                                              *
 *      + setRootNodeData(Node<Token>,Object):Node<Token>                           * 
 *      + toString(): String                                                        *
 * 																					*
 ************************************************************************************/
 
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
	int correctIndex;

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
	 * @return - Node representing the root of the parse tree
	 */
	public Node<Token> getParseTree() {
		return this.parseTree;
	}
    
    /**
	 * Evaluates the parse tree from the bottom up (most embedded method call).
	 *  
	 * @param rootNode - Represents the root node of the parse tree
	 * @param jarLoad - Represents the class under consideration
	 * @throws InvocationTargetException - wrapped Exception
	 * @throws IllegalArgumentException - Exception caused by break of naming convention
	 * @throws IllegalAccessException - Exception caused by entity being inaccessible
	 * @throws ParserException - Exceptions thrown for invalid parameter types and invalid methods
	 * @return - Node representing the root node of the parse tree
	 */
	public Node<Token> parse( Node<Token> rootNode, Class jarLoad ) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, ParserException{
		
		// If the node has no children and is a method
		if (rootNode.isLeaf() && (rootNode.getData().getStringType().equals("identifier") || rootNode.getData().getStringType().equals("unidentified") )) {
			// list of methods
			Method[] methods = jarLoad.getMethods(); 
			// index for methods
			ArrayList<Integer> indices = getMethodIndices ( jarLoad,  rootNode);
            
            // checking if rootNode method should have children, if doesn't returns -1
            int hasParams = hasParameters(methods, indices);
            			
			// root node does not match any method in the class
			if (indices.size() == 0 || hasParams == -1)	
			{
                correctIndex = rootNode.getData().getIndex() - rootNode.getData().getName().length()+1; // subexpression starting index
				throw new ParserException(String.valueOf("Matching function for '(" + rootNode.getData().getName() + ")' not found"), correctIndex, parseTreeConstructor.getInput());
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
				// creating string of subexpression
                String expression = "'(" +  rootNode.getData().getName();
                for (int i = 0; i < rootNode.getChildren().size(); i ++ ) {
                    expression += " " + rootNode.getChildren().get(i).getData().getStringType();
                }
                expression += ")'";
                correctIndex = rootNode.getData().getIndex() - rootNode.getData().getName().length()+1; // subexpression starting index
                    throw new ParserException(String.valueOf("Matching function for " + expression + " not found"), correctIndex, parseTreeConstructor.getInput());
			}
			
			// Get all children of root node, and store them in a arraylist
			ArrayList<Node<Token>> children = rootNode.getChildren();
			
			// gets method index that contains the valid params 
			int methodIndex = validParamType(methods,children, indices );		
			
			if (methodIndex == -1) {
				// node children does not match any method parameters.
				correctIndex = rootNode.getData().getIndex() - rootNode.getData().getName().length()+1; // subexpression starting index
				
				Type temp;
				String paramTypeList = "";
				
				// Creates a string representing the types of the parameters attempting to be being passed to the method
				for (int i = 0; i < rootNode.getChildren().size(); i ++ ) {
					temp = rootNode.getChildren().get(i).getData().getType();
					if (temp == String.class){
						paramTypeList = paramTypeList + " " + "string";
					}
					else if (temp == Float.class || temp == float.class){
						paramTypeList = paramTypeList + " " + "float";
					}
					else if (temp == Integer.class || temp == int.class){
						paramTypeList = paramTypeList + " " + "int";
					}					
                }	
				throw new ParserException(String.valueOf("Matching function for (" + rootNode.getData().getName() + paramTypeList + ") not found"), correctIndex, parseTreeConstructor.getInput());
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
	 * Checks a whether a method should have paramaters.
	 * @param methods - Represents the list of methods for the class
	 * @param methodIndex - Represents the indices of the methods
	 * @return - Integer representing the index of a list of methods with parameters
	 */
    public int hasParameters(Method[] methods, ArrayList<Integer> methodIndex) {
        
        int validParamLengthMatch = -1;
        for (Integer i : methodIndex) {
            // store types of parameters into an array
    		Class<?>[] paramTypes = methods[i].getParameterTypes(); 
    		// store the count of required parameters
			int leng = methods[i].getParameterCount();
		
            // if length of paramaters for the method == zero (i.e. does have no children), return index 
			if (leng == 0) {
				validParamLengthMatch = i;
            }
        }
        
        return validParamLengthMatch;
    }
    
    /**
     * Returns a list of indices of the methods in the class
     * @param obj - Represents the class under consideration
     * @param rootNode - Represents the root node of the parse tree
     * @return - ArrayList representing the indices of the methods
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
    	// Temporary subject used for testing
    	Class testSub = valueType;
        
    	// Following conditions will match the s to one of the acceptable types (int/Integer, float/Float, String)
    	
    	// Convert string to the proper string format
        if (valueType == String.class)
        {
            String result = s.substring(1, s.length() - 1);
            return result;
        }
        
        // Convert s to int
        if (valueType == int.class || valueType == Integer.class )
        {
        	try{
        		long result;
        		result = (long)Long.parseLong(s);	
				
				if (Integer.MIN_VALUE  < result && result < Integer.MAX_VALUE) {
					return (int)result;
				} else {
					throw new ParserException("Integer is out of range", this.correctIndex, parseTreeConstructor.getInput());
				}
            }catch (Exception e)
        	{//System.out.println("int is out of range.");
			}
        }
        
        // Convert s to float
        if (valueType == float.class || valueType == Float.class)
        {
        	try {
        		double result;
        		result = (double)Double.parseDouble(s); 
        		
				if (Float.MIN_VALUE  < result && result < Float.MAX_VALUE) {
					return (float)result;
				} else {
					throw new ParserException("Float is out of range", this.correctIndex, parseTreeConstructor.getInput());
				}
				
        	}catch (Exception e) {
        		//System.out.println("float out of range.");
			}
        }
        
        // If s doesnt match any of the acceptable types, return null to notify caller
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