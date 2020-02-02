package org.ex.infinite.map;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

import org.ex.external.fast.FastNoise;
import org.ex.infinite.map.exit.Exit;
import org.ex.infinite.map.location.Location;
import org.ex.infinite.map.location.PositionalLocation;
import org.ex.infinite.randomizer.Seed;

public class Map {
	private static final FastNoise NOISE = new FastNoise((int) Seed.value);
	private static final int MAP_RADIUS = 3;
	private static final int MAP_LENGTH = MAP_RADIUS * 2 + 1;
	private static final int MAP_HEIGHT = (MAP_LENGTH + 2); 
	private static final int MAP_WIDTH = (MAP_LENGTH * 2 + 2);
	private static final float MAP_SCALE = 10f;
	private static final char[] MAP_LINE = new char[MAP_WIDTH]; static {
		Arrays.fill(MAP_LINE, '=');
		MAP_LINE[MAP_LINE.length - 1] = '\n';
	}
	
	public static String getMap(Location location) {
		if (! (location instanceof PositionalLocation)) {
			return "map generation is not supported here";
		}
		
		Biome[][] values = new Biome[MAP_LENGTH][MAP_LENGTH];
		
		Position p = ((PositionalLocation) location).getPosition();
		Set<Location> visited = new HashSet<>();
		Queue<VisitedNode> seen = new ArrayDeque<>();
		seen.add(new VisitedNode(0, location));
		
		VisitedNode currentNode;
		while ((currentNode = seen.poll()) != null) {
			if (! visited.add(currentNode.location)) {
				continue;
			}
			
			boolean visibleSurroundings = currentNode.depth <= currentNode.location.getViewThreshold();
			
			if (visibleSurroundings) {
				addAll(currentNode.location.getExits(), seen, currentNode.depth);
			}
			
			if (currentNode.location instanceof PositionalLocation) {
				PositionalLocation currentLocation = (PositionalLocation) currentNode.location;
				Position currentPosition = currentLocation.getPosition();
				int x = currentPosition.x - p.x + MAP_RADIUS;
				int y = currentPosition.y - p.y + MAP_RADIUS;
				
				if (x >= 0 && y >= 0 && x < MAP_LENGTH && y < MAP_LENGTH) {
					values[x][y] = currentNode.location.getBiome();
				}

				if (visibleSurroundings) {
					addAll(currentLocation.getUnnavigableExits(), seen, currentNode.depth);
				}
			}
		}
		
		return drawMap(values);
	}

	private static void addAll(Collection<Exit> exits, Queue<VisitedNode> seen, int currentDepth) {
		int nextDepth = currentDepth + 1;
		
		for (Exit e : exits) {
			if (! e.isVisible()) {
				continue;
			}
			
			seen.add(new VisitedNode(nextDepth,  e.getLocation()));
		}
	}
	
	private static class VisitedNode {
		public final int depth;
		public final Location location;
		public VisitedNode(int depth, Location location) {
			this.depth = depth;
			this.location = location;
		}
	}

	private static String drawMap(Biome[][] values) {
		StringBuilder result = new StringBuilder(MAP_HEIGHT * MAP_WIDTH);
		result.append(MAP_LINE);
		
		for (Biome[] arr : values) {
			result.append('|');
			
			for (Biome b : arr) {
				
				if (b != null) {
					result.append(b.icon);
				} else {
					result.append(' ');
				}
				
				result.append(' ');
			}
			
			result.setLength(result.length() - 1);
			result.append('|');
			result.append('\n');
		}
		
		result.append(MAP_LINE);

		int playerPosition = ((MAP_RADIUS + 1) * MAP_WIDTH) + MAP_RADIUS * 2;
		result.setCharAt(playerPosition, '>');
		result.setCharAt(playerPosition + 2, '<');
		
		return result.toString();
	}
	
	public static Biome getBiome(float x, float y) {
		
		x *= MAP_SCALE;
		y *= MAP_SCALE;
		
		float value = NOISE.GetPerlin(x, y);

		if (value > 0.1) {
			return Biome.Plains;
		} else if (value > 0) {
			return Biome.Beach;
		} else {
			return Biome.Ocean;
		}
	}

	public static Biome getBiome(Position position) {
		return getBiome(position.x, position.y);
	}
}
