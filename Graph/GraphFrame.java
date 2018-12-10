import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/* Class for holding a GraphPanel to show a graph and add nodes, 
 * and a ButtonsPanel with several action buttons
 */
public class GraphFrame extends JFrame {

	private static final int FRAME_WIDTH = 600;
	private static final int FRAME_HEIGHT = 600;
	
	private GraphPanel _graphPanel;
	private static final Font TEXT_FONT = new Font(Font.SANS_SERIF, 0, 15);
	
	public GraphFrame() {
		super("Graph Application");
		initFrame();
	}
	
	private void initFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setResizable(false);
		setLayout(new BorderLayout());        
		
        _graphPanel = new GraphPanel();
        add(_graphPanel, BorderLayout.CENTER);
        
        add(new ButtonsPanel(this), BorderLayout.SOUTH);
        
    	JLabel lblInstr = new JLabel("Click on the area below to add a node");
		lblInstr.setFont(TEXT_FONT);
    	JPanel top = new JPanel();
    	top.setLayout(new FlowLayout());
    	top.add(lblInstr);
    	add(top, BorderLayout.NORTH);
	}
	
	public void clearGraph() {
		_graphPanel.clearGraph();
	}

	public GraphDS getGraph() {
		return _graphPanel.getGraph();
	}
	
	/* Returns true iff input contains "X,Y" where X and Y are single chars.
	 * Also shows error messages to the user if needed.	 */
	public static boolean isValidEdgeName(String input)
	{
		if (input == null) {
			return false;
		}
		
		if (input.length() != 3 || input.charAt(1) != ',') {
			JOptionPane.showMessageDialog(null, "Illegal format", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		} 
		
		char nodeName1 = input.charAt(0);
		char nodeName2 = input.charAt(2);
		input = input.toUpperCase();
    	
		if (!Character.isLetter(nodeName1) || !Character.isLetter(nodeName2)) {
			JOptionPane.showMessageDialog(null, "Node name must be one alphabetic letter", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}

	/* Returns true iff input contains a legal node name.
	 * Also shows error messages to the user if needed.	 */
	public static boolean isValidNodeName(String input){
		if (input == null){
    		return false;
		}
		if (input.length() != 1) {
			JOptionPane.showMessageDialog(null, "Node name must be one alphabetic letter", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		char nodeName = input.toUpperCase().charAt(0);
		if (!Character.isLetter(nodeName)) {
			JOptionPane.showMessageDialog(null, "Node name must be one alphabetic letter", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
}
