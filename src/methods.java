import java.util.*;
import java.lang.*;
import java.lang.reflect.Type;
import java.net.*;

public class methods {
	public static void main (String args[]){
		boolean shouldQuit = false;
		boolean verboseMode = false;
		
		TreeNode parseTree;
		
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
	
    //DONE
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
        //return c; // if all false, prompt invalid input || exception. for now settle as null
    }

    //TODO
    public boolean isMethod(String s)
    {
        return false;//check and see if s is a valid method
    }
    
    //TODO
    public int getParamNumber(String s)
    {
        return 0;//get acceptable number of arguments
    }
    
    //TODO
    public Type getReturnType(String s)
    {
        return int.class;//check the return type of method s
    }
    
    //DONE
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
    
    //DONE
    public boolean integerOutOfBounds(String s)
    {
        // TODO
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
    }
    
    //DONE
    public boolean floatOutOfBounds(String s)
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
    }
    
    //DONE
    public boolean isInt(String s)
    {
        char[] int_list = new char[]{'0','1','2','3','4','5','6','7','8','9'};
        boolean valid = false;
        for (int i = 0; i < s.length(); i++)
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
    
    //DONE
    public boolean isFloat(String s)
    {
        char[] int_list = new char[]{'0','1','2','3','4','5','6','7','8','9'};
        char dot = '.';
        boolean valid = false;
        boolean isFloat = false;
        boolean repeat = false;
        for (int i = 0; i < s.length(); i++)
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
                if (s.charAt(i) == dot && !repeat)
                {
                    valid = true;
                    isFloat = true;
                    repeat = true;
                }
                else
                {
                    return false;
                }
            }
            valid = false;
        }
        if (isFloat) { return true; }
        else { return false; }
    }
    
    //DONE
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
            int result = (int) Integer.parseInt(s);
            return result;
        }
        if (testSub == float.class)
        {
            float result = Float.parseFloat(s);
            return result;
        }
        return null;
    }
}

































