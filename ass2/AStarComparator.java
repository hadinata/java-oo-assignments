import java.util.Comparator;

/**
 * AStarComparator implements Comparator and compares two different search states based on their f cost,
 * the sum of the g cost and the heuristic. This thus follows the A* strategy pattern.
 * @author clintonhadinata
 *
 */
public class AStarComparator implements Comparator<SearchState> {
	
	/**
	 * Compares the f costs of two SearchStates
	 * @param 	ss1	the first SearchState
	 * @param 	ss2 the second SearchState
	 * @return 		-1 if ss1's f cost < ss2's, 1 if ss1's f cost > ss2's and 0 if they are equal
	 */
	@Override
	public int compare(SearchState ss1 , SearchState ss2) {
		return ss1.getfCost() - ss2.getfCost();
	}

}

