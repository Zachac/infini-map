package org.ex.infinite.features.smells.animals;

import org.ex.infinite.features.smells.Smell;

public class Rabbit implements Animal, Smell {

	@Override
	public String getName() {
		return "rabbit";
	}

	@Override
	public String getDescription() {
		return "A rabbit hops around here.";
	}

}
