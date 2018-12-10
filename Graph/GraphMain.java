import javax.swing.JFrame;

/* The main application class */
public class GraphMain {

	/* Just for testing class Graph */
	public static void testGraph() {
		char nodes[] = {'A', 'B', 'C', 'D', 'E'};
		char edges[][] = {{'A', 'C'}, {'A', 'D'}, {'C', 'D'}, {'C', 'E'}};
		
		try {
			GraphDS graph1 = new GraphDS(nodes, edges);
			System.out.println("Graph1: "+graph1);
		} catch (GraphDS.GraphException e) {
			e.printStackTrace();
		}

		GraphDS graph2 = new GraphDS();
		System.out.println("Graph2: "+graph2);
		
		try {
			graph2.addNode('X');
			graph2.addNode('Y');
			graph2.addNode('C');
			graph2.addEdge('C', 'Y');
			graph2.addEdge('Y', 'X');
			System.out.println("Graph2: "+graph2);
		} catch (GraphDS.GraphException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		//testGraph();
        JFrame frame = new GraphFrame();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
	}

}
