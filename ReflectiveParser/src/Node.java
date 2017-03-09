import java.util.ArrayList;

/**
 * Node class for a parse tree
 *
 * @param <T>
 */

public class Node<T> {
	private ArrayList<Node<T>> myChildren = new ArrayList<Node<T>>();
	private Node<T> myParent = null;
	private T data = null;
	
	//Basic Constructor to Create TreeNode (Only Initializes Data)
	public Node(T data){
		this.data = data;
	}
	
	//Constructor to Create TreeNode (Initializes data and Stores myParent)
	public Node(T data, Node<T> parent){
		this.data = data;
		this.myParent = parent;
	}
	
	//Getter that Returns List of myChildren
	public ArrayList<Node<T>> getChildren(){
		return this.myChildren;
	}
	
	//Getter for TreeNode myParent
	public Node<T> getParent( ){
		return this.myParent;
	}
	//Setter that Sets TreeNode myParent
	public void setParent(Node<T> parent){
		//parent.addChild(this);
		this.myParent = parent;
	}
	
	//Creates a new TreeNode that is a Child of this TreeNode, and sets this TreeNode to be the parent of the child
	public void addChild(T data){
		Node<T> child = new Node<T>(data);
		child.setParent(this);
		this.myChildren.add(child);
	}
	
	//Makes a TreeNode a child of this TreeNode
	public void addChild(Node<T> child){
		child.setParent(this);
		this.myChildren.add(child);
	}
	
	//Returns data from this TreeNode
	public T getData(){
		return this.data;
	}
	
	//Sets the data for a TreeNode
	public void setData(T data){
		this.data = data;
	}
	
	//Returns boolean that determines if parent is null
	//null parent means that this TreeNode is the root
	public boolean isRoot(){
		return (this.myParent == null);
	}
	
	//Returns a boolean that determines if TreeNode is leaf
	//If the TreeNode has no children then it is a leaf node
	public boolean isLeaf(){
		if (this.myChildren.size() == 0)
			return true;
		else
			return false;
	}
	
	//Remove this TreeNode's parent
	public void removeParent(){
		this.myParent = null;
	}
	
	// Deletes node and its children
	public void deleteNode() {
		this.myChildren = null;
		this.data = null;
	}

}
