package org.ex.infinite.map.location;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.ex.infinite.map.Biome;
import org.ex.infinite.map.exit.Exit;

public abstract class AbstractLocation implements Location {

	private Biome biome;
	private int viewThreshold;
	private final List<Exit> exits = new ArrayList<>();
	
	public Biome getBiome() {
		return biome;
	}
	
	protected void setBiome(Biome biome) {
		this.biome = biome;
	}
	
	public int getViewThreshold() {
		return viewThreshold;
	}
	
	protected void setViewThreshold(int viewThreshold) {
		this.viewThreshold = viewThreshold;
	}

	public Collection<Exit> getExits() {
		return Collections.unmodifiableCollection(exits);
	}
	
	protected void addExit(Exit e) {
		exits.add(e);
	}
}
