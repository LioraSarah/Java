/**
 * @author Liora Ferrero
 *
 * Class for representing the word that the user needs to guess.
 * We define it as a separate class because of the instruction in the Maman: "Define a class for the selected word".
 */
public class WordToGuess {

	private String _word;
	
	/**
	 * @param word: The selected word to guess
	 */
	public WordToGuess(String word) {
		_word = word;
	}

	/* Returns the length of the word */
	public int getLength() {
		return _word.length();
	}

	/* Returns true if the word contains letter */
	public boolean containsLetter(char letter) {
		return _word.contains(""+letter);
	}

	/* Returns the letter at index */
	public char getChar(int index) {
		return _word.charAt(index);
	}
	
	public String toString() {
		return _word;
	}
}
