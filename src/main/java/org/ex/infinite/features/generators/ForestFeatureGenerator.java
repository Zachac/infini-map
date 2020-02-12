package org.ex.infinite.features.generators;

import org.ex.infinite.features.smells.etc.BerryBush;
import org.ex.infinite.features.smells.etc.Mushrooms;
import org.ex.infinite.features.smells.etc.BerryBush.BerryColor;
import org.ex.infinite.features.smells.etc.Mushrooms.MushroomColor;
import org.ex.infinite.features.smells.etc.Nothing;
import org.ex.infinite.features.smells.etc.Sticks;
import org.ex.infinite.utils.Pair;

public class ForestFeatureGenerator extends AbstractRandomGenerator {


	{
		this.add(Pair.of(100, () -> new Nothing()));
		this.add(Pair.of(40, () -> new Sticks()));
		this.add(Pair.of(10, () -> new BerryBush(BerryColor.RED)));
		this.add(Pair.of(10, () -> new BerryBush(BerryColor.BLUE)));
		this.add(Pair.of(10, () -> new BerryBush(BerryColor.BLACK)));
		this.add(Pair.of(5, () -> new BerryBush(BerryColor.SPOTTED)));
		this.add(Pair.of(10, () -> new Mushrooms(MushroomColor.RED)));
		this.add(Pair.of(10, () -> new Mushrooms(MushroomColor.BLUE)));
		this.add(Pair.of(10, () -> new Mushrooms(MushroomColor.GREEN)));
		this.add(Pair.of(2, () -> new Mushrooms(MushroomColor.BLACK)));
	}
	
}
