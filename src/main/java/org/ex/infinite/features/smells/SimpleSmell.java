package org.ex.infinite.features.smells;

public class SimpleSmell implements Smell {

	String description;
	String name;
	
	public SimpleSmell(String description) {
		this.description = description;
		this.name = Integer.toHexString(description.hashCode());
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getDescription() {
		return description;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
