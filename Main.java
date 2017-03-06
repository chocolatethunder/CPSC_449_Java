package Java_Parser;

import java.util.*;
import java.text.*;
import java.lang.*;
import java.net.*;

public class Main {
	private static boolean shouldQuit = false;
	private static boolean verboseModeOn = false;			//TODO: Decided true if -v included in command line, false otherwise

	private static String userCommand = null;
	
	//TODO: These need to be loaded from the command line
	private static String jarFileName = "commands.jar";
	private static String jarClassName = "Commands";
	
	private static Class<?> jarFile = null;
	
	public static void main(String[] args) {
		//TODO: Display Synopsis
		printSynopsis(args);
		
		//Displays Startup Message
		printStartup();
		
		//Creates new Scanner Object
		Scanner userInput = new Scanner(System.in);
		
		//Load given class from command line
		loadJarFile(jarFileName, jarClassName);
		
		while (shouldQuit == false){
			//Display prompt
			System.out.printf(">");
			//Take in user input
			userCommand = userInput.nextLine();
			//Evaluate userCommand for meta commands
			switch (userCommand){
			//Quit
			case "q":
				shouldQuit = true;
				break;
				//Toggle Verbose mode
			case "v":
				if (verboseModeOn){
					//Toggle Verbose mode off
					System.out.println("Verbose off");
					verboseModeOn = false;
				} else {
					//Toggle Verbose mode on
					System.out.println("Verbose on");
					verboseModeOn = true;
				}
				break;
				//Function List - displays function list
			case "f":
				break;
				//Help - Displays startup text
			case "?":
				printStartup();
				break;
			default:
			}
		}
		//Close userInput
		userInput.close();
		//Display quit message
		System.out.println("bye.");
		//Exits program on exit code 0
		System.exit(0);
	}
	
	//Loads jar file
	private static void loadJarFile(String fileName, String className){

	}
	
	//Displays startup message
	private static void printStartup(){
		System.out.println(
		"q           : Quit the program.\n" +
		"v           : Toggle verbose mode (stack traces).\n" +
		"f           : List all known functions.\n" +
		"?           : Print this helpful text.\n" +
		"<expression>: Evaluate the expression.\n" +
		"Expressions can be integers, floats, strings (surrounded in double quotes) or function calls of the form '(identifier {expression}*)'."
		);
	}
	
	//TODO: Displays synopsis message
	private static void printSynopsis(String[] args){
		System.out.println(
				"Synopsis:\n" +
				"  methods\n" +
				"  methods { -h | -? | --help }+\n" +
				"  methods {-v --verbose}* <jar-file> [<class-name>]\n" +
				"Arguments:\n" +
				"  <jar-file>:   The .jar file that contains the class to load (see next line).\n" +
				"  <class-name>: The fully qualified class name containing public static command methods to call. [Default="+"Commands"+"]\n" +
				"Qualifiers:\n" +
				"  -v --verbose: Print out detailed errors, warning, and tracking.\n" +
				"  -h -? --help: Print out a detailed help message.\n" +
				"Single-char qualifiers may be grouped; long qualifiers may be truncated to unique prefixes and are not case sensitive.\n"
				);
				System.exit(0);
	}
}
