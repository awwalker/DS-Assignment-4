
public class Test {
	public static void main(String[] args) throws PostFixException{
		MyStack<String> testStack = new MyStack<String>();
		int place = testStack.search("Aaron");
		System.out.println(place);
	}
}
