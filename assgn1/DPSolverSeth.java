package assgn1;

/**
 * DPSolver determines whether the list of clauses is satisfiable or not.
 * If satisfiable, it will print the variable assignments that it first found could satisfy
 * the list.
 * @author Seth Wideman
 *
 */
public class DPSolverSeth {
	
	FormulaSeth fs;
	
	public DPSolverSeth(String fileName) {
		fs = new FormulaSeth();
		fs.read(fileName);
	}
	
	/**
	 * Goes through all variables and initially assigns them to true.
	 * If unsatisfied when all variable are set to true, the function will backtrack and switch the
	 * variable to false while re-checking the satisfiability. If the function ever satisfies all clauses,
	 * the function will return true and terminate, otherwise it will continue to backtrack until satisfied, or when all assignments are -1
	 * and will return false and terminate if still not satisfied.
	 * @param f
	 * @return
	 */
	public boolean dpSolver(FormulaSeth f){
		if(f.isFormulaEmpty()) return true;
		if(f.hasEmptyClause()) return false;
		int first = f.firstAvailable();
		if (first == -1) return false;
		f.assign(first, 1);
		if(dpSolver(f))	return true;
		f.assign(first, -1);
		if(dpSolver(f)) return true;
		f.assign(first, 0);
		return false;		
	}
	
	/**
	 * initiates the recursive function dpSolver and prints out the result whether it be 
	 * satisfiable or unsatisfiable
	 */
	public void solve() {
		if(dpSolver(fs)) {
			System.out.println("The formula was satisfiable.");
			fs.printAssignment();
		}
		else {
			System.out.println("The formula was unsatisfiable");
		}
	}

}
