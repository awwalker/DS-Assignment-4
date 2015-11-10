import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Calculator {
	public static void main(String[] args) throws PostFixException{
		try{
			File read = new File(args[0]);
			Scanner in = new Scanner(read);
			try{
				File write = new File(args[1]);
				PrintWriter out = new PrintWriter(write);
				while(in.hasNext()){
					StringBuilder line = new StringBuilder(in.nextLine() + " ");
					if(!(line.toString().trim().length() == 0)){
						try{
							if(ExpressionTools.testMatchingBrackets(line)){
								
								StringBuilder postFix = ExpressionTools.convertToPostfix(line);
								System.out.println(postFix.toString().trim());
								try{
									Integer result = ExpressionTools.evaluateExpression(postFix.toString().trim());
									out.print(result + "\n");
									
								}catch(PostFixException e){
									if(e.getMessage().equals("Divide By Zero")){
										out.write("UNDEFINED\n");
									}
									else
										out.write("INVALID\n");
								}
							}
							
						}catch(PostFixException e){
							out.write("INVALID\n");
						}
					}
					else{
						out.write("INVALID\n");
					}
				}
				out.flush();
				out.close();
			}catch(ArrayIndexOutOfBoundsException e){
				System.out.println("Erro: missing output file name");
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
