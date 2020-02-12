package org.ex.infinite.map.location;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.ex.infinite.features.generators.BiomeFeatureGenerator;
import org.ex.infinite.map.Direction;
import org.ex.infinite.map.Map;
import org.ex.infinite.map.Position;
import org.ex.infinite.map.exit.Exit;
import org.ex.infinite.map.exit.PositionalExit;

public final class PositionalLocation extends AbstractLocation {

	private static ConcurrentHashMap<Position, PositionalLocation> cache = new ConcurrentHashMap<>();
	
	private final List<Exit> unnavigableExits;

	private final Position position;
	
	public static PositionalLocation getLocation(int x, int y) {
		return getLocation(new Position(x, y));
	}
	
	public static PositionalLocation getLocation(Position position) {
		return cache.computeIfAbsent(position, (p) -> new PositionalLocation(p));
	}

	private PositionalLocation(Position p) {
		this.position = p;
		this.unnavigableExits = new ArrayList<>();
		
		setBiome(Map.getBiome(p.x, p.y));
		setViewThreshold(getBiome().viewThreshold);
		
		for (Direction d : Direction.values()) {
			PositionalExit e = new PositionalExit(d.name(), p.x + d.x, p.y + d.y);
			
			if (Map.getBiome(e.getPosition()).navigable) {
				exits.add(e);
			} else {
				unnavigableExits.add(e);
			}
		}
		
		features.addAll(BiomeFeatureGenerator.getFeatures(getBiome(), p.x, p.y));
	}

	public Position getPosition() {
		return position;
	}

	public Collection<Exit> getUnnavigableExits() {
		return Collections.unmodifiableCollection(unnavigableExits);
	}
}
