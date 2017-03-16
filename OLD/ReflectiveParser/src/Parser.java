import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
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
	
//PARSE TREE CREATOR ********************************************************************************************************************************
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
					
					
			}
		}
		return currentTree;
}
	
//PARSE TREE CREATOR END ************************************************************************************************************************  
    

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
				
				if (children.get(i).getData().getStringType().equals("identifier")) {
					treeToString(children.get(i), t);
				} else {
					System.out.println( "CHILD -> " + children.get(i).getData().getName() + " child of " + t.getData().getName());
				}
					
			}
		}
		
}
	
	


	
//EVALUATOR********************************************************************************************************************************
	/**
	 *  Parses a parse tree in the correct order. bottom up
	 *  
	 * @param t
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	public Node<Token> parse(Node<Token> rootNode, Class obj ) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		Object result = null;
		
		
		//**** no children but is method****
		if (rootNode.isLeaf() && rootNode.getData().getStringType().equals("identifier")) {
			
			
			Method[] methods = obj.getMethods(); // list of methods
			ArrayList<Integer> indices = getMethodIndices ( obj,  rootNode);	// index for methods
			
		
			
			if (indices.size() == 0)	// rootNode does not match any method in the class
			{
				
				// TODO add error handling here, method not valid!
			}
			
			
			// invoke the method, and store the result
			result = methods[indices.get(0)].invoke(rootNode.getData().getName());		// will only have one index if it has no children
			
			return setRootNodeData(rootNode, result);
			
			
			
			
			// *** no children but is not method *** 
		} else if ( rootNode.isLeaf()) {
			
			return rootNode;
			
			
			
			
		}
		//*** has children ****
		else {
			for (Node<Token> c : rootNode.getChildren()) {
				if(!c.isLeaf()) {
					parse(c, obj);		// recursive call
				}
			}
			
			
			Method[] methods = obj.getMethods(); // list of methods
			ArrayList<Integer> indices = getMethodIndices ( obj,  rootNode);	// index for methods
			
		
			if (indices.size() == 0)	// rootNode does not match any method in the class
			{
				
				// TODO add error handling here, method not valid!
			}
			
			
            
			// Get all children of root node, and store them in a arraylist
			ArrayList<Node<Token>> children = rootNode.getChildren();
			
			int methodIndex = validParamType(methods,children, indices );		// gets method index that contains the valid params 
			
			if (methodIndex == -1) {
				
				// node children does not match any method parameters.
				//TODO, throw exception here
			
			
			}else {
				
				Object[] args = new Object[children.size()]; // store arguments from node to an array

				for (int i = 0; i < rootNode.getChildren().size(); i++) {		// convert children from string to their actual type and store in args list
						args[i] = convert(children.get(i).getData().getName(), children.get(i).getData().getType());
				}
				

				// delete all children
				for (Node<Token> c : rootNode.getChildren()) { c.deleteNode(); }
				
				// invoke the method, and store the result
				 result = methods[methodIndex].invoke(rootNode.getData().getName(), args);

	    
			}
			return setRootNodeData(rootNode, result);
		}
	}
	
//EVALUATOR END ********************************************************************************************************************************
    
	
	//DONE 
	// The following method will check and see if user input method is valid
    public boolean isMethod(String s, Class cls)
    {
    	//check and see if s is a valid method
    	Method[] methods = cls.getMethods();
    	//if (!validName(s)) { return false; }
    	
    	for (int i = 0; i < methods.length; i++)
    	{
    		String method = methods[i].getName();
    		if (method.equals(s))
    		{
    			return true;
    		}
    	}
    	return false;
    }
    
    
    
    //DONE
    // The following method will return the index of the method with the correct param types.
    // Or return -1 if no params match
    public int validParamType(Method[] methods, ArrayList<Node<Token>> children, ArrayList<Integer> methodIndex)
    {	int validParam = -1;
    	
    	for (Integer i : methodIndex) {
    		
    		Class<?>[] paramTypes = methods[i].getParameterTypes(); // store types of parameters into an array
			int leng = methods[i].getParameterCount(); // store the count of required parameters
			
			if (leng != children.size()) {
				
				continue;		// if size not the same, go to next iteration
			}
			
			
			for (int j = 0; j < paramTypes.length; j ++) {
				 
				// if param is Integer class then converts it to int.class
				if (paramTypes[j].equals(Integer.class)) {
					paramTypes[j] = int.class;
				}
				
				// if param is Float.class then converts it to float.class
				if (paramTypes[j].equals(Float.class)) {
					paramTypes[j] = float.class;
				}
				
				// compare params to children
				if (paramTypes[j].equals(children.get(j).getData().getType())) {
					validParam = i;		
				} else {
					validParam = -1;
				}
			}
    		
			if (validParam != -1) return validParam;	// returns the valid param
    	}
    	return validParam;
    
    }
    
    
	// check if the method is valid
	public ArrayList<Integer> getMethodIndices (Class obj, Node<Token> rootNode) {
		ArrayList<Integer> index = new ArrayList<Integer>();
		Method[] methods = obj.getMethods(); // list of methods
		if (isMethod(rootNode.getData().getName(), obj))
		{
			for (int idk = 0; idk < methods.length; idk++)
			{
				if (rootNode.getData().getName().equals(methods[idk].getName())) {
					{index.add(idk);}		// add method index
				}
				
			}
		}

		return index;
	}
    
    
  //DONE
    // The following method will take the argument
    // and convert it into the desired type
    public Object convert(String s, Class valueType)
    {
        Class testSub = valueType;
        
        if (valueType == String.class)
        {
            String result = s.substring(1, s.length() - 1);
            return result;
        }
        if (valueType == int.class || valueType == Integer.class )
        {
        	try{
        		int result;
        		
        		 result = (int) Integer.parseInt(s); 

        		return result;
            }
            catch (NumberFormatException e)
        	{
            	System.out.println("Int out of range!");
            	System.exit(0);
        	}
        }
        if (valueType == float.class || valueType == Float.class)
        {
        	try {
        		
        		float result;
        		
        		 result = (float)Float.parseFloat(s); 
        		
        		return result;
        	}
        	catch (ArithmeticException e) {
        		System.out.println("Float out of range!");
        		System.exit(0);
        	}
        }
        return null;
    }
    

    
    // Sets rootNode data with result data. DOES NOT CHECK RETURN TYPE YET
	public Node<Token> setRootNodeData (Node<Token> rootNode, Object data ) {
		
		// store the result into the root node
		rootNode.getData().setName(data+"");
		
		if (data.getClass().equals(String.class)) { 
			rootNode.getData().setType(String.class);
			rootNode.getData().setStringType("string");
		}else if (data.getClass().equals(Integer.class)) {
			rootNode.getData().setType(int.class);
			rootNode.getData().setStringType("int");
		}else if (data.getClass().equals(Float.class)) {
			rootNode.getData().setType(float.class);
			rootNode.getData().setStringType("float");
		}
		
		return rootNode;
	}
    
	
	
	// test method for class
	public static void main(String[] args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, MalformedURLException, NoSuchMethodException, SecurityException, ClassNotFoundException {
        
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
					JarFileLoader jarFileLoader = new JarFileLoader("C:\\Users\\Kowther\\Desktop\\commands.jar", "Commands");
					Class jarClass = jarFileLoader.getC();
					Node<Token> finalParse = parser.parse(parseTree, jarClass);
					System.out.println( "\n" + finalParse.getData().getName());
				
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
