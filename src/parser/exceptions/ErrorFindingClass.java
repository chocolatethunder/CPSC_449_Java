/************************************************************************************
 * CPSC 449 - Winter 2017															*
 * Prof: Rob Kremer																	*
 * Assignment: Java																	*
 * Group #: 32																		*
 * Members: Saurabh Tomar, Kaylee Stelter, Kowther Hassan, Matthew Mullins, Tsz Lam	*
 * Description:																		*
 * 		- Handles the fatal error where the class could not be found				*
 *  	  in the loaded file														*
 * Contain methods:																	*
 * 		+ErrorFindingClass(String) <- Constructor									*			
 *																					*
 * 																					*
 ************************************************************************************/

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
		ExceptionHandler.printSynopsis(false);
        System.exit(-6);
    }

}