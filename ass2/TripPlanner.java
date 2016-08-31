import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * TripPlanner is a class that implements the TripPlanner system.
 * It stores two graphs along with a list of its nodes - a graph of a Map with cities as nodes, and a graph of required TripNodes.
 * It also stores a counter that keeps track of nodes expanded.
 * @author clintonhadinata
 *
 */
public class TripPlanner {
	
	public static void main(String[] args) {
		
		TripPlanner tp = new TripPlanner();
		tp.readInput(args[0]);	
		
	}
	
	//Constructor
	
	/**
	 * Constructs a TripPlanner with all graph and lists being empty, and the counter = 0.
	 */
	public TripPlanner() {
		this.mapGraph = new AdjListGraph<City>();
		this.listOfCities = new LinkedList<City>();
		this.tripGraph = new AdjListGraph<TripNode>();
		this.listOfReqTrips = new LinkedList<TripNode>();
		this.nodesExpanded = 0;
	}
	
	
	//Methods
	
	/**
	 * Executes the program based on input arguments contained in the file inputFileName
	 * @param inputFileName the name of the file to be used as input
	 */
	private void readInput(String inputFileName) {
		Scanner sc = null;
	      try
	      {
	          sc = new Scanner(new FileReader(inputFileName));    // args[0] is the first command line argument
	          
	          while (sc.hasNextLine()) {
					
					String x = sc.nextLine();
					String[] array = x.split(" ");
					String type = array[0];
					
					if(type.equals("Transfer")) {
						int transferTime = Integer.parseInt(array[1]);
						String cityName = array[2];
						
						City c = new City(cityName, transferTime);
						this.mapGraph.addVertex(c);
						this.listOfCities.add(c);
						
					}
					
					if(type.equals("Time")) {
						int travelTime = Integer.parseInt(array[1]);
						String from = array[2];
						String to = array[3];
						City cityFrom = this.getCity(from);
						City cityTo = this.getCity(to);
						this.mapGraph.insertBidirectionalEdge(cityFrom, cityTo, travelTime);
					}
					
					if (type.equals("Trip")) {
						String from = array[1];
						String to = array[2];
						City cityFrom = this.getCity(from);
						City cityTo = this.getCity(to);
						int travelTime = this.mapGraph.getEdgeWeight(cityFrom, cityTo);
						TripNode tn = new TripNode(cityFrom, cityTo, travelTime);
						this.tripGraph.addVertex(tn);
						this.listOfReqTrips.add(tn);
					}
					
				}	
	          
	      }
	      catch (FileNotFoundException e) {}
	      finally
	      {
	          if (sc != null) sc.close();
	      }
	      
	      
	      City london = this.findLondon();
	      TripNode dummy = new TripNode(null, london, 0);
	      this.tripGraph.addVertex(dummy);
	      
	      for (int i = 0; i < this.listOfReqTrips.size(); i++) {
	    	  TripNode tn = this.listOfReqTrips.get(i);
	    	  int edgeWeight = 0;
	    	  if (!tn.getFrom().getName().equals("London")) {
	    		  edgeWeight = this.mapGraph.getEdgeWeight(london, tn.getFrom());
	    	  }
	    	  this.tripGraph.insertEdge(dummy, tn, edgeWeight);
	      }
	      
	      for (int i = 0; i < this.listOfReqTrips.size(); i++) {
	    	  TripNode tnA = this.listOfReqTrips.get(i);
	    	  for(int j = 0; j < this.listOfReqTrips.size(); j++) {
	    		  TripNode tnB = this.listOfReqTrips.get(j);
	    		  if (tnA.equals(tnB)) {
	    			  //do nothing
	    		  } else {
		    		  City from = tnA.getTo();
		    		  City to = tnB.getFrom();
		    		  int edgeWeight = this.mapGraph.getEdgeWeight(from, to);
		    		  this.tripGraph.insertEdge(tnA, tnB, edgeWeight);
	    		  }
	    	  }
	      }
	      	      
	      this.printAstarSearch(tripGraph, dummy);
	      

	}
	
