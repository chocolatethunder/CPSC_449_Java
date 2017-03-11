package parser;

import java.util.*;

public class UnexpectedHelpQualifier extends ExceptionHandler {
    
    public UnexpectedHelpQualifier() {
        System.err.println("Qualifier --help (-h, -?) should not appear with any comand-line arguments.");
        ExceptionHandler.printSynopsis(false);
        System.exit(-4);
    }

}