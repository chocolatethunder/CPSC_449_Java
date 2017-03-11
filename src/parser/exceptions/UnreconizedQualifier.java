package parser;

import java.util.*;

public class UnreconizedQualifier extends ExceptionHandler {
    
    public UnreconizedQualifier(String qualifier) {
        System.err.println("Unrecognized qualifier: --" + qualifier + ".");
        ExceptionHandler.printSynopsis(false);
        System.exit(-1);
    }
    
    public UnreconizedQualifier(String letter, String source) {        
        System.err.println("Unrecognized qualifier '" + letter + "' in '" + source + "'.");
        ExceptionHandler.printSynopsis(false);
        System.exit(-1);
    }

}