
public class ExceptionHandler extends Exception {

	// Fatal Error handling
	// Takes in the type of Exception which has occurred
	// Writes error code to syserror and then exits the program
	public static void FatalError(String errorType, String[] args){
		
		switch (errorType){
		
			case "qualifierLetter":{
				System.err.println("Unrecognized qualifier '" + args[0] + "' in '-" + args[1] + "'.");
				//printSynopsis(args);
				System.exit(-1);
			}
			
			case "qualifierLong":{
				System.err.println("Unrecognized qualifier: --" + args[0] + ".");
				//printSynopsis(args);
				System.exit(-1);
			}
			
			case "tooManyCommands": {
				System.err.println("This program takes at most two command line arguments.");
				//printSynopsis(args);
				System.exit(-2);
			}
			
			case "jarNotFirst": {
				System.err.println("This program requires a jar file as the first command line argument (after any qualifiers).");
				//printSynopsis(args);
				System.exit(-3);
				
			}
			
			case "loneQualifier":{
				System.err.println("Qualifier --help (-h, -?) should not appear with any command-line arguments.");
				//printSynopsis(args);
				System.exit(-4);
			}
			
			case "failLoad":{
				System.err.println("Could not load jar file: " + args[0]);
				System.exit(-5);
			}
			
			case "failFindClass":{
				System.err.println("Could not find class: " + args[0]);
				System.exit(-6);
				
			}
		}
	}
	
	// Non-fatal error handling
	// Takes in the error type, the input entered by the user, and the index at which the error occurred
	// Prints the appropriate error message to the user based on input parameters
	public void NonFatalError(String errorType, String input, int index){
		
		switch (errorType){
			case "function":{
				
			}
			
			case "bracket":{
				
			}
				
		}
	}
	
	public static void main(String[] args) {
		
		FatalError("qualifierLetter", new String[]{"z", "zebra"});
		FatalError("qualifierLong", new String[]{"zebra"});
		FatalError("tooManyCommands", new String[]{});
		FatalError("jarNotFirst", new String[]{});
		FatalError("loneQualifier", new String[]{});
		FatalError("failLoad", new String[]{"file.jar"});
		FatalError("failFindClass", new String[]{"Method.class"});
		
	}
}
	

