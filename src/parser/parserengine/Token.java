/************************************************************************************
 * CPSC 449 - Winter 2017															*
 * Prof: Rob Kremer																	*
 * Assignment: Java																	*
 * Group #: 32																		*
 * Members: Saurabh Tomar, Kaylee Stelter, Kowther Hassan, Matthew Mullins, Tsz Lam	*
 * Description:																		*
 * 		-Contains th class that represents a token.                 				*
 * 																					*
 * Methods:																			*			
 * 		+ Token(String,Class,String,int)						        			*
 * 		+ getName():String                                                          *
 *      + setName(String)                                                           *
 *      + getType():Class                                                           *
 *      + setType(Class)                                                            * 
 *      + getStringType():String                                                    *
 *      + setStringType(String)                                                     *   
 *      + getIndex():int                                                            *
 *      + setIndex(int)                                                             *
 * 																					*
 ************************************************************************************/

package parser;

/**
 * Represents a token. Each toking is a string with a name, datatype, string type 
 * and index.
 */
public class Token {
	
	// name of Token
	private String name;
	// Type of token. Can be Integer, Float, String, Char	
	private Class type;
	// Type of token expressed as a string. Can be int, float, string, identifier, openBracket, closedBracket, unidentified
	private String stringType;
	// index in ArrayList. Can be used to for tracing error
	private int index;
	
	/**
	 * Constructor for the token component.
	 * @param name - Represents the name of the token
	 * @param type- Represents the type of the token (Integer, Float, String, Char)
	 * @param stringType - Represents the token expressed as a string for printing purposes
	 * @param index - Represents the index at which the token occurs in the command-line input
	 */
	public Token (String name, Class type, String stringType, int index) {
		this.setName(name);
		this.setType(type);
		this.setStringType(stringType);
		this.setIndex(index);		
	}
	
	/**
	 * 
	 * @return - String representing the name of the token
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @param name - String representing the name of the token
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return - Class representing the type of the token (Integer, String, Float, Char)
	 */
	public Class getType() {
		return this.type;
	}

	/**
	 * @param type - Class representing the type of the token (Integer, String, Float, Char)
	 */
	public void setType(Class type) {
		this.type = type;
	}	
	
	/**
	 * @return - Integer representing the index at which the token occurs in the command-line
	 */
	public int getIndex() {
		return this.index;
	}

	/**
	 * @param index - Integer representing the index at which the token occurs in the command-line
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	* @return - String representing the token expressed as a string for printing
	*/
	public String getStringType() {
		return this.stringType;
	}
	 
	 /**
	  * @param stringType - String representing the token expressed as a string for printing
	  */
	public void setStringType(String stringType) {
		this.stringType = stringType;
	}
}
