import java.util.ArrayList;
import java.util.Stack;

public class Parser<T extends Comparable<T>> {
	ArrayList<String> tokenList;
	
	public Parser( ArrayList<String> tokenList) {
		this.tokenList = tokenList;
	}
	
	/*
	 * Adapted from Think like a computer scientist parser
	 */
	
	public Node<String> createParseTree () {
		
		// Assuming error checking has been done. Create parse tree
		// takes a "legal" (i.e. correct brackets) token list.
		
		Stack<Node<String>> pStack = new Stack<Node<String>>( );	// parent node stack
		Stack<String> bStack = new Stack<String>( );	// bracket stack
		Node<String> currentTree = new Node<String>(null);	// make first node
		Node<String> currentNode = currentTree;
		
		for ( int i = 0; i < tokenList.size( ); i++ ) {
			String c = tokenList.get( i );
			// CHECKS ON c HERE!!
			switch ( c ){
				case "(":
					if ( currentTree.getData() != null) {
						currentTree.addChild(new Node<String>(null));
						ArrayList<Node<String>> children = currentTree.getChildren();
						currentTree = children.get(children.size()-1);
						pStack.push(currentTree);
						bStack.push( c );
					} else {
						pStack.push( currentTree );
						bStack.push( c );
					}
					//System.out.println(currentTree.getData());
					
					break;
				case "value":
					currentTree.addChild(new Node<String>( c ) );	// adds child to tree
					//System.out.println(currentTree.getData());
					break;
				case "funcCall":
					currentTree.setData( c );		// adds data to parent node
					//System.out.println(currentTree.getData());
					
					break;
				case ")":
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
	

	
	// preoder traversal
	public void treeToString(Node<String> t){
		//if(t == null) return "";
		System.out.println( "PARENT -> " +  t.getData());		// print parent
		ArrayList<Node<String>> children = t.getChildren();	// get children
		//System.out.println(t.getChildren().size());
		
		if(t.getChildren().size() > 0) {
			for (int i = 0; i < children.size(); i++) {
				
				if (children.get(i).getData().equals("funcCall")) {
					System.out.println(children.get(i).getChildren().size());
					treeToString(children.get(i));
				} else {
					System.out.println( "CHILD -> " + children.get(i).getData());
				}
					
			}
		}
		
	}
	
	
	
	public static void main(String[] args) {
        
		Tokenizer tokenizer = new Tokenizer( "(funcCall (funcCall value value) value)" );
        System.out.println(tokenizer.getTokens());
        
        Parser parser = new Parser(tokenizer.getTokens());
        parser.treeToString(parser.createParseTree());
        
   }

}
