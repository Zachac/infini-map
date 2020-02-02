package org.ex.infinite.map.exit;

import org.ex.infinite.map.Position;
import org.ex.infinite.map.location.Location;
import org.ex.infinite.map.location.PositionalLocation;

public class PositionalExit extends AbstractExit {

	private final String name;
	private final Position position;

	public PositionalExit(String name, int x, int y) {
		this(name, new Position(x, y));
	}
	
	public PositionalExit(String name, Position position) {
		this.name = name;
		this.position = position;
	}

	public Position getPosition() {
		return position;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean isVisible() {
		return true;
	}

	@Override
	public Location getLocation() {
		return PositionalLocation.getLocation(position);
	}
}
