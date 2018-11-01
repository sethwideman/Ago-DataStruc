package assgn4;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class WarshallFloydWideman1 {
	
	private int numNodes;
	private int[][] wm;
	private int[][] predecessorMatrix;
	final int Infinite = 999;
	
	public static void main(String[] args) throws IOException {
		//exits if 0 arguments
		if (0 == args.length) {
			System.out.println("No input file entered.");
			System.exit(1);
		}
		new WarshallFloydWideman1(args[0]);
	}
	
	public WarshallFloydWideman1(String fileName) throws IOException {
		constructWDAMatrix(new File(fileName));
		shortestPath(new File("WarshallFloydOutput.txt"));
	}

	private void constructWDAMatrix(File dataFile) throws IOException {
		Scanner sc = new Scanner (dataFile);
		numNodes = sc.nextInt();
		wm = new int[numNodes+1][numNodes+1];
		predecessorMatrix = new int[numNodes+1][numNodes+1];
		while (sc.hasNext()) {
			int i = sc.nextInt();
			int j = sc.nextInt();
			int k = sc.nextInt();
			wm[i][j] = k;
			predecessorMatrix[i][j] = i;
		}
		
		for (int x = 1; x <= numNodes; x++)
			for (int y = 1; y <= numNodes; y++) 
				if (x != y && wm[x][y] == 0) 
					wm[x][y] = Infinite;
	}
	
	private void shortestPath(File outFile) throws IOException {
		FileWriter fw = new FileWriter(outFile);
		PrintWriter output = new PrintWriter(fw);
		
		//printing initial adjacency matrix
		output.println("Initial Adjacency Matrix");
		for (int i = 1; i <= numNodes; i++) {
			for (int j = 1; j <= numNodes; j++) {
				output.print(wm[i][j]+" ");
			}
			output.println();
		}
		
		//creating shortest path matrix
		for (int k = 1; k <= numNodes; k++)
			for (int i = 1; i <= numNodes; i++)
				for (int j = 1; j <= numNodes; j++) {
					if (wm[i][j] > (wm[i][k] + wm[k][j])) {
						wm[i][j] = (wm[i][k] + wm[k][j]);
						predecessorMatrix[i][j] = k;
					}
				}
		
		//printing shortest path matrix
		output.println();
		output.println("Shortest Path Matrix");
		for (int i = 1; i <= numNodes; i++) {
			for (int j = 1; j <= numNodes; j++) {
				output.print(wm[i][j]+" ");
			}
			output.println();
		}
		
		//printing predecessor Matrix
		output.println();
		output.println("Predecessor Matrix");
		for (int i = 1; i <= numNodes; i++) {
			for (int j = 1; j <= numNodes; j++) {
				output.print(predecessorMatrix[i][j]+" ");
			}
			output.println();
		}
		
		output.println();
		output.println("Path from 4 to 2");
		output.println(path(4, 2));
		output.println("Weight:");
		output.println(wm[4][2]);
		
		output.close();
	}
	
	private String path(int i, int j) {
		if (wm[i][j] == Infinite) return "No such path.";
		if (j == i) return Integer.toString(j);
		return path(i, predecessorMatrix[i][j]) + " -> " + j;
	}
}
