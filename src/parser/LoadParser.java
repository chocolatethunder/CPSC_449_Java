/************************************************************************************
 * CPSC 449 - Winter 2017															*
 * Prof: Rob Kremer																	*
 * Assignment: Java																	*
 * Group #: 32																		*
 * Members: Saurabh Tomar, Kaylee Stelter, Kowther Hassan, Matthew Mullins, Tsz Lam	*
 * Description:																		*
 * 		-Checks the command-line input for various conditions prior to 				*
 * 		running the Parser															*
 * 		-Throws various fatal error exceptions where appropriate					*
 *  																				*
 * Contain methods:																	*			
 * 		+LoadParser(String[]) <- Constructor										*
 * 		+checkNumArguments(String[]):void											*
 * 		-fileAndClassSetter(String[], int):int										*
 * 																					*
 ************************************************************************************/
package parser;

import java.io.*;
import java.util.*;
import java.lang.*;
import java.text.*;
import java.net.*;

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
	 * @throws Exception - throws exceptions related to fatal errors
	 */
	public LoadParser(String[] args) throws Exception {
		
		// Make a local copy of the command line arguments
		cmdLnArgS = args.clone();
		
		// Print full help message if there are no arguments
		if (cmdLnArgS.length == 0) {
			ExceptionHandler.printSynopsis(false);
		}
		
		// Indexing helpers
		int i = 0, j = 0;
		
		// process all the command line tokens (flags, arguments, longFlags).
		// ONLY COLLECT. Do the logic after. This is to check for any malformed or illegal arguments. 
		while (i < cmdLnArgS.length) {

			this.arg = cmdLnArgS[i];
			
			// Deal with FAT arguments.	This comment will trigger the left wingers.
			if (this.arg.startsWith("--")) {
				
				if (this.arg.equals("--help")) {
					
					// Skip doubles and repeats
					while (i < cmdLnArgS.length && (cmdLnArgS[i].equals("--help") || cmdLnArgS[i].equals("-h"))) {
						i++;
					}
					
					// Help flag is now active
					this.hflag = true;
					
				} else if (this.arg.equals("--verbose")) {
					
					// Skip doubles and repeats
					while (i < cmdLnArgS.length && (cmdLnArgS[i].equals("--verbose") || cmdLnArgS[i].equals("-v"))) {
						i++;
					}
					
					// Verbose flag is now active
					this.vflag = true;
					
					// If there are tokens after verbose then ingest
					if (i < cmdLnArgS.length) {
						i = fileAndClassSetter(cmdLnArgS, i);
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
						if (j == this.arg.length()-1 && !this.cmdLnArgS[i].startsWith("-")) {
							i = fileAndClassSetter(cmdLnArgS, ++i);
						}
						break;
						
						// unidentified foreign flag. Call in the Vexillologist!
						default:
						throw new UnrecognizedQualifier(this.flag, this.arg);
					}					
				}
			}			
			// user has only entered arguments: 
			// java -jar methods.jar commands.jar
			else {
				if (!hflag) {
					
					if (i <= cmdLnArgS.length) {
						i = fileAndClassSetter(cmdLnArgS, i);
					}	
					
				} else {
					throw new UnexpectedHelpQualifier();
				}

			}			
			// move to the next token
			i++;
			
		}
		
		// check if the v flag as a jar file after it
		if (this.vflag) {			
			ExceptionHandler.toggleVerbose();			
		}
		
		// if help flag is active then there cannot be a input file		
		if (this.hflag) {			
			if (this.loadFile != "") {
				throw new UnexpectedHelpQualifier();
			} else {
				ExceptionHandler.printSynopsis(true);	
			}
		
		// if the help flag is INactive then jar loading can begine
		} else {
			// check if a file is provided. 
			if (this.loadFile != "") {
				// pass it to the file loader and throw an error if it failed (ErrorLoadingJarFile or ErrorFindingClass)
				try {
					// Try loading the jar file
					JarFileLoader jarLoad = new JarFileLoader(this.loadFile, this.loadClass);				
					
					// If we have gotten this far then we load the program
					// Need to pass the loaded class as an object
					if (jarLoad.isFileLoaded()) {
						RunParser newProgramInstance = new RunParser(jarLoad);
					}
				} catch (Exception e) { /* empty catch */ }
			} else {
				throw new NoJarFileGiven();
			}
		}	
	}
	
	/**
	 * @param args - represents the command-line arguments provided by the user
	 * @throws MoreThanTwoCommandsGiven - throws exception related to fatal error of having more
	 * than two commands given at the command-line input
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
	
	/**
	 * 
	 * @param cmdLnArgS - Represents the command-line arguments
	 * @param i - Represents the index of the last input read from command-line
	 * @return - Integer representing the index of the last input read
	 * @throws MoreThanTwoCommandsGiven - throws exception related to fatal error of having more
	 * than two commands given at the command-line input
	 */
	private int fileAndClassSetter(String[] cmdLnArgS, int i) throws MoreThanTwoCommandsGiven {
		
		// check if there are atmost 2 arguments
		this.checkNumArguments(this.cmdLnArgS);
		
		// capture the loadFile and optional loadClass
		if (i < cmdLnArgS.length && !this.cmdLnArgS[i].startsWith("-") ) {
			// The next argument after -v should be a jar file. This is what the currrent index is at.
			this.loadFile = cmdLnArgS[i++];
			// The next argument after jar file should be the optional class name
			if (i < cmdLnArgS.length && !this.cmdLnArgS[i].startsWith("-") ) {
				this.loadClass = cmdLnArgS[i];
			} else {
				i--;
			}
		}
		// return the current index of the command line token loop
		return i;
	}
}
