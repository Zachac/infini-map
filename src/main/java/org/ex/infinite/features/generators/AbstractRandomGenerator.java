package org.ex.infinite.features.generators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.function.Supplier;

import org.ex.infinite.features.Feature;
import org.ex.infinite.utils.Pair;

public abstract class AbstractRandomGenerator implements FeatureGenerator {

	
	private List<Pair<Integer, Supplier<Feature>>> featureWeights = new ArrayList<>();
	private int totalWeight = 0;
	
	protected void add(Pair<Integer, Supplier<Feature>> weightedFeature) {
		Objects.requireNonNull(weightedFeature.v2);
		
		if (weightedFeature.v1 < 0) {
			throw new IllegalArgumentException("Cannot compute negative probability.");
		}
		
		totalWeight += weightedFeature.v1;
		featureWeights.add(weightedFeature);
	}
	
	@Override
	public Collection<Feature> generateFeatures(long seed) {
		var selection = new Random(seed).nextInt(totalWeight);
		
		for (Pair<Integer, Supplier<Feature>> weightedFeature : this.featureWeights) {
			selection -= weightedFeature.v1;
			
			if (selection <= 0) {
				return Arrays.asList(weightedFeature.v2.get());
			}
		}
		
		throw new IllegalStateException();
	}
}
