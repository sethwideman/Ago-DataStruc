package assgn2;

import java.io.File;

public class MainSeth {
	
	public static void main(String[] args) {
		//exits if 0 arguments
		  if (0 == args.length) {
			  System.out.println("No input file entered.");
			  System.exit(1);
		  }
		  new SudokuToSatReducerSeth(new File(args[0]));
	}
}
