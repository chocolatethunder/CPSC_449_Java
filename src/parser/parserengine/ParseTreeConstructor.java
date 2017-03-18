/************************************************************************************
 * CPSC 449 - Winter 2017															*
 * Prof: Rob Kremer																	*
 * Assignment: Java																	*
 * Group #: 32																		*
 * Members: Saurabh Tomar, Kaylee Stelter, Kowther Hassan, Matthew Mullins, Tsz Lam	*
 * Description:																		*
 * 		-Class creates a parse tree from a list of Tokens. 				            *
 *  																				*
 * Contain methods:																	*
 *      + ParseTreeConstructor(Tokenizer)                                           *
 *      + createParseTree():Node<Token>                                             *
 *      + getInput():String                                                         *
 * 																					*
 ************************************************************************************/

package parser;

import java.io.*;
import java.util.*;
import java.lang.*;
import java.lang.reflect.*;
import java.text.*;

import static parser.Utilities.*;

/**
 * Class which creates a parse tree for the command-line input.
 */
public class ParseTreeConstructor {
	
    Tokenizer tokenizer;
    ArrayList<Token> tokenList;

    /**
     * @param tokenizer - Represents the object which contains the tokens (components) to be sorted into the parse tree
     */
	public ParseTreeConstructor(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
        tokenList = tokenizer.getTokens();	
	}
    
	/**
	 * Sorts the tokens derived from the command-line input into the parse tree
	 * @return - Node representing the root node of the parse tree
	 * @throws Exception - handles all thrown exceptions
	 */
	// If a node has children, it is a method call, and those children are its parameters (which may be additional method calls).
    public Node<Token> createParseTree () throws Exception{
		
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
				// Token is not of the datatype Integer, Float, String, or method
				case "unidentified":
					
					if (isLong(token.getName())) {
						if (!inIntRange(token.getName())) {
							throw new ParserException("Integer out of range", token.getIndex() - (token.getName().length()-1), tokenizer.getInput());
						}
					}
					
					if (isDouble(token.getName())) {
						if (!inFloatRange(token.getName())) {
							throw new ParserException("Float out of range", token.getIndex() - (token.getName().length()-1), tokenizer.getInput());
						}
					}
					
					
                    if (currentTree.getData() == null)  {  // 
                        // treat the unidentified as a method, add to tree, will be dealt with when parse tree is evaluated
                        currentTree.setData( token );		// adds data to parent node
                        previousType = stringType;
                        break;
                    } else {
                        // treat the unidentified as an invalid parameter, throw exception here
                        throw new ParserException("Invalid datatype encountered", token.getIndex() - (token.getName().length()-1), tokenizer.getInput());
                    }
			}
		}
		return currentTree;
    }
    
    /**
     * @return - String represents the original command-line input
     */
    public String getInput(){
    	return tokenizer.getInput();
    }
}