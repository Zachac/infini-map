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
	
	public static String getMap(int x, int y) {
		x -= MAP_RADIUS + 1;
		y -= MAP_RADIUS;
		
		char[] map = new char[MAP_HEIGHT * MAP_WIDTH];

		drawLine(map, 0, '=');
		drawLine(map, MAP_LENGTH + 1, '=');

		for (int i = 1; i <= MAP_LENGTH; i++) {
			int row_offset = i * MAP_WIDTH;
			
			for (int j = 0; j < MAP_LENGTH; j++) {
				int position = row_offset + j * 2 + 1;
				map[position] = getBiome(i + x, j + y).icon;
				map[position + 1] = ' ';
			}

			map[row_offset] = '|';
			map[row_offset + MAP_WIDTH - 2] = '|';
			map[row_offset + MAP_WIDTH - 1] = '\n';
		}
		
		int playerPosition = ((MAP_RADIUS + 1) * MAP_WIDTH) + MAP_RADIUS * 2;
		map[playerPosition] = '>';
		map[playerPosition + 2] = '<';
		
		return new String(map);
	}

	private static void drawLine(char[] map, int row, char value) {
		int offset = row * MAP_WIDTH;
		Arrays.fill(map, offset , offset + MAP_WIDTH - 1, value); 
		map[offset + MAP_WIDTH - 1] = '\n';
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
