package org.ex.infinite.map;

import java.util.Arrays;

import org.ex.external.fast.FastNoise;
import org.ex.infinite.map.location.Location;
import org.ex.infinite.map.location.PositionalLocation;
import org.ex.infinite.randomizer.Seed;

public class Map {
	private static final FastNoise NOISE = new FastNoise((int) Seed.value, 0.1f);
	private static final FastNoise NOISE2 = new FastNoise((int) Seed.value + 1, 0.05f);
	private static final FastNoise MOISTURE = new FastNoise((int) Seed.value + 2, 0.05f);
	private static final FastNoise ELEVATION = new FastNoise((int) Seed.value + 3, 0.05f);
	
	
	
	private static final int MAP_RADIUS = 3;
	private static final int MAP_LENGTH = MAP_RADIUS * 2 + 1;
	private static final int MAP_HEIGHT = (MAP_LENGTH + 2); 
	private static final int MAP_WIDTH = (MAP_LENGTH * 2 + 2);

	private static final char[] MAP_LINE = new char[MAP_WIDTH]; static {
		Arrays.fill(MAP_LINE, '=');
		MAP_LINE[MAP_LINE.length - 1] = '\n';
	}
	
	public static String getMap(BreadthFirstSight sight) {
		if (! (sight.getLocation() instanceof PositionalLocation)) {
			return null;
		}
		
		Biome[][] values = new Biome[MAP_LENGTH][MAP_LENGTH];
		Position center = ((PositionalLocation) sight.getLocation()).getPosition();
		
		for (Location l : sight.getVisible()) {
			if (l instanceof PositionalLocation) {
				PositionalLocation currentLocation = (PositionalLocation) l;
				Position currentPosition = currentLocation.getPosition();
				
				int x = currentPosition.x - center.x + MAP_RADIUS;
				int y = currentPosition.y - center.y + MAP_RADIUS;
				
				if (x >= 0 && y >= 0 && x < MAP_LENGTH && y < MAP_LENGTH) {
					values[x][y] = l.getBiome();
				}
			}
		}
		
		return drawMap(values);
	}

	public static String drawMap(Biome[][] values) {
		StringBuilder result = new StringBuilder(MAP_HEIGHT * MAP_WIDTH);
		result.append(MAP_LINE);

		for (Biome[] arr : values) {
			result.append('|');
			
			for (Biome b : arr) {
				
				if (b != null) {
					result.append(b.icon);
					result.append(b.leftIcon);
				} else {
					result.append(' ');
					result.append(' ');
				}
				
			}
			
			result.setLength(result.length() - 1);
			result.append('|');
			result.append('\n');
		}
		
		result.append(MAP_LINE);

		int playerPosition = ((MAP_RADIUS + 1) * MAP_WIDTH) + MAP_RADIUS * 2;
		result.setCharAt(playerPosition, '>');
		result.setCharAt(playerPosition + 2, '<');
		
		return result.toString();
	}
	
	public static Biome getBiome(int x, int y) {
//		Random r = new Random(Seed.hash(x, y));
		
		float value = NOISE.GetPerlinFractal(x, y)
				+ NOISE2.GetPerlin(x, y);
		
		float moisture = MOISTURE.GetPerlin(x, y);
		float elevation = (float) Math.pow(ELEVATION.GetPerlin(x, y), 1);
		
		if (value > 0.1) {
			
			if (elevation > 0.3) {
				return Biome.Mountains;
			}
			
			if (moisture >= 0) {
				return Biome.Forest;
			} else {
				return Biome.Plains;
			}
			
		} else if (value >= 0) {
			return Biome.Beach;
		} else {
			return Biome.Ocean;
		}
	}

	public static Biome getBiome(Position position) {
		return getBiome(position.x, position.y);
	}
}
