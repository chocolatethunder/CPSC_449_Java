package parser;

import java.util.*;

public class ErrorLoadingJarFile extends ExceptionHandler {
    
    public ErrorLoadingJarFile(String filename) {
        System.err.println("Could not load jar file: " + filename);
        System.exit(-5);
    }

}