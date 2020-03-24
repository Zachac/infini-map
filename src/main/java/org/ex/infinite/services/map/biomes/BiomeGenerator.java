package org.ex.infinite.services.map.biomes;

import org.ex.infinite.services.map.Tile;

public interface BiomeGenerator {

	Tile getTile(float blendValue, int x, int y);
	
}
