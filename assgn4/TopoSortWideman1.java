package assgn4;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;


public class TopoSortWideman1 {
	
	private LinkedList<Integer> aL[];
	private int[] inDegree;
	private int numVertices;

	public static void main(String[] args) throws IOException {
		//exits if 0 arguments
		  if (0 == args.length) {
			  System.out.println("No input file entered.");
			  System.exit(1);
		  }
		  new TopoSortWideman1(args[0]);
	}
	
	
	public TopoSortWideman1(String fileName) throws IOException {
		sort(new File(fileName));
	}
	
	
	private void createDigraph(File dataFile) throws FileNotFoundException {
		Scanner sc = new Scanner(dataFile);
		String [] line;
		while (sc.hasNextLine()) {
			line = sc.nextLine().split("\\s");
			if (!line[0].equals("c")) {
				numVertices = Integer.parseInt(line[0]);
				break;
			}
		}
		aL = new LinkedList[numVertices+1];
		for (int i = 1; i <= numVertices; i++) aL[i] = new LinkedList<>();
		inDegree = new int[numVertices+1];
		while (sc.hasNext()) {
			int x = sc.nextInt();
			int y = sc.nextInt();
			if (!aL[x].contains(y)) {
				aL[x].add(y);
				inDegree[y] += 1;
			}
		}
	}
	
	
	private void sort(File dataFile) throws IOException {
		createDigraph(dataFile);
		Stack<Integer> S = new Stack<Integer>();
		String outString = "";
		for (int u = 1; u <= numVertices; u++) 
			if (inDegree[u] == 0) S.push(u);
		int i = 1;
		while (!S.isEmpty()) {
			int u = S.pop();
			outString += u + " ";
			i++;
			for (int v = 1; v <= numVertices; v++) {
				if (aL[u].contains(v)) {
					inDegree[v] -= 1;
					if (inDegree[v] == 0) S.push(v);
				}
			}
		}
		
		FileWriter fw = new FileWriter(new File ("topoOutput.txt"));
		PrintWriter output = new PrintWriter(fw);
		if (i > numVertices) output.println(outString);
		else output.println("The graph is cyclic.");
		output.close();
	}
}
