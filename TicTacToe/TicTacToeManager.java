import java.util.Scanner;

/* Main class for managing one TicTacToe game for 3X3 board
 * 
 * @author Liora Ferrero
 * 
 * */
public class TicTacToeManager {

    private static Scanner scanner = new Scanner( System.in );

    /* Run one game after another as long as the user wants another game */
	public void run() throws Exception {
		System.out.println("Welcome to Tic Tac Toe!");
		System.out.println("To win, you need to complete a full row, column, or diagonal\n");
		while (true) {
			TicTacToeGame game = new TicTacToeGame();
			game.run();
			if (!userWantsAnotherGame()) {
				break;
			}
		}
		System.out.println("GoodBye!");
	}

	/* Ask user if wants another game. Returns true iff yes. */
	private boolean userWantsAnotherGame() {
		System.out.println("\nDo you want another game? (y = yes)");
        String input = scanner.nextLine();    
		System.out.println("");
        return input.toLowerCase().equals("y");		
	}
	
	public static void main(String[] args) throws Exception {
		TicTacToeManager game = new TicTacToeManager();
		game.run();
	}

}
