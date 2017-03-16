package parser;

import java.util.*;

/**
 * Handles when non-fatal errors are thrown.
 * The error message provided is print to sysout, alongside an error pointer which points to the index at which the error
 * occurred in the input provided by the user
 */
public class ParserException extends ExceptionHandler {
    
	/**
	 * @param errMsg - error message to output
	 * @param offset - offset required for printing the error pointer
	 * @param cmd - erroneous user input from the command line
	 * @param e - exception which was thrown, used to print appropriate stack trace in verbose mode
	 */
    public ParserException(String errMsg, int offset, String cmd) {
        
    	// Error message
        System.out.println(errMsg + " at offset " + offset);
        // Original input from user
        System.out.println(cmd);
        
        // Aligns error pointer for display
        while (offset > 0) {
            System.out.print("-");
            offset--;
        }
        System.out.print("^\n");
        
        // Prints a stack trace if verbose mode was enabled by the user
        if (ExceptionHandler.isVerbose() == true) {
            printStackTrace();
        }
        
    }

}