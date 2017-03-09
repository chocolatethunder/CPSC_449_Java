import java.util.Scanner;

/**
 * Gets user input from console.
 *
 */
public class UserInput {
	
	private Scanner keyboard;		//Create a new Scanner object

	
	/**
	 * constructor. Makes a new Scanner object that takes in input
	 */
	public UserInput() {
		
		keyboard = new Scanner(System.in);
	}
	
	/**
	 * Retrieves user input
	 * @return
	 */
	public String getUserInput() {
		
		return keyboard.nextLine();
		
	}
	
	/**
	 * closes the connection. 
	 */
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
    
    
}
