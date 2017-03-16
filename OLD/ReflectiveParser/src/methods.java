import java.util.*;
import java.lang.*;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.net.*;

public class methods {
	public static void main (String args[]){
		boolean shouldQuit = false;
		boolean verboseMode = false;
		
		
		
		//Create a new Scanner object
		Scanner userInput = new Scanner(System.in);
		
		//Continuous while loop until told to exit
		while (shouldQuit == false){
			//TODO: Prompt user for command, will be removed later
			System.out.println("Please enter a command:");
			//Reads user input and stores as a string
			String inputCommand = userInput.next();
			//Simple switch to detect single character commands
			switch (inputCommand){
			case "q":
				shouldQuit = true;
				break;
			case "v":
				if (verboseMode)
					verboseMode = false;
				else 
					verboseMode = true;
				break;
			case "f":
				//TODO: This needs to display all know functions from the commands.jar file
				System.out.println("All known functions");
				break;
			case "?":
				break;
			default:
				//TODO: This is where we create the parse tree
				break;
			
			}
		}
	}
	
    //DONE (Tested)
	// The following method will return the type of the argument
    public Type getValueType(String s)
    {
        boolean isString = false;
        boolean isInt = false;
        boolean isFloat = false;
        
        isInt = isInt(s);
        isString = isString(s);
        isFloat = isFloat(s);
        
        if(isInt) { return int.class; }
        if(isString) { return String.class; }
        if(isFloat) { return float.class; }
        
        return null;
        //return c;
        // if all false, prompt invalid input || exception.
        //for now settle as null
    }

    //DONE (Not Tested)
	// The following method will check and see if user input method is valid
    public boolean isMethod(String s, Object o)
    {
    	//check and see if s is a valid method
    	Class cls = o.getClass();
    	Method[] methods = cls.getMethods();
    	if (!validName(s)) { return false; }
    	
    	for (int i = 0; i < methods.length; i++)
    	{
    		String method = methods[i].getName();
    		if (method == s)
    		{
    			return true;
    		}
    	}
    	return false;
    }
    
    //DONE (Not Tested)
    // This method is used to check if the name is valid to begin with
    // NOTE: this method does not show if the name is a valid method
    // Valid: add addd adddddd
    // Invalid: ad'd d'iv id"k
    public boolean validName(String s)
    {
    	char[] testSubject = s.toCharArray();
    	for (int i = 0; i < testSubject.length; i++)
    	{
    		if(!Character.isLetter(testSubject[i]))
    		{
    			return false;
    		}
    	}
    	return true;
    }
    
    //DONE (Not Tested)
    // The following method will print out every function, its parameters' type, and return type
    public void printFunctionList(Object o)
    {
    	String functionList = "";
    	
    	Class cls = o.getClass();
    	Method[] methods = cls.getMethods();
    	String methodName;
    	
    	Class[] parameters = null;
    	String parameter;
    	String returnType;
    	
    	for (int i = 0; i < methods.length; i++)
    	{
    		methodName = methods[i].getName();
    		parameters = methods[i].getParameterTypes();
    		returnType = methods[i].getReturnType().getName();
    		
    		functionList += "("  + methodName;
    		for (int j = 0; j < parameters.length; j++)
    		{
    			parameter = parameters[j].getName();
    			functionList += " " + parameter;
    		}
    		functionList += ") : " + returnType + "\n";
    	}
    	
    	System.out.println(functionList);
    }
    
    //DONE (Not Tested)
	// The following method will check and
    // see how many arguments can be accepted by the method
    public int getParamNumber(String s, Object o)
    {
    	Class cls = o.getClass();
    	Method[] methods = cls.getMethods();
    	Class[] parameters = null;
    	for (int i = 0; i < methods.length; i++)
    	{
    		String method = methods[i].getName();
    		if (method == s)
    		{
    			parameters = methods[i].getParameterTypes();
    			return parameters.length;
    		}
    	}
        return 0;//get acceptable number of arguments
    }
    
    //DONE (Not Tested)
    // The following method will check if the argument type is valid for the method
    // type = argument type, s = method's name, o = jar file that contains all valid methods
    public boolean validParamType(Type type, String s, Object o, int index)
    {
    	Class cls = o.getClass();
    	Method[] methods = cls.getMethods();
    	Class[] parameters = null;
    	for (int i = 0; i < methods.length; i++)
    	{
    		String method = methods[i].getName();
    		if (method == s)
    		{
    			parameters = methods[i].getParameterTypes();
    			break;
    		}
    	}
    	if (type == parameters[index - 1])
    	{
    		return true;
    	}
    	return false;
    }
    
