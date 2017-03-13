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
	public Node<Token> parse(Node<Token> rootNode, Class o ) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		
		//**** no children but is method****
		if (rootNode.isLeaf() && rootNode.getData().getStringType().equals("identifier")) {
			
			Class obj = o; // class from jar file that contains all methods
			int Index = 0;	// index for methods 
			Method[] methods = o.getMethods(); // list of methods
			
			// check if the method is valid
			if (isMethod(rootNode.getData().getName(), obj))
			{
				for (int idk = 0; idk < methods.length; idk++)
				{
					if (rootNode.getData().getName().equals(methods[idk].getName())) {
						{Index = idk;}		// add method index
						
					}
					
				}
			}
			
			// invoke the method, and store the result
			Object result = methods[Index].invoke(rootNode.getData().getName());
            
            //String result = (temp.getClass() == String.class) ? temp : "" + temp ;
            
			// store the result into the root node
			rootNode.getData().setName(result+"");
			rootNode.getData().setType(result.getClass());
			if (result.getClass() == String.class) 
				rootNode.getData().setStringType("string");
			else if (result.getClass() == int.class)
				rootNode.getData().setStringType("int");
			else if (result.getClass()== float.class)
				rootNode.getData().setStringType("float");
			
			
			return rootNode;
			
			
			
			
			// *** no children but is not method *** 
		} else if ( rootNode.isLeaf()) {
			
			return rootNode;
			
			
			
			
		}
		//*** has children ****
		else {
			for (Node<Token> c : rootNode.getChildren()) {
				if(!c.isLeaf()) {
					parse(c, o);		// recursive call
				}
			}
			
			Class obj = o; // class from jar file that contains all methods
			ArrayList<Integer> Index = new ArrayList<Integer>();	// used for list of methods that match root node
			Method[] methods = o.getMethods(); // list of methods
			
			// check if the method is valid
			if (isMethod(rootNode.getData().getName(), obj))
			{
				for (int idk = 0; idk < methods.length; idk++)
				{
					if (rootNode.getData().getName().equals(methods[idk].getName())) {
						{Index.add(idk);}		// add method index
						
					}
					
				}
			}
			
            //Type returnType = getReturnType(rootNode.getData().getName(), obj);
            
			// Get all children of root node, and store them in a arraylist
			ArrayList<Node<Token>> children = rootNode.getChildren();
			int methodIndex = validParamType(methods,children, Index );		// gets method index that contains the valid params 
			
			if (methodIndex == -1) {
				System.out.println("no matching method call");
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
				Object result = methods[methodIndex].invoke(rootNode.getData().getName(), args);
	            System.out.println(methods[methodIndex].getName() + ": result is " + result);
				// store the result into the root node
				rootNode.getData().setName(result+"");
				
				if (result.getClass().equals(String.class)) { 
					rootNode.getData().setType(String.class);
					rootNode.getData().setStringType("string");
				}else if (result.getClass().equals(Integer.class)) {
					rootNode.getData().setType(int.class);
					rootNode.getData().setStringType("int");
				}else if (result.getClass().equals(Float.class)) {
					rootNode.getData().setType(float.class);
					rootNode.getData().setStringType("float");
				}
			}
			
			
			
				return rootNode;
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
        if (valueType == int.class)
        {
        	try{
        		int result;
        		
        		 result = (int) Integer.parseInt(s); 

        		return result;
            }
            catch (ArithmeticException e)
        	{
            	System.out.println("Int out of range!");
            	System.exit(0);
        	}
        }
        if (valueType == float.class)
        {
        	try {
        		
        		float result;
        		
        		 result = Float.parseFloat(s); 
        		
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
