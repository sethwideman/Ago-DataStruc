package assgn2;

public class SudokuBoardSeth {
	
	private int boxWidth;
	private int boxHeight;
	private int boardSize;
	private int numCells;
	private int[] boardCells;
	private int extraClauses = 0;
	
	public SudokuBoardSeth(int boxWidth, int boxHeight) {
		this.boxWidth = boxWidth;
		this.boxHeight = boxHeight;
		boardSize = boxWidth*boxHeight;
		numCells = boardSize*boardSize;
		boardCells = new int[numCells];
	}
	
	public int getRow(int cellNum) {
		return cellNum/boardSize;
	}
	
	public int getColumn(int cellNum) {
		return cellNum%boardSize;
	}
	
	public int getCellValue(int cellNum) {
		return boardCells[cellNum];
	}
	
	public int getBox(int cellNum) {
		return (getColumn(cellNum)/boxWidth) + (boxHeight * (getRow(cellNum)/boxWidth));
	}
	
	
	public int getBoxWidth() {
		return boxWidth;
	}
	public int getBoxHeight() {
		return boxHeight;
	}
	public int getBoardSize() {
		return boardSize;
	}
	public int getNumberOfCells() {
		return numCells;
	}
	
	@Override
	public String toString() {
		String s = "";
		int i=0;
		while (i < numCells) {
		for (int j=0; j<boardSize; j++) { 
				s += boardCells[i]+" ";
				i++;
		}
			s += "\n";
			
		}
		System.out.println(s);
		return s;
	}
	
	public void setCell(int cellNum, int val) {
		boardCells[cellNum] = val;
		if(val != 0) extraClauses++;
	}
	
	public int getNumberOfVariables() {
		return numCells*boardSize;
	}
	
	public int getNumberOfClauses() {
		int temp = 1;
		for (int i = 1; i < boardSize; i++) {
			temp += i;
		}
		temp *= boardSize;
		return temp*(boardSize*4) + extraClauses;
	}
	
	

}
