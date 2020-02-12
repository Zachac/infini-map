package org.ex.infinite.features.generators;

import java.util.Collection;

import org.ex.infinite.features.Feature;

public interface FeatureGenerator {

	Collection<Feature> generateFeatures(long seed);
	
}
