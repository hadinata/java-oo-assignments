import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Scanner;

/**
 * VenueHireSystem is a class that implements the booking system.
 * It stores a list of Venue objects and a list of Reservation objects.
 * @author clintonhadinata
 *
 */
public class VenueHireSystem {
	
	public static void main(String[] args) {
		
		VenueHireSystem vhSystem = new VenueHireSystem();
		vhSystem.readInput(args[0]);	
		
	}
	
	//Constructor
	/**
	 * Constructs a VenueHireSystem with an empty list of venues and reservations.
	 */
	public VenueHireSystem() {
		this.listOfVenues = new ArrayList<Venue>();
		this.listOfReservations = new ArrayList<Reservation>();
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
			sc = new Scanner(new FileReader(inputFileName));    //# args[0] is the first command line argument
			
			while (sc.hasNextLine()) {
				
				String x = sc.nextLine();
				String[] array = x.split(" ");
				String type = array[0];
				
				if(type.equals("Venue")) {
					String venueName = array[1];
					String roomName = array[2];
					String roomSize = array[3];
					
					boolean roomAlreadyAdded = false;
					for(int i = 0; i < this.getNumOfVenues() && roomAlreadyAdded == false; i++) {
						Venue v = this.listOfVenues.get(i);
						if (v.getName().equals(venueName)) {
							v.addNewRoom(roomName, roomSize);
							roomAlreadyAdded = true;
						} 
					}
					if (roomAlreadyAdded == false) {
						Venue newVenue = new Venue(venueName);
						newVenue.addNewRoom(roomName, roomSize);
						this.addVenue(newVenue);
					}
				}
				
				if(type.equals("Request")) {
					this.makeRequest(array, false);
				}
				
				if (type.equals("Change")) {
					this.makeRequest(array, true);
				}
				
				if (type.equals("Cancel")) {
					int reqID = Integer.parseInt(array[1]);
					deleteReservation(reqID);
					System.out.println("Cancel " + reqID);
				}
				
				if (type.equals("Print")) {
					String venueName = array[1];
					for (int i = 0; i < this.getNumOfVenues(); i++) {
						if (this.getVenue(i).getName().equals(venueName)) {
							this.getVenue(i).printVenue();;
						}
					}
				}
				
			}	
		}
		catch (FileNotFoundException e) {}
		finally
		{
			if (sc != null) sc.close();
		}
	     
	}
	
	/**
	 * Executes the "Request" or "Change" command given the input in array, and prints output accordingly.
	 * @param array		the array of input arguments for a given line of input
	 * @param isChange	whether or not the request is for a "Change" (as opposed to a reservation "Request")
	 */
	private void makeRequest(String[] array, boolean isChange) {
		int large = 0;
		int medium = 0;
		int small = 0;
		
		int reqID = Integer.parseInt(array[1]);
		
		String startMonth = array[2]; 
		int startMonthInt = convertMonthToInt(startMonth);
		int startDateOfMonth = Integer.parseInt(array[3]);
		String endMonth = array[4];
		int endMonthInt = convertMonthToInt(endMonth);
		int endDateOfMonth = Integer.parseInt(array[5]);
		
		GregorianCalendar startDate = new GregorianCalendar(2016, startMonthInt, startDateOfMonth);
		GregorianCalendar endDate = new GregorianCalendar(2016, endMonthInt, endDateOfMonth);

		for (int j = 6; j < array.length ; j++) {
			int numRoomsWanted = Integer.parseInt(array[j]);
			j++;
			String size = array[j]; 
			if (size.equals("small")) small = numRoomsWanted;
			if (size.equals("medium")) medium = numRoomsWanted;
			if (size.equals("large")) large = numRoomsWanted;
		}
		
		Venue foundVenue = searchAvailability(reqID, large, medium, small, startDate, endDate);
		if (foundVenue != null) {
			
			String tag = "Reservation ";			
			if (isChange) {
				tag = "Change ";
				deleteReservation(reqID);
			}
			
			Reservation newReservation = reserve(reqID, large, medium, small, foundVenue, startDate, endDate);
			this.listOfReservations.add(newReservation);
			String output = tag + newReservation.getID() + " " + newReservation.getRoom(0).getVenue().getName();
			for (int i = 0; i < newReservation.getNumOfRooms(); i++) {
				output += " " + newReservation.getRoom(i).getName();
			}
			
			System.out.println(output);

		} else {
			if (isChange) { 
				System.out.println("Change rejected"); 
			} else { 
				System.out.println("Request rejected"); 
			}
		}
	}
	
	/**
	 * Searches whether or not a venue is available to book for the given room sizes and booking dates 
	 * and returns the Venue object that is available.
	 * @param id		the request id
	 * @param large		the number of large rooms requested
	 * @param medium	the number of medium rooms requested
	 * @param small		the number of small rooms requested
	 * @param startDate	the date of the first day of the requested booking
	 * @param endDate	the date of the last day of the requested booking
	 * @return			the venue that is available to be booked; null if no venues are available
	 */
	private Venue searchAvailability(int id, int large, int medium, int small, GregorianCalendar startDate, GregorianCalendar endDate) {
		
		for (int i = 0; i < this.listOfVenues.size(); i++) {
			
			Venue v = this.listOfVenues.get(i);
				
			boolean largeFound = true;
			boolean mediumFound = true;
			boolean smallFound = true;

			if (large > 0) {
				largeFound = false;
				largeFound = searchBySize("large", large, v, startDate, endDate, id);
			}

			if (medium > 0) {
				mediumFound = false;
				mediumFound = searchBySize("medium", medium, v, startDate, endDate, id);
			}

			if (small > 0) {
				smallFound = false;
				smallFound = searchBySize("small", small, v, startDate, endDate, id);
			}

			if (largeFound == true && mediumFound == true && smallFound == true) {
				return v;
			}
	
		}
		
		return null;
	}
	
	/**
	 * Checks whether or not rooms of a particular size is available in Venue v, given booking dates.
	 * @param roomSize		the size of the room requested
	 * @param numOfRooms	the number of rooms requested 
	 * @param v				the venue at which the request is being checked against
	 * @param startDate		the date of the first day of the requested booking
	 * @param endDate		the date of the last day of the requested booking
	 * @param id			the request id
	 * @return				true if enough rooms are available in this venue; false otherwise
	 */
	private boolean searchBySize(String roomSize, int numOfRooms, Venue v, GregorianCalendar startDate, GregorianCalendar endDate, int id) {
		int numAvailableRooms = 0;
		for (int j = 0; j < v.getNoOfRooms(); j++) {
			Room r = v.getRoom(j);
			if(r.getSize().equals(roomSize)) {
				if (!isBookedOnDate(r,startDate, endDate, id)) numAvailableRooms++;
			}
		}
		if (numAvailableRooms >= numOfRooms) return true;
		return false;
	}
	
	/**
	 * Makes a reservation at venue v, given the number of rooms of each size 
	 * and the booking dates, and returns the Reservation made.
	 * @param id		the reservation id
	 * @param large		the number of large rooms requested
	 * @param medium	the number of medium rooms requested
	 * @param small		the number of small rooms requested
	 * @param v			the venue where the reservation will be made
	 * @param startDate	the date of the first day of the requested booking
	 * @param endDate	the date of the last day of the requested booking
	 * @return			the newly made reservation 
	 */
	private Reservation reserve(int id, int large, int medium, int small, Venue v, GregorianCalendar startDate, GregorianCalendar endDate) {
		
		Reservation newReservation = new Reservation(id, startDate, endDate);
		
		int largeBooked = 0;
		int mediumBooked = 0;
		int smallBooked = 0;
		
		for (int j = 0; j < v.getNoOfRooms(); j++) {
			Room r = v.getRoom(j);
			boolean booked = isBookedOnDate(r, startDate, endDate, id);
			if (r.getSize().equals("large")) {
				if (large - largeBooked > 0) {
					if (!booked) {
						newReservation.addRoom(r);
						r.addInPosition(newReservation);
						largeBooked++;
					}
				}
			}
			if (r.getSize().equals("medium")) {
				if (medium - mediumBooked > 0) {
					if (!booked) {
						newReservation.addRoom(r);
						r.addInPosition(newReservation);
						mediumBooked++;
					}
				}
			}
			if (r.getSize().equals("small")) {
				if (small - smallBooked > 0) {
					if (!booked) {
						newReservation.addRoom(r);
						r.addInPosition(newReservation);
						smallBooked++;
					}
				}
			}
			
		}
		
		return newReservation;
		
	}
	
	/**
	 * Checks whether or not a given room is already booked given booking dates. 
	 * <p>
	 * Takes into account the reservation id that this method is being checked for. If a reservation with
	 * the same id already exists and its dates conflict with the request, this conflict will be ignored to 
	 * allow for changes to existing reservations to be made.
	 * @param r			the room to be checked
	 * @param startDate	the date of the first day of the requested booking
	 * @param endDate	the date of the last day of the requested booking
	 * @param id		the reservation id that this method is being checked for
	 * @return			true if already booked; false otherwise, unless id matches the conflicting reservation id.
	 */
	private boolean isBookedOnDate (Room r, GregorianCalendar startDate, GregorianCalendar endDate, int id) {
		
		for (int k = 0; k < r.getNoOfReservations(); k++) {
			Reservation res = r.getReservation(k);
			if (!(res.getStartDate().after(endDate) || res.getEndDate().before(startDate)) && res.getID() != id) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Adds Venue v to the list of venues inside this VenueHireSystem object.
	 * @param v 	the venue to be added
	 */
	private void addVenue(Venue v) {
		this.listOfVenues.add(v);
	}
	
	/**
	 * Deletes a Reservation given its id.
	 * @param id	the reservation id
	 */
	private void deleteReservation(int id) {
		for(int i = 0; i < this.getNumOfReservations(); i++) {
			if (this.getReservation(i).getID() == id) {
				Reservation toBeDeleted = this.getReservation(i);
				this.deleteReservation(toBeDeleted);
				break;
			}
		}
	}
	
	/**
	 * Deletes a Reservation object toBeDeleted.
	 * @param toBeDeleted the Reservation object to be deleted
	 */
	private void deleteReservation(Reservation toBeDeleted) {
		for (int i = 0; i < toBeDeleted.getNumOfRooms(); i++) {
			toBeDeleted.getRoom(i).removeReservation(toBeDeleted);
		}
		for (int i = 0; i < this.getNumOfReservations(); i++) {
			if (this.getReservation(i) == toBeDeleted) {
				this.listOfReservations.remove(i);
			}
		}
	}
	
	/**
	 * Returns the integer representation of a month that is used in GregorianCalendar, 
	 * given its three-character code.
	 * @param month	the month's three-character code
	 * @return		the integer representation of that month
	 */
	private int convertMonthToInt(String month) {
		if (month.equals("Jan")) return 0;
		if (month.equals("Feb")) return 1;
		if (month.equals("Mar")) return 2;
		if (month.equals("Apr")) return 3;
		if (month.equals("May")) return 4;
		if (month.equals("Jun")) return 5;
		if (month.equals("Jul")) return 6;
		if (month.equals("Aug")) return 7;
		if (month.equals("Sep")) return 8;
		if (month.equals("Oct")) return 9;
		if (month.equals("Nov")) return 10;
		if (month.equals("Dec")) return 11;
		return 99;
	}
	
	//Getters and Setters
	/**
	 * Returns the number of venues stored inside this VenueHireSystem object.
	 * @return	the number of venues
	 */
	private int getNumOfVenues() {
		return this.listOfVenues.size();
	}
	
	/**
	 * Returns the Venue object stored at position index in the list of venues 
	 * inside this VenueHireSystem object.
	 * @param index	the location index for the list of venues
	 * @return		the Venue object stored at position index
	 */
	private Venue getVenue(int index) {
		return this.listOfVenues.get(index);
	}
	
	/**
	 * Returns the number of reservations stored inside this VenueHireSystem object.
	 * @return	the number of reservations
	 */
	private int getNumOfReservations() {
		return this.listOfReservations.size();
	}
	
	/**
	 * Returns the Reservation object stored at position index inside the list of 
	 * reservations in this VenueHireSystem object.
	 * @param index	the location index for the list of reservations
	 * @return		the Reservation object stored at position index
	 */
	private Reservation getReservation(int index) {
		return this.listOfReservations.get(index);
	}
	
	//Variables
	private ArrayList<Venue> listOfVenues;
	private ArrayList<Reservation> listOfReservations;
}