    //DONE (Not Tested)
    // The following method will check the proper return type of method
    public Type getReturnType(String s, Object o)
    {
    	Class cls = o.getClass();
    	Method[] methods = cls.getMethods();
    	Type returnType = null;
    	for (int i = 0; i < methods.length; i++)
    	{
    		String method = methods[i].getName();
    		if (method == s)
    		{
    			returnType = methods[i].getGenericReturnType();
    			return returnType;
    		}
    	}
        return null;//check the return type of method s
    }
    
    //DONE (Tested)
    // The following method will check if the # of '(' matches the # of ')'
    public boolean checkBrackets (String s)
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
    
    //DONE - Method not necessary, out of bound check done in Convert(), will comment out for now
 /*   public boolean integerOutOfBounds(String s)
    {
    	try
    	{
    		Object c = convert(s, getValueType(s));
    		return false;
    	}
    	//ArithmeticException
    	//NumberFormatException
    	catch (ArithmeticException e)
    	{
    		return true;
    	}
    }*/
    
    //DONE - Method not necessary, out of bound check done in Convert(), will comment out for now
/*    public boolean floatOutOfBounds(String s)
    {
    	try
    	{
    		Object c = convert(s, getValueType(s));
    		return false;
    	}
    	//ArithmeticException
    	//NumberFormatException
    	catch (ArithmeticException e)
    	{
    		return true;
    	}
    }*/
    
    //DONE (Tested)
    // The following method will check if the argument is an valid integer (int)
    public boolean isInt(String s)
    {
        char[] int_list = new char[]{'0','1','2','3','4','5','6','7','8','9'};
        boolean valid = false;
        int start = 0;
        if (s.charAt(0) == '-' || s.charAt(0) == '+')
        { start = 1; }
        
        for (int i = start; i < s.length(); i++)
        {
            for (int a = 0; a < int_list.length; a++)
            {
                if (s.charAt(i) == int_list[a])
                {
                    valid = true;
                    break;
                }
                
            }
            if (!valid) { return false; }
            valid = false;
        }
        return true;

    }
    
    //DONE (Tested)
    // The following method will check if the argument is a valid float number
    public boolean isFloat(String s)
    {
        char[] int_list = new char[]{'0','1','2','3','4','5','6','7','8','9'};
        char dot = '.';
        boolean valid = false;
        boolean isFloat = false;
        boolean repeat = false;
        
        int start = 0;
        if (s.charAt(0) == '-' || s.charAt(0) == '+')
        { start = 1; }
        
        for (int i = start; i < s.length(); i++)
        {
            for (int a = 0; a < int_list.length; a++)
            {
                if (s.charAt(i) == int_list[a])
                {
                    valid = true;
                    break;
                }
                
            }
            if (!valid)
            {
                if (s.charAt(i) == dot && !repeat && i != 0)
                {
                    valid = true;
                    isFloat = true;
                    repeat = true;
                }
                else { return false; }
            }
            valid = false;
        }
        if (isFloat) { return true; }
        else { return false; }
    }
    
    //DONE (Tested)
    // The following method will check if the argument is a valid string
    public boolean isString(String s)
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
    
    //DONE
    // The following method will take the argument
    // and convert it into the desired type
    public Object convert(String s, Type valueType)
    {
        Type testSub = valueType;
        
        if (testSub == s.getClass())
        {
            String result = s.substring(1, s.length() - 2);
            return result;
        }
        if (testSub == int.class)
        {
        	try{
        		int temp;
        		int result;
        		
        		if (s.charAt(0) == '-')
        		{
        			temp = (int) Integer.parseInt(s.substring(1, s.length() - 1));
        			result = 0 - temp;
        		}

        		else if (s.charAt(0) == '+')
        		{ result = (int) Integer.parseInt(s.substring(1, s.length() - 1)); }
        		
        		else { result = (int) Integer.parseInt(s); }
        		
        		return result;
            }
            catch (ArithmeticException e)
        	{
            	System.out.println("Int out of range!");
            	System.exit(0);
        	}
        }
        if (testSub == float.class)
        {
        	try {
        		float temp;
        		float result;
        		
        		if (s.charAt(0) == '-')
        		{
        			temp = Float.parseFloat(s.substring(1, s.length() - 1));
        			result = 0 - temp;
        		}
        		
        		else if (s.charAt(0) == '+')
        		{ result = Float.parseFloat(s.substring(1, s.length() - 1)); }
        		
        		else{ result = Float.parseFloat(s); }
        		
        		return result;
        	}
        	catch (ArithmeticException e) {
        		System.out.println("Float out of range!");
        		System.exit(0);
        	}
        }
        return null;
    }
}

































