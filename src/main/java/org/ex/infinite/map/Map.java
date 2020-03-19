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
				float value = noise.GetCellular((float) (x + i * zoom), (float) (y + j * zoom)) + 1;
				result.append((char) (' ' + (int) (3 * value)));
				result.append(' ');
			}
			result.setCharAt(result.length() - 1, '\n');
		}
		
		int center = ((radius * 2 + 1) * 2) * radius + radius * 2;
		result.setCharAt(center - 1, '>');
		result.setCharAt(center + 1, '<');
		System.out.println(result.toString());
		
		return result.toString();
	}
	
	public Float2 getCellCoordinates(int x, int y) {
		return noise.getCellCoordinates(x, y);
	}
}
