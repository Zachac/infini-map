package org.ex.infinite.services.map.biomes;

import org.ex.external.fast.generators.WhiteNoiseGenerator;
import org.ex.infinite.utility.CompositePerlinGenerator;

public class Plains implements BiomeGenerator {

	private CompositePerlinGenerator noise = new CompositePerlinGenerator(0, 3, 0.1f);
	private WhiteNoiseGenerator randomNoise = new WhiteNoiseGenerator();
	
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
			float r = randomNoise.GetWhiteNoise(x, y);
			if (r > 0.5) {
				return 'Y';
			} else if (r > 0.3) {
				return '&';
			} else {
				return '"';
			}
		}
	}
}
