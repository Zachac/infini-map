package org.ex.infinite.features.smells.etc;

import org.ex.infinite.features.smells.Smell;

public class Forest implements Smell {

	@Override
	public String getName() {
		return "forest";
	}

	@Override
	public String getDescription() {
		return "A thick forest surounds the area.";
	}

}
