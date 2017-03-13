package parser;

import java.io.*;
import java.util.*;
import java.lang.*;
import java.text.*;
import java.net.*;

import parser.exceptions.*;

/**
 * Checks the command-line input for various conditions prior to running the Parser
 * Throws various fatal error exceptions where appropriate
 */
public class LoadParser {
	
	/**
	 * @param args - represents command-line arguments
	 */
	public LoadParser(String[] args) throws Exception {
		
		
	   // Functional Requirement 1.1 and 1.2
	   
	   // flag represents what qualifier letter was chosen
	   int i = 0, j = 0, k = 0;		
	   char flag;
	   String arg;
	   String jarFile;
	   
	   
	   // Checks the command-line input for the length of the input as long as it begins with "-" (proper format)
	   while (i < args.length && args[i].startsWith("-")) {
		   
		   // current argument
		   arg = args[i];
		   
		   // check all the FAT arguments first
			if (arg.startsWith("--")) {				  
				
				// Check to see if help is called without any arguments
				if (arg.equals("--help")) {
					
					// this takes care of the { }* where there can be more than one --help punched in
					while(i < args.length && ( args[i].equals("--help") || args[i].equals("--verbose") )) {
						i++;
					}
					
					// if there no extra ARGS left then print synopsis else throw <Exception>				
					if ((i+1) > args.length) {
						ExceptionHandler.printSynopsis(true);	
					} else {
						throw new UnexpectedHelpQualifier();		
					}
					
				// check if verbose mode is set
				} else if (arg.equals("--verbose")) { 
					// Activate verbose mode
					ExceptionHandler.verboseOn();
					
					// this takes care of the { }* where there can be more than one --verbose punched in
					while(i < args.length && args[i].equals("--verbose")) {
						i++;
					}
					
					// check if the next argument is actually a jar file otherwise throw <Exception>
					if ((i+1) > args.length) {
					   // pass to jarLoader and launch program *********
					   jarFile = args[i++];
					} else {
						throw new NoJarFileGiven();
					}
					
				} else {
					// default is an <error>	
					// Invalid FAT qualifier entered
					throw new UnrecognizedQualifier(arg);
				}
		
		   // Checking short qualifiers (input starts with "-", but not "--")
		   } else {				  
			   
			   // check all the flags 
			   for (j = 1; j < arg.length(); j++) {					  
				   
					flag = arg.charAt(j);
					
					switch (flag) {
					   
						// help
						case 'h':
						case '?':
						
							// this takes care of the { }* where there can be more than one -h or -? punched in
							while(j < arg.length() && (arg.charAt(j) == 'h' || arg.charAt(j) == '?' || arg.charAt(j) == 'v')) {
								j++;
							}
							
							// if there no extra FLAGS left then print synopsis else throw <Exception>							
							if ((j+1) > (arg.length())) {
								ExceptionHandler.printSynopsis(true);
							} else {
								throw new UnexpectedHelpQualifier();
							}							
							break;
							 
						// verbose flag
						case 'v':
							// this takes care of the { }* where there can be more than one -v punched in
							while(j < arg.length() && flag == 'v') {
								j++;
							}

							// check if the next argument is actually a jar file
							if ((i+1) > args.length) {
							   // pass to jarLoader and launch program *********
							   jarFile = args[i++];
							} else {
								throw new NoJarFileGiven();
							}
							break;
						   
						// unknown flag <error>
						// If the short qualifier is invalid
						default:		
							throw new UnrecognizedQualifier(flag, arg);
					}
			   }
		   }
		   i++;
	   }
		
	   // Functional Requirement 1.3
	   if (args.length == 0) {
			ExceptionHandler.printSynopsis(false);
	   }
	   
	   // Enter some of the more finer checks
	   // Like what?
	   
	   // Check to see if there are at most 2 arguments given
	   try {
		   this.checkNumArguments(args);
	   } catch (Exception e) { }	   
	}
	
	/**
	 * @param args - represents the command-line arguments provided by the user
	 */
	public void checkNumArguments(String[] args) throws MoreThanTwoCommandsGiven {
		
		// Error checking		
		int numArguments = 0;

		for (String arg : args) {
		   if (arg.charAt(0) != '-') {
			   numArguments++;
		   }
		}
		
		// too many arguments throw <Exception>
		if (numArguments > 2) {
		   throw new MoreThanTwoCommandsGiven();
		}
	}
}
