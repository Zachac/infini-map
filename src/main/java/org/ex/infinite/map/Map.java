package org.ex.infinite.map;

import org.ex.infinite.utility.CompositePerlinGenerator;
import org.springframework.stereotype.Component;

@Component
public class Map {

	private CompositePerlinGenerator noise = new CompositePerlinGenerator(3, 0.1f);
	
	public String getArea(int x, int y, int radius, double zoom) {
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
		
		var value = noise.getNoise(x, y);
		
		if (value < 0) {
			return ' ';
		} else if (value <= 0.1f) {
			return '~';
		} else if (value < 1f) {
			return '#';
		} else {
			return '/';
		}
	}
}
