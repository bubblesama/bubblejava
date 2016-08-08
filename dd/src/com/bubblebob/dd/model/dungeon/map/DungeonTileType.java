package com.bubblebob.dd.model.dungeon.map;

public enum DungeonTileType {
	EMPTY(0), WALL(1), UP_RIGHT_CORNER(2), DOWN_RIGHT_CORNER(3), DOWN_LEFT_CORNER(4), UP_LEFT_CORNER(5);

	private int n;
	
	DungeonTileType(int n){
		this.n = n;
	}

	public int getNumber(){
		return n;
	}
	
	public static DungeonTileType getTypeFromInt(int n){
		switch (n) {
		case 1:
			return WALL;
		case 2:
			return UP_RIGHT_CORNER;
		case 3:
			return DOWN_RIGHT_CORNER;
		case 4:
			return DOWN_LEFT_CORNER;
		case 5:
			return UP_LEFT_CORNER;
		default:
			return EMPTY;
		}
	}
	
	public DungeonTileType quarterRotate(){
		DungeonTileType result = EMPTY;
		switch (this) {
		case EMPTY:
			result = EMPTY;
			break;
		case UP_RIGHT_CORNER:
			result = UP_LEFT_CORNER;
			break;
		case DOWN_RIGHT_CORNER:
			result = UP_RIGHT_CORNER;
			break;
		case DOWN_LEFT_CORNER:
			result = DOWN_RIGHT_CORNER;
			break;
		case UP_LEFT_CORNER:
			result = DOWN_LEFT_CORNER;
			break;
		case WALL:
			result = WALL;
			break;
		default:
			break;
		}
		return result;
	}
	
	
	/**
	 * Fournit un ordre sur les types de case
	 * @return
	 */
	public DungeonTileType next(){
		DungeonTileType result = EMPTY;
		switch (this) {
		case EMPTY:
			result = UP_RIGHT_CORNER;
			break;
		case UP_RIGHT_CORNER:
			result = DOWN_RIGHT_CORNER;
			break;
		case DOWN_RIGHT_CORNER:
			result = DOWN_LEFT_CORNER;
			break;
		case DOWN_LEFT_CORNER:
			result = UP_LEFT_CORNER;
			break;
		case UP_LEFT_CORNER:
			result = WALL;
			break;
		case WALL:
			result = EMPTY;
			break;
		default:
			break;
		}
		return result;
	}
}
