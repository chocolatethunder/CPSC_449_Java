package parser;

import java.util.*;

// Fatal error thrown when a help qualifier appears with any other command line arguments
public class UnexpectedHelpQualifier extends ExceptionHandler {
    
    public UnexpectedHelpQualifier() {
        System.err.println("Qualifier --help (-h, -?) should not appear with any comand-line arguments.");
        ExceptionHandler.printSynopsis(false);
        System.exit(-4);
    }

}