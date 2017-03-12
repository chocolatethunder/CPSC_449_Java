/**
 * Token class, each token has a name and type
 *
 *
 */
public class Token {
	private String name;		// name of Token
	private Class type;		// Type of token. Can be Integer, Float, String, Char
	private String stringType; // Type of token expressed as a string. Can be int, float, string, identifier, openBracket, closedBracket
	private int index;			// index in ArrayList. Can be used to for tracing error
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
	String getName() {
		return name;
	}

	/**
	 * setter for name
	 * @param name
	 */
	void setName(String name) {
		this.name = name;
	}


	/**
	 * getter for type
	 * @return
	 */
	Class getType() {
		return type;
	}

	/**
	 * setter for type
	 * @param type
	 */
	void setType(Class type) {
		this.type = type;
	}
	
	
	/**
	 * getter for index
	 * @return
	 */
	 int getIndex() {
		return index;
	}

	/**
	 * setter for index
	 * @param index
	 */
	 void setIndex(int index) {
		this.index = index;
	}

	 /**
	  * getter for stringType
	  * @return
	  */
	 String getStringType() {
		return stringType;
	}
	 
	 /**
	  * setter for stringType
	  * @param stringType
	  */
	 void setStringType(String stringType) {
		this.stringType = stringType;
	}

}
