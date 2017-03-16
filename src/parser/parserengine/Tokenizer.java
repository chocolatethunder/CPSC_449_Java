package parser;

import java.lang.reflect.Method;
import java.util.ArrayList;

import static parser.Utilities.*;


public class Tokenizer {
	
	String input = "";
	ArrayList<Token> tokens = new ArrayList<Token>();
	int tokenPosition = 0;
	
	public Tokenizer (String expression) {
		

		
	}
	
	public ArrayList<Token> getTokens() {
		return this.tokens;
	}

}
