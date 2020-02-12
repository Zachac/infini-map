package org.ex.infinite.features.smells.animals;

import org.ex.infinite.features.smells.Smell;

public class Mouse implements Animal, Smell {

	@Override
	public String getName() {
		return "mouse";
	}

	@Override
	public String getDescription() {
		return "A mouse squeeks around here.";
	}

}
