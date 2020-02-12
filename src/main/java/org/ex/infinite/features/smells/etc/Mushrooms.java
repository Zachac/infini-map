package org.ex.infinite.features.smells.etc;

import org.ex.infinite.features.smells.Smell;
import org.ex.infinite.utils.StringUtils;

public class Mushrooms implements Smell {

	public final MushroomColor color;
	
	public Mushrooms(MushroomColor color) {
		this.color = color;
	}

	@Override
	public String getName() {
		return color.name;
	}

	@Override
	public String getDescription() {
		return color.description;
	}

	public static enum MushroomColor {
		RED, BLUE, BLACK, GREEN;

		public final String description;
		public final String name;
		
		private MushroomColor() {
			this.name = this.name().toLowerCase() + " mushrooms";
			this.description = StringUtils.nameCase(this.name()) + " capped mushrooms grows here.";
		}
	}
}
