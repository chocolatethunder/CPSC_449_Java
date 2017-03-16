package parser;

import java.io.*;
import java.util.*;
import java.lang.*;
import java.lang.reflect.*;
import java.text.*;

import static parser.Utilities.*;

public class ParseTreeConstructor {
    Tokenizer tokenizer;
    ArrayList<Token> tokenList;

	public ParseTreeConstructor(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
        tokenList = tokenizer.getTokens();
		
	}
    
    public Node<Token> createParseTree () {
		
		Stack<Node<Token>> pStack = new Stack<Node<Token>>( );	// parent node stack
		Stack<String> bStack = new Stack<String>( );	// bracket stack
		Node<Token> currentTree = new Node<Token>(null);	// make first node
		String previousType = "";
		
		for ( int i = 0; i < tokenList.size( ); i++ ) {
			String stringType = tokenList.get( i ).getStringType();	// gets type expressed as a string
			Token token = tokenList.get( i );
			
			switch ( stringType ){
				case "openBracket":
					if ( currentTree.getData() != null) {
						currentTree.addChild(new Node<Token>(null));
						ArrayList<Node<Token>> children = currentTree.getChildren();
						children.get(children.size()-1).setParent(currentTree); 		// set childs parent to current tree
						currentTree = children.get(children.size()-1);					// set child to current tree
						pStack.push(currentTree);
						bStack.push( stringType );
					} else {
						pStack.push( currentTree );
						bStack.push( stringType );
					}
					previousType = stringType;
					break;
				case "int":
					currentTree.addChild(new Node<Token>( token ) );	// adds child to tree
					previousType = stringType;
					break;
				case "string":
					currentTree.addChild(new Node<Token>( token ) );	// adds child to tree
					previousType = stringType;
					break;
				case "float":
					currentTree.addChild(new Node<Token>( token ) );	// adds child to tree
					previousType = stringType;
					break;
				case "identifier":
					currentTree.setData( token );		// adds data to parent node
					previousType = stringType;
					break;
				case "closedBracket":
					if (!bStack.isEmpty() && pStack.size() > 1) {
						bStack.pop();	//	pop of "("
						pStack.pop();
						currentTree = pStack.peek();	// set tree back to parent tree
					} else if (!bStack.isEmpty() && pStack.size() == 1) {
						bStack.pop();
					}
					previousType = stringType;
					break;
				case "unidentified":
					// TODO error handling!!!!
					System.out.println(token.getName() + " invalid variable type in position " + token.getIndex() );
					
					
			}
		}
		return currentTree;
    }

}