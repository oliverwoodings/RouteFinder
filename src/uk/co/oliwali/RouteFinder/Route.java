package uk.co.oliwali.RouteFinder;

import java.util.ArrayList;
import java.util.List;

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

}
