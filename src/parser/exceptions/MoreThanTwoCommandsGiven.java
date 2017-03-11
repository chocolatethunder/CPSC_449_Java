package parser;

import java.util.*;

public class MoreThanTwoCommandsGiven extends ExceptionHandler {
    
    public MoreThanTwoCommandsGiven() {
        System.err.println("This program takes at most two command line arguments.");
        ExceptionHandler.printSynopsis(false);
        System.exit(-2);
    }

}