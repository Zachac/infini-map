package org.ex.infinite.features.smells.etc;

import org.ex.infinite.features.smells.Smell;

public class Well implements Smell {

	@Override
	public String getName() {
		return "well";
	}

	@Override
	public String getDescription() {
		return "A well has been built here.";
	}

}
