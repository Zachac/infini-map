package org.ex.infinite.map.biomes;

import org.ex.infinite.utility.CompositePerlinGenerator;

public class Plains implements BiomeGenerator {

	private CompositePerlinGenerator noise = new CompositePerlinGenerator(0, 3, 0.1f);
	
	@Override
	public char getTile(float blendValue, int x, int y) {
		

		// integrate sin wave intensity with the value generated
		// this let's there be more subtle edges as the sin wave approaches zero
		float value = blendValue * noise.getNoise(x, y);
		
		if (value < 0) {
			return ' ';
		} else if (value <= 0.1f) {
			return '~';
		} else {
			return '#';
		}
	}

}
