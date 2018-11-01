package assgn3;

import java.io.File;
import java.io.IOException;

import assgn2.SudokuToSatReducerSeth;

public class MainWideman1 {
	
	public static void main(String[] args) throws IOException {
		//exits if 0 arguments
		  if (0 == args.length) {
			  System.out.println("No input file entered.");
			  System.exit(1);
		  }
		  new KruskalMSTWideman1(args[0]);
	}

}
