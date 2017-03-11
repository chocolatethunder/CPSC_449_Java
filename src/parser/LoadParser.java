package parser;

import java.io.*;
import java.util.*;
import java.lang.*;
import java.text.*;
import java.net.*;

import parser.exceptions.*;

public class LoadParser {    
    
    public LoadParser(String[] args) {
       
       // Functional Requirement 1.1
       
       // Functional Requirement 1.2
       
       // Functional Requirement 1.3
       if (args.length == 0) {
            ExceptionHandler.printSynopsis(false);
       }
       
       // Check to see if there are at most 2 arguments given
       try {
           this.checkNumArguments(args);
       } catch (Exception e) { }
       
       // Run the program
       RunParser parser = new RunParser();
       
    }
    
    public void checkNumArguments(String[] args) throws MoreThanTwoCommandsGiven {
        
        // Error checking       
       int numArguments = 0;
       
       for (String arg : args) {
           if (arg.charAt(0) != '-') {
               numArguments++;
           }
       }
       
       if (numArguments > 2) {
           throw new MoreThanTwoCommandsGiven();
       }
        
    }
    
}
