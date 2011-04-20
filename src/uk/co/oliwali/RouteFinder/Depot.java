package uk.co.oliwali.RouteFinder;

public enum Depot {
	
	LINCOLN(1, "Lincoln"),
	LEICESTER(2, "Leicester"),
	LOUGHBOROUGH(3, "Loughborough"),
	NOTTINGHAM(4, "Nottingham"),
	DERBY(5, "Derby");
	
	private int id;
	private String name;
	
	Depot(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public int getId() {
		return this.id;
	}
	public String getName() {
		return this.name;
	}
	
}