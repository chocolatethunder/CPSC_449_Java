import java.util.ArrayList;
import java.util.Stack;

/**
 * Creates a parse tree
 * @author Kowther
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
		//Node<Token> currentNode = currentTree;		// set currentNode to empty tree
		
		for ( int i = 0; i < tokenList.size( ); i++ ) {
			String type = tokenList.get( i ).getType();
			Token token = tokenList.get( i );
			// CHECKS ON c HERE!!
			switch ( type ){
				case "openBracket":
					if ( currentTree.getData() != null) {
						currentTree.addChild(new Node<Token>(null));
						ArrayList<Node<Token>> children = currentTree.getChildren();
						currentTree = children.get(children.size()-1);
						pStack.push(currentTree);
						bStack.push( type );
					} else {
						pStack.push( currentTree );
						bStack.push( type );
					}
					
					break;
				case "int":
					currentTree.addChild(new Node<Token>( token ) );	// adds child to tree
					
					break;
				case "string":
					currentTree.addChild(new Node<Token>( token ) );	// adds child to tree
					
					break;
				case "float":
					currentTree.addChild(new Node<Token>( token ) );	// adds child to tree
					
					break;
				case "identifier":
					currentTree.setData( token );		// adds data to parent node
					
					
					break;
				case "closedBracket":
					if (!bStack.isEmpty() && pStack.size() > 1) {
						bStack.pop();	//	pop of "("
						pStack.pop();
						currentTree = pStack.peek();	// set tree back to parent tree
					} else if (!bStack.isEmpty() && pStack.size() == 1) {
						bStack.pop();
					}
					
					break;
					
					
			}
		}
		return currentTree;
	}
	
    
    

	
	/**
	 *  preoder traversal to print the tree
	 * @param t
	 */
	public void treeToString(Node<Token> t){
		//if(t == null) return "";
		System.out.println( "PARENT -> " +  t.getData().getName());		// print parent
		ArrayList<Node<Token>> children = t.getChildren();	// get children
		
		
		if(t.getChildren().size() > 0) {
			for (int i = 0; i < children.size(); i++) {
				
				if (children.get(i).getData().getType().equals("identifier")) {
					treeToString(children.get(i));
				} else {
					System.out.println( "CHILD -> " + children.get(i).getData().getName() + " of " + t.getData().getName());
				}
					
			}
		}
		
	}
	
	
	public static void main(String[] args) {
        
		System.out.println("Please enter expression: ");
		UserInput user = new UserInput();
		String input = user.getUserInput();
		System.out.println(input);
		Parser parser = new Parser();
		
		
		if (user.checkBrackets(input) == true) {
			if (!user.twoConsecutive(input, '(')) {
				if (user.characterCount(input, '(') != 0) {
					Tokenizer tokenList = new Tokenizer(input);		// create tokenlist
					parser.setTokenList(tokenList.getTokens());		// set tokenlist to parser
					parser.treeToString(parser.createParseTree());	// print parse tree
				} else if (user.characterCount(input, '(') == 0) {
					System.out.println("no brackets");
				}
			} else {
				System.out.println("two consecutive open brackets, illegal");
			}
		} else {
			System.out.println("no matching brackets");
		}
		

        
   }

}
