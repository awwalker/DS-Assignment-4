/**
 * This class provides an assortment of static methods
 * to enable the conversion of an infix expression to 
 * postfix and for the evaluation of a postfix expression. 
 * @author Aaron Walker
 *
 */
public class ExpressionTools {
	
	/**
	 * @throws PostFixException 
	 * 
	 */
	public static int evaluateExpression(String expression) throws PostFixException{
		StringBuilder integerString = new StringBuilder();
		MyStack<Integer> operands = new MyStack<Integer>();

		for(int i = 0; i < expression.length() ; i++){
			Character token = expression.charAt(i);
			if(Character.isDigit(token)){
				integerString.append(token);
				if(integerString.length() == expression.length()){
					operands.push(Integer.parseInt(integerString.toString()));
				}
			}

			else if( (Character.isWhitespace(token) && integerString.length() != 0 ) ){
				operands.push(Integer.parseInt(integerString.toString()));
				integerString.setLength(0);
			}

			else if(ExpressionTools.isOperator(token)){
				if(operands.isEmpty()){
					throw new PostFixException("Not enough operands1");
				}
				Integer operand2 = operands.pop();
				if(operands.isEmpty()){
					throw new PostFixException("Not enough operands2");
				}
				Integer operand1 = operands.pop();
				if(token == '+'){
					operands.push(operand2 + operand1);

				}
				else if(token == '*'){
					operands.push(operand2 * operand1);
				}
				else if(token == '-'){
					operands.push(operand1 - operand2);
				}
				else if(token == '/'){
					if(!ExpressionTools.divideByZero(operand2)){
						operands.push(operand1 / operand2);
					} 
				}
			}
		}
		//System.out.println(operands.peek());
		if(operands.isEmpty()){
			throw new PostFixException("Not enough operands3");
		}
		Integer result = operands.pop();
		if(!operands.isEmpty()){
			throw new PostFixException("Too many operands");
		}
		return result;
	}


	/**
	 * This method takes in an infix expression as a parameter
	 * and converts it to a postfix expression.
	 * 
	 * @param infix the infix expression to be converted as a StringBuilder
	 * @return postFix the postfix expression as a StringBuilder
	 */
	public static StringBuilder convertToPostfix(StringBuilder infix){
		MyStack<Character> operators = new MyStack<Character>();

		StringBuilder postFix = new StringBuilder(); //keep track of the whole postFix expression
		StringBuilder integerString = new StringBuilder(); //build up numbers as they come in case they are multiple digits

		for(int i = 0; i < infix.length(); i++){
			Character token = infix.charAt(i); //the token we are on
			
			if( Character.isDigit(token) ){ //if the current token is a digit add to number string
				integerString.append(token);
			}

			if( Character.isWhitespace(token) && (integerString.length() != 0)){//if we have a space and the number string is not empty
				postFix.append(integerString + " "); //add the whole number and a space to expression
				integerString.setLength(0); //recycle stringbuilder obj.
			}

			else if(ExpressionTools.isOpenBracket(token)){//if left bracket 
				operators.push(token); //push to operator stack
			}

			else if(ExpressionTools.isOperator(token)){//if the token is an operator
				if(!operators.isEmpty()){
					if(token == '*' || token == '/'){
						while(operators.peek() == '/' || operators.peek() == '*'){
							postFix.append(operators.pop() + " ");

						}
					}
					else if (token == '-' || token == '+'){
						while(!operators.isEmpty()){
							if(!ExpressionTools.isOpenBracket(operators.peek()))
								postFix.append(operators.pop() + " ");
							else
								break;
						}
					}
				}
				operators.push(token);
			}
			else if(ExpressionTools.isClosedBracket(token)){
				while(!operators.isEmpty()){
					if(!ExpressionTools.areMatchingBrackets(operators.peek(), token)){
						postFix.append(operators.pop() + " ");
					}
					else{
						operators.pop();
						break;
					}
				}
			}
		}
		while(!operators.isEmpty()){
			postFix.append(operators.pop() + " ");
		}

		return postFix;
	}

