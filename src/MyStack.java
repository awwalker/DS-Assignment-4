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
	 */
	public void push(E item){
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
		if(head != null){
			size --;
			head = head.getNext();
			return result;
		}
		else{
			throw new EmptyStackException();
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
		while(current != null && count != index){
			count += 1;
		}
		if(count == index){
			return current.getData();
		}
		throw new EmptyStackException();
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
			if(size == 1){
				if(o.equals(head.getData())){
					return 1;
				}
				else return -1;
			}
			else{
				Node<E> current = head.getNext();
				int count = 0;
				while(current != null && !o.equals(current.getData())){
					current = current.getNext();
					count += 1;
				}
				if(current != null){
					return count;
				}
			}
		}
		return -1;
	}
}
