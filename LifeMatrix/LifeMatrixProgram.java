import java.util.Scanner;

/**
 * @author Liora Ferrero
 *
 * The main class of the Life Matrix Simulation program
 */
public class LifeMatrixProgram {

    private static Scanner scanner = new Scanner( System.in );
	
	private final int NUM_ROWS = 10;
	private final int NUM_COLUMNS = 10;
	private LifeSimulator _simulator;
	
	private LifeMatrixProgram() {
		try {
			_simulator = new LifeSimulator(NUM_ROWS, NUM_COLUMNS);
		} catch (Exception e) {
			// This is not supposed to happend because we use legal row and column numbers
		}
	}

	/* Ask the user whether to show next step or quit */
	private boolean askUser() {
		System.out.println("Show next step? (y = yes, anything else = no)");
        String input = scanner.nextLine();
		return input.toLowerCase().equals("y");
	}
	
	/* Show matrix and do next step until user chooses to quit */
	public void run() {
		System.out.println("Welcome to the Life Simulator!");
		_simulator.printMatrix();
		while (askUser()) {
			_simulator.doStep();
			_simulator.printMatrix();
		};
		System.out.println("Goodbye!");
	}
	
	public static void main(String[] args) {
		LifeMatrixProgram program = new LifeMatrixProgram();
		program.run();
	}

}
