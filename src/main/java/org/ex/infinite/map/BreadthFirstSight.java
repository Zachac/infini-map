package org.ex.infinite.map;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

import org.ex.infinite.map.exit.Exit;
import org.ex.infinite.map.location.Location;
import org.ex.infinite.map.location.PositionalLocation;

public class BreadthFirstSight {

	private final Set<Location> visible;
	private final HashMap<Exit, Set<Biome>> biomesSeen;
	private final Location location;
	
	public BreadthFirstSight(Location location) {
		this.location = location;
		this.visible = new HashSet<>();
		this.biomesSeen = new HashMap<>();
		
		calculateVisibleLocations();
	}
	
	private void calculateVisibleLocations() {
		Queue<VisitedNode> seen = new ArrayDeque<>();
		
		for (Exit e : location.getExits()) {
			Set<Biome> biomesSet = EnumSet.noneOf(Biome.class);
			seen.add(new VisitedNode(1, e.getLocation(), biomesSet));
		}

		seen.add(new VisitedNode(0, location, null));
		
		VisitedNode currentNode;
		while ((currentNode = seen.poll()) != null) {
			if (! visible.add(currentNode.location)) {
				continue;
			} else if (currentNode.depth > currentNode.location.getViewThreshold()) {
				continue;
			}
			
			addAll(currentNode.location.getExits(), seen, currentNode);
			
			if (currentNode.location instanceof PositionalLocation) {
				PositionalLocation currentLocation = (PositionalLocation) currentNode.location;
				addAll(currentLocation.getUnnavigableExits(), seen, currentNode);
			}
		}
		
	}

	private void addAll(Collection<Exit> exits, Queue<VisitedNode> seen, VisitedNode currentNode) {
		
		for (Exit e : exits) {
			if (! e.isVisible()) {
				continue;
			}
			
			Location l = e.getLocation();
			if (!visible.contains(l)) {
				seen.add(new VisitedNode(currentNode.depth + 1,  e.getLocation(), currentNode.seenBiomes));
			}
		}
	}
	
	public HashMap<Exit, Set<Biome>> getBiomesSeen() {
		return biomesSeen;
	}
	
	public Set<Location> getVisible() {
		return visible;
	}

	public Location getLocation() {
		return location;
	}

	private static class VisitedNode {
		public final int depth;
		public final Location location;
		public final Set<Biome> seenBiomes;
		
		public VisitedNode(int depth, Location location, Set<Biome> seenBiomes) {
			this.depth = depth;
			this.location = location;
			this.seenBiomes = seenBiomes;
		}
	}
}