	/**
	 * Returns the City with name name in listOfCitiesc
	 * @param name	the name of the City
	 * @return		the City with the name
	 */
	private City getCity(String name) {
		for (City c : this.listOfCities) {
			if (c.getName().equals(name)) {
				return c;
			}
		}
		return null;
	}
	
	
	/**
	 * This method conducts A* search on a graph of TripNodes g, and returns a list of resulting SearchStates,
	 * the last of which contains the optimal solution in its history.
	 * @param g			The graph of required trips to be searched against.
	 * @param dummyNode	The dummyNode where the search will begin.
	 * @return			the list of resulting SearchStates, the last of which contains the optimal solution in its history.
	 */
	public LinkedList<SearchState> aStar(AdjListGraph<TripNode> g, TripNode dummyNode){
		
		// Initialise visited list and priority queue
		Comparator<SearchState> ssc = new AStarComparator();
		LinkedList<SearchState> nodesVisited = new LinkedList<SearchState>();
		PriorityQueue<SearchState> nodeToVisit = new PriorityQueue<SearchState>(10, ssc);
		
		// Add dummy node state to the queue
		LinkedList<TripNode> emptyList = new LinkedList<TripNode>();
		SearchState dummyNodeState = new SearchState(dummyNode, emptyList, 0, 0);
		nodeToVisit.add(dummyNodeState);
		
		while (!nodeToVisit.isEmpty()){ 
			
			//Remove from queue
			SearchState currentSS = nodeToVisit.remove();
			this.nodesExpanded++;	//increment nodesExpanded
			
			//if goalState is met, break
			if(meetsRequired(currentSS.getHistory())) {
				nodesVisited.add(currentSS);
				break;
			}
			
			TripNode currentTN = currentSS.getNode();
			LinkedList<TripNode> listOfNeighbours = g.vertexNeighbours(currentTN);
			
			// Iterate through the TripNode's neighbours
			for (int i = 0; i < listOfNeighbours.size(); i++) {
				
				TripNode neighbour = listOfNeighbours.get(i);		
				
				LinkedList<TripNode> newSSHistory = copyListOfTrips(currentSS.getHistory());
				newSSHistory.add(neighbour);
				
				// Calculate g cost
				int edgeWeight = g.getEdgeWeight(currentTN, neighbour);
				int gCost = currentSS.getgCost();
				gCost += currentTN.getTo().getTransferTime() + edgeWeight + neighbour.getFrom().getTransferTime() + neighbour.getTravelTime(); 
				if(edgeWeight == 0) {
					gCost -= currentTN.getTo().getTransferTime();
				}
				
				// Calculate heuristic
				int heuristic = this.getHeuristic(newSSHistory);
				
				SearchState newSS = new SearchState(neighbour, newSSHistory, gCost, heuristic);
				nodeToVisit.add(newSS);		//add to queue
				
			}
			nodesVisited.add(currentSS);
		}
		
		return nodesVisited;
		
	}
	
	/**
	 * This is the method to calculate the heuristic.
	 * What it does? 	It calculates the cost of all the remaining required trips that is yet to be visited.
	 * 					This includes the transfer time of the origin city as well as the travel time, 
	 * 					but not the transfer time of the destination city.
	 * Time Complexity?	O(n^2) where n = # of reqTrips. But n wouldn't be high (less than 10) so not thaaat big of an issue.
	 * Admissible?		Definitely admissible, always, because it can't be lesser that the g cost as that also includes 
	 * 					the edge weights between the required TripNodes.
	 * @param history	the list of TripNodes visited for this particular SearchState.
	 * @return			the heuristic.
	 */
	private int getHeuristic(LinkedList<TripNode> history) {
		LinkedList<TripNode> tripsLeft = this.copyListOfTrips(this.listOfReqTrips);
		for (int i = 0; i < history.size(); i++) {
			TripNode tn = history.get(i);
			tripsLeft.remove(tn);
		}
		int heuristic = 0;
		for (int i = 0; i < tripsLeft.size(); i++) {
			TripNode tn = tripsLeft.get(i);
			heuristic += tn.getFrom().getTransferTime() + tn.getTravelTime();
		}
		return heuristic;
	}
	
	/**
	 * Returns the City with the name "London"
	 * @return	 the City with the name "London"
	 */
	private City findLondon() {
		for (int i = 0; i < this.listOfCities.size(); i++) {
			City c = this.listOfCities.get(i);
			if (c.getName().equals("London")) {
				return c;
			}
		}
		return null;
	}
	
