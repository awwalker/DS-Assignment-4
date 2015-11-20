import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * This class represents a Calculator and holds the main
 * method for this project. The class is designed to read in
 * two files an input and output file and convert infix 
 * expressions in the input file to either an explicit value,
 * or "UNDEFINED\n", or "INVALID\n" depending on the errors 
 * encountered using methods from the ExpressionTools class.
 * 
 * @author Aaron Walker
 *
 */
public class Calculator {
	/**
	 * This is the main method of this assignment
	 * It calls methods from the ExpressionTools class 
	 * in order to transform and evaluate infix 
	 * expressions in the passed in input file
	 * to postfix and then write the result
	 * of said expression to the provided output file.
	 * @param args This main method expects two arguments:
	 * 		*input file containing either infix expressions 
	 * 		with all tokens separated by spaces or blank lines
	 * 		*output file to have results / INVALID / UNDEFINED
	 * 		written to. 
	 * @throws PostFixException several of the methods used 
	 * from the ExpressionTools class may throw PostFixExceptions
	 * if they encounter different types of errors. These errors
	 * are caught in the main method and for the line that caused
	 * the error (if error was anything other than / by 0) 
	 * INVALID is written to the output file. If it was a / by 0 
	 * error UNDEFINED is written to the output file. 
	 * 
	 * @author Aaron Walker
	 */
	public static void main(String[] args) throws PostFixException{
		try{
			File read = new File(args[0]);
			Scanner in = new Scanner(read);
			try{
				File write = new File(args[1]);
				PrintWriter out = new PrintWriter(write);
				while(in.hasNext()){
					StringBuilder line = new StringBuilder(in.nextLine() + " ");
					//pre-processing 
					if( ExpressionTools.checkSpaces(line) && //check spaces between characters
							!(line.toString().trim().length() == 0) &&  //make sure line is not empty
							//make sure line starts with digit or parantheses and only contains digits/operands/spaces/parentheses
							//and ends in only parantheses digits or whitespace
							(line.toString().trim().matches("^[() | \\p{Digit}]([\\+\\-\\*() / \\p{Digit}\\p{javaWhitespace}]*)[() \\p{Digit}]$")) ) 
					{
						try{
							//Make sure correct / balanced parantheses
							if(ExpressionTools.testMatchingBrackets(line)){
								//convert line
								StringBuilder postFix = ExpressionTools.convertToPostfix(line);
								try{
									//evaluate line
									Integer result = ExpressionTools.evaluateExpression(postFix.toString().trim());
									//output result
									out.print(result + "\n");
									
								}catch(PostFixException e){//if evaluating throws PostFixException
									if(e.getMessage().equals("Divide By Zero")){//if error was divide by 0
										out.write("UNDEFINED\n"); //output file gets UNDEFINED
									}
									else{
										out.write("INVALID\n"); //if not divide by 0 standard INVALID
									}
								}
							}else{
								out.write("INVALID\n");//if not matching brackets line is INVALID
							}
							
						}catch(PostFixException e){//if line cannot be converted line is INVALID
							out.write("INVALID\n");
						}
					}
					else{
						out.write("INVALID\n");//if line is faulty in preprocessing line is INVALID
					}
				}
				//close writer
				out.flush();
				out.close();
				
			}catch(ArrayIndexOutOfBoundsException e){//faulty output files get caught
				System.out.println("Error: missing output file name");
			}catch(IOException e){
				System.out.printf("Error: %s does not exist", args[1]);
			}
			in.close();
			
		}catch(ArrayIndexOutOfBoundsException e){//faulty input files get caught
			System.out.println("Error: missing input file name");
		}catch(IOException e){
			System.out.printf("Error: %s does not exist", args[0]);
		}
	}
}
