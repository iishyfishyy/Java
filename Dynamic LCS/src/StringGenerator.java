/*
Generates 100 pairs of random strings of letters with lengths between 1 and 100.
*/

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class StringGenerator {

	private static Random srand = new Random();
	
	public static void main(String... args) throws IOException{
		
		PrintWriter aString = new PrintWriter("src/aStrings.txt");
		PrintWriter bString = new PrintWriter("src/bStrings.txt");
		
		for(int i = 1; i <= 200; ++i){
			
			int len = srand.nextInt(100) + 1;
		
			if(i <= 100){
				aString.println(generate(len));
			} else {
				bString.println(generate(len));
			}
			
		}
		
		aString.close();
		bString.close();
		
	}
	
	private static String generate(int len){
		String theString = "";
		for(int j = 0; j < len; ++j){
			theString += Character.toString((char) (srand.nextInt(26) + 65));
			
		}
		return theString;
	}
	
}
