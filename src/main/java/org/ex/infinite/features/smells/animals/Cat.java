package org.ex.infinite.features.smells.animals;

import org.ex.infinite.features.smells.Smell;

public class Cat implements Smell, Animal {

	@Override
	public String getName() {
		return "cat";
	}

	@Override
	public String getDescription() {
		return "A cat prowls around here.";
	}

}
