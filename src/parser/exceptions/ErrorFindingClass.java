package parser;

import java.util.*;

// Fatal error thrown when the class could not be found
public class ErrorFindingClass extends ExceptionHandler {
    
    public ErrorFindingClass(String classname) {
        System.err.println("Could not find class: " + classname);
        System.exit(-6);
    }

}