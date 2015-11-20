/**
 * This class provides an assortment of static methods
 * to enable the conversion of an infix expression to 
 * postfix and for the evaluation of a postfix expression. 
 * @author Aaron Walker and (pieces by) Joanna Klukowska
 *
 */
public class ExpressionTools {
	/**
	 * private no arg constructor so user cannot create
	 * an instance of this class
	 * 
	 */
	private ExpressionTools(){
		
	}

	/**
	 * This method takes in an postfix expression and 
	 * evaluates it into an integer. If the expression
	 * is invalid or undefined the method throws a 
	 * PostFixException with a message indicating what
	 * caused the program to crash. 
	 * @parm expression 
	 * 		A string representation of an postfix
	 * 		expression
	 * @throws PostFixException 
	 * 		A message representing why the program
	 * 		crashed. Tries to catch all possible 
	 * 		errors that could be caused by this method:
	 * 			*Too many operands
	 * 			*Too few operands
	 * 			*Too many operators
	 * 			*Too few operators
	 */
	public static int evaluateExpression(String expression) throws PostFixException{
		StringBuilder integerString = new StringBuilder();
		MyStack<Integer> operands = new MyStack<Integer>();

		for(int i = 0; i < expression.length() ; i++){//move through string
			Character token = expression.charAt(i);
			if(Character.isDigit(token)){//keep track of digits in separate stringbuilder
				integerString.append(token);
				if(integerString.length() == expression.length()){//if num digits is equal to the whole string
					operands.push(Integer.parseInt(integerString.toString()));//add it all...will work guaranteed to be just digits
				}
			}
			//check if there is a space and if we have a number then add it
			else if( (Character.isWhitespace(token) && integerString.length() != 0 ) ){
				operands.push(Integer.parseInt(integerString.toString()));//add it all...will work guaranteed to be just digits
				integerString.setLength(0);//reset number stringbuilder
			}

			else if(ExpressionTools.isOperator(token)){//operator found
				//check if digits in stack
				if(operands.isEmpty()){
					throw new PostFixException("Not enough operands");
				}
				Integer operand2 = operands.pop();
				//check for second digits in stack
				if(operands.isEmpty()){
					throw new PostFixException("Not enough operands");
				}
				Integer operand1 = operands.pop();
				//various operations to be performed
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
					//make sure not dividing by zero
					if(!ExpressionTools.divideByZero(operand2)){
						operands.push(operand1 / operand2);
					} 
				}
			}
		}
		//if no more operands
		if(operands.isEmpty()){
			throw new PostFixException("Not enough operands");
		}
		Integer result = operands.pop();
		//if too many operands
		if(!operands.isEmpty()){
			throw new PostFixException("Too many operands");
		}
		return result;//give back final answer
	}


	/**
	 * This method takes in an infix expression as a parameter
	 * and converts it to a postfix expression.
	 * 
	 * @param infix 
	 * 		the infix expression to be converted as a StringBuilder
	 * @return 
	 * 		postFix the postfix expression as a StringBuilder
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
				if(!operators.isEmpty()){//make sure not calling peek / pop on empty stack
					if(token == '*' || token == '/'){ 
						//if operator is mult. / div. then only 
						//mult. / div. have same precedence only accept those operators
						while(!operators.isEmpty()){
							if(operators.peek() == '*' || operators.peek() == '/'){
								postFix.append(operators.pop() + " ");
							}
							else
								break;
						}
					}
					else if (token == '-' || token == '+'){
						//if operator is sub. / add. then all operators can be accepted
						while(!operators.isEmpty()){
							if(!ExpressionTools.isOpenBracket(operators.peek()) && !ExpressionTools.isClosedBracket(operators.peek()))
								postFix.append(operators.pop() + " ");
							else
								break;
						}
					}
				}
				//push recent token onto stack
				operators.push(token);
			}
			else if(ExpressionTools.isClosedBracket(token)){//if closing bracket
				while(!operators.isEmpty()){
					if(!ExpressionTools.areMatchingBrackets(operators.peek(), token)){//unless its a matching opening bracket
						postFix.append(operators.pop() + " "); //add the operator to final string
					}
					else{
						operators.pop();//if it is a matching bracket simply discard it
						break;
					}
				}
			}
		}
		//remove all left over operators
		while(!operators.isEmpty()){
			postFix.append(operators.pop() + " ");
		}

		return postFix; //give back postfix string
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
				unmatchedOpenBrackets.push(currentChar);
			}

			//if it is a closing bracket, check if the bracket
			//at the top of the stack matches it;
			//if it does, keep going, otherwise string is not valid
			if ( isClosedBracket ( currentChar) ) {

				if (unmatchedOpenBrackets.isEmpty() || !areMatchingBrackets( unmatchedOpenBrackets.pop(), currentChar)  )
					throw new PostFixException( "Unbalanced Brackets" );
			}

		}
		//if the stack is empty at this point, the string is valid,
		//otherwise it has unmatched brackets
		if ( unmatchedOpenBrackets.getSize() == 0 ){ 
			return true;
		}
		else{
			throw new PostFixException( "Unbalanced Brackets" );
		}
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
	 *This method is used to determine if there is a
	 *space between each character (operator / operand /
	 *parantheses) within an infix expression using two 
	 *stacks one of integers and one of tokens.
	 *
	 *@return true if there are spaces between each character
	 *false otherwise
	 * 
	 */
	public static boolean checkSpaces(StringBuilder input){
		String check = input.toString().trim();
		StringBuilder num = new StringBuilder();

		MyStack<Integer> ints = new MyStack<Integer>();
		MyStack<Character> tokens = new MyStack<Character>();

		for(int i = 0; i < check.length(); i++){//move through input
			Character current = check.charAt(i);
			if(Character.isDigit(current)){//if digit is found
				num.append(current);//add digit to separate stringbuilder
				if(i == check.length() - 1){
					ints.push(Integer.parseInt(num.toString()));
				}
			}
			//found operator or other token...not whitespace
			else if(ExpressionTools.isOperator(current) || ExpressionTools.isOpenBracket(current) || ExpressionTools.isClosedBracket(current)){
				tokens.push(current);//add token to appropriate stack
				if(num.length() != 0){//if we have a number ready to add 
					ints.push(Integer.parseInt(num.toString())); //add now
					num.setLength(0); //reset stringbuilder
				}
			}
			else{//have found whitespace now
				if(num.length() != 0){//if num is filled
					ints.push(Integer.parseInt(num.toString())); //add it to stack
					num.setLength(0);//reset stringbuilder
				}
				//take off most recent num if there is one
				if(!ints.isEmpty()){
					ints.pop();
				}
				//if there wasn't a num take off a token
				else if(!tokens.isEmpty()){
					tokens.pop();
				}
			}

		}
		//in order to have correct number of spaces there must be at most one number OR token and nothing of the other kind
		if( ints.getSize() == 1  && tokens.isEmpty()|| (tokens.getSize() == 1 && ints.isEmpty())){
			return true;
		}
		else{//otherwise there were not enough spaces to separate nums and tokens
			return false;
		}
	}
}