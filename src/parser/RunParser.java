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
			input = reader.nextLine();  // used to parse expression
			
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
	
					try {
						
						// Tokenize
						Tokenizer tokenizer = new Tokenizer(input, jarLoad);
						
						// Check if the order of tokens is corret
						if (checkOrderOfTokens(tokenizer.getTokens()) == -1) {
					
							// Construct Parse Trees. We must construct additional parsetrees!!
							ParseTreeConstructor parseTree = new ParseTreeConstructor(tokenizer);
							
							// Evaluate expression
							Evaluator evaluator = new Evaluator(parseTree);
							
							/* This line needs to go. I need a nice clean evaluator.toString() to generate the output here.
							 * Please go into you Evaluator.java file and take the result variable on line 62 and pass it to
							 * the toString() method at the end. Remove this comment upon completion. 
							 */
							// System.out.println(evaluator.parse(evaluator.getParseTree(), jarLoad.getLoadedClass()).getData().getName());
						
						}
						
					} catch (Exception e) { /*Do nothing*/ }

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
