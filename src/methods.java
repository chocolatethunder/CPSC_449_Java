import java.util.*;

public class methods {
	public static void main (String args[]){
		boolean shouldQuit = false;
		boolean verboseMode = false;
		
		TreeNode parseTree;
		
		//Create a new Scanner object
		Scanner userInput = new Scanner(System.in);
		
		//Continuous while loop until told to exit
		while (shouldQuit == false){
			//TODO: Prompt user for command, will be removed later
			System.out.println("Please enter a command:");
			//Reads user input and stores as a string
			String inputCommand = userInput.next();
			//Simple switch to detect single character commands
			switch (inputCommand){
			case "q":
				shouldQuit = true;
				break;
			case "v":
				if (verboseMode)
					verboseMode = false;
				else 
					verboseMode = true;
				break;
			case "f":
				//TODO: This needs to display all know functions from the commands.jar file
				System.out.println("All known functions");
				break;
			case "?":
				break;
				default:
					//TODO: This is where we create the parse tree
					break;
			}
		}
	}
}

































