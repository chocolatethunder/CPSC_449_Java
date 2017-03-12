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
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	public Node<Token> parse(Node<Token> rootNode, Class o ) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		
		if (rootNode.isLeaf()) {
			// TODO this doesn't take into account methods with no arguments!
			// need to check if the node is a method with no args, if it is, need
			// to invoke it here. so like if (rootNode.isLeaf() && isMethod()) then invoke method!
			return rootNode;
		}
		else {
			for (Node<Token> c : rootNode.getChildren()) {
				if(!c.isLeaf()) {
					parse(c, o);
				}
			}
			
			Class obj = o; // suppose to be the jar file that contains all methods
			int Index = 0;
			Method[] methods = o.getMethods(); // list of methods
			
			// check if the method is valid
			if (isMethod(rootNode.getData().getName(), obj))
			{
				for (int idk = 0; idk < methods.length; idk++)
				{
					if (rootNode.getData().getName().equals(methods[idk].getName()))
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
					//if(validParamType(children.get(i).getData().getType(), children.get(i).getData().getName(), obj, i))	DOESNT WORK!!!!!!! NEED FIXING!!!!
					//{	
						// if so, convert the node into the proper type, then store into args
						args[i] = convert(children.get(i).getData().getName(), children.get(i).getData().getType());
					//}
                    //else
                    //{ // TODO: error handling
                    //}
				}
			}
			else
			{ // TODO: error handling
            }
			
			// delete all children
			for (Node<Token> c : rootNode.getChildren()) { c.deleteNode(); }
			
			
			// invoke the method, and store the result
			Object result = methods[Index].invoke(rootNode.getData().getName(), args);
            
            //String result = (temp.getClass() == String.class) ? temp : "" + temp ;
            
			// store the result into the root node
			rootNode.getData().setName(result+"");
			rootNode.getData().setType(result.getClass());
			if (result.getClass() == String.class) 
				rootNode.getData().setStringType("string");
			else if (result.getClass() == Integer.class)
				rootNode.getData().setStringType("int");
			else if (result.getClass()== Float.class)
				rootNode.getData().setStringType("float");
		
			return rootNode;
		}
	}
	
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
    
    //DONE (Not Tested) ****DOESNT WORK!!*****
    // The following method will check if the argument type is valid for the method
    // type = argument type, s = method's name, o = jar file that contains all valid methods
    public boolean validParamType(Class type, String s, Class cls, int index)
    {
    	Method[] methods = cls.getMethods();
    	//Class[] parameters = null;
    	for (int i = 0; i < methods.length; i++)
    	{
    		String method = methods[i].getName();
    		if (method.equals(s))
    		{
    			Class[] parameters = methods[i].getParameterTypes();
    			if (type == parameters[index - 1])
    	    	{
    				System.out.println(type.getName() + " param " + parameters[index-1].getName() );
    	    		return true;
    	    	}
    			break;
    		}
    	}
    	
    	return false;
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
        if (valueType == Integer.class)
        {
        	try{
        		int temp;
        		int result;
        		
        		if (s.charAt(0) == '-')
        		{
        			temp = (int) Integer.parseInt(s.substring(1, s.length() - 1));
        			result = 0 - temp;
        		}

        		else if (s.charAt(0) == '+')
        		{ result = (int) Integer.parseInt(s.substring(1, s.length() - 1)); }
        		
        		else { result = (int) Integer.parseInt(s); }
        		
        		return result;
            }
            catch (ArithmeticException e)
        	{
            	System.out.println("Int out of range!");
            	System.exit(0);
        	}
        }
        if (valueType == Float.class)
        {
        	try {
        		float temp;
        		float result;
        		
        		if (s.charAt(0) == '-')
        		{
        			temp = Float.parseFloat(s.substring(1, s.length() - 1));
        			result = 0 - temp;
        		}
        		
        		else if (s.charAt(0) == '+')
        		{ result = Float.parseFloat(s.substring(1, s.length() - 1)); }
        		
        		else{ result = Float.parseFloat(s); }
        		
        		return result;
        	}
        	catch (ArithmeticException e) {
        		System.out.println("Float out of range!");
        		System.exit(0);
        	}
        }
        return null;
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
