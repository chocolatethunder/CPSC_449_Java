package parser;

import java.util.*;

public class ErrorFindingClass extends ExceptionHandler {
    
    public ErrorFindingClass(String classname) {
        System.err.println("Could not find class: " + classname);
        System.exit(-6);
    }

}