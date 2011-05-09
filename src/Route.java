import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Route {
	
	public final Depot start;
	public final Depot end;
	public final double price;
	public final int time;
	private List<String> stops = new ArrayList<String>();
	
	/**
	 * Creates an instance of a route.
	 * @param start starting Depot
	 * @param end ending Depot
	 * @param price cost of travel
	 * @param time time of travel
	 * @see Depot
	 */
	public Route(Depot start, Depot end, double price, int time) {
		this.start = start;
		this.end = end;
		this.price = price;
		this.time = time;
	}
	
	/**
	 * Add a stop to the end of the stop list
	 * @param stop name of the stop to add
	 */
	public void addStop(String stop) {
		stops.add(stop);
	}
	
	/**
	 * Get a list of stops
	 */
	public List<String> getStops() {
		return stops;
	}
	
	/**
	 * Parse a string of stops into the stop list.
	 * Clears the stop list first.
	 * @param stopString string of stops separated by \n
	 */
	public void saveStops(String stopString) {
		stops.clear();
		for (String stop : stopString.split("\n"))
			addStop(stop);
	}
	
	/**
	 * Get the formatted price.
	 * If it is the last day of the month there is a 10% discount
	 * @param date {@link Date} date of which to get the price
	 * @return returns a price-formatted string
	 */
	public String getPrice(Date date) {
		NumberFormat money = NumberFormat.getCurrencyInstance(Locale.UK);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		if (cal.get(Calendar.DATE) == cal.getActualMaximum(Calendar.DATE))
			return money.format(price*0.9);
		else
			return money.format(price);
	}
	
	/**
	 * Get the journey time formatted in hours and minutes
	 * @return returns the formatted time in a String
	 */
	public String getFormattedTime() {
		int hours = time / 60;
		int minutes = time % 60;
		if (hours == 1)
			return "1 hour " + minutes + " minutes";
		else
			return hours + " hours " + minutes + " minutes";
	}

}
