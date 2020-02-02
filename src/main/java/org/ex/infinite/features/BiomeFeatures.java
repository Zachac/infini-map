package org.ex.infinite.features;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.ex.infinite.features.smells.SimpleSmell;
import org.ex.infinite.map.Biome;
import org.ex.infinite.randomizer.Randomizer;
import org.ex.infinite.randomizer.Seed;

public class BiomeFeatures {

	// seeds for the random number generator
	private static final long a, b, c; static {
		Random R = new Random(Seed.value);
		a = R.nextLong();
		b = R.nextLong();
		c = R.nextLong();
	}
	
	
	private static final int BIOME_FEATURES = 1;
	private static Map<Biome, List<Feature>> features = new EnumMap<Biome, List<Feature>>(Biome.class);
	
	static {
		for (Biome b : Biome.values()) {
			features.put(b, new ArrayList<Feature>());
		}

		features.get(Biome.Plains).add(new SimpleSmell("Scattered yellow flowers sprout out the grass."));
		features.get(Biome.Plains).add(new SimpleSmell("The grass plains gently slope up into a hill here."));
	}
	
	public static Collection<Feature> getFeatures(Biome biome, int x, int y) {
		// hashing from https://lemire.me/blog/2018/08/15/fast-strongly-universal-64-bit-hashing-everywhere/
		long seed = ((a * x + b * y + c) >>> 32);
		
		return Randomizer.getSamples(BIOME_FEATURES, features.get(biome), seed);
	}
}
