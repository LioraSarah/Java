import java.util.Random;
import java.util.Scanner;

/* Main class for managing one TicTacToe game for 3X3 board
 * 
 * @author Liora Ferrero
 * 
 * */
public class TicTacToeGame {
	
    private static Scanner scanner = new Scanner( System.in );
	
	private static final int NUM_ROWS = 3;
	private static final int NUM_COLS = 3;

	private static final Random _RANDOM = new Random();
	
	private Board _board;
	private StrategyPlayer _ComputerPlayer;

	public TicTacToeGame() throws Exception {
		_board = new Board(NUM_ROWS, NUM_COLS);
		_ComputerPlayer = new StrategyPlayer(_board);
	}

	/* Run the game */
	public void run() {
		System.out.println("You play 'X', I play 'O'");
		
		try {
	
			/* Randomly select if computer goes first, and if so, do its first move according to strategry */
			if (computerGoesFirst()) {
				System.out.println("I go first!");
				Position position = _ComputerPlayer.nextMove();
				_board.updateCellState(position.row(), position.col(), Board.Marking.O);
			} else {
				System.out.println("You go first!");
			}
			_board.printBoard();
			
			/* While the board is not empty and no on wins, continue alternating between user and computer */
			while (!_board.isBoardFull()) {
				/* Get user selection and check board status */
				Position position = getUserSelection();
				_board.updateCellState(position.row(), position.col(), Board.Marking.X);
				_board.printBoard();
				if (_board.findWinner() == Board.Marking.X) {
					System.out.println("Congrats! You win!");
					return;
				}
				if (_board.isBoardFull()) {
					break;
				}
				
				/* Get computer's move and check board status */
				System.out.println("Now it's my turn");
				position = _ComputerPlayer.nextMove();
				_board.updateCellState(position.row(), position.col(), Board.Marking.O);
				_board.printBoard();
				if (_board.findWinner() == Board.Marking.O) {
					System.out.println("I win!");
					return;
				}
			}
			System.out.println("We got to a draw, nobody wins.");
		
		} catch (Exception e) {
			// Does not happen (we take care to put only legal row+col in updateCellState
		}
	}

	/* Randomly returns true of false */
	private boolean computerGoesFirst() {
		return _RANDOM.nextBoolean();
	}
	
	/* Ask user for selection. Keep asking until you get a valid cell (legal index, empty cell) */
	private Position getUserSelection() {
		while (true) {
			System.out.println("Type your selection: row_number, then space, then column_number, e.g.: 1 2");
	        String input = scanner.nextLine();    // Read a text line from the user.
	        Scanner str_scanner = new Scanner(input);
	        int user_row = 0;
	        int user_col = 0;
	        
	        try {
		        user_row = str_scanner.nextInt();
		        user_col = str_scanner.nextInt();
	        } catch (Exception e) {
	        	System.out.println("Illegal input");
	        	continue;
			} finally {
		        str_scanner.close();
			}
	        
	        /* convert from human indexing (starting from 1) to computer indexing (starting from 0):*/
	        int row = user_row-1;
	        int col = user_col-1;

	        try {
		        /* check if cell is empty */
		        if (_board.isCellEmpty(row, col)) {
			        return new Position(row, col);
		        } else { 
		        	System.out.println("Error: Cell ("+(user_row)+","+(user_col)+") is already full");
		        }
	        } catch (Exception e) {
				System.out.println("Illegal board position");
			}
		}
	}
}
