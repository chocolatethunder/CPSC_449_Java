/************************************************************************************
 * CPSC 449 - Winter 2017															*
 * Prof: Rob Kremer																	*
 * Assignment: Java																	*
 * Group #: 32																		*
 * Members: Saurabh Tomar, Kaylee Stelter, Kowther Hassan, Matthew Mullins, Tsz Lam	*
 * Description:																		*
 * 		- Handles the fatal error where an unrecognized qualifier is found			*
 * 																					*
 * Contain methods:																	*
 * 		+UnrecognizedQualifier(String) <- Constructor								*
 * 		+UnrecognizedQualifier(char, String) <- Constructor							*			
 *																					*
 * 																					*
 ************************************************************************************/

package parser;

import java.util.*;

/**
 * Fatal error thrown when an unrecognized qualifier (letter or long) is provided by the user
 */
public class UnrecognizedQualifier extends ExceptionHandler {
    
	/**
	 * @param qualifier - represents the qualifier entered at command-line
	 * Exception for a long qualifier error
	 */
    public UnrecognizedQualifier(String qualifier) {
        System.err.println("Unrecognized qualifier: " + qualifier + ".");
        ExceptionHandler.printSynopsis(false);
        System.exit(-1);
    }
    
    /**
     * @param letter - represents the short qualifier entered at command-line
     * @param source - represents the full input entered at command-line
     * Exception for a short qualifier error
     */
    public UnrecognizedQualifier(char letter, String source) {        
        System.err.println("Unrecognized qualifier '" + letter + "' in '" + source + "'.");
        ExceptionHandler.printSynopsis(false);
        System.exit(-1);
    }

}