/**
 * City is a class that represents a city. It consists of the city's name and its transfer time.
 * @author clintonhadinata
 *
 */
public class City {

	//Constructor
	/**
	 * Constructs a City with name name and transfer time transferTime.
	 * @param name			the name of the City
	 * @param transferTime	the transfer time of the City
	 */
	public City(String name, int transferTime) {
		this.name = name;
		this.transferTime = transferTime;
	}
	
	//Methods
	
	/**
	 * Returns whether or not another city is equivalent to this city.
	 * @param 	c	The City that is being compared to this one.
	 * @return		true if City c is equivalent, false otherwise.
	 */
	public boolean equals(City c) {
		if (this.name.equals(c.name) && this.transferTime == c.transferTime) {
			return true;
		} else {
			return false;
		}
	}
	
	//Getters and Setters
	/**
	 * Returns the name of this City
	 * @return	the name of this City
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the name of this City
	 * @param name	the name this City will be named
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Returns the transfer time of this City
	 * @return	the transfer time of this City
	 */
	public int getTransferTime() {
		return transferTime;
	}
	
	/**
	 * Sets the transfer time of this City
	 * @param transferTime	the time that this City's travel time will be set to
	 */
	public void setTransferTime(int transferTime) {
		this.transferTime = transferTime;
	}
	
	//Variables
	private String name;
	private int transferTime;
}
