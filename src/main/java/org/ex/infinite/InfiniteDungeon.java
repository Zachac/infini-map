package org.ex.infinite;

import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.ex.infinite.map.Map;
import org.ex.infinite.map.exit.Exit;
import org.ex.infinite.map.location.Location;
import org.ex.infinite.map.location.PositionalLocation;

public class InfiniteDungeon {

	public static void main(String[] args) {
		Location l = new PositionalLocation(0, 0);
		
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
			System.out.println(Map.getMap(positionalLocation.getX(), positionalLocation.getY()));
		}
		
		System.out.println("Type: " + l.getBiome().name());
		System.out.println("View threshold: " + l.getViewThreshold());
		System.out.println("Exits: " + l.getExits().stream().filter(e -> e.isVisible()).collect(Collectors.toList()));
		System.out.println("*Hidden exits: " + l.getExits().stream().filter(e -> ! e.isVisible()).collect(Collectors.toList()));
	}
}
