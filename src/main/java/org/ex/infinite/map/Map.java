package org.ex.infinite.map;

import java.util.Arrays;

import org.ex.external.fast.FastNoise;
import org.ex.infinite.randomizer.Seed;

public class Map {
	private static final FastNoise NOISE = new FastNoise((int) Seed.value);
	private static final int MAP_RADIUS = 3;
	private static final int MAP_LENGTH = MAP_RADIUS * 2 + 1;
	private static final int MAP_HEIGHT = (MAP_LENGTH + 2); 
	private static final int MAP_WIDTH = (MAP_LENGTH * 2 + 2);
	private static final float MAP_SCALE = 10f;
	private static final char[] MAP_LINE = new char[MAP_WIDTH]; static {
		Arrays.fill(MAP_LINE, '=');
		MAP_LINE[MAP_LINE.length - 1] = '\n';
	}
	
	public static String getMap(Position p) {
		Biome[][] values = new Biome[MAP_LENGTH][MAP_LENGTH];
		for (int i = 0; i < MAP_LENGTH; i++) {
			for (int j = 0; j < MAP_LENGTH; j++) {
				values[i][j] = getBiome(i - MAP_RADIUS + p.x , p.y - MAP_RADIUS + j);
			}
		}
		
		return drawMap(values);
	}

	private static String drawMap(Biome[][] values) {
		StringBuilder result = new StringBuilder(MAP_HEIGHT * MAP_WIDTH);
		result.append(MAP_LINE);
		
		for (Biome[] arr : values) {
			result.append('|');
			
			for (Biome b : arr) {
				
				if (b != null) {
					result.append(b.icon);
				} else {
					result.append(' ');
				}
				
				result.append(' ');
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
	
	public static Biome getBiome(float x, float y) {
		
		x *= MAP_SCALE;
		y *= MAP_SCALE;
		
		float value = NOISE.GetPerlin(x, y);

		if (value > 0.1) {
			return Biome.Plains;
		} else if (value > 0) {
				return Biome.Beach;
		} else {
			return Biome.Ocean;
		}
	}
}
