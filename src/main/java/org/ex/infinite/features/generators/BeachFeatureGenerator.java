package org.ex.infinite.features.generators;

import org.ex.infinite.features.smells.animals.Crab;
import org.ex.infinite.features.smells.animals.Jellyfish;
import org.ex.infinite.features.smells.animals.Starfish;
import org.ex.infinite.features.smells.etc.Nothing;
import org.ex.infinite.features.smells.etc.RockyBeach;
import org.ex.infinite.utils.Pair;

public class BeachFeatureGenerator extends AbstractRandomGenerator {

	{
		this.add(Pair.of(50, () -> new Nothing()));
		this.add(Pair.of(10, () -> new Crab()));
		this.add(Pair.of(10, () -> new RockyBeach()));
		this.add(Pair.of(5, () -> new Jellyfish()));
		this.add(Pair.of(5, () -> new Starfish()));
	}
	
}
