package org.ex.infinite.features.generators;

import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

import org.ex.infinite.features.Feature;
import org.ex.infinite.map.Biome;
import org.ex.infinite.randomizer.Seed;

public class BiomeFeatureGenerator {

	private static Map<Biome, FeatureGenerator> features = new EnumMap<>(Biome.class);
	
	static {
		features.put(Biome.Plains, new PlainsFeatureGenerator());
		features.put(Biome.Beach, new BeachFeatureGenerator());
		features.put(Biome.Forest, new ForestFeatureGenerator());
	}
	
	public static Collection<Feature> getFeatures(Biome biome, int x, int y) {
		var seed = Seed.hash(x, y);
		var generator = features.get(biome);
		
		if (generator == null) {
			return Collections.emptySet();
		} else {
			return generator.generateFeatures(seed);
		}
	}
}
