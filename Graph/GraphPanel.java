import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

/* A panel that draws a Graph */
public class GraphPanel extends JPanel {

	private static final int NODE_RADIUS = 24;
	private static final int FONT_SIZE = 20;
	private static final Font NODE_FONT = new Font("TimesRoman", Font.BOLD, FONT_SIZE);
	
	private GraphDS _graph;
	private int[][] _nodePositions;   // 2D array from nodeIds to vector of coordinates x,y of the node

	public GraphPanel() {
		initGraph();
		addMouseListener(new Listener());
	}

	private void initGraph() {
		_nodePositions = new int[GraphDS.NUM_POSSIBLE_NODES][2];

		/* This is just to initialize the graph with some example to show */
		char nodes[] = {'B', 'D'};
		char edges[][] = {{'B', 'D'}};
		try {
			_graph = new GraphDS(nodes, edges);
			_nodePositions[GraphDS.charToNodeId('B')][0] = 50;
			_nodePositions[GraphDS.charToNodeId('B')][1] = 50;
			_nodePositions[GraphDS.charToNodeId('D')][0] = 170;
			_nodePositions[GraphDS.charToNodeId('D')][1] = 130;
		} catch (GraphDS.GraphException e) {
			_graph = new GraphDS();
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		paintEdges(g);
		paintNodes(g);
	}

	/* Paint all the graph edges */
	private void paintEdges(Graphics g) {
		char[] nodeNames = _graph.getNodes();
	
		try {
			for (int pos1=0; pos1<nodeNames.length; ++pos1) {
				char nodeName1 = nodeNames[pos1];
				for (int pos2=pos1; pos2<nodeNames.length; ++pos2) {
					char nodeName2 = nodeNames[pos2];
					if (_graph.hasEdge(nodeNames[pos1], nodeNames[pos2])) {
						paintEdge(g, _nodePositions[GraphDS.charToNodeId(nodeName1)], _nodePositions[GraphDS.charToNodeId(nodeName2)]);
					}
				}
			}
		} catch (GraphDS.GraphException e) {
			// Should not happen
		}
	}

	/* Paint one graph edge between the two positions */
	private void paintEdge(Graphics g, int[] position1, int[] position2) {
		Graphics2D g2 = (Graphics2D)g;
		g.setColor(Color.BLUE);
		g2.setStroke(new BasicStroke(5));
		if (position1[0] == position2[0] && position1[1] == position2[1]){ // draw an 'ovalic' edge from the node to itself
			g2.drawOval(position1[0], position1[1], NODE_RADIUS*2, NODE_RADIUS*2);
		} else {
			g2.drawLine(position1[0], position1[1], position2[0], position2[1]);
		}
	}
	
	/* Paint all the graph nodes */
	private void paintNodes(Graphics g) {
		for (char nodeName : _graph.getNodes()) {
			try {
				if (_graph.hasNode(nodeName)) {
					paintNode(g, nodeName, _nodePositions[GraphDS.charToNodeId(nodeName)]);
				}
			} catch (GraphDS.GraphException e) {
				// Should not happen
			}
		}
	}

	/* Paint one graph node */
	private void paintNode(Graphics g, char nodeName, int[] position) {
		g.setColor(Color.RED);
		g.fillOval(position[0]-NODE_RADIUS, position[1]-NODE_RADIUS, NODE_RADIUS*2, NODE_RADIUS*2);
		g.setColor(Color.BLACK);
		g.setFont(NODE_FONT);
		g.drawString(""+nodeName, (int)(position[0]-FONT_SIZE/3.5), (int)(position[1]+FONT_SIZE/2.5));
	}

	/* Clear the graph (use a new Graph instance) */
	public void clearGraph() {
		_graph = new GraphDS();
		repaint();
	}

	/* Listener for mouse click:
	 * ask user for new node name and create it in mouse click position */
    private class Listener implements MouseListener
    {
		@Override
		public void mouseClicked(MouseEvent e) {
			int x = e.getX();
			int y = e.getY();
			try {
				String newName = JOptionPane.showInputDialog(null, "Enter new node name (one character)", "Input", JOptionPane.QUESTION_MESSAGE);
				if(!GraphFrame.isValidNodeName(newName)){
					return;
				}
				char nodeName = newName.toUpperCase().charAt(0);
				if (_graph.hasNode(nodeName)) {
					JOptionPane.showMessageDialog(null, "Node already exists in the graph", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				_graph.addNode(nodeName);
				int nodeId = GraphDS.charToNodeId(nodeName);
				_nodePositions[nodeId][0] = x;
				_nodePositions[nodeId][1] = y;
			} catch (GraphDS.GraphException e1) {
				// Should not happen
			}
			repaint();
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
		}
    }

	public GraphDS getGraph() {
		return _graph;
	}
	
}
