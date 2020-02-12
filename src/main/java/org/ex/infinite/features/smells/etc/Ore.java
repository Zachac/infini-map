package org.ex.infinite.features.smells.etc;

import org.ex.infinite.features.smells.Smell;
import org.ex.infinite.utils.StringUtils;

public class Ore implements Smell {

	public final OreType type;
	
	public Ore(OreType type) {
		this.type = type;
	}
	
	@Override
	public String getName() {
		return type.name;
	}

	@Override
	public String getDescription() {
		return type.description;
	}

	
	public static enum OreType {

		COPPER, TIN, IRON, GOLD;

		public final String description;
		public final String name;
		
		private OreType() {
			this.name = this.name().toLowerCase() + " ore";
			this.description = StringUtils.nameCase(this.name()) + " ore can be seen peeking out of a rock outcropping.";
		}
	}
}
