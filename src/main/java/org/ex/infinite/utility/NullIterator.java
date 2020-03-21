package org.ex.infinite.utility;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class NullIterator<T> implements Iterator<T> {

	@SuppressWarnings("rawtypes")
	private static NullIterator instance = new NullIterator();
	
	private NullIterator() {}
	
	@Override
	public boolean hasNext() {
		return false;
	}

	@Override
	public T next() {
		throw new NoSuchElementException();
	}
	
	@SuppressWarnings("unchecked")
	public static <T> NullIterator<T> value() {
		return instance;
	}
}
