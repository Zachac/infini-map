package org.ex.infinite.features.smells.animals;

import org.ex.infinite.features.smells.Smell;

public class Owl implements Animal, Smell {

	@Override
	public String getName() {
		return "owl";
	}

	@Override
	public String getDescription() {
		return "A owl's hoot can be heard.";
	}

}
