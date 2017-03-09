/**
 * Token class, each token has a name and type
 *
 *
 */
public class Token {
	private String name;		// name of Token
	private String type;		// Type of token. Can be: int, float, string, identifier, openBracket, closedBracket
	private int index;			// index in ArrayList. Can be used to for tracing error
	/**
	 * constructor for token. Sets the name and type
	 * @param name
	 * @param type
	 */
	public Token (String name, String type, int index) {
		this.setName(name);
		this.setType(type);
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
	String getType() {
		return type;
	}

	/**
	 * setter for type
	 * @param type
	 */
	void setType(String type) {
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

}
