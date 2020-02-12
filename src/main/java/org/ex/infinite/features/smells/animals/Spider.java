package org.ex.infinite.features.smells.animals;

import org.ex.infinite.features.smells.Smell;

public class Spider implements Animal, Smell {

	@Override
	public String getName() {
		return "spider";
	}

	@Override
	public String getDescription() {
		return "A spider has set up its web here.";
	}

}
