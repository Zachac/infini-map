package org.ex.infinite.map.location;

import org.ex.infinite.features.BiomeFeatures;
import org.ex.infinite.map.Direction;
import org.ex.infinite.map.Map;
import org.ex.infinite.map.exit.PositionalExit;

public final class PositionalLocation extends AbstractLocation {

	private final int x;
	private final int y;

	public PositionalLocation(int x, int y) {
		this.x = x;
		this.y = y;
		
		setBiome(Map.getBiome(x, y));
		setViewThreshold(getBiome().viewThreshold);
		
		for (Direction d : Direction.values()) {
			if (Map.getBiome(x + d.x, y + d.y).navigable) {
				exits.add(new PositionalExit(d.name(), x + d.x, y + d.y));
			}
		}
		
		features.addAll(BiomeFeatures.getFeatures(getBiome(), x, y));
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
