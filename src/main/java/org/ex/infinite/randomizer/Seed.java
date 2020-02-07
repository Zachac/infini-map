package org.ex.infinite.randomizer;

import java.util.Random;

public class Seed {

	public static long value = 1337;
	
	private static final long a, b, c; static {
		Random R = new Random(Seed.value);
		a = R.nextLong();
		b = R.nextLong();
		c = R.nextLong();
	}
	
	public static long hash(int x, int y) {
		return ((a * x + b * y + c) >>> 32);
	}
}
