package uk.co.oliwali.RouteFinder;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import uk.co.oliwali.RouteFinder.Depot;
import uk.co.oliwali.RouteFinder.Route;

public class RouteManager {
	
	private List<Route> routes = new ArrayList<Route>();
	
	public RouteManager() {
		loadRoutes();
	}
	
	private void loadRoutes() {
		
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
	
	private void loadRoute(int start, int end, double price, int time) {
		routes.add(new Route(this, start, end, price, time));
	}
	
	public Route getRoute(String from, String to) {
		System.out.println(from + to);
		Depot fromDepot = getDepot(from);
		Depot toDepot = getDepot(to);
		for (Object obj : routes.toArray()) {
			Route route = (Route) obj;
			if (route.start == fromDepot && route.end == toDepot)
				return route;
		}
		return null;
	}
	
	public List<Route> getRouteList() {
		return routes;
	}
	
	public Depot getDepot(int id) {
		for (Depot depot : Depot.values()) {
			if (depot.getId() == id)
				return depot;
		}
		return null;
	}
	
	public Depot getDepot(String string) {
		for (Depot depot : Depot.values()) {
			if (depot.getName().equalsIgnoreCase(string))
				return depot;
		}
		return null;
		
	}
	
	public void saveRoutesToFile(File file) throws IOException {
		if (file.exists()) {
			file.delete();
			file.createNewFile();
		}
		PrintWriter out = new PrintWriter(file);
		for (Route route : routes) {
			String output = route.start.getId() + "," + route.end.getId();
			for (String stop : route.getStops()) {
				output = output + "," + stop;
			}
			out.println(output);
		}
		out.close();
	}
	
	public void loadRoutesFromFile(File file) throws IOException {
		Scanner scanner = new Scanner(file);
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
