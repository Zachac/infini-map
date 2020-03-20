package org.ex.infinite.utility;

import java.util.Random;

public class SeedGenerator {

	private long initialSeed;
	private final Random random;

	public SeedGenerator(long initialSeed) {
		this.initialSeed = initialSeed;
		this.random = new Random(initialSeed);
	}
	
	public int next() {
		return this.random.nextInt();
	}
	
	public int getSeed(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("Cannot get negative nth seed.");
		}
		
		var rand = new Random(this.initialSeed);
		
		for (int i = 0; i < n; i++) {
			rand.nextInt();
		}
		
		return rand.nextInt();
	}
}
