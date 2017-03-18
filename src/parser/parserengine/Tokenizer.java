/************************************************************************************
 * CPSC 449 - Winter 2017															*
 * Prof: Rob Kremer																	*
 * Assignment: Java																	*
 * Group #: 32																		*
 * Members: Saurabh Tomar, Kaylee Stelter, Kowther Hassan, Matthew Mullins, Tsz Lam	*
 * Description:																		*
 * 		- Creates an ArrayList list of Tokens from a string.                        *
 *  																				*
 * Contain methods:																	*
 *      + Tokenizer(String, JarFileLoader)                                          *
 *      + makeTokenList():void                                                      *
 *      + ArrayList<Token> getTokens():ArrayList<Token>                             *
 *      + isOkayToParse ():boolean                                                  *
 *      + runPrelimChecks(String):boolean                                           *
 *      + getInput():String                                                         *
 ************************************************************************************/

package parser;

import java.lang.reflect.Method;
import java.util.ArrayList;

import static parser.Utilities.*;

/**
 * Creates an ArrayList list from a string. Each token contains the name and
 * type. They type consists of int, float, string, identifier, openBracket, closedBracket,
 * unidentified.
 */
public class Tokenizer {
	
	String input = "";
	ArrayList<Token> tokens = new ArrayList<Token>();
	int tokenPosition = 0;
	Class jarLoad;
    private boolean okayToParse = true;
    
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
	 * @throws Exception - exception handling for reading string errors (mis-matched quotation marks)
	 */
	public void makeTokenList() throws Exception {
		tokens = new ArrayList<Token>();
		
		String subExpression = "";
		
		for (int i = 0; i < input.length(); i++) {
			
			char j = input.charAt( i );
			
			switch ( j ) {
				case '(':
					tokens.add(new Token((j + ""), char.class, "openBracket", i-1));	// converted into string
					break;
				case ' ':
					if (subExpression.length() > 0) {
						Class type = getType(subExpression, jarLoad);				// get type
						String stringType = getStringType(subExpression, jarLoad);				// get type
						tokens.add(new Token (subExpression, type, stringType, i-1));		// add token if expression is not empty
					}
					subExpression = "";									// reset subExpression
					break;
				case ')':
					if (subExpression.length() > 0) {						// if there is an expression, add it 
						Class type = getType(subExpression, jarLoad);				// get type
						String stringType = getStringType(subExpression, jarLoad);
						tokens.add(new Token (subExpression, type, stringType, i-1));		// add subExpression before ')'
					}
					subExpression = "";									// reset subExpression
					tokens.add(new Token((j + ""), char.class, "closedBracket", i-1));	// converted into string
					break;
					
				case '"':
                    int savedStartingIndex = i;
					do {
                       
						subExpression += (j + "");								// add chacacter to expression
						j = input.charAt( ++i );							// increment character
                        if (i == input.length()-1) {
                            this.okayToParse = false;
                            String message = "Encountered end-of-input while reading string beginning at offset " + savedStartingIndex;
                            throw new ParserException(message, input.length()-1, input);
                        } 
					} while (j != '"'); 									// keep adding until second " is seen
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
	 * @return - ArrayList representing the list of tokens created from the command-line input
	 */
	public ArrayList<Token> getTokens() {
		return this.tokens;		
	}
	
    /**
    *@return - boolean that represents whether an input is okay to parse. 
    */
    public boolean isOkayToParse () {
        return this.okayToParse;
    }
    /**
     * Checks the command-line input for various syntax errors.
     * @param input - String representing the command-line input
     * @return - Boolean representing if the input checks passed
     * @throws Exception - handles all thrown exception
     */
	public boolean runPrelimChecks(String input) throws Exception {		
		
		// check even number of brackets
		if (checkBrackets(input) == -1) {       // i.e not even number of brackets
			
			// if there are brackets
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
                	// prints out the location of the unexpected character
                	int errorIndex = 0;
                	
                	// loop through each character to make sure the error message will point at the right offset.
                	for (int i = 0; i < input.length(); i++){
                		String temp = String.valueOf(input.charAt(i));
                		if (isInt(temp)){
                			errorIndex++;
                		}
                	}
                	throw new ParserException("Unexpected character encountered", errorIndex, input);
                }
			 }
		}
			
		else {
			throw new ParserException("Mis-matched brackets encountered", checkBrackets(input), input);
		}
		
		
		return false;
	}
	
	/**
	 * @return - Returns the original input string derived from the command-line
	 */
	public String getInput(){
		return this.input;
	}
}
