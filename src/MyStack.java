
public class MyStack<E> {
	private int size;
	private Node<E> head;
	
	public MyStack(){
		head = null;
		size = 0;
	}
	
	public void push(E item){
		//insertFront
		if(item != null){
			Node<E> newNode = new Node<E>(item, null);
			if(head == null){
				head = newNode;
			}
			else{
				newNode.setNext(head);
				head = newNode;
			}
			size++;
		}
		
	}
	
	public boolean isEmpty(){
		if(head == null){
			return true;
		}
		return false;
	}
	
	public E pop(){
		//removeFront
		E result = head.getData();
		if(head != null){
			size --;
			head = head.getNext();
			return result;
		}
		return null;
	}
	
	public E peek(){
		//get() called with last index
		return peek(size);
	}
	
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
		return null;
	}
	
	public int getSize(){
		return size;
	}
	
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
