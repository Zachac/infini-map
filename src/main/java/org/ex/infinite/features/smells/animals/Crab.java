package org.ex.infinite.features.smells.animals;

import org.ex.infinite.features.smells.Smell;

public class Crab implements Smell, Animal {

	@Override
	public String getName() {
		return "crab";
	}

	@Override
	public String getDescription() {
		return "A crab crawls along the ground.";
	}

}
