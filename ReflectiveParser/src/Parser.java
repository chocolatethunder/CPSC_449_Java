import java.util.ArrayList;
import java.util.Stack;

/**
 * Creates a parse tree
 *
 * @param <T>
 */
public class Parser<T extends Comparable<T>> {
	ArrayList<Token> tokenList;
	

	/**
	 * constructor default
	 */
	public Parser( ) {
		this.tokenList = new ArrayList<Token>();
	}
	
	
	/**
	 * constructor
	 * @param tokenList
	 */
	public Parser( ArrayList<Token> tokenList) {
		this.tokenList = tokenList;
	}
	
	
	/**
	 * sets the tokenlist
	 * @param list
	 */
	public void setTokenList( ArrayList<Token> list) {
		this.tokenList = list;
	}
	
	
	/**
	 * returns the tokenList
	 * @return 
	 */
	public ArrayList<Token> getTokenList() {
		return this.tokenList;
	}
	
	
	/**
	 * Creates a parse tree. Error checking has been done. 
	 * Takes a "legal" (i.e. correct brackets) token list.
	 * @return
	 */
	public Node<Token> createParseTree () {
		
		Stack<Node<Token>> pStack = new Stack<Node<Token>>( );	// parent node stack
		Stack<String> bStack = new Stack<String>( );	// bracket stack
		Node<Token> currentTree = new Node<Token>(null);	// make first node
		String previousType = "";
		
		for ( int i = 0; i < tokenList.size( ); i++ ) {
			String type = tokenList.get( i ).getType();
			Token token = tokenList.get( i );
			
			switch ( type ){
				case "openBracket":
					if ( currentTree.getData() != null) {
						currentTree.addChild(new Node<Token>(null));
						ArrayList<Node<Token>> children = currentTree.getChildren();
						children.get(children.size()-1).setParent(currentTree); 		// set childs parent to current tree
						currentTree = children.get(children.size()-1);					// set child to current tree
						pStack.push(currentTree);
						bStack.push( type );
					} else {
						pStack.push( currentTree );
						bStack.push( type );
					}
					previousType = type;
					break;
				case "int":
					currentTree.addChild(new Node<Token>( token ) );	// adds child to tree
					previousType = type;
					break;
				case "string":
					currentTree.addChild(new Node<Token>( token ) );	// adds child to tree
					previousType = type;
					break;
				case "float":
					currentTree.addChild(new Node<Token>( token ) );	// adds child to tree
					previousType = type;
					break;
				case "identifier":
					currentTree.setData( token );		// adds data to parent node
					previousType = type;
					break;
				case "closedBracket":
					if (!bStack.isEmpty() && pStack.size() > 1) {
						bStack.pop();	//	pop of "("
						pStack.pop();
						currentTree = pStack.peek();	// set tree back to parent tree
					} else if (!bStack.isEmpty() && pStack.size() == 1) {
						bStack.pop();
					}
					previousType = type;
					break;
					
					
			}
		}
		return currentTree;
	}
	
    
    

	
	/**
	 *  preoder traversal to print the tree
	 * @param t
	 */
	public void treeToString(Node<Token> t, Node<Token> parent ){
		
		if (parent != null) {
			System.out.println( "PARENT -> " +  t.getData().getName() + " child of " + parent.getData().getName());		// print parent
		} else {
			System.out.println( "PARENT -> " +  t.getData().getName());		// print parent
			
		}
		ArrayList<Node<Token>> children = t.getChildren();	// get children
		if(t.getChildren().size() > 0) {
			for (int i = 0; i < children.size(); i++) {
				
				if (children.get(i).getData().getType().equals("identifier")) {
					treeToString(children.get(i), t);
				} else {
					System.out.println( "CHILD -> " + children.get(i).getData().getName() + " child of " + t.getData().getName());
				}
					
			}
		}
		
	}
	
	// JUST USED FOR TESTING NOT FOR ASSIGNMENT. WILL REMOVE.
	public String printChildren( Node<Token> parent ) {
		ArrayList<Node<Token>> children = parent.getChildren();	// get children
		String s= "";
		for (int i = 0; i < children.size(); i ++ ) {
			s+= children.get(i).getData().getName() + " ";
		}
		return s;
	}
	
	/**
	 *  Parses a parse tree in the correct order. bottom up
	 *  NOT WORKING PROPERLY!!
	 * @param t
	 */
	public Node<Token> parse(Node<Token> rootNode ){
		if (rootNode.isLeaf()) {
			return rootNode;
		} else {
			for (Node<Token> c : rootNode.getChildren()) {
				if(!c.isLeaf()) {
					parse(c);
				}
				
			}
			/*
			 * *****************************************************************************
			 *  THIS SECTION IS JUST A TEST!!! Needs to be replaced by invoking method code
			 *  Always adds two parameters together!! This only takes integer
			 *  values now. But will be replaced with whatever the method requires. 
			 */
			ArrayList<Node<Token>> children = rootNode.getChildren();		// get children names
			int sum = 0;													// set sum to zero
			
			for (int i = 0; i < rootNode.getChildren().size(); i++) {		// add each child to sum	
				sum += Integer.parseInt(children.get(i).getData().getName());
			}
			
			rootNode.getData().setName(sum + "");	//converts to string before setting rootNode name 
			rootNode.getData().setType("int");		// sets rootNode type to int
			/*
			 * END TEST SECTION
			 *****************************************************************************
			 */
			
			
			// delete all children
			for (Node<Token> c : rootNode.getChildren()) {
				c.deleteNode();
			}
			return rootNode;
		}
		
		
	}
	
	
	// test method for class
	public static void main(String[] args) {
        
		System.out.println("Please enter expression: ");
		UserInput user = new UserInput();
		String input = user.getUserInput();
		System.out.println(input);
		Parser parser = new Parser();
		
		
		if (user.checkBrackets(input) == true) {		// check even number of brackets
			if (user.characterCount(input, '(') != 0) {
				Tokenizer tokenList = new Tokenizer(input);		// create tokenlist
				if (tokenList.checkOrderOfTokens(tokenList.getTokens()) == -1) {		// no errors 
				
					parser.setTokenList(tokenList.getTokens());		// set tokenlist to parser
					Node<Token> parseTree = parser.createParseTree();
					System.out.println("\nPRINT PARSE TREE:");
					parser.treeToString(parseTree, null);	// print parse tree
					System.out.println("\nPARSE TREE EVALUATION FINAL RESULT:");
					Node<Token> finalParse = parser.parse(parseTree);
					System.out.println("Parse result: " + finalParse.getData().getName());
					System.out.println("Parse result type: " + finalParse.getData().getType());
				
				} else
					System.out.println("there is an error in format at index: " + tokenList.checkOrderOfTokens(tokenList.getTokens()) );	
				
				//System.out.println(parser.parse();
			} else if (user.characterCount(input, '(') == 0) {
				System.out.println("no brackets");
			}
			
		} else {
			System.out.println("no matching brackets");
		}
		
		

        
   }

}
