import java.util.Scanner;

/**
 * Gets user input from console.
 * @author Kowther
 *
 */
public class UserInput {
	//Create a new Scanner object
	private Scanner keyboard;

	
	public UserInput() {
		
		keyboard = new Scanner(System.in);
	}
	
	public String getUserInput() {
		
		return keyboard.nextLine();
		
	}
	
	public void closeConnection() {
		
		keyboard.close();
		
	}
	

	/**
	 * checks if the number of brackets match, i.e. the # of '(' matches the # of ')'
	 * @param s
	 * @return boolean
	 */
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
    
    
    /**
     * Takes a string and a character, and return the number of times the character
     * appears in the string.
     * @param s
     * @param c
     * @return
     */
    public int characterCount ( String s, char c ) {
    	int counter = 0;
    	
    	for (int i = 0; i < s.length(); i++)
        {
            if (s.charAt(i) == c) { counter++; }
        }
        
    	return counter;
    }
    
    /**
     * Takes a string and a character, and returns true if that character appears
     * consecutively in the string and false otherwise.
     * @param s
     * @param c
     * @return
     */
    public boolean twoConsecutive ( String s, char c ) {
    	char currentChar, nextChar;
    	currentChar = s.charAt(0);		// set current char to first character of s
    	
    	for ( int i = 1; i < s.length(); i++ ) {
    		nextChar = s.charAt(i);
    		if ( currentChar == c && nextChar == c) {
    			return true;
    		}
    		currentChar = nextChar;
    	}
    	return false;
    }
    
}
