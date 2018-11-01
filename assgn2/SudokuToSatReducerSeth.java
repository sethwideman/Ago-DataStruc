package assgn2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import assgn1.TimerSeth;

public class SudokuToSatReducerSeth {
	
	private SudokuBoardSeth board;
	private PrintWriter output;
	
	public SudokuToSatReducerSeth(File file) {
		createBoard(file);
		outFile();
		TimerSeth timer = new TimerSeth();
		timer.start();
		reduceBoard();
		timer.stop();
		System.out.println(timer.getDuration()+" milliseconds.");
	}
	
	private void outFile() {
		try {
			FileWriter fw = new FileWriter(new File("output.cnf"));
			output = new PrintWriter(fw);
			output.println("p cnf "+board.getNumberOfVariables()+" "+board.getNumberOfClauses());
		} catch (IOException e) {
			System.out.println("Something went wrong with the output file.");
		}
	}
	
	private void createBoard(File file) {
		try {
			Scanner sc = new Scanner(file);
			board = new SudokuBoardSeth(sc.nextInt(), sc.nextInt());
			int cell = 0;
			while (sc.hasNextInt()) {
				board.setCell(cell++, sc.nextInt());
			}
			board.toString();
		} catch (FileNotFoundException e){
			System.out.println("File not found");
		}
	}
	
	
	private void reduceBoard() {
		atLeastOneInRow();
		atMostOneInRow();
		atLeastOneInColumn();
		atMostOneInColumn();
		atLeastOneInBox();
		atMostOneInBox();
		atLeastOneValue();
		atMostOneValue();
		otherClauses();
		output.close();
	}
	
	private void atLeastOneInRow() {
		for (int k = 1; k <= board.getBoardSize(); k++) {
			for (int i=0; i < board.getBoardSize(); i++) {
				for (int j=0; j < board.getBoardSize(); j++) {
					output.print(encode(i,j,k)+" ");
				}
				output.print("0\n");
			}
		}
	}
	
	private void atMostOneInRow() {
		for (int k=1; k <= board.getBoardSize(); k++) {
			for (int n=0; n < board.getBoardSize(); n++) {
				for (int j=0; j < board.getBoardSize(); j++) {
					int ptr = 1;
					for (int i=j; i < board.getBoardSize()-1; i++) {
						output.print("-"+encode(n,j,k)+" ");
						output.print("-"+encode(n,(j+ptr),k)+" 0\n");
						ptr++;
					}
				}
			}
		}
	}
	
	private void atLeastOneInColumn() {
		for (int k = 1; k <= board.getBoardSize(); k++) {
			for (int j=0; j < board.getBoardSize(); j++) {
				for (int i=0; i < board.getBoardSize(); i++) {
					output.print(encode(i,j,k)+" ");
				}
				output.print("0\n");
			}
		}
	}
	
	private void atMostOneInColumn() {
		for (int k=1; k <= board.getBoardSize(); k++) {
			for (int n=0; n < board.getBoardSize(); n++) {
				for (int j=0; j < board.getBoardSize(); j++) {
					int ptr = 1;
					for (int i=j; i < board.getBoardSize()-1; i++) {
						output.print("-"+encode(j,n,k)+" ");
						output.print("-"+encode((j+ptr),n,k)+" 0\n");
						ptr++;
					}
				}
			}
		}
	}
	
	private void atLeastOneInBox() {
		for (int k = 1; k <= board.getBoardSize(); k++) {
			for (int m = 0; m < board.getBoardSize(); m+= board.getBoxHeight()) {
				for (int n = 0; n < board.getBoardSize(); n+= board.getBoxWidth()) {
					for (int i = 0; i < board.getBoxHeight(); i++) {
						for (int j = 0; j < board.getBoxWidth(); j++) {
							output.print(encode((m+i),(n+j),k)+" ");
						}
					}
					output.print("0\n");
				}
			}
		}
	}
	
	private void atMostOneInBox() {
		for (int k = 1; k <= board.getBoardSize(); k++) {
			for (int m = 0; m < board.getBoardSize(); m+=board.getBoxHeight()) {
				for (int n = 0; n < board.getBoardSize(); n+=board.getBoxWidth()) {
					int ptr = 0;
					int[] temp = new int[board.getBoardSize()];
					for (int i = 0; i < board.getBoxHeight(); i++) {
						for (int j = 0; j < board.getBoxWidth(); j++) {
							temp[ptr] = encode((m+i),(n+j),k);
							ptr++;
						}
					}
					for (int j = 0; j <= temp.length; j++) {
						int ptr2 = 1;
						for (int i = j; i < temp.length-1; i++) {
							output.print("-"+temp[j]+" ");
							output.print("-"+temp[(j+ptr2)]+" 0\n");
							ptr2++;
						}
					}
				}
			}
		}
	}
	
	private void atLeastOneValue() {
		for (int i = 0; i < board.getBoardSize(); i++) {
			for (int j = 0; j < board.getBoardSize(); j++) {
				for (int k = 1; k <= board.getBoardSize(); k++) {
					output.print(encode(i,j,k)+" ");
				}
				output.print("0\n");
			}
		}
	}
	
	private void atMostOneValue() {
		for (int i = 0; i < board.getBoardSize(); i++) {
			for (int j = 0; j < board.getBoardSize(); j++) {
				for (int k = 1; k <= board.getBoardSize(); k++) {
					for (int m = k+1; m <= board.getBoardSize(); m++) {
						output.print("-"+encode(i,j,k)+" ");
						output.print("-"+encode(i,j,m)+" 0\n");
					}
				}
			}
		}
	}
	
	private void otherClauses() {
		for (int i = 0; i < board.getNumberOfCells(); i++) {
			if (board.getCellValue(i) !=0)
				output.println(encode(board.getRow(i),board.getColumn(i),board.getCellValue(i))+" 0");
		}
	}
	
	
	private int encode(int i, int j, int k) {
		return i*board.getBoardSize()*board.getBoardSize() + j*board.getBoardSize() + k;
	}

}
