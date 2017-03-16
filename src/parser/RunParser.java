package parser;

import java.io.*;
import java.util.*;
import java.lang.*;
import java.text.*;

import static parser.Utilities.*;

/**
 * Contains various methods which parse the command-line input.
 */
public class RunParser {

	private boolean quit = false;
	private String originalInput = "";
    private String input = "";
	ArrayList<Character> allowedExprStarts = new ArrayList<Character>(Arrays.asList('(','+','-','"'));

	/**
	 * Runs the parser on the command-line input
	 * @param jarLoad - Represents the loaded jar file
	 */
	public RunParser(JarFileLoader jarLoad) {
		
		Scanner reader = new Scanner(System.in);
		
		// Print start message
		this.printStartup();

		while (!quit) {
			
			// Print prompt
			System.out.print("> ");
			
			// Capture input
			originalInput = reader.nextLine();  // used to parse expression
			input = originalInput;
			// Process
			
			// Trim leading and trailing spaces
			input.trim();
			
			// Capture the first character
			char meta = input.charAt(0);
			
			// Either a meta command has been entered
			if (input.length() == 1) {
				
				// Process meta commands
				switch(meta) {
					
					// Quit
					case 'q':
					this.quit = true;
					System.out.println("bye.");
					break;
					
					// Verbose
					case 'v':
					ExceptionHandler.toggleVerbose();
					if (ExceptionHandler.isVerbose()) {
						System.out.println("Verbose on");
					} else {
						System.out.println("Verbose off");
					}
					break;
					
					// Function List
					case 'f':
					printFunctionList(jarLoad.getLoadedClass());
					break;
					
					// Pring Help
					case '?':
					this.printStartup();
					break;
					
					default:
					break;
					
				}
				
			} else {
				
				// or an expression is being entered
				if (allowedExprStarts.contains(meta)) {
					
					/**** ENTRY INTO THE CODE ****/
                    
					// Tokenize
                    try {
                        Tokenizer tokenizer = new Tokenizer(originalInput, jarLoad.getLoadedClass());
                        
                        // Construct Parse Trees. We must construct additional parsetrees!!
                        ParseTreeConstructor parseTree = new ParseTreeConstructor(tokenizer);
                        
                      
                       
                       if (checkBrackets(originalInput) == true) {		// check even number of brackets
                            if (characterCount(originalInput, '(') != 0) {
                                //Tokenizer tokenizer = new Tokenizer(input, jarLoad.getLoadedClass());
                                if (checkOrderOfTokens(tokenizer.getTokens()) == -1) {		// no errors 
                                    // Construct Parse Trees. We must construct additional parsetrees!!
                                    ParseTreeConstructor parseTreeConstructor = new ParseTreeConstructor(tokenizer);
                        
                                    // Evaluate expression
                                    Evaluator evaluator = new Evaluator(parseTreeConstructor);
                    
                                    
                                    System.out.println(evaluator.parse(evaluator.getParseTree(), jarLoad.getLoadedClass()).getData().getName());;
                                } else
                                    System.out.println("there is an error in format at index: " + checkOrderOfTokens(tokenizer.getTokens()) );	
                    
                
                            } else if (characterCount(originalInput, '(') == 0) {
                                System.out.println("no brackets");
                            }
                
                        } else {
                            System.out.println("no matching brackets");
                            }
                    
                    } catch (Exception e) {
                        // NOT SURE WHAT TO DO HERE
                    }
                    
					
					
					
					
					 
					
					/**** EXIT FROM THE CODE ****/
					
				}
				
			}		
			
		}

	}
    
	/**
	 * Prints the appropriate start-up messages to the console
	 */
	private void printStartup(){
		System.out.println(
		"q           : Quit the program.\n" +
		"v           : Toggle verbose mode (stack traces).\n" +
		"f           : List all known functions.\n" +
		"?           : Print this helpful text.\n" +
		"<expression>: Evaluate the expression.\n" +
		"Expressions can be integers, floats, strings (surrounded in double quotes) or function calls of the form '(identifier {expression}*)'."
		);
	}    
    
    
}
