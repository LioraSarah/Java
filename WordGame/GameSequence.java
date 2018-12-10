import java.util.Scanner;

/**
 * @author Liora Ferrero
 *
 * Class for managing a series of guessing games.
 */
public class GameSequence extends WordGuessingGame {

	private final static WordCollection wordCollection = new WordCollection();

    private static Scanner scanner = new Scanner( System.in );

	public GameSequence() {
	}

	/* Ask use whether to play another game */
	private boolean askUserAnotherGame() {
		System.out.println("\nDo you want to play again? (y = yes, other = no)");
        String input = scanner.nextLine();
		return (input.toLowerCase().equals("y"));
	}

	/* Manage a series of games: run one game and ask user if wants again */
	public void runGameSeries() {
		System.out.println("Welcome to the Word Guessing Game!\n");
		while (true) {
			WordToGuess word = wordCollection.getRandomWord();
			GameManager oneGameManager = new GameManager(word);   // We create a new GameManager object for every game
			oneGameManager.run();
			if (!askUserAnotherGame()) {
				break;
			}
		}
		System.out.println("\nGoodbye!");
	}
}
