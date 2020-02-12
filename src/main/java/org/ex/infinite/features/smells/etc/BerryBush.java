package org.ex.infinite.features.smells.etc;

import org.ex.infinite.features.smells.Smell;
import org.ex.infinite.utils.StringUtils;

public class BerryBush implements Smell {

	public final BerryColor color;
	
	public BerryBush(BerryColor color) {
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

	public static enum BerryColor {
		RED, BLUE, BLACK, SPOTTED;

		public final String description;
		public final String name;
		
		private BerryColor() {
			this.name = this.name().toLowerCase() + " berries";
			this.description = StringUtils.nameCase(this.name()) + " berries grows here.";
		}
	}
}
