import java.lang.reflect.Method;
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
	
	
	/**
	 *  Parses a parse tree in the correct order. bottom up
	 *  
	 * @param t
	 */
	public Node<Token> parse(Node<Token> rootNode, Object o){
		if (rootNode.isLeaf()) {
			return rootNode;
		}
		else {
			for (Node<Token> c : rootNode.getChildren()) {
				if(!c.isLeaf()) {
					parse(c);
				}
			}
			
			Object obj = o; // suppose to be the jar file that contains all methods
			int Index = 0;
			Class<?> cls = obj.getClass();
			Method[] methods = cls.getMethods(); // list of methods
			
			// check if the method is valid
			if (isMethod(rootNode.getData().getName(), obj))
			{
				for (int idk = 0; idk < methods.length; idk++)
				{
					if (rootNode.getData().getName() == methods[idk].getName())
					{Index = idk;}
				}
			}
			
            //Type returnType = getReturnType(rootNode.getData().getName(), obj);
            
			Class<?>[] paramTypes = methods[Index].getParameterTypes(); // store types of parameters into an array
			int leng = methods[Index].getParameterCount(); // store the count of required parameters
			
			Object[] args = new Object[leng]; // store arguments from node to an array
			
			// Get all children of root node, and store them in a arraylist
			ArrayList<Node<Token>> children = rootNode.getChildren();
			
			if (leng == rootNode.getChildren().size()) // check if there is enough arguments for the method
			{
				for (int i = 0; i < rootNode.getChildren().size(); i++) {		// check each node and see if they are valid to the method	
					if(validParamType(children.get(i).getData().getType(), children.get(i).getData().getName(), obj, i))
					{	
						// if so, convert the node into the proper type, then store into args
						args[i] = convert(children.get(i).getData().getName(), paramTypes[i]);
					}
                    else
                    { // TODO: error handling
                    }
				}
			}
			else
			{ // TODO: error handling
            }
			
			// delete all children
			for (Node<Token> c : rootNode.getChildren()) { c.deleteNode(); }
			
			// invoke the method, and store the result
			Object temp = methods[Index].invoke(obj, args);
            
            String result = (getValueType(temp) == String.Class) ? temp : "" + temp ;
            
			// store the result into the root node
			rootNode.getData().setName(result);
			rootNode.getData().setType("String");
			
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
