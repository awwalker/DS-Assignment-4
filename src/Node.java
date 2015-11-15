/**
 * This class represents a single node. 
 * A node may have both a data field and a
 * reference to the node 'ahead' of it.
 * @author Aaron Walker
 *
 * @param <E> a Node is a generic object and can
 * take any class as a type. 
 */
public class Node <E> {
	private Node<E> next;
	private E data;
	
	/**
	 * No arg constructor simply sets both
	 * the Node's data field and the next link 
	 * in the chain to null.
	 */
	public Node(){
		data = null;
		next = null;
	}
	
	/**
	 * One arg constructor takes in one argument
	 * and sets the Node's data field to the arg
	 * @param data the data the Node should 
	 * encapsulate.
	 */
	public Node(E data){
		if(data != null){
			this.data = data;
		}
	}
	
	/**
	 * Two arg constructor takes in both a data 
	 * field and a reference to the next Node 
	 * @param data the data meant to be encapsulated
	 * by this Node
	 * @param next the Node that should be referenced
	 * by this Node
	 */
	public Node(E data, Node<E> next){
		if(data != null){
			this.data = data;
		}
		if(next != null){
			this.next = next;
		}
	}
	
	/**
	 * Allows access to the private data
	 * field of a Node
	 * @return data the data encapsulated in this Node
	 */
	public E getData(){
		return data;
	}
	
	/**
	 * Allows access to the private reference
	 * this Node makes to the next Node
	 * @return the Node referenced by this Node
	 */
	public Node<E> getNext(){
		return next;
	}
	
	/**
	 * Allows the private data field of 
	 * this Node to be set
	 * @param data the data that this Node
	 * should encapsulate
	 */
	public void setData(E data){
		this.data = data;
	}
	/**
	 * Allows the private reference to the
	 * Node referenced by this Node to be set
	 * @param next the Node that should be referenced
	 * by this current Node
	 */
	public void setNext(Node<E> next){
		this.next = next;
	}
}
