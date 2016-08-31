import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 * Room is a class that represents rooms in the booking system.
 * @author clintonhadinata
 *
 */
public class Room {
	//Constructor
	/**
	 * Constructs a Room of name name, size size, inside Venue venue.
	 * @param venue
	 * @param name
	 * @param size
	 */
	public Room(Venue venue, String name, String size) {
		this.venue = venue;
		this.name = name;
		this.size = size;
		this.reservations = new ArrayList<Reservation>();
	}
	
	//Methods
	
	/**
	 * Adds Reservation resToAdd in the correct chronological position in the list of 
	 * reservations inside this Room
	 * @param resToAdd	the reservation to be inserted
	 */
	public void addInPosition(Reservation resToAdd) {
		GregorianCalendar startDate = resToAdd.getStartDate();
		GregorianCalendar endDate = resToAdd.getEndDate();
		int position = this.getNoOfReservations();
		
		if(this.reservations.size() == 0) {
			this.reservations.add(resToAdd);
			return;
		}
		
		if(this.reservations.size() == 1) {
			Reservation curRes = this.getReservation(0);
			if (curRes.getStartDate().after(endDate)) {
				position = 0;
			} else {
				position = 1;
			} 
		}
		
		if(this.reservations.size() > 1) {
			Reservation curRes = this.getReservation(0);
			if (curRes.getStartDate().after(endDate)) {
				position = 0;
			} else {
				for (int k = 0; k < this.getNoOfReservations()-1; k++) {
					curRes = this.getReservation(k);
					Reservation nextRes = this.getReservation(k+1);
					if (curRes.getEndDate().before(startDate) && nextRes.getStartDate().after(endDate)) {
						position = k+1;
					}
				}
				int endIndex = this.getNoOfReservations()-1;
				Reservation endRes = this.getReservation(endIndex);
				if (endRes.getEndDate().before(startDate)) { 
					this.reservations.add(resToAdd);
					return;
				}
			}
		}
		
		this.addReservation(position, resToAdd);
	}
	
	/**
	 * Adds Reservation res into location index of the list of reservations inside this Room
	 * @param index	the location index
	 * @param res		the reservation to be inserted
	 */
	public void addReservation(int index, Reservation res) {
		this.reservations.add(index, res);
	}
	
	/**
	 * Returns the Reservation stored at location index in the list of 
	 * reservations inside this Room
	 * @param index	the location index
	 * @return		the reservation at location index
	 */
	public Reservation getReservation(int index) {
		return this.reservations.get(index);
	}
	
	/**
	 * Removes the Reservation with id number id from the list of reservations inside this Room
	 * @param id	the reservation id
	 */
	public void removeReservation(int id) {
		for (int i = 0; i < this.reservations.size(); i++) {
			if (this.reservations.get(i).getID() == id) {
				this.reservations.remove(i);
			}
		}
	}
	
	/**
	 * Removes the Reservation r from the list of reservations inside this Room
	 * @param r	the reservation to be removed
	 */
	public void removeReservation(Reservation r) {
		for (int i = 0; i < this.reservations.size(); i++) {
			if (this.reservations.get(i) == r) {
				this.reservations.remove(i);
			}
		}
	}
	
	
	//Getters and Setters
	/**
	 * Returns the Venue associated with this Room
	 * @return	the associated venue
	 */
	public Venue getVenue() {
		return this.venue;
	}
	
	/**
	 * Returns the name of this Room
	 * @return	the name of the room
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Returns the size of this Room
	 * @return	the size of the room
	 */
	public String getSize() {
		return this.size;
	}
	
	/**
	 * Returns the number of reservations stored in the list of reservations inside this room
	 * @return
	 */
	public int getNoOfReservations() {
		int num = this.reservations.size();
		return num;
	}
	
	//Variables
	private Venue venue;
	private String name;
	private String size;
	private ArrayList<Reservation> reservations;

}
