package parser;

import java.io.*;
import java.util.*;
import java.lang.*;
import java.text.*;
import java.net.*;

import parser.exceptions.*;

public class RunParser {    
    
    public RunParser() {
       
       RunParser.printStartup();
       
    }
    
    private static void printStartup(){
		System.out.println(
		"q           : Quit the program.\n" +
		"v           : Toggle verbose mode (stack traces).\n" +
		"f           : List all known functions.\n" +
		"?           : Print this helpful text.\n" +
		"<expression>: Evaluate the expression.\n" +
		"Expressions can be integers, floats, strings (surrounded in double quotes) or function calls of the form '(identifier {expression}*)'."
		);
	}
    
    
    
}
