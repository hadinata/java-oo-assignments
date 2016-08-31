/**
 * TripNode is a class that represents Trips.
 * @author clintonhadinata
 *
 */
public class TripNode {

	//Constructor
	/**
	 * Constructs a TripNode representing a trip from City from to City to with travel time travelTime.
	 * @param from			the trip's origin City
	 * @param to			the trip's destination City
	 * @param travelTime	the trip's travel time
	 */
	public TripNode(City from, City to, int travelTime) {
		this.from = from;
		this.to = to;
		this.travelTime = travelTime;
	}
	
	//Methods
	/**
	 * Returns the total cost of a trip including both transfer times.
	 * @return	the total cost of a trip including both transfer times
	 */
	public int getCost() {
		return this.from.getTransferTime() + this.travelTime + this.to.getTransferTime();
	}
	
	//Getters and Setters:
	/**
	 * Returns the origin City of this TripNode
	 * @return	the origin City of this TripNode
	 */
	public City getFrom() {
		return from;
	}

	/**
	 * Sets the origin City of this TripNode to from
	 * @param from	the city that will be this TripNode's origin City
	 */
	public void setFrom(City from) {
		this.from = from;
	}

	/**
	 * Returns the destination city of this TripNode
	 * @return	the destination city of this TripNode
	 */
	public City getTo() {
		return to;
	}

	/**
	 * Sets the destination City of this TripNode to to
	 * @param to	the city that will be this TripNode's destination city
	 */
	public void setTo(City to) {
		this.to = to;
	}

	/**
	 * Returns this TripNode's travel time
	 * @return	the travel time of this TripNode
	 */
	public int getTravelTime() {
		return travelTime;
	}

	/**
	 * Sets this TripNode's travel time to travelTime
	 * @param travelTime	the value that will be this TripNode's travel time
	 */
	public void setTravelTime(int travelTime) {
		this.travelTime = travelTime;
	}
	
	
	//Variables
	private City from;
	private City to;
	private int travelTime;
	
}
