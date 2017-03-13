package parser;

import java.io.*;
import java.util.*;
import java.lang.*;
import java.text.*;
import java.net.*;


/**
 * Checks the command-line input for various conditions prior to running the Parser
 * Throws various fatal error exceptions where appropriate
 */
public class LoadParser {
	
	private List<String> arguments	= new ArrayList<String>();
	private List<String> flags		= new ArrayList<String>();
	private List<String> longFlags 	= new ArrayList<String>();
	private String[] cmdLnArgS;
	
	/**
	 * @param args - represents command-line arguments
	 */
	public LoadParser(String[] args) throws Exception {
		
		// make a local copy of the command line arguments
		cmdLnArgS = args.clone();
		
		// Functional Requirement 1.1 and 1.2
		int i = 0, j = 0, k = 0;		
		char flag;
		String cmdLnArg;
		String jarFile;		
		
		
		
		// Collect all the args, flags, and longFlags		
		while (i < this.cmdLnArgS.length) {
			
			cmdLnArg = this.cmdLnArgS[i];
			
			// collect --flags
			if (cmdLnArg.startsWith("--")) {				
				if (cmdLnArg.length() > 2) {					
					longFlags.add(cmdLnArg);					
				} else {
					throw new UnrecognizedQualifier(cmdLnArg);
				}
			// collect -flag
			} else if (cmdLnArg.startsWith("-")) {				
				if (cmdLnArg.length() > 1) {
					flags.add(cmdLnArg);
				} else {
					throw new UnrecognizedQualifier(' ', cmdLnArg);
				}
			// collect arguments
			} else {
				arguments.add(cmdLnArg);
			}
			
			i++;			
		}
		
		// remove duplicate flags and long flags
		this.removeDuplicateFlags();
		
		// Then do checks in order of priority. Order matters here.
		
		// Run flag compatibility (i.e. can some flags be allowed with some others)
		
		// Check if there are only at most 2 arguments
		this.checkNumOfArguments();
		
		// Functional Requirement 1.3: No qualifiers or arguments
		this.checkForNoArguments();
		
	}
	
	private void removeDuplicateFlags() {		
		this.flags 		= new ArrayList<String>(new LinkedHashSet<String>(this.flags));
		this.longFlags 	= new ArrayList<String>(new LinkedHashSet<String>(this.longFlags));	
	}
	
	private void checkNumOfArguments() throws MoreThanTwoCommandsGiven {
		if (this.arguments.size() > 2) {
			throw new MoreThanTwoCommandsGiven();
		}
	}
	
	private void checkForNoArguments() {
		if (this.cmdLnArgS.length == 0) {
			ExceptionHandler.printSynopsis(false);
		}
	}

}
