/************************************************************************************
 * CPSC 449 - Winter 2017															*
 * Prof: Rob Kremer																	*
 * Assignment: Java																	*
 * Group #: 32																		*
 * Members: Saurabh Tomar, Kaylee Stelter, Kowther Hassan, Matthew Mullins, Tsz Lam	*
 * Description:																		*
 * 		- Handles the fatal error where the jar file could not be loaded			*
 *  	                     														*
 * Contain methods:																	*
 * 		+ErrorLoadingJarFile() <- Constructor										*			
 *		+ErrorLoadingJarFile(String) <- Constructor									*
 * 																					*
 ************************************************************************************/

package parser;

import java.util.*;

/**
 *  Fatal error thrown when the jar file could not be loaded
 */
public class ErrorLoadingJarFile extends ExceptionHandler {
    
	// In the case no jar is provided (already handled in exception NoJarFileGiven???)
    public ErrorLoadingJarFile() {
        System.err.println("Could not load jar file: None given.");
        System.exit(-5);
    }
    
    /**
     * @param filename - file which could not be loaded, used in error message
     */
    // In the case an invalid jar file is provided
    public ErrorLoadingJarFile(String filename) {
        System.err.println("Could not load jar file: " + filename);
        System.exit(-5);
    }

}