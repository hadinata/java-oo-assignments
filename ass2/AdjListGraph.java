import java.util.LinkedList;

/**
 * AdjListGraph is an adjacency list graph implementation of interface Graph.
 * @author clintonhadinata
 *
 */
public class AdjListGraph<E> implements Graph<E> {
	
	//Constructor
	/**
	 * Constructs an empty graph with no vertices or edges
	 */
	public AdjListGraph(){
		this.adjList = new LinkedList<LinkedList<GraphNode<E>>>();
		this.indexList = new LinkedList<E>();
	}

	
	//Methods
	
	/**
	 * Adds vertex vertex to the graph.
	 * @param 	vertex 	the vertex to be added.
	 */
	public void addVertex(E vertex) {
		this.indexList.add(vertex);
		LinkedList<GraphNode<E>> emptyList = new LinkedList<GraphNode<E>>();
		this.adjList.add(emptyList);
	}
	
	/**
	 * Removes vertex vertex from the graph.
	 * @param 	vertex 	the vertex to be removed.
	 */
	public void removeVertex(E vertex) {
		int index = this.indexList.indexOf(vertex);
		this.indexList.remove(vertex);
		this.adjList.remove(index);
		for (LinkedList<GraphNode<E>> list : adjList) {
			for (GraphNode<E> nodeT : list) {
				if (nodeT.getValue().equals(vertex)) { 
					list.remove(nodeT);
				}
			}
		}
		
	}
	
	/**
	 * Inserts a directed edge from vertex from to vertex to with weight weight.
	 * @param	from	the vertex edge is coming from
	 * @param	to		the vertex edge is going to
	 * @param	weight	the weight of the edge
	 */
	public void insertEdge(E from, E to, int weight) {
		int index = 0;
		for (int i = 0; i < this.indexList.size(); i++) {
			if (indexList.get(i).equals(from)) {
				index = i;
			}
		}
		LinkedList<GraphNode<E>> listOfNodes = this.adjList.get(index);
		GraphNode<E> gn = new GraphNode<E>(to, weight);
		listOfNodes.add(gn);
		
	}
	
	/**
	 * Inserts a bidirectional edge of equal weight.
	 * @param vertexA	the first vertex
	 * @param vertexB	the second vertex
	 * @param weight	the weight of the bidirectional edge
	 */
	public void insertBidirectionalEdge(E vertexA, E vertexB, int weight) {
		this.insertEdge(vertexA, vertexB, weight);
		this.insertEdge(vertexB, vertexA, weight);
	}
	
	/**
	 * Inserts a bidirectional edge with differing weights.
	 * @param vertexA	the first vertex
	 * @param vertexB	the second vertex
	 * @param weight1	the weight of edge going from vertexA to vertexB
	 * @param weight2	the weight of edge going from vertexB to vertexA
	 */
	public void insertBidirectionalEdge(E vertexA, E vertexB, int weight1, int weight2) {
		this.insertEdge(vertexA, vertexB, weight1);
		this.insertEdge(vertexB, vertexA, weight2);
	}

	/**
	 * Removes edge from vertex from to vertex to
	 * @param from	the vertex the edge is coming from
	 * @param to	the vertex the edge is going to
	 */
	public void removeEdge(E from, E to) {
		for (int i = 0; i < this.adjList.size(); i++) {
			LinkedList<GraphNode<E>> insideList = this.adjList.get(i);
			E vertex = indexList.get(i);
			if (vertex.equals(from)) {
				for(GraphNode<E> n : insideList) {
					if (n.getValue().equals(to)) {
						insideList.remove(n);
					}
				}
				return;
			}
		}
	}
	
	/**
	 * Returns the degree of vertex vertex.
	 * @param 	vertex	the vertex degree is being calculated on.
	 * @return			the degree of the vertex.
	 */
	public int vertexDegree(E vertex) {
		int index = this.indexList.indexOf(vertex);
		return this.adjList.get(index).size();
	}
	
	/**
	 * Returns the number of vertices in the graph
	 * @return	the number of vertices in the graph
	 */
	public int numOfVertices() {
		return this.indexList.size();
	}
	
	/**
	 * Returns a LinkedList of vertex's neighbours (what vertex is pointing to)
	 * @param 	vertex	the vertex
	 * @return			a LinkedList of the vertex's neighbours.
	 */
	public LinkedList<E> vertexNeighbours (E vertex) {
		LinkedList<E> listOfNeighbours = new LinkedList<E>();
		int index = 0;
		for(int i = 0; i < this.indexList.size(); i++) {
			E eNode = this.indexList.get(i);
			if(eNode.equals(vertex)) {
				index = i;
			}
		}
		for(int i = 0; i < this.adjList.get(index).size(); i++) {
			listOfNeighbours.add(this.adjList.get(index).get(i).getValue());
		}
		return listOfNeighbours;
	}
	
	/**
	 * Returns the vertex e that is in the graph.
	 * @param e	the vertex requested
	 * @return	the vertex requested, if it is in the graph. If not, returns null.
	 */
	public E getVertex(E e) {
		for(E eCurr : this.indexList) {
			if (eCurr.equals(e)) {
				return eCurr;
			}
		}
		return null;
	}
	
	/**
	 * Returns the weight of the edge from a to b in the graph
	 * @param a	the vertex the edge is coming from
	 * @param b	the vertex the edge is going to
	 * @return	the weight of the edge
	 */
	public int getEdgeWeight(E a, E b) {
		int index = 0;
		for(int i = 0; i < this.indexList.size(); i++) {
			if(indexList.get(i).equals(a)) {
				index = i;
			}
		}
		int weight = 0;
		LinkedList<GraphNode<E>> listOfNodes = this.adjList.get(index);
		for(int j = 0; j < listOfNodes.size(); j++) {
			GraphNode<E> currNode = listOfNodes.get(j);
			if(currNode.getValue().equals(b)) {
				weight = currNode.getWeight();
			}
		}
		return weight;
	}
	
	/**
	 * Prints the graph to output.
	 */
	public void printGraph() {
		for (int i = 0; i < this.adjList.size(); i++) {
			E vertexF = this.indexList.get(i);
			String output = vertexF.toString();
			output += " points to";
			LinkedList<GraphNode<E>> insideList = this.adjList.get(i);
			for (GraphNode<E> vertexT : insideList) {
				output += " -> " + vertexT.getWeight() + " " + vertexT.getValue().toString();
			}
			System.out.println(output);
		}
		
	}
	
	
	//Variables 
	private LinkedList<LinkedList<GraphNode<E>>> adjList;
	private LinkedList<E> indexList;

}
