/************************************************************************************
 * CPSC 449 - Winter 2017															*
 * Prof: Rob Kremer																	*
 * Assignment: Java																	*
 * Group #: 32																		*
 * Members: Saurabh Tomar, Kaylee Stelter, Kowther Hassan, Matthew Mullins, Tsz Lam	*
 * Description:																		*
 * 		-This class is used to start the program (i.e. load the parser)				*
 * 																					*
 ************************************************************************************/

package parser;

import java.io.*;
import java.util.*;
import java.lang.*;
import java.text.*;
import java.net.*;

public class Main {    
    
	public static void main (String[] args) {
		try {
			LoadParser newProgram = new LoadParser(args);
		} catch (Exception e) { 
			System.out.println(e.getMessage());
		}

	} 
}
