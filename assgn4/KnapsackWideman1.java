package assgn4;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

import assgn3.Edge;

public class KnapsackWideman1 {
	
	private int setSize;
	private int sackSize;
	private int[] weightArray;
	private int[] valueArray;
	private int[][] knapsackArray;
	
	public static void main(String[] args) throws IOException {
		//exits if 0 arguments
		  if (0 == args.length) {
			  System.out.println("No input file entered.");
			  System.exit(1);
		  }
		  new KnapsackWideman1(args[0]);
	}
	
	public KnapsackWideman1(String fileName) throws IOException {
		createKnapsackArray(new File(fileName));
		knapsackSolutionSet(new File("KnapsackOutput.txt"));
	}
	
	private void createKnapsackArray(File dataFile)throws IOException{
			Scanner sc = new Scanner(dataFile);
			String[] line = sc.nextLine().split("\\s+");
			while (line[0].equals("c"))
				line = sc.nextLine().split("\\s+");
			setSize = Integer.parseInt(line[0]);
			sackSize = Integer.parseInt(line[1]);
			weightArray = new int[setSize];
			valueArray = new int[setSize];
			for (int i = 0; i < setSize; i++)
				weightArray[i] = sc.nextInt();
			for (int j = 0; j < setSize; j++)
				valueArray[j] = sc.nextInt();
			knapsackArray = new int[setSize+1][sackSize+1];
	}
	
	private void knapsackSolutionSet(File outFile) throws IOException {
		FileWriter fw = new FileWriter(outFile);
		PrintWriter output = new PrintWriter(fw);
		output.println("Knapsack Problem");
		output.println("Number of elements: "+setSize);
		output.println("Max size of sack: "+sackSize);
		output.println("Values: "+Arrays.toString(valueArray));
		output.println("Weights: "+Arrays.toString(weightArray));
		output.println("Max value: "+Arrays.stream(valueArray).sum());
		
		for (int i = 1; i <= setSize; i++) {
			for (int j = 0; j <= sackSize; j++) {
				if (j - weightArray[i-1] >= 0) {
					if (knapsackArray[i-1][j] >= knapsackArray[i-1][j-weightArray[i-1]]+valueArray[i-1]) {
						knapsackArray[i][j] = knapsackArray[i-1][j];
					}
					else {
						knapsackArray[i][j] = knapsackArray[i-1][j-weightArray[i-1]]+valueArray[i-1];
					}
				} 
				else knapsackArray[i][j] = knapsackArray[i-1][j];
			}
		}
		String s = "Optimal Subset: { ";
		int i = setSize;
		int j = sackSize;
		while (i > 0 && j > 0) {
			if (knapsackArray[i][j] != knapsackArray[i-1][j]) {
				s += i+" ";
				i -= 1;
				j -= weightArray[i];
			} else i -= 1;
		}
		s += "}";
		output.println(s);
		output.println("Optimal Value: "+knapsackArray[setSize][sackSize]);
		output.close();
	}
}
