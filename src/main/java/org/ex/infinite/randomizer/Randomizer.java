package org.ex.infinite.randomizer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Randomizer {
	
	/**
	 * Use resevior sampling to get a simple random sample of the elements of the list
	 * O(n)
	 * 
	 * @param <T> the type of the list
	 * @param sampleSize the number of samples to gather
	 * @param list the list to select from
	 * @return a list of sampleSize samples
	 */
	public static <T> List<T> getSamples(int sampleSize, List<T> list, long seed) {
		List<T> result = new ArrayList<>(sampleSize);
		Random r = new Random(seed);
		
		int i = 0;
		for (T value : list) {
			
			if (result.size() < sampleSize) {
				result.add(value);
			} else {
				int j = r.nextInt(i + 1);
				
				if (j < sampleSize) {
					result.set(j, value);
				}
			}

			i++;
		}

		return result;
	}
}
