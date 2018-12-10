/* Class for representing a Character Graph data structure. 
 * (assuming only uppercase characters A-Z can be nodes) 
 * In this simple implementation, we hold a vector of booleans 
 *   that indicates which nodes are in the graph,
 * and a 2D array of booleans that indicates which edges are in the graph. 
 * Various exceptions are defined at the end 
 * - they are thrown for different kinds of illegal inputs to methods.
 */
public class GraphDS {

	public static final int NUM_POSSIBLE_NODES = 'Z'-'A'+1;
	
	private boolean _nodes[] = new boolean[NUM_POSSIBLE_NODES];
	private boolean _edges[][] = new boolean[NUM_POSSIBLE_NODES][NUM_POSSIBLE_NODES];
	private int _numNodes = 0;

	public GraphDS() {
		// All nodes and edges are automatically initialized to false, so no need to do anything
	}
	
	public GraphDS(char[] nodeIds, char[][] edges) throws IllegalCharacterNodeException, NodeAlreadyExistsException, 
														IllegalEdgeInputException, EdgeAdditionForNonExistingNodeException, 
														EdgeAlreadyExistsException, NullInputException {
		if (nodeIds == null || edges == null) {
			throw new NullInputException();
		}
		for (char nodeId : nodeIds) {
			addNode(nodeId);
		}
		for (char[] pair : edges) {
			if (pair.length != 2) {
				throw new IllegalEdgeInputException(pair);
			}
			addEdge(pair[0], pair[1]);
		}
	}

	/* Convert from a node name char 
	 * to the index in the nodes vector (or edge table) that represents this char */
	public static int charToNodeId(char nodeId) throws IllegalCharacterNodeException {
		if (nodeId >= 'A' && nodeId <= 'Z')
			return nodeId - 'A';
		throw new IllegalCharacterNodeException(nodeId);
	}

	/* Convert from index in the nodes vector (or edge table) to the name of the node */
	public static char nodeIdToChar(int nodeId) {
		assert nodeId >= 0 && nodeId < GraphDS.NUM_POSSIBLE_NODES;
		return (char) ('A' + nodeId);
	}

	/* Returns true iff the graph has the node */
	public boolean hasNode(char nodeName) throws IllegalCharacterNodeException {
		int pos = charToNodeId(nodeName);
		return _nodes[pos];
	}

	/* Returns a vector with the names of the nodes in the graph */
	public char[] getNodes() {
		char[] answer = new char[_numNodes];
		int j = 0;
		for (int i=0; i<NUM_POSSIBLE_NODES; ++i) {
			if (_nodes[i]) {
				answer[j++] = nodeIdToChar(i);
			}
		}
		return answer;
	}

	/* Returns true iff the graph has an edge between the two nodes */
	public boolean hasEdge(char nodeId1, char nodeId2) throws IllegalCharacterNodeException {
		int pos1 = charToNodeId(nodeId1);
		int pos2 = charToNodeId(nodeId2);
		return _edges[pos1][pos2];
	}

	/* Adds a new node to the graph (exception if already exists) */
	public void addNode(char nodeId) throws IllegalCharacterNodeException, NodeAlreadyExistsException {
		int pos = charToNodeId(nodeId);
		if (_nodes[pos]) {
			throw new NodeAlreadyExistsException(nodeId);
		}
		_nodes[pos] = true;
		_numNodes++;
	}
	
	/* Deletes a node from the graph (exception if does not exist),
	 * as well as all the edges in which it participates */
	public void deleteNode(char nodeId) throws IllegalCharacterNodeException, NodeDoesNotExistException {
		int pos = charToNodeId(nodeId);
		if (!_nodes[pos]) {
			throw new NodeDoesNotExistException(nodeId);
		}
		_nodes[pos] = false;
		_numNodes--;
		for (int p=0; p < NUM_POSSIBLE_NODES; ++p) {
			_edges[pos][p] = false;
			_edges[p][pos] = false;
		}
	}

	/* Add edge to graph (exception if already exists, 
	 * or if one of the nodes does not exist) */
	public void addEdge(char nodeId1, char nodeId2) throws IllegalCharacterNodeException, EdgeAdditionForNonExistingNodeException, EdgeAlreadyExistsException {
		int pos1 = charToNodeId(nodeId1);
		int pos2 = charToNodeId(nodeId2);
		if (!(_nodes[pos1])) {
			throw new EdgeAdditionForNonExistingNodeException(nodeId1);
		}
		if (!(_nodes[pos2])) {
			throw new EdgeAdditionForNonExistingNodeException(nodeId2);
		}
		if (_edges[pos1][pos2]) {
			throw new EdgeAlreadyExistsException(nodeId1, nodeId2);
		}
		_edges[pos1][pos2] = true;
		_edges[pos2][pos1] = true;
	}

