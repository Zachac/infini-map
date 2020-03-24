package org.ex.infinite.services.map;

public enum Tile {

	WATER(' ', "Water", null),
	BEACH('~', "Beach", "A soft sandy beach."),
	MOUNTAINS('/', "Mountains", "An impassible mountain. Probably best to go around."),
	
	GRASSY_PLAINS('\'', "Grassy Plains", "A flat grassy plateau."),
	FOREST_OUTCROPPING('Y', "Tree Outcropping", "A few rare trees sprout from the ground here."),
	ORE_OUTCROPPING('&', "Ore Outcropping", "Ore can be seen here, breaching the surface of the land.");
	
	public final char c;
	public final String name;
	public final String description;
	
	private Tile(char displayChar, String name, String description) {
		this.c = displayChar;
		this.name = name;
		this.description = description;
	}
	
}
