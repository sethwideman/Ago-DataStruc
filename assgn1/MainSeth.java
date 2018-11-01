package assgn1;

/**
 * 
 * @author Seth Wideman
 *
 */
public class MainSeth {
		
	
	public static void main(String[] args) {
		//exits if 0 arguments
		  if (0 == args.length) {
			  System.out.println("No input file entered.");
			  System.exit(1);
		  }
		  
		  DPSolverSeth dps = new DPSolverSeth(args[0]);
		  TimerSeth timer = new TimerSeth();
		  timer.start();
		  dps.solve();
		  timer.stop();
		  System.out.println("Time taken to solve the problem: " + timer.getDuration() + " milliseconds.");
		
		  
		}

}
