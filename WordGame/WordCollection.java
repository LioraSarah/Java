import java.util.Random;

/**
 * @author Liora Ferrero
 * 
 * Class for representing a collection of words 
 */
public class WordCollection {

	/* According to the instructions in the Maman, we can initialize the words inside the program,
	 * so not reading them from an external file.
	 */
	private static final String[] _words = {"computer", "department", "difficult", "metabolism", "international", "abstraction", "argentina",
											"emphasizing", "productivity", "encapsulation", "polymorphism", "inheritance"};
	private Random _random = new Random();
	
	public WordCollection() {
	}
	
	/* Returns a random word from the collection */
	WordToGuess getRandomWord() {
		int index = _random.nextInt(_words.length);
		return new WordToGuess(_words[index]);
	}
}
