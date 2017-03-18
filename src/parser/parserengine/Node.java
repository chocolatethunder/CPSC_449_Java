/************************************************************************************
 * CPSC 449 - Winter 2017															*
 * Prof: Rob Kremer																	*
 * Assignment: Java																	*
 * Group #: 32																		*
 * Members: Saurabh Tomar, Kaylee Stelter, Kowther Hassan, Matthew Mullins, Tsz Lam	*
 * Description:																		*
 * 		-Contains a generic class that represents a node.                 			*
 * 																					*
 * Methods:																			*			
 * 		+ Node(T)						        	                        		*
 * 		+ Node(T, Node<T>)                                                          *
 *      + getChildren():ArrayList<Node<T>>                                          *
 *      + getParent():Node<T>                                                       *
 *      + setParent(Node<T>):void                                                   * 
 *      + addChild(Node<T>):void                                                    *
 *      + getData():T                                                               *   
 *      + setData(T)                                                                *
 *      + IsRoot():boolean                                                          *
 * 		+ IsLeaf():boolean                                                          *
 *      + removeParent():void                                                       *
 *      + deleteNode():void                                                         *
 ************************************************************************************/
package parser;

import java.util.ArrayList;

/**
 * Class which represents the Node in the parse tree and contains various methods to manipulate/retrieve
 * data from a node.
 */
public class Node<T> {
	
	private ArrayList<Node<T>> myChildren = new ArrayList<Node<T>>();
	private Node<T> myParent = null;
	private T data = null;
	
	/**
	 * Initializes data of node.
	 * @param data - Represents the node's data
	 */
	public Node(T data) {
		this.data = data;
	}
	
	/**
	 * Initializes data and parents of node.
	 * @param data - Represents the node's data
	 * @param parent - Represents the node's parent node
	 */
	public Node(T data, Node<T> parent) {
		this.data = data;
		this.myParent = parent;
	}
	
	/**
	 * @return - ArrayList representing all the children node's for the node of interest
	 */
	public ArrayList<Node<T>> getChildren() {
		return this.myChildren;
	}
	
	/**
	 * @return - Returns the Node object which is the parent node of the node of interest
	 */
	public Node<T> getParent(){
		return this.myParent;
	}
	
	/**
	 * @param parent - Node representing the parent node of the node of interest
	 */
	public void setParent(Node<T> parent) {
		this.myParent = parent;
	}
	
	/**
	 * Adds a new child node for the node of interest and sets its parent to the current node
	 * @param child - Represents the new child node
	 */
	public void addChild(Node<T> child) {
		child.setParent(this);
		this.myChildren.add(child);
	}
	
	/**
	 * @return - Generic type representing the data contained within a node
	 */
	public T getData() {
		return this.data;
	}
	
	/**
	 * @param data - Generic type representing the data contained within a node
	 */
	public void setData(T data){
		this.data = data;
	}
	
	/**
	 * @return - Boolean representing whether or not the node of interest is the parent node of the tree
	 */
	public boolean isRoot(){
		return (this.myParent == null);
	}
	
	/**
	 * @return - Boolean representing whether or not the node of interest is a leaf node of the tree
	 */
	public boolean isLeaf(){
		if (this.myChildren.size() == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Removes the parent of a node
	 */
	public void removeParent(){
		this.myParent = null;
	}
	
	/**
	 * Deletes a node and all of its children nodes
	 */
	public void deleteNode() {
		this.myChildren = null;
		this.data = null;
	}
}
