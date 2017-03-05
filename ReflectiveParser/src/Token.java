/**
 * Token class, each token has a name and type
 * 
 * @author Kowther
 *
 */
public class Token {
	private String name;
	private String type;
	
	
	public Token (String name, String type ) {
		this.setName(name);
		this.setType(type);
		
	}


	String getName() {
		return name;
	}


	void setName(String name) {
		this.name = name;
	}



	String getType() {
		return type;
	}


	void setType(String type) {
		this.type = type;
	}

}
