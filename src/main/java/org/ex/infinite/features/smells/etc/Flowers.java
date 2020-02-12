package org.ex.infinite.features.smells.etc;

import org.ex.infinite.features.smells.Smell;
import org.ex.infinite.utils.StringUtils;

public class Flowers implements Smell {

	public final FlowerColor color;
	
	public Flowers(FlowerColor color) {
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

	public static enum FlowerColor {
		RED, BLUE, YELLOW, WHITE;

		public final String description;
		public final String name;
		
		private FlowerColor() {
			this.name = this.name().toLowerCase() + " flowers";
			this.description = StringUtils.nameCase(this.name()) + " flowers grows here.";
		}
	}
}
