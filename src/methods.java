import java.util.*;

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
    //In-progress
    public T getValueType(String s)
    {
        char[] int_list = new char[]{'0','1','2','3','4','5','6','7','8','9'};
        Class c = null;
        char som = s.charAt(0);
        
        boolean isString = false;
        boolean isInt = false;
        boolean isFloat = false;
        
        isInt = validInt(s);
        isString = validString(s);
        isFloat = validFloat(s);
        
        if(isInt)
        {
            return int.getClass();
        }
        if(isString)
        {
            return String.getClass();
        }
        if(isFloat)
        {
            return float.getClass();
        }
        return null; // if all false, prompt invalid input || exception. for now settle as null
    }

    public boolean isMethod(String s){}
    
    public int getParamNumber(String s){}
    
    public T getReturnType(String s){}
    
    public boolean checkBrackets (String s)
    {
        int counter = 0;
        
        for (int i = 0; i < s.length(); i++)
        {
            if (s.charAt(i) == '(')
            {
                counter++;
            }
            if (s.charAt(i) == ')')
            {
                counter--;
            }
        }
        
        if (counter == 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    public boolean integerOutOfBounds(String s){}
    
    public boolean floatOutOfBounds(String s){}
    
    //DONE
    public boolean isInt(String s)
    {
        char[] int_list = new char[]{'0','1','2','3','4','5','6','7','8','9'};
        boolean valid = false;
        boolean isInt = false;
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
                return false;
            }
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
        if (isFloat)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    //DONE
    public boolean isString(String s)
    {
        boolean valid_start = false;
        boolean valid_end = false;
        for (int i = 0; i < s.length(); i++)
        {
            if (s.charAt(i) == '"' && i == 0)
            {
                valid_start = true;
            }
            if (s.charAt(i) == '"' && i != 0 && i != (s.length() - 1))
            {
                return false;
            }
            if (s.charAt(i) == '"' && i == (s.length() - 1))
            {
                valid_end = true;
            }
        }
        if (valid_start && valid_end)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    //In-progress
    public T convert(String s, T valueType)
    {
        if (valueType.Class == s.Class)
        {
            String result = s.substring(1, s.length() - 2);
            return result;
        }
        if (valueType.Class == int.Class)
        {
            int result = Integer.parseInt(s);
            return result;
        }
        if (valueType.Class == float.Class)
        {
            float result = Float.parseFloat(s);
            return result;
        }
    }
}

































