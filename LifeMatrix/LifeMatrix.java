/**
 * @author Liora Ferrero
 *
 * Data structure to hold the cells of the matrix, and accessor methods
 */
public class LifeMatrix {
	private int _numRows, _numColumns;
	private boolean[][] _content;
	
	/*
	 * @param numRows - the number of rows
	 * @param numColumns - the number of columns
	 * Throws exception if illegal input
	 */
	public LifeMatrix(int numRows, int numColumns) throws Exception {
		if (numRows <= 0 || numColumns <= 0)
			throw new Exception("numRows and numColumns must be positive");
		_numRows = numRows;
		_numColumns = numColumns;
		_content = new boolean[numRows][numColumns];
	}

	public int getNumRows() {
		return _numRows;
	}

	public int getNumColumns() {
		return _numColumns;
	}

	/* Returns true iff the row and column indices are legal for the matrix. */
	private boolean isLegalIndex(int row, int column) {
		if (row < 0 || column < 0)
			return false;
		if (row >= _numRows || column >= _numColumns)
			return false;
		return true;
	}

	/* Verifies that the row and column indices are legal for the matrix.
	 * If not, throws an Exception. */
	private void verifyLegalIndex(int row, int column) throws Exception {
		if (!isLegalIndex(row, column))
			throw new Exception("Matrix index out of range");
	}
	
	/* returns the value (alive == true or not-alive == false) of a cell in the matrix 
	 * (if the cell indices are legal) */
	public boolean getCellValue(int row, int column) throws Exception {
		verifyLegalIndex(row, column);
		return _content[row][column];
	}

	/* sets the given cell (if legal indices) to be alive(true) or not-alive(false) */
	public boolean setCellValue(int row, int column, boolean value) throws Exception {
		verifyLegalIndex(row, column);
		return _content[row][column] = value;
	}

	/* returns the number of alive neighbors of the given cell (if legal indices) */
	public int numberOfNeighbors(int row, int column) throws Exception {
		int result = 0;
		
		verifyLegalIndex(row, column);

		/* go over all 8 neighbors of the center cell(row,column), and consider only the legal ones */
		for (int rowIndex=row-1; rowIndex<=row+1; ++rowIndex) {
			for (int colIndex=column-1; colIndex<=column+1; ++colIndex) {
				if (!(rowIndex == row && colIndex == column) &&  isLegalIndex(rowIndex, colIndex)) {
					if (getCellValue(rowIndex, colIndex))
						result += 1;
				}
			}
		}
		return result;		
	}
	
	/* a function to display the matrix */
	public void printMatrix() {
		try {
			for (int row=0; row<_numRows; ++row) {
				String rowStr = "";
				for (int col=0; col<_numColumns; ++col) {
					// "[+]" == alive cell, "[ ]" == not alive cell
					rowStr += getCellValue(row, col) ? "[+]" : "[ ]";
				}
				System.out.println(rowStr);
			}
		} catch (Exception e) {
			// This is not supposed to happend because we use only legal row and column numbers
		}
	}

}
