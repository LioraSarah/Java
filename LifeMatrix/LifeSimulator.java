import java.util.Random;

/**
 * @author Liora Ferrero
 *
 * The class for managing the simulation: initialization, calculating next step etc.
 */
public class LifeSimulator {

	/* percent of live cell during initalization */
	private final static int CHANCE_OF_ALIVE = 50;
	
	private int _numRows, _numColumns;
	private LifeMatrix _matrix;
	
	/* the constructor that builds and initializes the matrix */
	public LifeSimulator(int numRows, int numColumns) throws Exception {
		_numRows = numRows;
		_numColumns = numColumns;
		_matrix = new LifeMatrix(numRows, numColumns);
		initMatrix();
	}
	
	/* initialize the matrix with random alive or not alive cells */
	private void initMatrix() {
		try {
			Random random = new Random();
			for (int row=0; row<_numRows; ++row) {
				for (int column=0; column<_numColumns; ++column) {
					// generate alive cells, with CHANCE_OF_ALIVE chance
					boolean value = (random.nextInt(100) < CHANCE_OF_ALIVE);
					_matrix.setCellValue(row, column, value);
				}
			}
		} catch (Exception e) {
			// This is not supposed to happend because we use only legal row and column numbers
		}
	}
	
	/* a function for calculating a 'new generation' in the matrix, based on the 'old generation' matrix */
	public void doStep() {
		try {
			LifeMatrix newMatrix = new LifeMatrix(_numRows, _numColumns);
			for (int row=0; row<_numRows; ++row) {
				for (int column=0; column<_numColumns; ++column) {
					updateCell(_matrix, newMatrix, row, column);
				}
			}
			_matrix = newMatrix;   // update to the new matrix (the old one is discarded)
		} catch (Exception e) {
			// This is not supposed to happend because we use only legal row and column numbers
			System.out.println("Unexpected Error in doStep()");
		}
	}
	
	/* a function for updating a cell in the 'new generation' matrix */
	private void updateCell(LifeMatrix oldMatrix, LifeMatrix newMatrix, int row, int column) throws Exception {
		boolean oldVal = oldMatrix.getCellValue(row, column);
		int numNeighbors = oldMatrix.numberOfNeighbors(row, column);
		boolean newVal = oldVal;
		if (oldVal) { // there is life in this cell
			if (numNeighbors < 2 || numNeighbors > 3)
				newVal = false;    // kill it!
		} else {  // oldVal == false, there is no life in this cell
			if (numNeighbors == 3)
				newVal = true;     // A cell is born
		}
		newMatrix.setCellValue(row, column, newVal);
	}
	
	public void printMatrix() {
		_matrix.printMatrix();
	}
	
}
