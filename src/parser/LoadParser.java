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
	
	private String[] cmdLnArgS;
	private String arg;
	private char flag;
	
	private boolean hflag = false;
	private boolean vflag = false;
	
	private String loadFile = "";
	private String loadClass = "Commands"; // Default
	
	
	/**
	 * @param args - represents command-line arguments
	 */
	public LoadParser(String[] args) throws Exception {
		
		// Make a local copy of the command line arguments
		cmdLnArgS = args.clone();
		
		// Indexing helpers
		int i = 0, j = 0;
		
		// process all the command line tokens (flags, arguments, longFlags).
		while (i < cmdLnArgS.length) {
			
			this.arg = cmdLnArgS[i++];
			
			// Deal with FAT arguments. 
			// ONLY COLLECT. Do the logic after. This is to check for any malformed or illegal arguments. 
			
			if (this.arg.startsWith("--")) {
				
				if (this.arg.equals("--help")) {
					
					// Skip doubles and repeats
					while (i < cmdLnArgS.length && (cmdLnArgS[i].equals("--help") || cmdLnArgS[i].equals("-h"))) {						
						this.arg = cmdLnArgS[i++];
					}
					
					// Help flag is now active
					this.hflag = true;
					
				} else if (this.arg.equals("--verbose")) {
					
					// Skip doubles and repeats
					while (i < cmdLnArgS.length && (cmdLnArgS[i].equals("--verbose") || cmdLnArgS[i].equals("-v"))) {						
						this.arg = cmdLnArgS[i++];
					}
					
					// Verbose flag is now active
					this.vflag = true;					
					
					// If there are tokens after verbose then ingest
					if (i < cmdLnArgS.length) {
						// check if there are atmost 2 arguments
						this.checkNumArguments(this.cmdLnArgS);
						// The next argument after -v should be a jar file
						this.loadFile = cmdLnArgS[i++];
						// if there is an additional class name provided 
						if (i < cmdLnArgS.length) {
							this.loadClass = cmdLnArgS[i++];
						}
					}
					
				} else {
					// Invalid FAT qualifier entered
					throw new UnrecognizedQualifier(this.arg);
				}				
				
			}
			
			// Deal with BABY flags here. What kind of a man sends babies to fight me?
			else if (this.arg.startsWith("-")) {
				
				// step through each character of the flag
				for (j = 1; j < this.arg.length(); j++) {
					
					// collect each flag after - in -vh
					this.flag = this.arg.charAt(j);
					
					// walk through each individual flag
					switch (this.flag) {
						
						// -h flag
						case 'h':
						this.hflag = true;
						break;
						
						// -v flag
						case 'v':
						this.vflag = true;
						
						// If there are tokens after verbose then ingest
						// Need to collect the jar file name right after the flag
						// length is finished not after the flag itself
						if (j == this.arg.length()) {
							// check if there are atmost 2 arguments
							this.checkNumArguments(this.cmdLnArgS);
							// The next argument after -v should be a jar file
							this.loadFile = cmdLnArgS[i++];
							// if there is an additional class name provided 
							if (i < cmdLnArgS.length) {
								this.loadClass = cmdLnArgS[i++];
							}
						}
						break;
						
						// unidentified foreign flag
						default:
						throw new UnrecognizedQualifier(this.flag, this.arg);
						
					}					
					
				}
				
			} 
			
			// user has only entered arguments: 
			// java -jar methods.jar commands.jar
			else {				
				// check if there are atmost 2 arguments
				this.checkNumArguments(this.cmdLnArgS);
				// The next argument after -v should be a jar file
				this.loadFile = cmdLnArgS[i++];
			}			
			
		}
		
		// DEAL WITH THE LOGIC
		
		// if help flag is active then there cannot be a input file		
		if (this.hflag) {			
			if (this.loadFile != "") {
				throw new UnexpectedHelpQualifier();
			} else {
				ExceptionHandler.printSynopsis(true);	
			}
		// if the hel flag is INactive then jar loading can begine
		} else {
			System.out.println("End of Program");
			// pass it to the file loader and throw an error if it failed (ErrorLoadingJarFile or ErrorFindingClass)
		}	
		
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
		
		// Too many arguments
		if (numArguments > 2) {
		   throw new MoreThanTwoCommandsGiven();
		}
	}
	
	

}
