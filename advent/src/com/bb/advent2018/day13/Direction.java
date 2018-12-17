package com.bb.advent2018.day13;

public enum Direction {

	UP(0,-1),DOWN(0,1),LEFT(-1,0),RIGHT(1,0);

	public int di;
	public int dj;
	
	private Direction(int di, int dj) {
		this.di = di;
		this.dj = dj;
	}
	
	public Direction turn(CartTurn turn) {
		Direction result = this;
		switch (turn) {
		case LEFT:
			switch (this) {
			case UP:
				result = LEFT;
				break;
			case DOWN:
				result = RIGHT;
				break;
			case LEFT:
				result = DOWN;
				break;
			case RIGHT:
				result = UP;
				break;
			default:
				break;
			}
		case RIGHT:
			switch (this) {
			case UP:
				result = RIGHT;
				break;
			case DOWN:
				result = LEFT;
				break;
			case LEFT:
				result = UP;
				break;
			case RIGHT:
				result = DOWN;
				break;
			default:
				break;
			}
		default:
			break;
		}
		return result;
	}
	
}
