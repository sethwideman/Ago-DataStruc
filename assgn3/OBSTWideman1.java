package assgn3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class OBSTWideman1 {
	
	private int numKeys;
	private String[] keys;
	private double[] probabilities;
	private double[][] cost;
	
	public static void main(String[] args) {
		//exits if 0 arguments
		  if (0 == args.length) {
			  System.out.println("No input file entered.");
			  System.exit(1);
		  }
		  new OBSTWideman1(args[0]);
	}
	
	public OBSTWideman1(String filename) {
		  readFile(new File(filename));
		  constructMatrix();
		  printMatrix();
		  printTree(0, numKeys-1);
	}
	
	private void readFile(File file){
		try {
			Scanner sc = new Scanner(file);
			numKeys = sc.nextInt();
			keys = new String[numKeys];
			probabilities = new double[numKeys];
			double sum = 0.0;
			for (int i = 0; i < numKeys; i++)
				keys[i] = sc.next();
			for (int j = 0; j < numKeys; j++) {
				probabilities[j] = sc.nextDouble();
				sum += probabilities[j];
			}
			for (int k = 0; k < numKeys; k++)
				probabilities[k] /= sum;
		}catch (FileNotFoundException e) {
			System.out.println("File not found");
		}
	}
	
	
	private void constructMatrix() {
		cost = new double[numKeys+2][numKeys+1];
		for (int i = 1; i <= numKeys; i++) {
			cost[i][i] = probabilities[i-1];
		}
		for (int d = 1; d < numKeys; d++)
			for (int i = 1; i <= (numKeys-d); i++) {
				int j = i+d;
				double minK = 0;
				double tempMin = Double.MAX_VALUE;
				for (int k = i; k <= j; k++) {
					double c = cost[i][k-1] + cost[k+1][j];
					if (tempMin > c) {
						tempMin = c;
						minK = k;
					}
				}
				cost[i][j] = tempMin + Arrays.stream(probabilities, i-1, j).sum();
				cost[j][i-1] = minK-1;//stores array index of root instead of key number
				
			}
	}
	private void printMatrix() {
		for (int i = 0; i < numKeys+1; i++) {
			for (int j = 0; j < numKeys+1; j++)
				System.out.print("["+cost[i][j]+ "]");
			System.out.println();
		}
	}
	private void printTree(int start, int end) {
		if(start > end) return;
		int root = (int) cost[end+1][start];
		if (start == end) root = start;
		System.out.print("(");
		printTree(start, root-1);
		System.out.print(keys[root]);
		printTree(root+1, end);
		System.out.print(")");

	}

}
