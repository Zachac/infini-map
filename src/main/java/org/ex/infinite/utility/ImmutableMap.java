package org.ex.infinite.utility;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ImmutableMap {

	public static <T> Map<T, T> of(@SuppressWarnings("unchecked") T... values) {
		if ((values.length & 1) != 0) {
			throw new IllegalArgumentException("Cannot create map of unbalanced pair array!");
		}
		
		var result = new HashMap<T,T>();
		
		for (var i = 0; i < values.length; i += 2) {
			result.put(values[i], values[i + 1]);
		}
		
		return Collections.unmodifiableMap(result);
	}
}
