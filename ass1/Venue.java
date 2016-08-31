import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Venue is a class that represents venues in the booking system.
 * @author clintonhadinata
 *
 */
public class Venue {
	
	//Constructor
	/**
	 * Constructs a Venue of name name and with an empty list of rooms.
	 * @param name	the name of the venue to be constructed
	 */
	public Venue (String name) {
		this.name = name;
		this.rooms = new ArrayList<Room>();
	}
	
	//Methods
	
	/**
	 * Adds a new Room to the list of rooms in this Venue, given a name and size.
	 * @param name	the name of the new room
	 * @param size	the size of the new room
	 */
	public void addNewRoom(String name, String size){
		Room r = new Room(this, name, size);
		this.addRoom(r);
	}
	
	/**
	 * Adds a new Room r to the list of rooms in this Venue.
	 * @param r	the new room to be added
	 */
	public void addRoom(Room r) {
		this.rooms.add(r);
	}
	
	/**
	 * Prints what is expected of the "Print" command given this Venue.
	 */
	public void printVenue() {
		String output = this.getName();
		for (int i = 0; i < this.getNoOfRooms(); i++) {
			Room r = this.getRoom(i);
			output += " " + r.getName();
			for (int j = 0; j < r.getNoOfReservations(); j++) {
				GregorianCalendar startDate = r.getReservation(j).getStartDate();
				GregorianCalendar endDate = r.getReservation(j).getEndDate();
				output += " " + intToMonth(startDate.get(GregorianCalendar.MONTH));
				output += " " + startDate.get(GregorianCalendar.DATE);
				output += " " + daysBetween(startDate,endDate);
			}
			System.out.println(output);
			output = this.getName();
		}
	}
	
	/**
	 * Returns the number of days between two dates, inclusive.
	 * @param startDate	the start date
	 * @param endDate	the end date
	 * @return			the number of days between the two dates
	 */
	private int daysBetween(GregorianCalendar startDate, GregorianCalendar endDate) {
		return endDate.get(Calendar.DAY_OF_YEAR) - startDate.get(Calendar.DAY_OF_YEAR) +1;
	}
	
	/**
	 * Returns a String of the three-character code associated with a month, given 
	 * that month's integer representation.
	 * @param month	the month's integer representation
	 * @return		the three-character code associated with the month
	 */
	private String intToMonth(int month) {
		String s = "Undefined";
		if (month == 0) s = "Jan";
		if (month == 1) s = "Feb";
		if (month == 2) s = "Mar";
		if (month == 3) s = "Apr";
		if (month == 4) s = "May";
		if (month == 5) s = "Jun";
		if (month == 6) s = "Jul";
		if (month == 7) s = "Aug";
		if (month == 8) s = "Sep";
		if (month == 9) s = "Oct";
		if (month == 10) s = "Nov";
		if (month == 11) s = "Dec";
		return s;
	}
	
	
	//Getters and Setters
	/**
	 * Returns the number of rooms stored in the list of rooms in this Venue.
	 * @return	the number of rooms
	 */
	public int getNoOfRooms() {
		return this.rooms.size();
	}
	
	/**
	 * Returns the Room stored at location index inside the list of rooms in this Venue.
	 * @param index	the location index
	 * @return		the Room stored at index
	 */
	public Room getRoom(int index) {
		return this.rooms.get(index);
	}
	
	/**
	 * Returns the name of this Venue.
	 * @return	the name of this venue
	 */
	public String getName() {
		return this.name;
	}
	
	//Variables
	private String name;
	private ArrayList<Room> rooms;
}
