package org.ex.infinite.services.map;

import org.ex.infinite.services.map.biomes.BiomeGenerator;
import org.ex.infinite.services.map.biomes.Plains;
import org.springframework.stereotype.Component;

@Component
public class Map {
	
	private BiomeGenerator[] biomeRings = new BiomeGenerator[] {
			new Plains(),
			new Plains(),
			new Plains(),
			new Plains()
	};
	
	public String getArea(int x, int y, int radius, double zoom) {
		if (radius > 100) {
			throw new IllegalArgumentException("Radius cannot be greater than 100");
		}
		
		StringBuilder result = new StringBuilder();

		for (int i = -radius; i <= radius; i++) {
			for (int j = -radius; j <= radius; j++) {
				result.append(getBiome((int) (x + i * zoom), (int) (y + j * zoom)));
				result.append(' ');
			}
			result.setCharAt(result.length() - 1, '\n');
		}
		
		int center = ((radius * 2 + 1) * 2) * radius + radius * 2;
		result.setCharAt(center - 1, '>');
		result.setCharAt(center + 1, '<');
		
		return result.toString();
	}
	
	public char getBiome(int x, int y) {
		
		
		double distance = Math.sqrt(y * y + x * x);
		double ringFactor = Math.sqrt(distance); // create larger and larger concentric rings
		int ring = (int) (ringFactor / (Math.PI * 2) + 0.25); // calculate which ring we are on
		float value = (float) Math.sin(ringFactor);
		value = value + 0.5f; // prefer land over ocean
		
		if (ring >= biomeRings.length) {
			// if there are no further rings, return mountains
			return Tile.MOUNTAINS.c;
		}
		
		if (value < 0) {
			// replace the bottom of the sin wave with ocean
			return Tile.WATER.c;
		}
		
		return biomeRings[ring].getTile(value, x, y).c;
	}
}