	/**
	 * Copies a list of TripNodes into a new list.
	 * @param listOfTrips	list of TripNodes
	 * @return				the new copied list
	 */
	private LinkedList<TripNode> copyListOfTrips(LinkedList<TripNode> listOfTrips) {
		LinkedList<TripNode> copy = new LinkedList<TripNode>();
		for (int i = 0; i < listOfTrips.size(); i++) {
			copy.add(listOfTrips.get(i));
		}
		return copy;
	}
	
	/**
	 * Checks if a list of TripNodes contains all of the required trips.
	 * @param list	the list of TripNodes
	 * @return		true if it contains all of the required trips, false otherwise.
	 */
	private boolean meetsRequired(LinkedList<TripNode> list) {
		boolean meets = false;
		int counter = 0;
		for (int i = 0; i < this.listOfReqTrips.size(); i++) {
			TripNode tn = this.listOfReqTrips.get(i);
			if (list.contains(tn)) {
				counter++;
			}
		}
		if (counter == this.listOfReqTrips.size()) {
			meets = true;
		}
		return meets;
	}
	
	/**
	 * Prints the specified required output resulting from the A* search.
	 * @param g		The graph of TripNodes being searched on
	 * @param dummy	The dummy TripNode that determines starting point of A* search.
	 */
	private void printAstarSearch(AdjListGraph<TripNode> g, TripNode dummy) {
		
		LinkedList<SearchState> ssList = this.aStar(g, dummy);
		LinkedList<TripNode> resultList = ssList.getLast().getHistory();
		
		
		TripNode tn0 = resultList.get(0);
		City london = new City("London", 60);
		int totalCost = 0;
		if (tn0.getFrom().getName().equals("London")) {
			totalCost += resultList.get(0).getTravelTime() + resultList.get(0).getTo().getTransferTime();
		} else {
			City from1 = tn0.getFrom();
			totalCost += this.mapGraph.getEdgeWeight(london, from1) + from1.getTransferTime();
			totalCost += resultList.get(0).getTravelTime() + resultList.get(0).getTo().getTransferTime();
		}
		int i = 0;
		for(i = 0; i < resultList.size()-1; i++) {
			TripNode tnNext = resultList.get(i+1);
			TripNode tnCurr = resultList.get(i);
			//City fromA = tnCurr.getFrom();
			City toA = tnCurr.getTo();
			City fromB = tnNext.getFrom();
			City toB = tnNext.getTo();
			
			if(!toA.equals(fromB)) {
				totalCost += this.mapGraph.getEdgeWeight(toA, fromB) + fromB.getTransferTime();
			}
			totalCost += this.mapGraph.getEdgeWeight(fromB, toB) + toB.getTransferTime();
			if(i+1 == resultList.size() - 1) {
				totalCost -= toB.getTransferTime();
			}
		}
		
		System.out.println(this.nodesExpanded + " nodes expanded");
		System.out.println("cost = " + totalCost);
		if (tn0.getFrom().getName().equals("London"))
			System.out.println("Trip " + resultList.get(0).getFrom().getName() + " to " + resultList.get(0).getTo().getName());
		else {
			System.out.println("Trip London to " + tn0.getFrom().getName());
			System.out.println("Trip " + resultList.get(0).getFrom().getName() + " to " + resultList.get(0).getTo().getName());
		}
		for (int j = 0; j < resultList.size()-1; j++) {
			TripNode tnNext = resultList.get(j+1);
			TripNode tnCurr = resultList.get(j);
			//City fromA = tnCurr.getFrom();
			City toA = tnCurr.getTo();
			City fromB = tnNext.getFrom();
			City toB = tnNext.getTo();
			
			if(toA.equals(fromB)) {
				System.out.println("Trip " + fromB.getName() + " to " + toB.getName());
			} else {
				System.out.println("Trip " + toA.getName() + " to " + fromB.getName());
				System.out.println("Trip " + fromB.getName() + " to " + toB.getName());
			}
		}
		
	}
	
	
	//Variables
	private AdjListGraph<City> mapGraph;
	private LinkedList<City> listOfCities;
	
	private AdjListGraph<TripNode> tripGraph;
	private LinkedList<TripNode> listOfReqTrips;
	
	int nodesExpanded;
	
}
