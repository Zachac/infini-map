package org.ex.infinite.features.smells.animals;

import org.ex.infinite.features.smells.Smell;

public class Starfish implements Animal, Smell {

	@Override
	public String getName() {
		return "starfish";
	}

	@Override
	public String getDescription() {
		return "A starfish lies on the ground here";
	}

}
