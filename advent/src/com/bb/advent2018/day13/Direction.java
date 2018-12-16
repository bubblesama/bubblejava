package com.bb.advent2018.day13;

public enum Direction {

	UP(0,-1),DOWN(0,1),LEFT(-1,0),RIGHT(1,0);

	public int dx;
	public int dy;
	
	private Direction(int dx, int dy) {
		this.dx = dx;
		this.dy = dy;
	}
	
}