	/**
	 * Determine if the string passed as a parameter contains
	 * matching brackets or not.
	 * @param line 
	 *    String that contains text to be tested for the
	 *    matching brackets.
	 * @return
	 *    true, if the fileToParse contains only matching brackets.
	 * @throws
	 * 	  PostFixException if there are unmatched brackets.
	 */
	public static boolean testMatchingBrackets(StringBuilder line) throws PostFixException {
		//stack to hold unmatched open brackets
		MyStack<Character> unmatchedOpenBrackets = new MyStack<Character>();
		char currentChar;
		for (int i = 0; i < line.length(); i++ ) {

			currentChar = line.charAt(i);

			//if the current character is an opening
			//bracket, add it to the stack
			if ( isOpenBracket( currentChar )){
				//System.out.println(unmatchedOpenBrackets);

				unmatchedOpenBrackets.push(currentChar);

			}

			//if it is a closing bracket, check if the bracket
			//at the top of the stack matches it;
			//if it does, keep going, otherwise string is not valid
			if ( isClosedBracket ( currentChar) ) {
				//System.out.println(unmatchedOpenBrackets);
				//System.out.println(unmatchedOpenBrackets.peek()+ "  " + currentChar  );
				if (unmatchedOpenBrackets.isEmpty() || !areMatchingBrackets( unmatchedOpenBrackets.pop(), currentChar)  )
					throw new PostFixException("Unbalanced Brackets");
			}
			//if the character is anything else, ignore it

		}
		//if the stack is empty at this point, the string is valid,
		//otherwise it has unmatched brackets
		if ( unmatchedOpenBrackets.getSize() == 0 ) 
			return true;
		else
			throw new PostFixException("Unbalanced Brackets");
		//return false;
	}

	/**
	 * Determine if character passed as a parameter is an open bracket.
	 * @param c
	 *   character to be tested
	 * @return
	 *   true if c is an open bracket: (, { or [
	 *   false if it is any other character
	 */
	private static boolean isOpenBracket(Character c) {
		if (c == '(' || c == '{' || c == '['  )
			return true;
		return false;
	}



	/**
	 * Determine if character passed as a parameter is a close bracket.
	 * @param c
	 *   character to be tested
	 * @return
	 *   true if c is a close bracket: ), } or ]
	 *   false if it is any other character
	 */
	private static boolean isClosedBracket(Character c) {
		if (c == ')' || c == '}' || c == ']'  )
			return true;
		return false;
	}


	/**
	 * Determines if the characters passed as parameters
	 * are a set of matching open and close brackets
	 * @param open
	 *   character that is potentially an open bracket
	 * @param close
	 *   character that is potentially a close bracket
	 * @return
	 *   true, if the characters are brackets and are matching
	 *   false, otherwise
	 */
	private static boolean areMatchingBrackets(Character open, Character close) {
		if (open == null || close == null)
			return false;
		if (       ( open == '(' && close == ')' )
				|| ( open == '{' && close == '}' )
				|| ( open == '[' && close == ']' )
				)
			return true;
		return false;
	}
	/**
	 * 
	 * @param c
	 * @return
	 */
	private static boolean isOperator(Character c){
		if( c == '+' || c == '/' || c == '*' || c == '-')
			return true;
		return false;
	}

	/**
	 * Determines if the character passed as a parameter is 0
	 * to check if expression wants to divide by 0
	 * @param denominator Character to check
	 * @return false if Character is not 0
	 * @throws PostFixException if Character is 0
	 */
	public static boolean divideByZero(int denominator) throws PostFixException{
		if ( denominator == 0 )
			throw new PostFixException("Divide By Zero");
		return false;
	}
	
	/**
	 *
	 * 
	 */
	public static boolean checkSpaces(StringBuilder input){
		String check = input.toString().trim();
		StringBuilder num = new StringBuilder();
		MyStack<Integer> ints = new MyStack<Integer>();
		MyStack<Character> tokens = new MyStack<Character>();
		//MyStack<Character> spaces = new MyStack<Character>();
		
		for(int i = 0; i < check.length(); i++){
			Character current = check.charAt(i);
			if(Character.isDigit(current)){
				num.append(current);
				if(i == check.length() - 1){
					ints.push(Integer.parseInt(num.toString()));
				}
			}
			else if(ExpressionTools.isOperator(current) || ExpressionTools.isOpenBracket(current) || ExpressionTools.isClosedBracket(current)){
				tokens.push(current);
				if(num.length() != 0){
					ints.push(Integer.parseInt(num.toString()));
					num.setLength(0);
				}
			}
			else{
				if(num.length() != 0){
					ints.push(Integer.parseInt(num.toString()));
					num.setLength(0);
				}
				if(!ints.isEmpty()){
					ints.pop();
				}
				else if(!tokens.isEmpty()){
					tokens.pop();
				}
			}

		}
		//close but not yet
		
		if( ints.getSize() == 1  && tokens.isEmpty()|| (tokens.getSize() == 1 && ints.isEmpty())){
			
			return true;
		}
		else{
			return false;
		}
	}
}