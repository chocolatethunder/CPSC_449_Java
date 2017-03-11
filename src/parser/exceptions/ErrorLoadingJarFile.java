package parser;

import java.util.*;

// Fatal error thrown when the jar file could not be loaded
public class ErrorLoadingJarFile extends ExceptionHandler {
    
    public ErrorLoadingJarFile(String filename) {
        System.err.println("Could not load jar file: " + filename);
        System.exit(-5);
    }

}