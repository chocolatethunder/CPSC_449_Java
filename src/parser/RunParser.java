/************************************************************************************
 * CPSC 449 - Winter 2017															*
 * Prof: Rob Kremer																	*
 * Assignment: Java																	*
 * Group #: 32																		*
 * Members: Saurabh Tomar, Kaylee Stelter, Kowther Hassan, Matthew Mullins, Tsz Lam	*
 * Description:																		*
 * 		-Contains various methods which parse the command-line input.				*
 * 																					*
 * Methods:																			*			
 * 		+RunParser(JarFileLoader) <- Constructor									*
 * 		-printStartup():void														*
 * 																					*
 ************************************************************************************/

package parser;

import java.io.*;
import java.util.*;
import java.lang.*;
import java.text.*;

import static parser.Utilities.*;

public class RunParser {

	private boolean quit = false;
	private String userInput = "";
    private String input = "";
	ArrayList<Character> allowedQuantifierStarts = new ArrayList<Character>(Arrays.asList('q','v','f','?'));

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
			userInput = reader.nextLine();  // used to parse expression
			
			// Process
			
			// Trim leading and trailing spaces
			input = userInput.trim();
			
			// Capture the first character
			char meta = input.charAt(0);
			
			// Either a meta command has been entered
			if (input.length() == 1 && allowedQuantifierStarts.contains(meta)) {
				
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
					
					// Print Help
					case '?':
					this.printStartup();
					break;
					
					default:
					break;	
				}
			}
			
			// or an expression is being entered
			else  {				
	
				try {
					
					// Tokenize
					Tokenizer tokenizer = new Tokenizer(input, jarLoad);
					
					// Check if the order of tokens is correct
					if (checkOrderOfTokens(tokenizer.getTokens()) == -1 && tokenizer.isOkayToParse()) {
				
						// Construct Parse Trees. We must construct additional parsetrees!!
						ParseTreeConstructor parseTree = new ParseTreeConstructor(tokenizer);
						
						// Evaluate expression
						Evaluator evaluator = new Evaluator(parseTree);
						
                        // parse tree 
                        evaluator.parse(evaluator.getParseTree(), jarLoad.getLoadedClass());
                         
                        // print result 
						System.out.println(evaluator.toString());
					}	
				} catch (Exception e) { /*Do nothing*/ }
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
