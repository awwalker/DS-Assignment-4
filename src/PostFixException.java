/**
 * This class defines a PostFixException
 * PostFixExceptions will be thrown by methods within the
 * ExpressionTools class in order to alert users what caused 
 * the program to crash.
 * 
 * @author Aaron Walker
 *
 */
@SuppressWarnings("serial")
public class PostFixException extends Exception {
	/**
	 * No arg constructor calls no arg constructor 
	 * of Exception Class
	 */
	public PostFixException(){
		super();
	}
	
	/**
	 * This constructor takes in a String to output
	 * as the label of the error
	 * @param message the String used to describe the error
	 * that caused the Exception to be thrown
	 */
	public PostFixException(String message){
		super(message);
	}
}
