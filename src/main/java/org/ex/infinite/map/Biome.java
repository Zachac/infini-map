package org.ex.infinite.map;

public enum Biome {
	
	Plains('x', 2, true),
	Forest('#', 2, true), 
	Ocean('`', 5, false), 
	Beach('~', 2, true),
	Mountains('/', '\\', 5, false),
	Misc('!', 2, true);

	public final char icon;
	public final char leftIcon;
	public final int viewThreshold;
	public final boolean navigable;

	private Biome(char icon, char leftIcon, int viewThreshold, boolean navigable) {
		this.icon = icon;
		this.leftIcon = leftIcon;
		this.viewThreshold = viewThreshold;
		this.navigable = navigable;
	}
	
	private Biome(char icon, int viewThreshold, boolean navigable) {
		this(icon, ' ', viewThreshold, navigable);
	}
}
