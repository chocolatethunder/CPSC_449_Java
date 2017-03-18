/************************************************************************************
 * CPSC 449 - Winter 2017															*
 * Prof: Rob Kremer																	*
 * Assignment: Java																	*
 * Group #: 32																		*
 * Members: Saurabh Tomar, Kaylee Stelter, Kowther Hassan, Matthew Mullins, Tsz Lam	*
 * Description:																		*
 * 		- Handles the fatal error where more than two command are provided			*
 * 		on the command-line															*
 * 																					*
 * Contain methods:																	*
 * 		+MoreThanTwoCommandsGiven() <- Constructor									*			
 *																					*
 * 																					*
 ************************************************************************************/

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