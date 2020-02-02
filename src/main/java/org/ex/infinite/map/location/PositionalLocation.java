package org.ex.infinite.map.location;

import java.util.HashMap;

import org.ex.infinite.features.BiomeFeatures;
import org.ex.infinite.map.Direction;
import org.ex.infinite.map.Map;
import org.ex.infinite.map.Position;
import org.ex.infinite.map.exit.PositionalExit;

public final class PositionalLocation extends AbstractLocation {

	private static HashMap<Position, PositionalLocation> cache = new HashMap<>();
	
	private final Position position;
	
	public static PositionalLocation getLocation(int x, int y) {
		return getLocation(new Position(x, y));
	}
	
	public static PositionalLocation getLocation(Position position) {
		return cache.computeIfAbsent(position, (p) -> new PositionalLocation(p));
	}

	private PositionalLocation(Position p) {
		this.position = p;
		
		setBiome(Map.getBiome(p.x, p.y));
		setViewThreshold(getBiome().viewThreshold);
		
		for (Direction d : Direction.values()) {
			if (Map.getBiome(p.x + d.x, p.y + d.y).navigable) {
				exits.add(new PositionalExit(d.name(), p.x + d.x, p.y + d.y));
			}
		}
		
		features.addAll(BiomeFeatures.getFeatures(getBiome(), p.x, p.y));
	}

	public Position getPosition() {
		return position;
	}
	
}
