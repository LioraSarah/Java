import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/* Class for the row of buttons at the bottom of the Graph JFrame */
public class ButtonsPanel extends JPanel {

	private GraphFrame _parent;
	
	public ButtonsPanel(GraphFrame parent) {
		_parent = parent;
		JButton btnAddEdge = new JButton("Add Edge");
		btnAddEdge.addActionListener(new AddEdgeButtonListener());
		JButton btnDelEdge = new JButton("Delete Edge");
		btnDelEdge.addActionListener(new DeleteEdgeButtonListener());
		JButton btnDelNode = new JButton("Delete Node");
		btnDelNode.addActionListener(new DeleteNodeButtonListener());
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ClearButtonListener());
		add(btnAddEdge);
		add(btnDelEdge);
		add(btnDelNode);
		add(btnClear);
	}

	/* Ask user to type an edge and check if node names are legal and nodes exist in the graph */
	private char[] getEdgeFromUser(String message) {
    	
		String input = JOptionPane.showInputDialog(null, message, "Input", JOptionPane.QUESTION_MESSAGE);
		if (input == null) {
			return null;
		}
    	
		if (!GraphFrame.isValidEdgeName(input))
			return null;

		char nodeName1 = input.toUpperCase().charAt(0);
		char nodeName2 = input.toUpperCase().charAt(2);

		try {
			if (!_parent.getGraph().hasNode(nodeName1) || !_parent.getGraph().hasNode(nodeName2)) {
				JOptionPane.showMessageDialog(null, "Node does not exist in the graph", "Error", JOptionPane.ERROR_MESSAGE);
				return null;
			}
			char[] answer = new char[2];
			answer[0] = nodeName1;
			answer[1] = nodeName2;
			return answer;
		} catch (GraphDS.GraphException e1) {
			// Should not happen
			return null;
		}
    }

    /* Handle pressing the "Add Edge" button */
    private class AddEdgeButtonListener implements ActionListener
    {
		@Override
		public void actionPerformed(ActionEvent e) {
			char[] nodeNames = getEdgeFromUser("Enter new edge in the format: X,Y");
			if (nodeNames == null)
				return;

			char nodeName1 = nodeNames[0];
			char nodeName2 = nodeNames[1];
			try {
				if (_parent.getGraph().hasEdge(nodeName1, nodeName2)) {
					JOptionPane.showMessageDialog(null, "Edge already exists in the graph", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				_parent.getGraph().addEdge(nodeName1, nodeName2);
				_parent.repaint();
			} catch (GraphDS.GraphException e1) {
				// Should not happen
			}
		}
    }
    
    /* Handle pressing the "Delete Edge" button */
    private class DeleteEdgeButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			char[] nodeNames = getEdgeFromUser("Enter edge to delete in the format: X,Y");
			if (nodeNames == null)
				return;
			
			char nodeName1 = nodeNames[0];
        	char nodeName2 = nodeNames[1];
        	try {
	        	if (!_parent.getGraph().hasEdge(nodeName1, nodeName2)) {
	        		JOptionPane.showMessageDialog(null, "Edge does not exists in the graph", "Error", JOptionPane.ERROR_MESSAGE);
	        		return;
	        	}
	        	_parent.getGraph().deleteEdge(nodeName1, nodeName2);
	        	_parent.repaint();
			} catch (GraphDS.GraphException e1) {
				// Should not happen
			}
		}
		
    }
    
    /* Handle pressing the "Delete Node" button */
    private class DeleteNodeButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				String input = JOptionPane.showInputDialog(null, "Enter node name to delete (one character)", "Input", JOptionPane.QUESTION_MESSAGE);
				if (!GraphFrame.isValidNodeName(input)){
					return;
				}
				char nodeName = input.toUpperCase().charAt(0);
				if (!_parent.getGraph().hasNode(nodeName)) {
					JOptionPane.showMessageDialog(null, "Node does not exists in the graph", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				_parent.getGraph().deleteNode(nodeName);
			} catch (GraphDS.GraphException e1) {
				// Should not happen
			}
			_parent.repaint();
		}
    	
    }

    /* Handle pressing the "Clear" button - makes the graph empty*/
	private class ClearButtonListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			_parent.clearGraph();
		}
	}
}
