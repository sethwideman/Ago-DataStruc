package assgn1;

import java.util.*;

/**
 * 
 * @author Seth Wideman
 *
 */

public class FormulaSeth {
	
	private int numVar;
	private int numClause;
	private int[][] formula;
	private LinkedList<Integer> list;
	private int[] assgn;
	private int numMarkers = 0;
	
	public FormulaSeth() {	
	}
	
	
	/**
	 * Calls read on formulaReader object and creates a local copy of the number of variables,
	 * number of clauses, and the formula.
	 * Also creates linked list to keep track of satisfied and unsatisfied clauses.
	 * Creates array to keep track of variable assignments
	 * @param file
	 */
	public void read(String fileName) {
		FormulaReaderSeth fr = new FormulaReaderSeth();
		fr.read(fileName);
		numVar = fr.getNumVar();
		numClause = fr.getNumClause();
		formula = fr.getFormula();
		list = new LinkedList<Integer>();
		for (int i = 0; i < numClause; i++) list.add(i);
		list.add(-1);
		assgn = new int[numVar+1];
	}
	
	/**
	 * returns true if the formula is empty. Also means all clauses have been satisfied
	 * @return
	 */
	public boolean isFormulaEmpty() {
		if (list.getFirst() == -1) return true;
		else return false;
	}
	
	/**
	 * Returns true if all variable already assigned but clause is still not satisfied
	 * @param clauseN
	 * @return
	 */
	public boolean isClauseEmpty(int clauseN) {
		boolean empty = true;
		for (int i=0; i < numVar; i++) {
			if ((formula[clauseN][i] != 0 && assgn[i] !=0) || formula[clauseN][i] == 0);
			else empty = false;
		}
		return empty;
	}
	
	/**
	 * returns true if formula contains an empty clause
	 * @return
	 */
	public boolean hasEmptyClause() {
		if (list.getFirst() != -1 && firstAvailable() == -1) return true;
		else return false;
	}
	
	/**
	 * Returns the first variable that has yet to be assigned
	 * @return
	 */
	public int firstAvailable() {
		for (int i=1; i < numVar+1; i++) {
			if (assgn[i] == 0) return i;
		}
		return -1;
	}
	
	/**
	 * Creates a sublist of Clauses that haven't been satisfied yet
	 * @return
	 */
	private LinkedList<Integer> separateClauses() {
		LinkedList<Integer> temp = new LinkedList<Integer>();
		int marker = list.getFirst();
		while (marker != -1) {
			temp.add(marker);
			list.remove();
			marker = list.getFirst();
		}
		return temp;
	}
	
	/**
	 * @param var Variable to be given assignment
	 * @param val The assignment to be given to specified var
	 * 
	 * if val passed in is a -1 or a 0, calls removeMarker() to remove the most recent 
	 * -1 marker from the satisfied clauses list
	 * Gets temporary list of unsatisfied clauses and checks if new assignment of 
	 * var satisfies the unsatisfied clauses. After satisfied clauses are put back into the list,
	 * a -1 marker is added to separate satisfied/unsatisfied and the remaining unsatisfied are added to the front.
	 */
	public void assign(int var, int val) {
		if (val == -1 || val == 0) removeMarker();
		assgn[var] = val;
		boolean sat = false;
		LinkedList<Integer> temp = separateClauses();
		final int size = temp.size();
		for (int i=0; i < size; i++) {
			if(isClauseSatisfied(temp.peekFirst())) {
				list.addFirst(temp.pop());
				sat = true;
			}else {
				if (temp.size() != 1) {
					temp.addLast(temp.pop());
				}
			}
		}
		
		if(sat) {
			list.addFirst(-1);
			numMarkers++;
		}
		
		while (!temp.isEmpty()) {
			list.addFirst(temp.removeLast());
		}
		
	}
	
	
	/**
	 * Removes the most recent -1 marker from the linkedList of clauses
	 */
	private void removeMarker() {
		if(numMarkers > 0) {
			list.removeFirstOccurrence(-1);
			numMarkers--;
		}
	}
	
	/**
	 * Returns true if any of the variables are set to true, and the corresponding variable 
	 * in the current clause is greater than 0. Also returns true if var is false and corresponding
	 * clause variable is less than 0. Otherwise returns false meaning no variables in the current clause
	 * cno are satisfied
	 * 
	 * @param cno
	 * @return
	 */
	private boolean isClauseSatisfied(int cno) {
		for (int i=0; i < formula[cno].length; i++) {
			if (formula[cno][i] > 0 && assgn[Math.abs(formula[cno][i])] == 1) return true;
			if (formula[cno][i] < 0 && assgn[Math.abs(formula[cno][i])] == -1) return true;
		}
		return false;
	}
	
	/**
	 * Prints the current assignments of all the variables
	 */
	public void printAssignment() {
		for (int i=0; i < numVar; i++) {
			System.out.println("X"+(i+1)+": "+assgn[i+1]);
		}
	}
	
	
	/**
	 * Prints the formula in a 2D array format
	 */
	public void printFormula() {
		System.out.println("Formula:");
		for (int i=0; i < numClause; i++) {
			System.out.println("Clause "+i+": ");
			for (int j=0; j < formula[i].length; j++)
				System.out.print(formula[i][j]+" ");
			System.out.println();
		}
	}
	
	/**
	 * Prints the satisfied/unsatisfied clause list as well as
	 * the assignment of all the variables
	 */
	public void Print() {
		System.out.println(list.toString());
		printAssignment();
	}
	


}
