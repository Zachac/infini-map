package org.ex.infinite.features.smells.animals;

import org.ex.infinite.features.smells.Smell;

public class Deer implements Animal, Smell {

	@Override
	public String getName() {
		return "deer";
	}

	@Override
	public String getDescription() {
		return "Deer tracks can be found here.";
	}

}
