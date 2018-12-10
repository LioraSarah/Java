/**
 * @author Liora Ferrero
 *
 * The main entry point of the program -
 * simply creates a GameSequence instance and activates it.
 */
public class WordGuessingGame {

	public static void main(String[] args) {
		GameSequence game = new GameSequence();
		game.runGameSeries();
	}

}
