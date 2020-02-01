package org.ex.infinite.map;

public enum Direction {

	n(-1, 0),
	e(0, 1),
	s(1, 0),
	w(0, -1),
	ne(n, e),
	se(s, e),
	nw(n, w),
	sw(s, w);
	
	public final int x;
	public final int y;

	private Direction(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	private Direction(Direction a, Direction b) {
		this.x = a.x + b.x;
		this.y = a.y + b.y;
	}
}
