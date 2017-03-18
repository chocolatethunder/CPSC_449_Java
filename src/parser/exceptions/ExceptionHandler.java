/************************************************************************************
 * CPSC 449 - Winter 2017															*
 * Prof: Rob Kremer																	*
 * Assignment: Java																	*
 * Group #: 32																		*
 * Members: Saurabh Tomar, Kaylee Stelter, Kowther Hassan, Matthew Mullins, Tsz Lam	*
 * Description:																		*
 * 		- Parent exception handler for all fatal and non-fatal program errors		*
 *  	  																			*
 * Contain methods:																	*
 * 		+ExceptionHandler() <- Constructor											*
 * 		+ExceptionHandler(String) <- Constructor									*			
 *		+toggleVerbose()															*
 * 		+isVerbose():boolean														*
 * 		+printSynopsis(boolean)														*
 * 																					*
 ************************************************************************************/

package parser;

import java.util.*;
import java.lang.*;
import java.io.*;
import java.net.*;

/**
 * Fatal and non-fatal error handling
 * When an exception is thrown in the program, it is directed to the appropriate class which extends this class
 * Writes error code to syserror and then exits the program
 */
public class ExceptionHandler extends Exception {
    
    private static boolean verbose = false;
    
    public ExceptionHandler() {        
    }
    
    public ExceptionHandler(String message) {
        super(message);
    }
	
	// Toggles Verbose mode
	public static void toggleVerbose() {
		if (ExceptionHandler.verbose) {
			ExceptionHandler.verbose = false;
		} else {
			ExceptionHandler.verbose = true;
		}
	}

    // Returns whether verbose mode is enabled/disabled (set by the user at command-line)
    public static boolean isVerbose() {
        return ExceptionHandler.verbose;
    }
    
    /**
     * @param full - this checks to see if the extra line of help needs to be printed or not. 
     */
    public static void printSynopsis(boolean full) {
        try {  
        	
            // Get the file name to dynamically show the user the name if the jar file that is 
            // excecuting the code. Hard coding is not a good idea incase the name of the jar file
            // is changed or altered. 
            
            // Get the file name path
            String path = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            String decodedPath = URLDecoder.decode(path, "UTF-8");
            // Get the name of the jar file with extension
            File f = new File(decodedPath);
            String filename = f.getName();
            // Remove the extension
            int pos = filename.lastIndexOf(".");
            String justName = pos > 0 ? filename.substring(0, pos) : filename;
            
            // Print the Synopsis
            System.err.println(
				"Synopsis:\n" +
				"  " + justName + "\n" +
				"  " + justName + " { -h | -? | --help }+\n" +
				"  " + justName + " {-v --verbose}* <jar-file> [<class-name>]\n" +
				"Arguments:\n" +
				"  <jar-file>:   The .jar file that contains the class to load (see next line).\n" +
				"  <class-name>: The fully qualified class name containing public static command methods to call. [Default="+"Commands"+"]\n" +
				"Qualifiers:\n" +
				"  -v --verbose: Print out detailed errors, warning, and tracking.\n" +
				"  -h -? --help: Print out a detailed help message.\n" +
				"Single-char qualifiers may be grouped; long qualifiers may be truncated to unique prefixes and are not case sensitive.\n"
				);
            
            // Print this if there are no arguments provided.
            if (full) {
                System.err.println("This program interprets commands of the format '(<method> {arg}*)' on the command line, finds corresponding methods in <class-name>, and executes them, printing the result to sysout.");
            }
			
			System.exit(0);
            
        } catch (Exception e) {
            
        }
        
    }

}