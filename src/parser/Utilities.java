package parser;

import java.io.*;
import java.util.*;
import java.lang.*;
import java.lang.reflect.*;
import java.text.*;
import java.net.*;

public class Utilities {
	
	// The following method will print out every function, its parameters' type, and return type
    public static void printFunctionList(Class cls) {
    	String functionList = "";
    	
    	Method[] methods = cls.getDeclaredMethods();
    	String methodName;
    	
    	Class[] parameters = null;
    	String parameter = null;
    	String returnType = null;
    	
    	for (int i = 0; i < methods.length; i++) {
    		methodName = methods[i].getName();
    		parameters = methods[i].getParameterTypes();
    		Object temp = methods[i].getReturnType();
    		if (temp == String.class)
    		{
    			returnType = "string";
    		}
    		else if (temp == int.class)
    		{
    			returnType = "int";
    		}
    		else if (temp == float.class)
    		{
    			returnType = "float";
    		}
    		temp = null;
    		
    		functionList += "("  + methodName;
    		
			for (int j = 0; j < parameters.length; j++) {
    			temp = parameters[j];
    			if (temp == String.class)
        		{
    				functionList += " " + "string";
        		}
        		else if (temp == int.class)
        		{
        			functionList += " " + "int";
        		}
        		else if (temp == float.class)
        		{
        			functionList += " " + "float";
        		}
    			//functionList += " " + parameter;
    		}
			
    		functionList += ") : " + returnType + "\n";
    	}
    	
    	System.out.println(functionList);
    }

}