package org.ex.infinite.map.location;

import java.util.Collection;

import org.ex.infinite.map.Biome;
import org.ex.infinite.map.exit.Exit;

public interface Location {

	Biome getBiome();
	int getViewThreshold();
	Collection<Exit> getExits();
	
}
