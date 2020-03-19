package org.ex.infinite.map;

import org.ex.external.fast.generators.AbstractNoiseGenerator.Float2;
import org.ex.external.fast.generators.CellularNoiseGenerator;
import org.springframework.stereotype.Component;

@Component
public class Map {

	private CellularNoiseGenerator noise = new CellularNoiseGenerator(); {
		noise.SetFrequency(0.25f);
	}
	
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
		float value = noise.GetCellular(x, y) + 1;
		return (char) (' ' + (int) (3 * value));
	}
	
	public Float2 getCellCoordinates(int x, int y) {
		return noise.getCellCoordinates(x, y);
	}
}
