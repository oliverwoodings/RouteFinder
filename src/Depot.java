

public enum Depot {
	
	LINCOLN(1, "Lincoln"),
	LEICESTER(2, "Leicester"),
	LOUGHBOROUGH(3, "Loughborough"),
	NOTTINGHAM(4, "Nottingham"),
	DERBY(5, "Derby");
	
	private int id;
	private String name;
	
	/**
	 * Represents a Depot
	 * @param id depot id
	 * @param name name of the depot
	 */
	Depot(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	/**
	 * Get the id of the depot
	 * @return returns an int
	 */
	public int getId() {
		return this.id;
	}
	
	/**
	 * Get the name of the depot
	 * @return returns a String
	 */
	public String getName() {
		return this.name;
	}
	
}