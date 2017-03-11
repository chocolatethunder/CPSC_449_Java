package parser;

import java.util.*;

// Fatal error thrown when an unrecognized qualifier (letter or long) is provided by the user
public class UnrecognizedQualifier extends ExceptionHandler {
    
    public UnrecognizedQualifier(String qualifier) {
        System.err.println("Unrecognized qualifier: --" + qualifier + ".");
        ExceptionHandler.printSynopsis(false);
        System.exit(-1);
    }
    
    public UnrecognizedQualifier(String letter, String source) {        
        System.err.println("Unrecognized qualifier '" + letter + "' in '" + source + "'.");
        ExceptionHandler.printSynopsis(false);
        System.exit(-1);
    }

}