package parser;

import java.io.*;
import java.util.*;
import java.lang.*;
import java.lang.reflect.*;
import java.text.*;

import static parser.Utilities.*;

public class Evaluator {
    ParseTreeConstructor parseTreeConstructor;
    Node<Token> parseTree;
    Object result = null;   // holds the result of the parse

	public Evaluator(ParseTreeConstructor parseTreeConstructor) {
        this.parseTreeConstructor = parseTreeConstructor;
        this.parseTree = parseTreeConstructor.createParseTree ();
        
	}
    	
	
	/**
	 * returns the tokenList
	 * @return 
	 */
	public Node<Token> getParseTree() {
		return this.parseTree;
	}
    
    /**
	 *  Parses a parse tree in the correct order. bottom up
	 *  
	 * @param t
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	public Node<Token> parse( Node<Token> rootNode, Class jarLoad ) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		
		
		//**** no children but is method****
		if (rootNode.isLeaf() && rootNode.getData().getStringType().equals("identifier")) {
			
			
			Method[] methods = jarLoad.getMethods(); // list of methods
			ArrayList<Integer> indices = getMethodIndices ( jarLoad,  rootNode);	// index for methods
			
		
			
			if (indices.size() == 0)	// rootNode does not match any method in the class
			{
				
				// TODO add error handling here, method not valid!
			}
			
			
			// invoke the method, and store the result
			result = methods[indices.get(0)].invoke(rootNode.getData().getName());		// will only have one index if it has no children
			
			return setRootNodeData(rootNode, result);
			
			
			
			
			// *** no children but is not method *** 
		} else if ( rootNode.isLeaf()) {
			
			return rootNode;
			
			
			
			
		}
		//*** has children ****
		else {
			for (Node<Token> c : rootNode.getChildren()) {
				if(!c.isLeaf()) {
					parse(c, jarLoad);		// recursive call
				}
			}
			
			
			Method[] methods = jarLoad.getMethods(); // list of methods
			ArrayList<Integer> indices = getMethodIndices ( jarLoad,  rootNode);	// index for methods
			
		
			if (indices.size() == 0)	// rootNode does not match any method in the class
			{
				
				// TODO add error handling here, method not valid!
			}
			
			
            
			// Get all children of root node, and store them in a arraylist
			ArrayList<Node<Token>> children = rootNode.getChildren();
			
			int methodIndex = validParamType(methods,children, indices );		// gets method index that contains the valid params 
			
			if (methodIndex == -1) {
				
				// node children does not match any method parameters.
				//TODO, throw exception here
			
			
			}else {
				
				Object[] args = new Object[children.size()]; // store arguments from node to an array

				for (int i = 0; i < rootNode.getChildren().size(); i++) {		// convert children from string to their actual type and store in args list
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
    
    //DONE
    // The following method will return the index of the method with the correct param types.
    // Or return -1 if no params match
    public int validParamType(Method[] methods, ArrayList<Node<Token>> children, ArrayList<Integer> methodIndex)
    {	int validParam = -1;
    	
    	for (Integer i : methodIndex) {
    		
    		Class<?>[] paramTypes = methods[i].getParameterTypes(); // store types of parameters into an array
			int leng = methods[i].getParameterCount(); // store the count of required parameters
			
			if (leng != children.size()) {
				
				continue;		// if size not the same, go to next iteration
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
					validParam = i;		
				} else {
					validParam = -1;
				}
			}
    		
			if (validParam != -1) return validParam;	// returns the valid param
    	}
    	return validParam;
    
    }
    
    
	// check if the method is valid
	public ArrayList<Integer> getMethodIndices (Class obj, Node<Token> rootNode) {
		ArrayList<Integer> index = new ArrayList<Integer>();
		Method[] methods = obj.getMethods(); // list of methods
		if (isMethod(rootNode.getData().getName(), obj))
		{
			for (int idk = 0; idk < methods.length; idk++)
			{
				if (rootNode.getData().getName().equals(methods[idk].getName())) {
					{index.add(idk);}		// add method index
				}
				
			}
		}

		return index;
	}
    
    
  //DONE
    // The following method will take the argument
    // and convert it into the desired type
    public Object convert(String s, Class valueType)
    {
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
            }
            catch (ArithmeticException e)
        	{
            	System.out.println("Int out of range!");
            	System.exit(0);
        	}
        }
        if (valueType == float.class || valueType == Float.class)
        {
        	try {
        		
        		float result;
        		
        		 result = (float)Float.parseFloat(s); 
        		
        		return result;
        	}
        	catch (ArithmeticException e) {
        		System.out.println("Float out of range!");
        		System.exit(0);
        	}
        }
        return null;
    }
        
    
    // Sets rootNode data with result data. DOES NOT CHECK RETURN TYPE YET
	public Node<Token> setRootNodeData (Node<Token> rootNode, Object data ) {
		
		// store the result into the root node
		rootNode.getData().setName(data+"");
		
		if (data.getClass().equals(String.class)) { 
			rootNode.getData().setType(String.class);
			rootNode.getData().setStringType("string");
		}else if (data.getClass().equals(Integer.class)) {
			rootNode.getData().setType(int.class);
			rootNode.getData().setStringType("int");
		}else if (data.getClass().equals(Float.class)) {
			rootNode.getData().setType(float.class);
			rootNode.getData().setStringType("float");
		}
		
		return rootNode;
	}
	
    
	
	public String toString() {
		
		return result + "";
	}

}