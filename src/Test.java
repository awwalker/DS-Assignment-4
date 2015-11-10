
public class Test {
	public static void main(String[] args) throws PostFixException{
		MyStack<String> testStack = new MyStack<String>();
		
		testStack.push("Aaron");
		System.out.println(testStack.search("Aaron"));
		testStack.pop();
		System.out.println(testStack.search("Aar"));
	}
}
