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
    	String parameter;
    	String returnType;
    	
    	for (int i = 0; i < methods.length; i++) {
    		methodName = methods[i].getName();
    		parameters = methods[i].getParameterTypes();
    		returnType = methods[i].getReturnType().getName();
    		
    		functionList += "("  + methodName;
    		
			for (int j = 0; j < parameters.length; j++) {
    			parameter = parameters[j].getName();
    			functionList += " " + parameter;
    		}
			
    		functionList += ") : " + returnType + "\n";
    	}
    	
    	System.out.println(functionList);
    }

}