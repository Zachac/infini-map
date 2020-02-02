package org.ex.infinite.map;

public enum Biome {
	
	Plains('x', 2, true), 
	Ocean('-', 5, false), 
	Beach('~', 2, true);

	public final char icon;
	public final int viewThreshold;
	public final boolean navigable;

	private Biome(char icon, int viewThreshold, boolean navigable) {
		this.icon = icon;
		this.viewThreshold = viewThreshold;
		this.navigable = navigable;
	}
}
