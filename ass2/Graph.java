import java.util.LinkedList;

/**
 * This is a graph interface with operations one would want to do on a graph.
 * @author clintonhadinata
 *
 */
public interface Graph<E> {
	
	//methods
	
	/**
	 * Adds vertex vertex to the graph.
	 * @param 	vertex 	the vertex to be added.
	 */
	public void addVertex(E vertex);
	
	/**
	 * Removes vertex vertex from the graph.
	 * @param 	vertex 	the vertex to be removed.
	 */
	public void removeVertex(E vertex);
	
	/**
	 * Inserts a directed edge from vertex from to vertex to with weight weight.
	 * @param	from	the vertex edge is coming from
	 * @param	to		the vertex edge is going to
	 * @param	weight	the weight of the edge
	 */
	public void insertEdge(E from, E to, int weight);
	
	/**
	 * Removes edge from vertex from to vertex to
	 * @param from	the vertex the edge is coming from
	 * @param to	the vertex the edge is going to
	 */
	public void removeEdge(E from, E to);
	
	/**
	 * Returns the degree of vertex vertex.
	 * @param 	vertex	the vertex degree is being calculated on.
	 * @return			the degree of the vertex.
	 */
	public int vertexDegree (E vertex);
	
	/**
	 * Returns a LinkedList of vertex's neighbours (what vertex is pointing to)
	 * @param 	vertex	the vertex
	 * @return			a LinkedList of the vertex's neighbours.
	 */
	public LinkedList<E> vertexNeighbours (E vertex);
	
	/**
	 * Returns the number of vertices in the graph
	 * @return	the number of vertices in the graph
	 */
	public int numOfVertices();
	
	/**
	 * Prints the graph to output.
	 */
	public void printGraph();
}
