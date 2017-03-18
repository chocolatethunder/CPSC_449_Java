/************************************************************************************
 * CPSC 449 - Winter 2017															*
 * Prof: Rob Kremer																	*
 * Assignment: Java																	*
 * Group #: 32																		*
 * Members: Saurabh Tomar, Kaylee Stelter, Kowther Hassan, Matthew Mullins, Tsz Lam	*
 * Description:																		*
 * 		- Handles the fatal error where no jar file is given						*
 * 																					*
 * Contain methods:																	*
 * 		+NoJarFileGiven() <- Constructor											*			
 *																					*
 * 																					*
 ************************************************************************************/

package parser;

import java.util.*;

/**
 * Fatal error thrown when no jar file is provided
 */
public class NoJarFileGiven extends ExceptionHandler {
    
    public NoJarFileGiven() {
        System.err.println("This program requires a jar file as the first command line argument (after any qualifiers).");
        ExceptionHandler.printSynopsis(false);
        System.exit(-3);
    }

}