package parser;

import java.util.*;

public class ParserException extends ExceptionHandler {
    
    public ParserException(String errMsg, int offset, String cmd, Exception e) {
        
        // format and print the Non-Fatal error
        System.out.println(errMsg + " at offset " + offset);
        System.out.println(cmd);
        while (offset > 0) {
            System.out.print("-");
            offset--;
        }
        System.out.print("^\n");
        
        // if verbose mode is on print stack trace
        if (ExceptionHandler.isVerbose() == true) {
            e.printStackTrace();
        }
        
    }

}