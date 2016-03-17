import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class DynamicLCS {

	public static void main(String... args) throws IOException{
		
		StringGenerator.main();
		
		File fileA = new File("src/aStrings.txt");
		File fileB = new File("src/bStrings.txt");
		Scanner scannerA = new Scanner(fileA);
		Scanner scannerB = new Scanner(fileB);
		PrintWriter printResult = new PrintWriter("src/results.txt");		
		
		try{
			int i = 0;
			while(scannerA.hasNextLine() && scannerB.hasNextLine()){
				printResult.println("Pair #" + (i+1));
				findLCS(scannerA.nextLine().toCharArray(), scannerB.nextLine().toCharArray(), printResult);
				printResult.println("\n");
				++i;
			}
		} finally {
			scannerA.close();
			scannerB.close();
			printResult.close();
			System.out.println("Done");
		}
		
	}
	
	public static void findLCS(char[] aString, char[] bString, PrintWriter printResult){
		
		int[][] indexes = new int[aString.length + 1][bString.length + 1];
		String[][] traversal = new String[aString.length + 1][bString.length + 1];
		
		for(int i = 0 ; i < aString.length + 1; ++i){
			indexes[i][0] = 0;
			traversal[i][0] = "0";
		}
		
		for(int i = 0 ; i < bString.length + 1; ++i){
			indexes[0][i] = 0;
			traversal[0][i] = "0";
		}
		
		for(int i = 1; i < aString.length + 1; ++i){
			for(int j = 1; j < bString.length + 1; ++j){
				
				if(aString[i-1] == bString[j-1]){
					indexes[i][j] = indexes[i - 1][j - 1] + 1;
					traversal[i][j] = "BRIDGE";
				} else {
					indexes[i][j] = (indexes[i - 1][j] > indexes[i][j - 1] ? indexes[i - 1][j] : indexes[i][j - 1]);
					traversal[i][j] = (indexes[i][j] == indexes[i - 1][j] ? "TOP" : "LEFT");
				}
				
			}
		}
		
		printSolution(indexes, traversal, aString, bString, printResult);
		
	}
	
	private static void printSolution(int[][] solution, String[][] traversal, char[] aStr, char[] bStr, PrintWriter printResult) {
		
		int aLen = aStr.length, bLen = bStr.length;
		
		printResult.println("Length of LCS: " + solution[aLen][bLen]);
		
		if(solution[aLen][bLen] == 0)
			return;
		
		for(int i = 0; i < aStr.length + 1; ++i){
			for(int j = 0; j < bStr.length + 1; ++j){
				printResult.print(solution[i][j]);
			}
			printResult.println();
		}
		
		String theLCS = "";
		
		while(traversal[aLen][bLen] != "0"){
			
			String temp = traversal[aLen][bLen];
			
			if(temp == "BRIDGE"){
				theLCS = aStr[aLen - 1] + theLCS;
				--bLen;
				--aLen;
			} else if (temp == "LEFT"){
				--bLen;
			} else if(temp == "TOP"){
				--aLen;
			}
		}
		printResult.println("The LCS is: " + theLCS);
		
	}
	
}
