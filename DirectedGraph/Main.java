public class Main {

	public static void printGraph(GraphDS<?> graph) {
		System.out.println(graph);
		System.out.println("--------------");
	}

	public static void printException(Exception e) {
		System.out.println("Caught exception: "+e);
		System.out.println("--------------");
	}
	
	public static void tryDeleteEdge(GraphDS<Point> graph, Point p1, Point p2) {
		try {
			System.out.println("Trying to deleted edge: "+p1+"->"+p2+" from graph1:");
			graph.deleteEdge(p1, p2);
			printGraph(graph);
		} catch (GraphDS.GraphException e) {
			printException(e);
		}
	}
	
	public static void tryAddEdge(GraphDS<Point> graph, Point p1, Point p2) {
		try {
			System.out.println("Trying to add edge: "+p1+"->"+p2+" to graph1:");
			graph.addEdge(p1, p2);
			printGraph(graph);
		} catch (GraphDS.GraphException e) {
			printException(e);
		}
	}
	
	public static void tryDeleteNode(GraphDS<Point> graph, Point p) {
		try {
			System.out.println("Trying to deleted node: "+p+" from graph1:");
			graph.deleteNode(p);
			printGraph(graph);
		} catch (GraphDS.GraphException e) {
			printException(e);
		}
	}
	
	public static void tryAddNode(GraphDS<Point> graph, Point p) {
		try {
			System.out.println("Trying to add node: "+p+" to graph1:");
			graph.addNode(p);;
			printGraph(graph);
		} catch (GraphDS.GraphException e) {
			printException(e);
		}
	}
	
	public static void tryEqual(GraphDS<Point> graph1, GraphDS<Point> graph2) {
		System.out.println("Does graph1 equals graph2?");
		if (graph1.equals(graph2)) {
			System.out.println("Yes");
		} else {
			System.out.println("No");
		}
	}
	
	public static void tryHasNode(GraphDS<Point> graph, Point p) {
		System.out.println("Does graph1 has the point - "+p+"?");
		if (graph.hasNode(p)) {
			System.out.println("Yes");
		} else {
			System.out.println("No");
		}
		System.out.println("--------------");
	}
	
	public static void tryHasEdge(GraphDS<Point> graph, Point p1, Point p2) {
		System.out.println("Does graph1 has the Edge - "+p1+"->"+p2+"?");
		try {
			if (graph.hasEdge(p1, p2)) {
				System.out.println("Yes");
			} else {
				System.out.println("No");
			}
		} catch (GraphDS.GraphException e) {
			// if the given nodes does not even exist in the graph
			System.out.println("No");
		}
		System.out.println("--------------");
	}
	
	public static void main(String[] args) {
		Point p1 = new Point(1,2);
		Point p2 = new Point(3,4);
		Point p3 = new Point(5,6);
		Point[] nodes1 = { p1 , p2, p3 };
		Point[][] edges1 = {{p1, p2}, {p3, p2}, {p3, p3}};
		Point[] nodes2 = { p1 , p2, p3 };
		Point[][] edges2 = {{p1, p2}};
		try {
			GraphDS<Point> graph1 = new GraphDS<>(nodes1, edges1);
			GraphDS<Point> graph2 = new GraphDS<>(nodes2, edges2);
			
			System.out.println("The initial graph1 is:");
			printGraph(graph1);
			
			System.out.println("And graph2 is:");
			System.out.println("(graph2 won't be changed)");
			printGraph(graph2);
			
			tryEqual(graph1, graph2);
			System.out.println("--------------");
			
			tryDeleteEdge(graph1, p2, p3);
			tryDeleteEdge(graph1, p3, p2);
			
			tryHasEdge(graph1, p3, p2);
			tryHasEdge(graph1, p1, p2);
			
			tryAddEdge(graph1, p2, p3);
			tryAddEdge(graph1, p2, p3);
			tryAddEdge(graph1, p3, p2);
			
			tryDeleteNode(graph1, p3);
			tryDeleteNode(graph1, p3);
			
			tryHasNode(graph1, p3);
			tryHasNode(graph1, p2);
			
			tryAddNode(graph1, p3);
			tryEqual(graph1, graph2);
			
		} catch (GraphDS.GraphException e) {
			e.printStackTrace();
		}
	}

}
