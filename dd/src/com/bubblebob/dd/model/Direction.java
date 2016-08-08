package com.bubblebob.dd.model;

public enum Direction {

	NORTH, NORTH_EAST, EAST, SOUTH_EAST, SOUTH, SOUTH_WEST, WEST, NORTH_WEST, NONE;
	
	public Direction opposite(){
		switch (this) {
		case NORTH:
			return SOUTH;
		case NORTH_EAST:
			return SOUTH_WEST;
		case EAST:
			return WEST;
		case SOUTH_EAST:
			return NORTH_WEST;
		case SOUTH:
			return NORTH;
		case SOUTH_WEST:
			return NORTH_EAST;
		case WEST:
			return EAST;
		case NORTH_WEST:
			return SOUTH_EAST;
		default:
			return NONE;
		}
	}
	
	
	public MovementElement getMovement(){
		switch (this) {
		case NORTH:
			return new MovementElement(0, -1);
		case NORTH_EAST:
			return new MovementElement(1, -1);
		case EAST:
			return new MovementElement(1, 0);
		case SOUTH_EAST:
			return new MovementElement(1, 1);
		case SOUTH:
			return new MovementElement(0, 1);
		case SOUTH_WEST:
			return new MovementElement(-1, 1);
		case WEST:
			return new MovementElement(-1, 0);
		case NORTH_WEST:
			return new MovementElement(-1, -1);
		default:
			return new MovementElement(0, 0);
		}
	}
	
	
}
