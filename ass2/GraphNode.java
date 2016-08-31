/**
 * GraphNode is a class that represents a node in a graph. 
 * It contains the value of the node as well as its weight.
 * @author clintonhadinata
 *
 * @param <E>
 */
public class GraphNode<E> {
	
	//Constructor
	/**
	 * Constructs a GraphNode with value value and weight weight.
	 * @param value		the value of this node
	 * @param weight	the weight of the edge directed at this node
	 */
	public GraphNode(E value, int weight) {
		this.value = value;
		this.weight = weight;
	}
	
	//Methods
	/**
	 * Returns the value of this GraphNode
	 * @return	the value of this GraphNode
	 */
	public E getValue() {
		return value;
	}
	
	/**
	 * Sets the value of this GraphNode
	 * @param value	the value that this GraphNode will be set to have
	 */
	public void setValue(E value) {
		this.value = value;
	}
	
	/**
	 * Returns the weight of the edge directed at this GraphNode
	 * @return	the weight of the edge directed at this GraphNode
	 */
	public int getWeight() {
		return weight;
	}
	
	/**
	 * Sets the weight of the edge directed at this GraphNode
	 * @param weight	the weight that the edge directed at this GraphNode will be set to have
	 */
	public void setWeight(int weight) {
		this.weight = weight;
	}

	//Variables
	private E value;
	private int weight;
}
