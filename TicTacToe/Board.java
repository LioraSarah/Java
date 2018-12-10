import java.util.Arrays;

/* Class for representing the game board 
 * 
 * @author Liora Ferrero
 * 
 * */
public class Board {

	/* Marking is used to represent a cell's status.
	 * It is also used for method replies e.g. who is the winner in the board.
	 */
	public static enum Marking {None, X, O};

	private int _numRows;
	private int _numCols;
	private Marking[][] _cells;
	private int _numEmptyCells;

	// initialize board with numRows rows and numCols columns with all empty cells
	public Board(int numRows, int numCols) throws Exception {
		if (numRows <= 0 || numCols <= 0) {
			throw new Exception("Illegal board size: "+numRows+", "+numCols);
		}
		_numRows = numRows;
		_numCols = numCols;
		_numEmptyCells = _numRows * _numCols;
		_cells = new Marking[_numRows][_numCols];
		for (int row=0; row<_numRows; ++row) {
			for (int col=0; col<_numCols; ++col) {
				_cells[row][col] = Marking.None;
			}
		}
	}

	public int numRows() {
		return _numRows;
	}

	public int numCols() {
		return _numCols;
	}

	// returns true iff the board has no empty cells
	public boolean isBoardFull() {
		return _numEmptyCells == 0;
	}

	// returns true iff cell (row,col) is empty
	public boolean isCellEmpty(int row, int col) throws Exception {
		return getCellState(row, col) == Marking.None;
	}

	// returns true iff row is a legal row number
	private boolean isLegalRow(int row) {
		return row >= 0 && row < _numRows;
	}

	// returns true iff col is a legal column number
	private boolean isLegalColumn(int col) {
		return col >= 0 && col < _numCols;
	}

	// returns the state of cell (row,col)  (None/X/O)
	public Marking getCellState(int row, int col) throws Exception {
		if (!isLegalRow(row) || !isLegalColumn(col)) {
			throw new Exception("Illegal cell index: ("+row+","+col+")");
		}
		return _cells[row][col];
	}
	
	// update cell (row,col) with state which must be either X or O
	public void updateCellState(int row, int col, Marking state) throws Exception {
		assert state == Marking.O || state == Marking.X;
		assert isCellEmpty(row, col);
		_cells[row][col] = state;
		_numEmptyCells--;
	}

	/* returns a vector with the content of row */
	public Marking[] getRow(int row) throws Exception {
		if (!isLegalRow(row)) {
			throw new Exception("Illegal row index: "+row);
		}
		Marking[] vector = new Marking[_numCols];
		for (int col=0; col<_numCols; ++col) {
			vector[col] = _cells[row][col];
		}
		return vector;
	}

	/* returns a vector with the content of column */
	public Marking[] getColumn(int col) throws Exception {
		if (!isLegalColumn(col)) {
			throw new Exception("Illegal column index: "+col);
		}
		Marking[] vector = new Marking[_numRows];
		for (int row=0; row<_numRows; ++row) {
			vector[row] = _cells[row][col];
		}
		return vector;
	}

	/* Checks rows, columns, and diagonal. 
	 * If one of these lines has all X, returns CellState.X.
	 * If one of these lines has all O, returns CellState.O.
	 * Otherwise, returns CellState.None
	 */
	public Marking findWinner() {
		/* try to find winning row */
		for (int row=0; row<_numRows; ++row) {
			int o_counter = 0;
			int x_counter = 0;
			for (int col=0; col<_numCols; ++col) {
				if (_cells[row][col] == Marking.O) {
					o_counter++;
				}
				else if (_cells[row][col] == Marking.X) {
					x_counter++;
				}
			}
			if (o_counter == _numCols) {
				return Marking.O;
			}
			else if (x_counter == _numCols) {
				return Marking.X;
			}
		}
		
		/* try to find winning column */
		for (int col=0; col<_numCols; ++col) {
			int o_counter = 0;
			int x_counter = 0;
			for (int row=0; row<_numRows; ++row) {
				if (_cells[row][col] == Marking.O) {
					o_counter++;
				}
				else if (_cells[row][col] == Marking.X) {
					x_counter++;
				}
			}
			if (o_counter == _numRows) {
				return Marking.O;
			}
			else if (x_counter == _numRows) {
				return Marking.X;
			}
		}
		
		/* check diagonals */
		if (_numRows == _numCols) {
			for (int is_reverse=0; is_reverse<=1; is_reverse ++) {
				// When is_reverse == 0, we check the left-to-right diagonal
				// When is_reverse == 1, we check the right-to-left diagonal
				int o_counter = 0;
				int x_counter = 0;
				for (int row=0; row<_numRows; ++row) {
					int col = (is_reverse==1) ? _numRows-row-1 : row;
					if (_cells[row][col] == Marking.O) {
						o_counter++;
					}
					else if (_cells[row][col] == Marking.X) {
						x_counter++;
					}
				}
				if (o_counter == _numRows) {
					return Marking.O;
				}
				else if (x_counter == _numRows) {
					return Marking.X;
				}
			}
		}
		
		return Marking.None;   /* no winner */
	}

	/* Nicely print the game board */
	public void printBoard() {
		char[] _aux = new char[1+_numCols*2];
		Arrays.fill(_aux, '-');
		String solid_line = new String(_aux);
		System.out.println(solid_line);
		for (int row=0; row<_numRows; ++row) {
			String row_str = "|";
			for (int col=0; col<_numCols; ++col) {
				String cell_str = " ";
				if (_cells[row][col] == Board.Marking.O) {
					cell_str = "O";
				} else if (_cells[row][col] == Board.Marking.X) {
					cell_str = "X";
				}
				row_str += cell_str + "|";
			}
			System.out.println(row_str);
			System.out.println(solid_line);
		}
	}
}
