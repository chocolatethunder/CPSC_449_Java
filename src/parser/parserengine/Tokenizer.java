package parser;

import java.lang.reflect.Method;
import java.util.ArrayList;

import static parser.Utilities.*;

/**
 * Creates an ArrayList<Token> list from a string. Each token contains the name and
 * type. They type consists of int, float, string, identifier, openBracket, closedBracket,
 * unidentified.
 */
public class Tokenizer {
	
	String input = "";
	ArrayList<Token> tokens = new ArrayList<Token>();
	int tokenPosition = 0;
	Class jarLoad;
    
	/**
	 * @param input - String representing the command line input
	 * @param jarLoad - Class representing the class under consideration
	 */
	public Tokenizer (String input, JarFileLoader jarLoad ) {
		this.input = input;
        this.jarLoad = jarLoad.getLoadedClass();
		
		try {
			// If the preliminary checks are good then continue
			// to make a token list.
			if (this.runPrelimChecks(input)) {				
				// makes token list
				this.makeTokenList();				
			}
		} catch (Exception e) { /*Errors handled elsewhere*/ }
	}
     
    /**
	 * Makes a list of tokens that encodes the name and type of each character. 
	 */
	public void makeTokenList() {
		tokens = new ArrayList<Token>();
		
		String subExpression = "";
		// index for each token, used in error tracing
		int tokenPosition = 0;	
		
		for (int i = 0; i < input.length(); i++) {
			
			char j = input.charAt( i );
			
			switch ( j ) {
				case '(':
					tokens.add(new Token((j + ""), char.class, "openBracket", tokenPosition++));	// converted into string
					break;
				case ' ':
					if (subExpression.length() > 0) {
						Class type = getType(subExpression, jarLoad);				// get type
						String stringType = getStringType(subExpression, jarLoad);				// get type
						tokens.add(new Token (subExpression, type, stringType, tokenPosition++));		// add token if expression is not empty
					}
					subExpression = "";									// reset subExpression
					break;
				case ')':
					if (subExpression.length() > 0) {						// if there is an expression, add it 
						Class type = getType(subExpression, jarLoad);				// get type
						String stringType = getStringType(subExpression, jarLoad);
						tokens.add(new Token (subExpression, type, stringType, tokenPosition++));		// add subExpression before ')'
					}
					subExpression = "";									// reset subExpression
					tokens.add(new Token((j + ""), char.class, "closedBracket", tokenPosition++));	// converted into string
					break;
					
				case '"':
					do {
						subExpression += (j + "");								// add chacacter to expression
						j = input.charAt( ++i );							// increment character
					} while (j != '"' ); 									// keep adding until second " is seen
					
					subExpression += (j + "");								// add closing "" to expression
					j = input.charAt( i );							// increment character
					break;
				default:
					subExpression += (j + "");								// add character to expression
					break;
			}	
		}
	}
	
	/**
	 * @return - ArrayList<Token> representing the list of tokens created from the command-line input
	 */
	public ArrayList<Token> getTokens() {
		return this.tokens;		
	}
	
    /**
     * Checks the command-line input for various syntax errors.
     * @param input - String representing the command-line input
     * @return - Boolean representing if the input checks passed
     */
	public boolean runPrelimChecks(String input) throws Exception {		
		
		// check even number of brackets
		if (checkBrackets(input) == true) {
			
			// if the input begins with a '('
			if (characterCount(input, '(') != 0) {
				
				return true;}
		
			
            // deals with literals
			 else {
                
                // If no brackets needs checks to see if it is a int float or string,
                if (isInt(input) || isFloat(input)) {
                    System.out.println(input);
                } else if (isString(input)) {
                    System.out.println(input.substring(1, input.length()-1));
                    
                } else {
                	throw new ParserException("Unexpected character encountered", 0, input);
                }
			 }
		}
			
		else {
			throw new ParserException("Mis-matched brackets encountered", 0, input);
		}
		
		
		return false;
	}
}
