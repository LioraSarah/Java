/* Class for calculating the computer's strategy for the next move 
 * 
 * @author Liora Ferrero
 * 
 * The strategy implemented here is a simplified version of the strategy described on the Wikipedia page:
 * https://en.wikipedia.org/wiki/Tic-tac-toe#Strategy
 *  
 * */
public class StrategyPlayer {
	
	private Board _board;
	
	// initialize the StrategyPlayer with the game's board
	public StrategyPlayer(Board board) {
		_board = board;
	}
	
	/* Try one possible move after another according to the strategy until you find a valid move */
	public Position nextMove() throws Exception {
		Position pos;
		if ((pos = tryWin()) != null) return pos;
		if ((pos = tryBlock()) != null) return pos;
		if ((pos = tryCenter()) != null) return pos;
		if ((pos = tryOppositeCorner()) != null) return pos;
		if ((pos = tryEmptyCorner()) != null) return pos;
		if ((pos = findEmptyPosition()) != null) return pos;
		throw new Exception("No next move is possible");
	}

	/* Try to find a position in which you can put O and immediately win */
	private Position tryWin() {
		return canWin(Board.Marking.O);
	}
	
	/* Try to find a position in which the opponent can put X and immediately win - so we can block him */
	private Position tryBlock() {
		return canWin(Board.Marking.X);
	}
	
	/* Try to find a position in which the player can put his marking and immediately win */
	private Position canWin(Board.Marking marking) {
		assert marking == Board.Marking.O || marking == Board.Marking.X;
		try {
			for (int row=0; row<_board.numRows(); ++row) {
				int col = canWinVector(_board.getRow(row), marking);
				if (col >= 0)
					return new Position(row, col);
			}
			for (int col=0; col<_board.numCols(); ++col) {
				int row = canWinVector(_board.getColumn(col), marking);
				if (row >= 0)
					return new Position(row, col);
			}
		} catch (Exception e) {
			// Does not happen (we put only legal input in getColumn)
		}
		return null;
	}
	
	/* Try to find an empty position in the line in which the player can put his marking and immediately win the line.
	 * Returns -1 if no such position. */
	private int canWinVector(Board.Marking vec[], Board.Marking marking) {
		int empty_pos = -1;   // -1 indicates that no empty position was found
		int counter = 0;
		for (int i=0; i<vec.length; ++i) {
			if (vec[i] == Board.Marking.None) {
				empty_pos = i;    // found an empty position
			} else if (vec[i] == marking) {
				counter++;        // counting the marking
			}
		}
		if (empty_pos >= 0 && counter == vec.length-1) {
			// the vector contains exactly one empty position and all other positions are filled with the marking
			return empty_pos;
		}
		return -1;
	}

	/* Check if center is empty, and if so, choose it */
	private Position tryCenter() {
		int center_row = _board.numRows() / 2;
		int center_col = _board.numCols() / 2;
		try {
			if (_board.isCellEmpty(center_row, center_col)) {
				return new Position(center_row, center_col);
			}
		} catch (Exception e) {
			// Does not happen because we put a legal input in isCellEmpty
		}
		return null;
	}

	/* Check if opponent put X in one of the corners, and if so, put O in the opposite corner (if it's empty) */
	private Position tryOppositeCorner() {
		if (checkOppositeCorner(0,0)) return new Position(0,0);
		if (checkOppositeCorner(0, _board.numCols()-1)) return new Position(0, _board.numCols()-1);
		if (checkOppositeCorner(_board.numRows()-1, 0)) return new Position(_board.numRows()-1, 0);
		if (checkOppositeCorner(_board.numRows()-1, _board.numCols()-1)) return new Position(_board.numRows()-1, _board.numCols()-1);
		return null;
	}
	
	/* Check if (row,col) is empty and opponent put X in the opposite corner */
	private boolean checkOppositeCorner(int row, int col) {
		try {
			return _board.isCellEmpty(row, col) 
					&& _board.getCellState(_board.numRows()-row-1, _board.numCols()-col-1) == Board.Marking.X;
		} catch (Exception e) {
			// Does not happen (we use only legal input)
		}
		return false;
	}

	/* Try to find empty corner */
	private Position tryEmptyCorner() {
		if (checkEmptyCorner(0, 0)) return new Position(0, 0);
		if (checkEmptyCorner(0, _board.numCols()-1)) return new Position(0, _board.numCols()-1);
		if (checkEmptyCorner(_board.numRows()-1, 0)) return new Position(_board.numRows()-1, 0);
		if (checkEmptyCorner(_board.numRows()-1, _board.numCols()-1)) return new Position(_board.numRows()-1, _board.numCols()-1);
		return null;
	}
	
	private boolean checkEmptyCorner(int row, int col) {
		try {
			return _board.isCellEmpty(row, col);
		} catch (Exception e) {
			// Does not happen (we use only legal input)
		}
		return false;
	}
	
	/* Find any empty position remaining */
	private Position findEmptyPosition() {
		try {
			for (int row=0; row<_board.numRows(); ++row) {
				for (int col=0; col<_board.numCols(); ++col) {
					if (_board.isCellEmpty(row, col)) {
						return new Position(row, col);
					}
				}
			}
		} catch (Exception e) {
			// Does not happen (we use only legal input)
		}
		return null;   /* should not happen */
	}
}
	
