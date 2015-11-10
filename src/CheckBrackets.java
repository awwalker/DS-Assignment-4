
public class CheckBrackets {

	/**
	 * Determine if the string passed as a paramer contains
	 * matching brackets or not.
	 * @param line 
	 *    String that contains text to be tested for the
	 *    matching brackets.
	 * @return
	 *    true, if the fileToParse contains only matching brackets.
	 *    false, if it contains unmatched brackets.
	 */
	public static boolean testMatchingBrackets(StringBuffer line) {
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
				if ( !areMatchingBrackets( unmatchedOpenBrackets.pop(), currentChar) )
					return false;
			}
			//if the character is anything else, ignore it
			
		}
		//if the stack is empty at this point, the string is valid,
		//otherwise it has unmatched brackets
		if ( unmatchedOpenBrackets.getSize() == 0 ) 
			return true;
		else
			return false;
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
}
