import java.util.*;

public class TreeNode<T>{
	private List<TreeNode<T>> myChildren = new ArrayList<TreeNode<T>>();
	private TreeNode<T> myParent = null;
	private T data = null;
	
	//Basic Constructor to Create TreeNode (Only Initializes Data)
	public TreeNode(T data){
		this.data = data;
	}
	
	//Constructor to Create TreeNode (Initializes data and Stores myParent)
	public TreeNode(T data, TreeNode<T> parent){
		this.data = data;
		this.myParent = parent;
	}
	
	//Getter that Returns List of myChildren
	public List<TreeNode<T>> getChildren(){
		return this.myChildren;
	}
	
	//Setter that Sets TreeNode myParent
	public void setParent(TreeNode<T> parent){
		parent.addChild(this);
		this.myParent = parent;
	}
	
	//Creates a new TreeNode that is a Child of this TreeNode, and sets this TreeNode to be the parent of the child
	public void addChild(T data){
		TreeNode<T> child = new TreeNode<T>(data);
		child.setParent(this);
		this.myChildren.add(child);
	}
	
	//Makes a TreeNode a child of this TreeNode
	public void addChild(TreeNode<T> child){
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
}
