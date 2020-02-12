package org.ex.infinite;

import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.ex.infinite.features.smells.Smell;
import org.ex.infinite.map.Biome;
import org.ex.infinite.map.BreadthFirstSight;
import org.ex.infinite.map.Map;
import org.ex.infinite.map.exit.Exit;
import org.ex.infinite.map.location.Location;
import org.ex.infinite.map.location.PositionalLocation;

public class InfiniteDungeon {

	public static void main(String[] args) {
		
		mainLoop();
//		sampleMap();
		
	}

	 static void sampleMap() {
		int x = -1;
		int y = -10;
		
		Biome[][] map = new Biome[100][100];
		
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				
				map[i][j] = Map.getBiome(x + i, y + j);
				
			}
		}
		
		System.out.println(Map.drawMap(map));
	}

	public static void mainLoop() {
		try (Scanner s = new Scanner(System.in)) {

			Location l = PositionalLocation.getLocation(0, 0);
			display(l);
			
			while (s.hasNextLine()) {
				String input = s.nextLine();
				
				Optional<Exit> ex = l.getExits()
						.stream()
						.filter(e -> e.getName().equalsIgnoreCase(input))
						.findAny();
				
				if (ex.isPresent()) {
					l = ex.get().getLocation();
					display(l);
				} else {
					System.out.println("Exit not found.");
				}
			}
		}
	}

	private static void display(Location l) {
		BreadthFirstSight sight = new BreadthFirstSight(l);
		
		System.out.print(Map.getMap(sight));
		System.out.println("The " + l.getBiome().name());
		l.getFeatures().stream().filter(Smell.class::isInstance).forEach(f -> {
			System.out.println(((Smell) f).getDescription());
		});
		
		System.out.println("View threshold: " + l.getViewThreshold());
		System.out.println("Exits: " + l.getExits().stream().filter(e -> e.isVisible()).collect(Collectors.toList()));
		System.out.println();
		System.out.println("*Hidden exits: " + l.getExits().stream().filter(e -> ! e.isVisible()).collect(Collectors.toList()));
		System.out.println("*Features: " + l.getFeatures().stream().map((loc) -> loc.getName()).collect(Collectors.toList()));
		
		if (l instanceof PositionalLocation) {
			System.out.println("*Position: " + ((PositionalLocation) l).getPosition());
		}
	}
}
