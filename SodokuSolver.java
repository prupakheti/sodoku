package sodoku;

public class SodokuSolver {
	int [][]matrix;
	int [][]workingCopy;
	
	Tracker sodokuTracker;
	boolean isSolved;
	
	SodokuSolver(){
		matrix = new int[9][9];
		workingCopy = new int[9][9];
		isSolved = false;
		sodokuTracker = new Tracker();
	}
	
	public void solve(){
		backTrack(0);
		showResult();
	}
	
	private void showResult(){
		for( int i = 0; i < 9; ++i){
			for( int j = 0; j < 9; ++j ){
				System.out.print(workingCopy[i][j]+" ");
			}
			System.out.println();
		}
	}
	
	private void backTrack(int indx){
		if(isSolved) return;
		if(indx >= 81) {
			isSolved = true;
			return;
		}
		int r = indx / 9;
		int c = indx % 9;	
		
		
		if(isFilled(r, c)){
			backTrack(indx+1);
		}
		else{
			for( int val = 1; val <=9; ++val ){
				if( !sodokuTracker.isValid(r, c, val)) continue;
				sodokuTracker.setVal(r, c, val);
				workingCopy[r][c] = val;
				backTrack(indx+1);
				sodokuTracker.unsetVal(r, c, val);
				if( isSolved ) break;
			}
		}
	}
	
	/**
	 * 
	 * @param r : row position
	 * @param c : column position
	 * @return true if the given row r and col c is filled in the original partial solution matrix. 
	 */
	private boolean isFilled(int r, int c){
		if(matrix[r][c] > 0 )return true;
		return false;
	}
	
	
	
	/*
	 * This class to describe in a nutshell is a datastructure that keeps track of which numbers are filled the row column and 3x3 grid of the sodoku matrix
	 */
	private class Tracker{
		boolean [][]rows; // row[i][j] is true if j+1 is already present in row i of the the sodoku matrix 
		boolean [][]cols;//  col[i][j] is true if j+1 is already present in col i of the the sodoku matrix
		
		/**
		 * There are 9  3x3 grid in soduku matrix. each grid should meet the criteria that all the cells in the grid should have the numbers from 1 to 9 unrepeated.
		 * Now the logic below for using grid is we want to index 9 grid from 0 to 8. so we will do it like this.
		 * grid0 grid1 grid2
		 * grid3 grid4 grid5
		 * grid6 grid7 grid8
		 * 
		 * Each grid here is a 3x3 matrix
		 */
		
		boolean [][]grids; //grids[i][j] is true if j+1 is already present in 3x3 grid i of the the sodoku matrix
		
		public Tracker(){
			rows = new boolean[9][9];
			cols = new boolean[9][9];
			grids = new boolean[9][9];
		}
		/**
		 * 
		 * @param r : row number of the cell where we want to put val in the sodoku matrix
		 * @param c : column number of the cell where we want to put val in the sodoku matrix
		 * @param val : the value that we want to put in the cell with row number as r and column number as c
		 * @return returns true if it is valid to put val in row r and column c otherwise returns false
		 */
		boolean isValid(int r, int c, int val){
			if( val < 1 || val > 9) return false;
			val = val - 1;
			if( rows[r][val]) return false;
			if( cols[c][val]) return false;
			
			int gridRow = r/3;
			int gridCol = r%3;
			int gridIndx = gridRow*3 + gridCol; // gridIndx is the value which grid we are in.
			
			if( grids[gridIndx][val]) return false;
			
			
			return true;
		}
		
		
		/**
		 * 
		 * @param r : row number for the row where we want to put val 
		 * @param c : column number for the column where we want to put val 
		 * @param val : value to be put in cell with row number r and column number c
		 */
		void setVal(int r, int c, int val){
			val = val-1;
			rows[r][val] = true;
			cols[c][val] = true;
			
			int gridRow = r/3;
			int gridCol = r%3;
			int gridIndx = gridRow*3 + gridCol; // gridIndx is the value which grid we are in.
			
			grids[gridIndx][val] = true;
		}
		
		
		/**
		 * 
		 * @param r : row number for the row where we want to remove val 
		 * @param c : column number for the column where we want to remove val
		 * @param val : value to be removed in the cell with row number r and column number c
		 */
		void unsetVal(int r, int c, int val){
			val = val-1;
			rows[r][val] = false;
			cols[c][val] = false;
			
			int gridRow = r/3;
			int gridCol = r%3;
			int gridIndx = gridRow*3 + gridCol; // gridIndx is the value which grid we are in.
			
			grids[gridIndx][val] = false;
		}
		
		
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SodokuSolver sodokuSolver = new SodokuSolver();
		sodokuSolver.solve();
	}

}
