import java.util.LinkedList;

/**
 * SearchState is a class that represents the state of a search. It contains
 * the TripNode the state is at, the history/trail of TripNodes that created the state
 * as well as its g cost and heuristic value. 
 * @author clintonhadinata
 *
 */
public class SearchState {
	
	//Constructor
	/**
	 * Creates a SearchState at TripNode node, with history history, gCost gCost, and heuristic heuristic.
	 * @param node		the TripNode the SearchState is at
	 * @param history	the list of trips that forms this SearchState
	 * @param gCost		the gCost of the SearchState
	 * @param heuristic	the heuristic of the SearchState
	 */
	public SearchState(TripNode node, LinkedList<TripNode> history, int gCost, int heuristic) {
		this.node = node;
		this.history = history;
		this.gCost = gCost;
		this.heuristic = heuristic;
		
	}
	
	//Methods
	
	/**
	 * Returns the f cost, which is the sum of the SearchState's gCost and heuristic.
	 * @return 	the SearchState's fCost.
	 */
	public int getfCost() {
		return this.gCost + this.heuristic;
	}
	
	//Getters and Setters
	/**
	 * Returns the TripNode this SearchState is at
	 * @return	the TripNode this SearchState is at
	 */
	public TripNode getNode() {
		return node;
	}
	
	/**
	 * Sets the TripNode this SearchState is at
	 * @param node	the TripNode that this SearchState will be set to be at
	 */
	public void setNode(TripNode node) {
		this.node = node;
	}
	
	/**
	 * Returns the history of this SearchState (the list of trips that form this SearchState)
	 * @return	the history of this SearchState
	 */
	public LinkedList<TripNode> getHistory() {
		return history;
	}
	
	/**
	 * Sets the history of this SearchState (the list of trips that form this SearchState)
	 * @param history	the list of TripNodes that will form this SearchState's history
	 */
	public void setHistory(LinkedList<TripNode> history) {
		this.history = history;
	}
	
	/**
	 * Returns the gCost of this SearchState
	 * @return	the gCost of this SearchState
	 */
	public int getgCost() {
		return gCost;
	}

	/**
	 * Sets the gCost of this SearchState
	 * @param gCost	the gCost that this SearchState will be set to have
	 */
	public void setgCost(int gCost) {
		this.gCost = gCost;
	}

	/**
	 * Returns the heuristic of this SearchSpace
	 * @return	the heuristic of this SearchSpace
	 */
	public int getHeuristic() {
		return heuristic;
	}

	/**
	 * Sets the heuristic of this SearchSpace
	 * @param heuristic	the heuristic that this SearchState will be set to have
	 */
	public void setHeuristic(int heuristic) {
		this.heuristic = heuristic;
	}


	//Variables
	private TripNode node;
	private LinkedList<TripNode> history;
	private int gCost;
	private int heuristic;
	
}
