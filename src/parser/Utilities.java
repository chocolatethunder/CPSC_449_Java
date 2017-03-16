package parser;

import java.io.*;
import java.util.*;
import java.lang.*;
import java.lang.reflect.*;
import java.text.*;

public class Utilities {
	
	// The following method will print out every function, its parameters' type, and return type
    public static void printFunctionList(Class cls) {
    	String functionList = "";
    	
    	Method[] methods = cls.getDeclaredMethods();
    	String methodName;
    	
    	Class[] parameters = null;
    	String returnType = null;
    	
    	for (int i = 0; i < methods.length; i++) {
    		methodName = methods[i].getName();
    		parameters = methods[i].getParameterTypes();
    		Type temp = methods[i].getReturnType();
    		if (temp == String.class)
    		{
    			returnType = "string";
    		}
    		else if (temp == float.class || temp == Float.class)
    		{
    			returnType = "float";
    		}
    		else if (temp == int.class || temp == Integer.class)
    		{
    			returnType = "int";
    		}
    		temp = null;
    		
    		functionList += "("  + methodName;
    		
			for (int j = 0; j < parameters.length; j++) {
    			temp = parameters[j];
    			if (temp == String.class)
        		{
    				functionList += " " + "string";
        		}
        		else if (temp == float.class || temp == Float.class)
        		{
        			functionList += " " + "float";
        		}
        		else if (temp == int.class || temp == Integer.class)
        		{
        			functionList += " " + "int";
        		}
    		}
			
    		functionList += ") : " + returnType + "\n";
    	}
    	
    	System.out.println(functionList);
    }
	
	/**
	 * checks if the number of brackets match, i.e. the # of '(' matches the # of ')'
	 * @param s
	 * @return boolean
	 */
    public static boolean checkBrackets (String s)
    {
        int counter = 0;
        
        for (int i = 0; i < s.length(); i++)
        {
            if (s.charAt(i) == '(') { counter++; }
            if (s.charAt(i) == ')') { counter--; }
        }
        
        if (counter == 0) { return true; }
        else { return false; }
    }
	
    /**
    * counts the number of a specific character in a string 
    */
	public static int characterCount (String s, char c) {	
		return s.length() - s.replace(Character.toString(c),"").length();
	}
	/**
	 * get the type of an input.
	 * Types are string, int, float or identifier
	 * 
	 * @param s
	 * @return
	 */
	public static Class getType (String s , Class jarLoad) {
		
		
        if(isInt(s))  return int.class; 
        if(isString(s)) return String.class; 
        if(isFloat(s))  return float.class; 
        
        if(isMethod(s, jarLoad)) return Method.class;	// if not method return generic class
		
        
        
		return Class.class;
	}
	
	/**
	 * get the type of an input.
	 * Types are string, int, float or identifier
	 * 
	 * @param s
	 * @return
	 */
	public static String getStringType (String s, Class jarLoad ) {
		
        
        if(isInt(s)) return "int"; 
        if(isString(s)) return "string"; 
        if(isFloat(s))  return "float"; 
        
        if(isMethod(s, jarLoad)) return "identifier"; // if not method return generic class
        
		return "unidentified";
	}
	
	/**
	 * The following method will check if the argument is an valid integer (int)
	 * @param s
	 * @return
	 */
     
    public static boolean isInt(String s)
    {
        boolean valid = false;
        int start = 0;
        if (s.charAt(0) == '-' || s.charAt(0) == '+')
        { start = 1; }
        
        try{
        	int temp = Integer.parseInt(s.substring(start, s.length()));
        	return true;
        }
        catch (Exception e) { return false; }
    }
    
    
    /**
     *  The following method will check if the argument is a valid float number
     * @param s
     * @return
     */
    public static boolean isFloat(String s)
    {
        int start = 0;
        if (s.charAt(0) == '-' || s.charAt(0) == '+')
        { start = 1; }
        
        if (!isInt(s) && s.charAt(start) != '.')
        {
	        try{
	        	float temp = Float.parseFloat(s.substring(start, s.length()));
	        	return true;
	        }
	        catch (Exception e) { return false; }
        }
        else { return false; }
    }
    
    /**
     *  The following method will check if the argument is a valid string
     * @param s
     * @return
     */
    public static boolean isString(String s)
    {
        boolean valid_start = false;
        boolean valid_end = false;
        for (int i = 0; i < s.length(); i++)
        {
            if (s.charAt(i) == '"' && i == 0)
            { valid_start = true; }
            
            if (s.charAt(i) == '"' && i != 0 && i != (s.length() - 1)) 
            { return false; }
            
            if (s.charAt(i) == '"' && i == (s.length() - 1))
            { valid_end = true; }
        }
        if (valid_start && valid_end) { return true; }
        else { return false; }
    }
    
      /**
      * The following method will check and see if user input is a valid method
      */ 
    public static boolean isMethod(String s, Class jarLoad)
    {
    	//check and see if s is a valid method
    	Method[] methods = jarLoad.getMethods();
    	//if (!validName(s)) { return false; }
    	
    	for (int i = 0; i < methods.length; i++)
    	{
    		String method = methods[i].getName();
    		if (method.equals(s))
    		{
    			return true;
    		}
    	}
    	return false;
    }
    
    	/**
	 * 
	 * If the ordering is not correct, returns the index where it is wrong
	 * else, returns -1
	 * @param input
	 * @return
	 */
	public static int checkOrderOfTokens( ArrayList<Token> input ) {
		String previousToken = input.get(0).getStringType();
		String currentToken;
		
		for (int index = 1; index < input.size(); index ++ ) {
			currentToken = input.get(index).getStringType();
			switch( currentToken ) {
				case "identifier":
					if (!previousToken.equals("openBracket" ))		// previous type does not equal openBracket
						return index;
					break;
				case "int":
					if (previousToken.equals("openBracket" ))
						return index;
					break;
				case "float":
					if (previousToken.equals("openBracket" ))
						return index;
					break;
				case "string":
					if (previousToken.equals("openBracket" ) )
						return index;
					break;
				case "closedBracket":
					if (previousToken.equals("openBracket" ))
						return index;
					break;
				case "openBracket":
					if (previousToken.equals("openBracket" ))
						return index;
					break;
			
			}
			previousToken = currentToken;
		}
		return -1;
	}

}