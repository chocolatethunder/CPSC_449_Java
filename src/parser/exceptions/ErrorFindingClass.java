package parser;

import java.util.*;

/**
 * Fatal error thrown when the class could not be found
 */
public class ErrorFindingClass extends ExceptionHandler {
    
	/**
	 * @param classname - class which is trying to be loaded
	 */
    public ErrorFindingClass(String classname) {
        System.err.println("Could not find class: " + classname);
        System.exit(-6);
    }

}