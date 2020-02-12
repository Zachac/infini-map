package org.ex.infinite.features.smells.animals;

import org.ex.infinite.features.smells.Smell;

public class Wolf implements Animal, Smell {

	@Override
	public String getName() {
		return "wolf";
	}

	@Override
	public String getDescription() {
		return "Wolf tracks can be found here.";
	}

}
