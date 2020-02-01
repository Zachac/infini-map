package org.ex.infinite.map.exit;

import org.ex.infinite.map.location.PositionalLocation;

public class PositionalExit extends AbstractExit {

	
	private final String name;
	private final int x;
	private final int y;

	public PositionalExit(String name, int x, int y) {
		this.name = name;
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	@Override
	public PositionalLocation getLocation() {
		return new PositionalLocation(x, y);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean isVisible() {
		return true;
	}
}
