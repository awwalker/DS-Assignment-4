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
	 * 
	 * @param args
	 * @throws PostFixException
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
							(line.toString().matches("^[() | \\p{Digit}][\\+\\-\\*() / \\p{Digit}\\p{javaWhitespace}]*$")) ) //make sure line starts with digit or parantheses and only contains digits/operands/spaces/parentheses
					{
						try{
							//Can probably move / delete this line
							if(ExpressionTools.testMatchingBrackets(line)){
								
								StringBuilder postFix = ExpressionTools.convertToPostfix(line);
								
								try{
									Integer result = ExpressionTools.evaluateExpression(postFix.toString().trim());
									out.print(result + "\n");
									
								}catch(PostFixException e){
									if(e.getMessage().equals("Divide By Zero")){
										out.write("UNDEFINED\n");
									}
									else
										out.write(e.getMessage());
								}
							}else{
								out.write("INVALID\n");
							}
							
						}catch(PostFixException e){
							out.write(e.getMessage());
						}
					}
					else{
						out.write("INVALID\n");
					}
				}
				out.flush();
				out.close();
				
			}catch(ArrayIndexOutOfBoundsException e){
				System.out.println("Error: missing output file name");
			}catch(IOException e){
				System.out.printf("Error: %s does not exist", args[1]);
			}
			in.close();
			
		}catch(ArrayIndexOutOfBoundsException e){
			System.out.println("Error: missing input file name");
		}catch(IOException e){
			System.out.printf("Error: %s does not exist", args[0]);
		}
	}
}
