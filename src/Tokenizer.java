import java.util.ArrayList;

public class Tokenizer {
	String input;
	ArrayList<String> tokens;



	// constructor takes in an input string to be tokenized
	public Tokenizer ( String input ) {
		
		this.input = input;
		makeTokenList();		// makes token list
		
	}
	
	
	public void makeTokenList() {
		tokens = new ArrayList<String>();
		String expression = "";
		
		for (int i = 0; i < input.length(); i++) {
			
			char j = input.charAt( i );
			switch ( j ) {
				case '(':
					
					tokens.add(j + "");				// converted into string
					break;
				case ' ':
					if (expression.length() > 0)
						tokens.add(expression);		// add token if expression is not empty
					expression = "";				// reset expression
					break;
				case ')':
					tokens.add(expression);			// add expression before ')'
					expression = "";				// reset expression
					tokens.add(j + "");				// converted into string
					break;
				default:
					expression += (j + "");			// add character to expression
					break;
			}	
		}
	}
	
	// getter for tokens
	public ArrayList<String> getTokens() {
		return tokens;
	}
	
	public static void main(String[] args) {
	        
			Tokenizer tokenizer = new Tokenizer( "(add (add 'three' 2) 2)" );
	        System.out.println(tokenizer.getTokens());
	        
	        String str = "(add (add 'three' 2) 2)";
	        String[] splitted = str.split("\\s+");
	        for (int i = 0; i < splitted.length; i++ ){
	        	System.out.println(splitted[i]);
	        }
	   }
}
