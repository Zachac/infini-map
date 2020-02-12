package org.ex.infinite.features.smells.animals;

import org.ex.infinite.features.smells.Smell;

public class Jellyfish implements Smell, Animal {

	@Override
	public String getName() {
		return "jellyfish";
	}

	@Override
	public String getDescription() {
		return "A jelly fish lies on the ground.";
	}

}
