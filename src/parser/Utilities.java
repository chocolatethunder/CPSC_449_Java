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
	
	public static boolean checkBrackets (String s) {
		
		Stack<Character> stack = new Stack<Character>();
		
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c == '(') {
				stack.push(c);
			} else if (c == ')') {
				if (stack.isEmpty()) { return false; }
				if (stack.pop() != '(') { return false; }
			}
		}
		
		return stack.isEmpty();
		
	}
	
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
	public Class getType (String s ) {
		
		boolean isString = false;
        boolean isInt = false;
        boolean isFloat = false;
        
        isInt = isInt(s);
        isString = isString(s);
        isFloat = isFloat(s);
        
        if(isInt)  return int.class; 
        if(isString) return String.class; 
        if(isFloat)  return float.class; 
        
		return Method.class;
	}
	
	/**
	 * get the type of an input.
	 * Types are string, int, float or identifier
	 * 
	 * @param s
	 * @return
	 */
	public String getStringType (String s ) {
		
		boolean isString = false;
        boolean isInt = false;
        boolean isFloat = false;
        
        isInt = isInt(s);
        isString = isString(s);
        isFloat = isFloat(s);
        
        if(isInt)  return "int"; 
        if(isString) return "string"; 
        if(isFloat)  return "float"; 
        
		return "identifier";
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

}