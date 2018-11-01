package assgn3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Scanner;

import assgn2.SudokuBoardSeth;

public class KruskalMSTWideman1 {
	
	private int numNodes;
	private LinkedList<Edge> list;
	private PriorityQueue<Edge> pq;
	
	public KruskalMSTWideman1(String filename) throws IOException {
		initializeHeap(new File(filename));
		findMST(new File("KruskalOutput.txt"));
		System.out.println(list.toString());
	}
	
	private void initializeHeap(File dataFile) throws FileNotFoundException {
		pq = new PriorityQueue<Edge>(100);
		Scanner sc = new Scanner(dataFile);
		String[] line = sc.nextLine().split("\\s");
		while (line[0].equals("c")) {
			line = sc.nextLine().split("\\s");
		}
		numNodes = Integer.parseInt(line[0]);
		while(sc.hasNext()) {
			pq.add(new Edge(sc.nextInt(), sc.nextInt(), sc.nextInt()));
		}
	}
	
	private void findMST(File outFile) throws IOException {
		FileWriter fw = new FileWriter(outFile);
		PrintWriter output = new PrintWriter(fw);
		output.println("Minimum Spanning Tree:");
		output.println("(vertex1, vertex2, weight)");
		UnionFind uf = new UnionFind(numNodes);
		list = new LinkedList<Edge>();
		while (list.size() < numNodes-1) {
			Edge temp = pq.poll();
			if(uf.find(temp.getFirst()) != uf.find(temp.getSecond())) {
				list.add(temp);
				uf.union(temp.getFirst(), temp.getSecond());
			}
		}
		Iterator<Edge> it = list.iterator();
		int totalWeight = 0;
		while (it.hasNext()) {
			Edge temp = (Edge) it.next();
			output.println(temp.toString());
			totalWeight += temp.getWeight();
		}
		output.println("Total weight of the tree: "+totalWeight);
		output.close();
	}
}