package parser;

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
	 * constructor for token. Sets the name and type
	 * @param name
	 * @param type
	 */
	public Token (String name, Class type, String stringType, int index) {
		this.setName(name);
		this.setType(type);
		this.setStringType(stringType);
		this.setIndex(index);		
	}
	
	/**
	 * getter for name
	 * @return
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * setter for name
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * getter for type
	 * @return
	 */
	public Class getType() {
		return this.type;
	}

	/**
	 * setter for type
	 * @param type
	 */
	public void setType(Class type) {
		this.type = type;
	}	
	
	/**
	 * getter for index
	 * @return
	 */
	public int getIndex() {
		return this.index;
	}

	/**
	 * setter for index
	 * @param index
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	* getter for stringType
	* @return
	*/
	public String getStringType() {
		return this.stringType;
	}
	 
	 /**
	  * setter for stringType
	  * @param stringType
	  */
	public void setStringType(String stringType) {
		this.stringType = stringType;
	}

}
