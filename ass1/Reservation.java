import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 * Reservation is a class that represents reservations (bookings) in the booking system.
 * @author clintonhadinata
 *
 */
public class Reservation {
	
	//Constructor
	/**
	 * Constructs a Reservation with an empty list of rooms, ID id, 
	 * and on dates between startDate and endDate inclusive. 
	 * @param id		the reservation ID
	 * @param startDate	the start date
	 * @param endDate	the end date
	 */
	public Reservation(int id, GregorianCalendar startDate, GregorianCalendar endDate) {
		this.id = id;
		this.startDate = startDate;
		this.endDate = endDate;
		this.rooms = new ArrayList<Room>();
		
	}
	
	//Methods
	
	/**
	 * Adds Room r into the list of rooms inside this Reservation
	 * @param r	the room to be added
	 */
	public void addRoom(Room r) {
		this.rooms.add(r);
	}
	
	/**
	 * Returns the Room located at location index of the list of rooms inside this Reservation.
	 * @param index	the location index
	 * @return		the room stored at location index
	 */
	public Room getRoom(int index) {
		return this.rooms.get(index);
	}
	
	//Getters & setters:
	/**
	 * Returns the number of rooms stored in the list of rooms inside this Reservation.
	 * @return	the number of rooms stored
	 */
	public int getNumOfRooms() {
		return this.rooms.size();
	}
	
	/**
	 * Returns the ID of this reservation.
	 * @return	the ID of this reservation
	 */
	public int getID() {
		return this.id;
	}
	
	/**
	 * Returns the start date of this reservation.
	 * @return	the start date of this reservation
	 */
	public GregorianCalendar getStartDate() {
		return this.startDate;
	}
	
	/**
	 * Returns the end date of this reservation.
	 * @return	the end date of this reservation.
	 */
	public GregorianCalendar getEndDate() {
		return this.endDate;
	}
	
	//Variables
	private int id;
	private GregorianCalendar startDate;
	private GregorianCalendar endDate;
	private ArrayList<Room> rooms;

}
