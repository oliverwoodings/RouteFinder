package uk.co.oliwali.RouteFinder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import uk.co.oliwali.RouteFinder.Depot;
import uk.co.oliwali.RouteFinder.Route;

public class RouteManager {
	
	private List<Route> routes = new ArrayList<Route>();
	
	/**
	 * Loads all the routes into a list
	 * @see Route
	 */
	public RouteManager() {
		loadRoute(1,2,2.50,35);
		loadRoute(1,3,3.50,65);
		loadRoute(1,4,1.70,55);
		loadRoute(1,5,2.20,90);
		loadRoute(2,1,2.50,40);
		loadRoute(2,3,1.50,20);
		loadRoute(2,4,1.00,25);
		loadRoute(2,5,2.00,30);
		loadRoute(3,1,3.50,65);
		loadRoute(3,2,1.50,20);
		loadRoute(3,4,0.50,10);
		loadRoute(3,5,1.20,25);
		loadRoute(4,1,3.00,48);
		loadRoute(4,2,1.25,23);
		loadRoute(4,3,0.75,23);
		loadRoute(4,5,1.20,45);
		loadRoute(5,1,3.50,65);
		loadRoute(5,2,1.50,25);
		loadRoute(5,3,1.50,25);
		loadRoute(5,4,1.20,45);
	}
	
	/**
	 * Loads a route into the route list
	 * @param start ID of starting depot
	 * @param end ID of ending depot
	 * @param price price of journey
	 * @param time length of journey in minutes
	 * @see Route
	 */
	private void loadRoute(int start, int end, double price, int time) {
		routes.add(new Route(getDepot(start), getDepot(end), price, time));
	}
	
	/**
	 * Returns the route matching the inputted from and to depots
	 * @param from string name of 'from' depot
	 * @param to string name of 'to' depot
	 * @return returns a {@link Route}
	 */
	public Route getRoute(String from, String to) {
		for (Route route : routes.toArray(new Route[0]))
			if (route.start == getDepot(from) && route.end == getDepot(to))
				return route;
		return null;
	}
	
	/**
	 * Returns an array of {@link Route}s
	 * @return returns a Route[] array
	 */
	public Route[] getRouteList() {
		return routes.toArray(new Route[0]);
	}
	
	/**
	 * Gets the depot with the inputed id
	 * @param id id of the depot you want to retrieve
	 * @return returns a {@link Depot}
	 */
	public Depot getDepot(int id) {
		for (Depot depot : Depot.values()) {
			if (depot.getId() == id)
				return depot;
		}
		return null;
	}
	
	/**
	 * Gets the depot with the inputed name
	 * @param string name of the depot you want to retrieve
	 * @return returns a {@link Depot}
	 */
	public Depot getDepot(String string) {
		for (Depot depot : Depot.values()) {
			if (depot.getName().equalsIgnoreCase(string))
				return depot;
		}
		return null;
		
	}
	
	/**
	 * Saves all routes to inputed {@link File}
	 * @param file File instance to save to
	 */
	public void saveRoutesToFile(File file) {
		if (file.exists()) {
			file.delete();
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		PrintWriter out = null;
		try {
			out = new PrintWriter(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		for (Route route : routes) {
			String output = route.start.getId() + "," + route.end.getId();
			for (String stop : route.getStops()) {
				output = output + "," + stop;
			}
			out.println(output);
		}
		out.close();
	}
	
	/**
	 * Load route information from a {@link File} instance
	 * @param file File to load information from
	 */
	public void loadRoutesFromFile(File file) {
		Scanner scanner = null;
		try {
			scanner = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while (scanner.hasNextLine()) {
			String[] args = scanner.nextLine().split(",");
			for (Route route : routes)
				if (route.start.getId() == Integer.parseInt(args[0]) && route.end.getId() == Integer.parseInt(args[1]))
					for (int i = 2; i < args.length; i++)
						route.addStop(args[i]);
		}
		scanner.close();
	}

}
