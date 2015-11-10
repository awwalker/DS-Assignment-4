/**
 * 
 * @author Aaron Walker
 *
 * @param <E>
 */
public class Node <E> {
	private Node<E> next;
	private E data;
	
	public Node(){
		data = null;
		next = null;
	}
	
	public Node(E data){
		if(data != null){
			this.data = data;
		}
	}
	
	public Node(E data, Node<E> next){
		if(data != null){
			this.data = data;
		}
		if(next != null){
			this.next = next;
		}
	}
	
	public E getData(){
		return data;
	}
	
	public Node<E> getNext(){
		return next;
	}
	
	public void setData(E data){
		this.data = data;
	}
	
	public void setNext(Node<E> next){
		this.next = next;
	}
}
