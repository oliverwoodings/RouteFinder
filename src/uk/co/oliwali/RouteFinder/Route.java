package uk.co.oliwali.RouteFinder;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import uk.co.oliwali.RouteFinder.Depot;
import uk.co.oliwali.RouteFinder.RouteManager;

public class Route {
	
	public final Depot start;
	public final Depot end;
	public final double price;
	public final int time;
	public RouteManager routeManager;
	private List<String> stops = new ArrayList<String>();
	
	public Route(RouteManager routeManager, int start, int end, double price, int time) {
		this.routeManager = routeManager;
		this.start = routeManager.getDepot(start);
		this.end = routeManager.getDepot(end);
		this.price = price;
		this.time = time;
	}
	
	public void addStop(String stop) {
		stops.add(stop);
	}
	
	public List<String> getStops() {
		return stops;
	}
	
	public void saveStops(String stopString) {
		stops.clear();
		for (String stop : stopString.split("\n"))
			addStop(stop);
	}
	
	public String getPrice(Date date) {
		NumberFormat money = NumberFormat.getCurrencyInstance(Locale.UK);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		if (cal.get(Calendar.DATE) == cal.getActualMaximum(Calendar.DATE))
			return money.format(price*0.9);
		else
			return money.format(price);
	}
	
	public String getFormattedTime() {
		int hours = time / 60;
		int minutes = time % 60;
		if (hours == 1)
			return "1 hour " + minutes + " minutes";
		else
			return hours + " hours " + minutes + " minutes";
	}

}
