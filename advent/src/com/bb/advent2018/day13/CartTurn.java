package com.bb.advent2018.day13;

public enum CartTurn {

	LEFT,RIGHT,STRAIGHT;
	
	public CartTurn next() {
		CartTurn result = CartTurn.STRAIGHT;
		switch (this) {
		case LEFT:
			result = CartTurn.STRAIGHT;
			break;
		case STRAIGHT:
			result = CartTurn.RIGHT;
			break;
		case RIGHT:
			result = CartTurn.LEFT;
			break;
		default:
			break;
		}
		return result;
		
	}
	
}
