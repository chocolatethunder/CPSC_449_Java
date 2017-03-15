import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Creates an ArrayList<Token> list from a string. Each token contains the name and
 * type. They type consists of int, float, string, identifier, openBracket, closedBracket
 *
 */
public class Tokenizer {
	String input;
	ArrayList<Token> tokens;


	/**
	 *  constructor takes in an input string to be tokenized
	 * @param input
	 */
	public Tokenizer ( String input ) {
		
		this.input = input;
		makeTokenList();		// makes token list
		
	}
	
	/**
	 * takes a String and makes a list of tokens that encodes the
	 * name and type of each character. 
	 */
	public void makeTokenList() {
		tokens = new ArrayList<Token>();
		
		String expression = "";
		int index = 0;	// index for each token. Can be used later for error tracing. 
		
		for (int i = 0; i < input.length(); i++) {
			
			char j = input.charAt( i );
			switch ( j ) {
				case '(':
					
					tokens.add(new Token((j + ""), char.class, "openBracket", index++));	// converted into string
					break;
				case ' ':
					if (expression.length() > 0) {
						Class type = getType(expression);				// get type
						String stringType = getStringType(expression);				// get type
						tokens.add(new Token (expression, type, stringType, index++));		// add token if expression is not empty
					}
					expression = "";									// reset expression
					break;
				case ')':
					if (expression.length() > 0) {						// if there is an expression, add it 
						Class type = getType(expression);				// get type
						String stringType = getStringType(expression);
						tokens.add(new Token (expression, type, stringType, index++));		// add expression before ')'
					}
					expression = "";									// reset expression
					tokens.add(new Token((j + ""), char.class, "closedBracket", index++));	// converted into string
					break;
					
				case '"':
					do {
						expression += (j + "");								// add chacacter to expression
						j = input.charAt( ++i );							// increment character
					} while (j != '"' ); 									// keep adding until second " is seen
					
					expression += (j + "");								// add closing "" to expression
					j = input.charAt( i );							// increment character
					break;
				default:
					expression += (j + "");								// add character to expression
					break;
			}	
		}
	}
	
	/**
	 * getter for token list
	 * @return
	 */
	public ArrayList<Token> getTokens() {
		return tokens;
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
    
    
    /**
     *  The following method will check if the argument is a valid float number
     * @param s
     * @return
     */
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
    
    /**
     *  The following method will check if the argument is a valid string
     * @param s
     * @return
     */
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
	
	
	/**
	 * 
	 * If the ordering is not correct, returns the index where it is wrong
	 * else, returns -1
	 * @param input
	 * @return
	 */
	public int checkOrderOfTokens( ArrayList<Token> input ) {
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
	
	
	// test method for class
	public static void main(String[] args) {
	        
			Tokenizer tokenizer = new Tokenizer( "(add (add \"three three\" 2) 2)" );
	        
	        ArrayList<Token> tokenList = tokenizer.getTokens();
	        
	        System.out.println(tokenizer.checkOrderOfTokens(tokenizer.getTokens()));
	        
	       
	        for (int i = 0; i < tokenList.size(); i++ ){
	        	System.out.println("name: " + tokenList.get(i).getName() + " \ttype: " + tokenList.get(i).getStringType()+ " \tindex: " + tokenList.get(i).getIndex());
	        }
	   }
}
