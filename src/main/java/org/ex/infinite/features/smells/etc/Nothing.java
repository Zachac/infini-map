package org.ex.infinite.features.smells.etc;

import org.ex.infinite.features.smells.Smell;

public class Nothing implements Smell {

	@Override
	public String getName() {
		return "nothing";
	}

	@Override
	public String getDescription() {
		return "There is nothing here.";
	}

}
