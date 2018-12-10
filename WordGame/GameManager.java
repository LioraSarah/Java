import java.util.Scanner;

/**
 * @author Liora Ferrero
 *
 * Class for managing one game (with one word to guess)
 */
public class GameManager {

	/* The English alphabet */
	private static String ALL_LETTERS = "abcdefghijklmnopqrstuvwxyz";

    private static Scanner scanner = new Scanner( System.in );

    /* Private members for the current state of the game: word to guess, used and unused letters, etc. */
	private WordToGuess _wordToGuess;
	private String _usedLetters;
	private String _remainingLetters;
	private char[] _currentGuess;
	private int _numRemainingToGuess;   // numbers of letters remaining to guess
	private int _numberOfAttempts;      // number of user attempts so far
	
	public GameManager(WordToGuess wordToGuess) {
		_wordToGuess = wordToGuess;
		init();
	}

	/* Initialize the state of the game */
	private void init() {
		int length = _wordToGuess.getLength();
		_numRemainingToGuess = length;
		_numberOfAttempts = 0;
		_usedLetters = "";
		_remainingLetters = ALL_LETTERS;
		_currentGuess = new char[length];
		for (int i=0; i<length; ++i) {
			_currentGuess[i] = '_';
		}
	}

	/* Ask the user to type one guess letter */
	private char askUserLetter() {
		while (true) {
			System.out.println("Type one letter for your guess:   (one letter + Enter)");
	        String input = scanner.nextLine();    // Read a text line from the user.
	        if (input.length() != 1) {
				System.out.println("You must type exactly one letter + Enter");
				continue;
	        }
			char letter = input.toLowerCase().charAt(0);
			if (_usedLetters.contains(""+letter)) {
				System.out.println("You already typed that letter! Try again.");
			} else if (letter < 'a' || letter > 'z') {
				System.out.println("Illegal letter! Try again.");
			} else {
				return letter;
			}
		}
	}

	/* Update the currrent memory with a letter that is known to be in the word to guess */
	private void updateWithGoodLetter(char letter) {
		for (int i=0; i<_wordToGuess.getLength(); ++i) {
            if (_wordToGuess.getChar(i) == letter) { 
            	// if the guessed letter is in the (hidden) wordToGuess[i] 
                // then update the currentGuess (displayed) array with the letter in its right place
				_currentGuess[i] = letter;
				_numRemainingToGuess -= 1;
			}
		}
	}

	/* Return a nice string for the current guess (with spaces between letters) */
	private String currentGuessToString() {
		String result = "";
		for (int i=0; i<_currentGuess.length; ++i) {
			if (i > 0)
				result += " ";
			result += _currentGuess[i];
		}
		return result;
	}

	/* manage one guessing game (for one word to guess) */
	public void run() {
		System.out.println("You need to guess letters one by one");
		while (_numRemainingToGuess > 0) {
			System.out.println("Your guess so far: " + currentGuessToString() + "     Remaining letters: " + _remainingLetters);
			char letter = askUserLetter();
			
			/* update the current state */
			_numberOfAttempts += 1;
			_usedLetters = _usedLetters + letter;
			_remainingLetters = _remainingLetters.replace(""+letter, "");
			
			/* check if the letter appears in the word to guess */
			if (_wordToGuess.containsLetter(letter)) {
				System.out.println("Good guess!");
				updateWithGoodLetter(letter);
			} else {
				System.out.println("Sorry, wrong guess");
			}
		}
		System.out.println("Congratulations! You used " + _numberOfAttempts + " guesses for the word: " + _wordToGuess);
	}
}
