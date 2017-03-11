package parser;

import java.util.*;

public class NoJarFileGiven extends ExceptionHandler {
    
    public NoJarFileGiven() {
        System.err.println("This program requires a jar file as the first command line argument (after any qualifiers).");
        ExceptionHandler.printSynopsis(false);
        System.exit(-3);
    }

}