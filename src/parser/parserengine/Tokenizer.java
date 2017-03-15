package parser;

import java.lang.reflect.Method;
import java.util.ArrayList;

import static parser.Utilities.*;

// this file needs to be re-written. what is this code bruh? It hurt my brain!

public class Tokenizer {
	
	String input = "";
	ArrayList<Token> tokens = new ArrayList<Token>();
	int tokenPosition = 0;
	
	public Tokenizer (String expression) {
		
		// Debug
		System.out.println(expression);
		
	}
	
	public ArrayList<Token> getTokens() {
		return this.tokens;
	}

}
