import java.util.EmptyStackException;
/**
 * This class represents a Stack of type E using
 * references. 
 * @author Aaron Walker
 *
 * @param <E> the type of object that this 
 * class should hold.
 */
public class MyStack<E> {
	private int size; //the number of items in this MyStack object
	private Node<E> head;
	
	/**
	 * No arg constructor simply sets the head to 
	 * null and instantiates size to zero.
	 */
	public MyStack(){
		head = null;
		size = 0;
	}
	
	/**
	 * This method adds an item to the top of the 
	 * MyStack object.
	 * 
	 * @param item the item to be added
	 * @return item the item to be added
	 */
	public E push(E item){
		//insertFront
		if(item != null){//as long as the item has a value
			Node<E> newNode = new Node<E>(item, null);
			if(head == null){//if current stack has no items this item becomes head
				head = newNode;
			}
			else{//make the newNode the first in the Stack and move head reference down 
				newNode.setNext(head);
				head = newNode;
			}
			size++; //increase size
		}
		return item;
	}
	
	/**
	 * This method checks if a MyStack object is empty
	 * @return true if the MyStack is empty, false otherwise
	 */
	public boolean isEmpty(){
		if(head == null){
			return true;
		}
		return false;
	}
	
	/**
	 * This method returns the value at the top of a MyStack
	 * object and takes it off the MyStack.
	 * @return the item at the top of the MyStack
	 * @throws EmptyStackException if a pop is attempted
	 * on an empty stack. 
	 */
	public E pop(){
		//removeFront
		E result = head.getData();
		if(head != null){//make sure stack is not empty
			size --;
			head = head.getNext();
			return result;
		}
		else{
			throw new EmptyStackException(); //otherwise stack is empty
		}
	}
	
	/**
	 * This method is a public wrapper for the
	 * method peek(int index) and simply returns 
	 * the item at the top of the MyStack object
	 * @return the item at the top of the MyStack object
	 */
	public E peek(){
		//get() called with last index
		return peek(size);
	}
	
	/**
	 * This method returns the item held in a MyStack
	 * object at a passed in index
	 * @param index the location of the item in the
	 * MyStack object to return  
	 * @return the item in a MyStack object at the index
	 * passed in
	 * @throws EmptyStackException if peek(int index) is called on 
	 * an empty stack 
	 */
	private E peek(int index){
		//generic get
		Node<E> current = head;
		int count = 0;
		if(head != null){
			while(current != null && count != index){//move through stack
				count += 1;
			}
			if(count == index){
				return current.getData();
			}else{
				throw new EmptyStackException();//otherwise it wasn't here
			}
		}else{
			throw new EmptyStackException();//we have empty stack
		}
	}
	
	/**
	 * This method allows access to the
	 * private size variable of the MyStack 
	 * object
	 * @return size the number of elements in
	 * the MyStack object
	 */
	public int getSize(){
		return size;
	}
	
	/**
	 * This method allows the search of a MyStack
	 * object for a specific object
	 * @param o the object to be searched for
	 * @return the 1-based index of the first location
	 * of o, or -1 if the item is not in the MyStack
	 * object
	 */
	public int search(Object o){
		
		if(!o.equals(null) && head != null){
			if(size == 1){//trivial one item case
				if(o.equals(head.getData())){
					return 1;
				}
				else return -1;
			}
			else{
				Node<E> current = head.getNext();//otherwise
				int count = 0;
				while(current != null && !o.equals(current.getData())){//move through stack
					current = current.getNext();
					count += 1;
				}
				if(current != null){//if its been found
					return count;
				}
			}
		}
		return -1;//wasn't in stack
	}
	/**
	 * This private class represents a single node. 
	 * Because the stack class is the only class 
	 * using the Node class it is encapsulated inside
	 * and declared private so only the Stack class
	 * my have access to it. 
	 * A node may have both a data field and a
	 * reference to the node 'ahead' of it.
	 * @author Aaron Walker
	 *
	 * @param <E> a Node is a generic object and can
	 * take any class as a type. 
	 */
	@SuppressWarnings("hiding")
	private class Node <E> {
		private Node<E> next;
		private E data;
		
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
		 * Allows the private reference to the
		 * Node referenced by this Node to be set
		 * @param next the Node that should be referenced
		 * by this current Node
		 */
		public void setNext(Node<E> next){
			this.next = next;
		}
	}

}


