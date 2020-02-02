package org.ex.infinite.map.location;

import java.util.Collection;

import org.ex.infinite.features.Feature;
import org.ex.infinite.map.Biome;
import org.ex.infinite.map.exit.Exit;

public interface Location {

	public Biome getBiome();
	public int getViewThreshold();
	public Collection<Exit> getExits();
	public Collection<Feature> getFeatures();
	
}
