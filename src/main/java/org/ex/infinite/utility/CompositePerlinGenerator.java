package org.ex.infinite.utility;

import org.ex.external.fast.generators.PerlinNoiseGenerator;

public class CompositePerlinGenerator {

	private PerlinNoiseGenerator[] generators;
	
	public CompositePerlinGenerator(int depth, float freq) {
		this.generators = new PerlinNoiseGenerator[depth];
		
		var seeds = new SeedGenerator(1337);
		for (int i = 0; i < depth; i++, freq /= 2) {
			var gen = new PerlinNoiseGenerator();
			gen.SetSeed(seeds.next());
			gen.SetFrequency(freq);
			generators[i] = gen;
		}
	}
	
	public float getNoise(float x, float y) {
		float value = 0;
		
		for (int i = 0; i < generators.length; i++) {
			value += generators[i].GetPerlin(x, y);
		}
		
		return value;
	}
}
