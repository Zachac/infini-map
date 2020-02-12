package org.ex.infinite.features.generators;

import org.ex.infinite.features.smells.animals.Cat;
import org.ex.infinite.features.smells.animals.Mouse;
import org.ex.infinite.features.smells.animals.Rabbit;
import org.ex.infinite.features.smells.etc.Flowers;
import org.ex.infinite.features.smells.etc.Flowers.FlowerColor;
import org.ex.infinite.features.smells.etc.LongGrass;
import org.ex.infinite.features.smells.etc.ManaSpring;
import org.ex.infinite.features.smells.etc.Mushrooms;
import org.ex.infinite.features.smells.etc.Mushrooms.MushroomColor;
import org.ex.infinite.features.smells.etc.Nothing;
import org.ex.infinite.features.smells.etc.Ore;
import org.ex.infinite.features.smells.etc.Ore.OreType;
import org.ex.infinite.features.smells.etc.Well;
import org.ex.infinite.utils.Pair;

public class PlainsFeatureGenerator extends AbstractRandomGenerator {

	{
		this.add(Pair.of(100, () -> new Nothing()));
		this.add(Pair.of(40, () -> new LongGrass()));
		this.add(Pair.of(10, () -> new Flowers(FlowerColor.RED)));
		this.add(Pair.of(10, () -> new Flowers(FlowerColor.WHITE)));
		this.add(Pair.of(10, () -> new Flowers(FlowerColor.BLUE)));
		this.add(Pair.of(10, () -> new Flowers(FlowerColor.YELLOW)));
		this.add(Pair.of(10, () -> new Ore(OreType.COPPER)));
		this.add(Pair.of(10, () -> new Ore(OreType.TIN)));
		this.add(Pair.of(15, () -> new Ore(OreType.IRON)));
		this.add(Pair.of(5, () -> new Ore(OreType.GOLD)));
		this.add(Pair.of(5, () -> new Well()));
		this.add(Pair.of(5, () -> new ManaSpring()));
		this.add(Pair.of(5, () -> new Mushrooms(MushroomColor.RED)));
		this.add(Pair.of(5, () -> new Mushrooms(MushroomColor.BLUE)));
		this.add(Pair.of(5, () -> new Mushrooms(MushroomColor.GREEN)));
		this.add(Pair.of(1, () -> new Mushrooms(MushroomColor.BLACK)));
		this.add(Pair.of(5, () -> new Cat()));
		this.add(Pair.of(5, () -> new Rabbit()));
		this.add(Pair.of(5, () -> new Mouse()));
	}
	
}
