/************************************************************************************
 * CPSC 449 - Winter 2017															*
 * Prof: Rob Kremer																	*
 * Assignment: Java																	*
 * Group #: 32																		*
 * Members: Saurabh Tomar, Kaylee Stelter, Kowther Hassan, Matthew Mullins, Tsz Lam	*
 * Description:																		*
 * 		- Handles the fatal error where a help qualifier appears with any			*
 * 		other command-line arguments												*
 * Contain methods:																	*
 * 		+UnexpectedHelpQualifier() <- Constructor									*			
 *																					*
 * 																					*
 ************************************************************************************/

package parser;

import java.util.*;

/**
 * Fatal error thrown when a help qualifier appears with any other command-line arguments
 */
public class UnexpectedHelpQualifier extends ExceptionHandler {
    
    public UnexpectedHelpQualifier() {
        System.err.println("Qualifier --help (-h, -?) should not appear with any command-line arguments.");
        ExceptionHandler.printSynopsis(false);
        System.exit(-4);
    }

}