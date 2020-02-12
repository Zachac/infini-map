package org.ex.infinite.features.smells.animals;

import org.ex.infinite.features.smells.Smell;

public class Boar implements Animal, Smell {

	@Override
	public String getName() {
		return "boar";
	}

	@Override
	public String getDescription() {
		return "Boar tracks can be found here.";
	}

}
