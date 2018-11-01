package assgn1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.*;
import java.lang.Math;

public class FormulaReaderSeth {
	
	private int numVar;
	private int numClause;
	private int[][] formula;
	
	public FormulaReaderSeth() {
	}
	
	public void read(String fileName) {
		try {
			Scanner sc = new Scanner(new File(fileName));
			String line = sc.nextLine();
			Matcher m = Pattern.compile("p cnf").matcher(line);
			while (!m.find() && sc.hasNextLine()) {
				line = sc.nextLine();
				m = Pattern.compile("p cnf").matcher(line);
			}
			String[] found = line.split("\\s+");
			numVar = Integer.parseInt(found[2]);
			numClause = Integer.parseInt(found[3]);
			int end, clauseNum = 0;
			formula = new int[numClause][];
			while (clauseNum < numClause) {
				LinkedList<Integer> temp = new LinkedList<Integer>();
				while ((end = sc.nextInt()) != 0) temp.addLast(end);
				formula[clauseNum] = new int[temp.size()];
				Iterator<Integer> it = temp.iterator();
				int i = 0;
				while (it.hasNext()) {
					formula[clauseNum][i++] = it.next();
				}
				clauseNum++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public int getNumVar() {
		return numVar;
	}
	public int getNumClause() {
		return numClause;
	}
	public int[][] getFormula() {
		return formula;
	}

}