	/* Delete edge from graph (exception if does not exist) */
	public void deleteEdge(char nodeId1, char nodeId2) throws IllegalCharacterNodeException, EdgeDoesNotExistException {
		int pos1 = charToNodeId(nodeId1);
		int pos2 = charToNodeId(nodeId2);
		if (!_edges[pos1][pos2]) {
			throw new EdgeDoesNotExistException(nodeId1, nodeId2);
		}
		_edges[pos1][pos2] = false;
		_edges[pos2][pos1] = false;
	}

	/* Returns a string representation of the graph in the form:
	 * "Graph<nodes, edges>", i.e.:
	 * "Graph<{node1, node2 ...}, {(node1,node2), (node3,node4),...}>"
	 */
	@Override
	public String toString() {
		String nodesStr = "";
		for (int i=0; i<NUM_POSSIBLE_NODES; ++i) {
			if (_nodes[i]) {
				if (!nodesStr.isEmpty()) {
					nodesStr += ",";
				}
				nodesStr += nodeIdToChar(i);
			}
		}
		nodesStr = "{"+nodesStr+"}";
		
		String edgesStr = "";
		for (int i=0; i<NUM_POSSIBLE_NODES; ++i) {
			for (int j=i; j<NUM_POSSIBLE_NODES; ++j) {
				if (_edges[i][j]) {
					if (!edgesStr.isEmpty()) {
						edgesStr += ",";
					}
					edgesStr += "(" + nodeIdToChar(i) + "," + nodeIdToChar(j) + ")";
				}
			}
		}
		edgesStr = "{"+edgesStr+"}";
		
		return "Graph<"+nodesStr+" "+edgesStr+">";
	}

	/* Two graphs are equal iff they have exactly the same nodes and same edges */
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof GraphDS))
			return false;
		GraphDS graph = (GraphDS) other;
		for (int i=0; i<NUM_POSSIBLE_NODES; ++i) {
			if (_nodes[i] != graph._nodes[i])
				return false;
		}
		for (int i=0; i<NUM_POSSIBLE_NODES; ++i) {
			for (int j=i; j<NUM_POSSIBLE_NODES; ++j) {
				if (_edges[i][j] != graph._edges[i][j])
					return false;
			}
		}
		return true;
	}

	/* The parent of all the exceptions that can be thrown from the Graph class */
	public static abstract class GraphException extends Exception {
		public GraphException(String message) {
			super(message);
		}
	}

	public static class NullInputException extends GraphException {
		public NullInputException() {
			super("Null input");
		}
	}

	public static class IllegalCharacterNodeException extends GraphException {
		public IllegalCharacterNodeException(char nodeId) {
			super("Illegal character id: "+nodeId);
		}
	}
	
	public static class NodeAlreadyExistsException extends GraphException {
		public NodeAlreadyExistsException(char nodeId) {
			super("Node "+nodeId+" already in the graph");
		}
	}
	
	public static class NodeDoesNotExistException extends GraphException {
		public NodeDoesNotExistException(char nodeId) {
			super("Node "+nodeId+" does not exist in the graph");
		}
	}

	public static class EdgeAdditionForNonExistingNodeException extends GraphException {
		public EdgeAdditionForNonExistingNodeException(char nodeId) {
			super("Cannot add edge on no-existing node "+nodeId);
		}
	}
	
	public static class EdgeAlreadyExistsException extends GraphException {
		public EdgeAlreadyExistsException(char nodeId1, char nodeId2) {
			super("Edge ("+nodeId1+","+nodeId2+") already exists in the graph");
		}
	}

	public static class EdgeDoesNotExistException extends GraphException {
		public EdgeDoesNotExistException(char nodeId1, char nodeId2) {
			super("Edge ("+nodeId1+","+nodeId2+") does not exist in the graph");
		}
	}
	
	public static class IllegalEdgeInputException extends GraphException {
		public IllegalEdgeInputException(char[] vec) {
			super("Edge vector should have exactly two characters");
		}
	}

}
