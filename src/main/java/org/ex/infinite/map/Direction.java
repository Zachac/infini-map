package org.ex.infinite.map;

public enum Direction {

	n(-1, 0),
	e(0, 1),
	s(1, 0),
	w(0, -1);
	
	public final int x;
	public final int y;

	private Direction(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
