package parser;

import java.util.*;

/**
 * Fatal error thrown when more then two command line arguments are provided by the user
 */
public class MoreThanTwoCommandsGiven extends ExceptionHandler {
    
    public MoreThanTwoCommandsGiven() {
        System.err.println("This program takes at most two command line arguments.");
        ExceptionHandler.printSynopsis(false);
        System.exit(-2);
    }

}