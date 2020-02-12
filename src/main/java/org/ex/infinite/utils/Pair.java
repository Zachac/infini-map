package org.ex.infinite.utils;

public class Pair<T,V> {

	public final T v1;
	public final V v2;
	
	private Pair(T v1, V v2) {
		this.v1 = v1;
		this.v2 = v2;
	}
	
	public static <T,V> Pair<T,V> of(T v1, V v2) {
		return new Pair<>(v1, v2);
	}
}
