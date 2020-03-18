package org.ex.infinite.map;

import org.ex.external.fast.generators.AbstractNoiseGenerator.Float2;
import org.ex.external.fast.generators.CellularNoiseGenerator;
import org.springframework.stereotype.Component;

@Component
public class Map {

	private CellularNoiseGenerator noise = new CellularNoiseGenerator(); {
		noise.SetFrequency(0.25f);
	}
	
	public String getArea(int x, int y, int radius) {
		StringBuilder result = new StringBuilder();

		for (int i = x - radius; i <= x + radius; i++) {
			for (int j = y - radius; j <= y + radius; j++) {
				float value = noise.GetCellular(i, j) + 1;
				result.append((char) (' ' + (int) (3 * value)));
				result.append(' ');
			}
			result.setCharAt(result.length() - 1, '\n');
		}
		System.out.println(result.toString());
		
		return result.toString();
	}
	
	public Float2 getCellCoordinates(int x, int y) {
		return noise.getCellCoordinates(x, y);
	}
}
