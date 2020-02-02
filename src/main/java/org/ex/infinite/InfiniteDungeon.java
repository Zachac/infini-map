package org.ex.infinite;

import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.ex.infinite.features.smells.Smell;
import org.ex.infinite.map.Map;
import org.ex.infinite.map.exit.Exit;
import org.ex.infinite.map.location.Location;
import org.ex.infinite.map.location.PositionalLocation;

public class InfiniteDungeon {

	public static void main(String[] args) {
		Location l = PositionalLocation.getLocation(0, 1);
		
		display(l);
		
		try (Scanner s = new Scanner(System.in)) {
			
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
		if (l instanceof PositionalLocation) {
			PositionalLocation positionalLocation = (PositionalLocation) l;
			System.out.println(Map.getMap(positionalLocation.getPosition()));
		}
		
		System.out.println("The " + l.getBiome().name());
		l.getFeatures().stream().filter(Smell.class::isInstance).forEach(f -> {
			System.out.println(((Smell) f).getDescription());
		});
		
		System.out.println("View threshold: " + l.getViewThreshold());
		System.out.println("Exits: " + l.getExits().stream().filter(e -> e.isVisible()).collect(Collectors.toList()));
		System.out.println("*Hidden exits: " + l.getExits().stream().filter(e -> ! e.isVisible()).collect(Collectors.toList()));
		System.out.println("*Features: " + l.getFeatures());
	}
}
