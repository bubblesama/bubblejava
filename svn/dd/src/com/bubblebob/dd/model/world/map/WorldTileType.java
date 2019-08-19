package com.bubblebob.dd.model.world.map;

public enum WorldTileType {
	EMPTY(' '), 
	RIVER_UP_DOWN('r'), RIVER_UP_RIGHT('s'), RIVER_RIGHT_DOWN('t'),RIVER_DOWN_LEFT('u'),RIVER_LEFT_UP('v'),
	MOUTAIN_BLACK('m'),MOUTAIN_GREY('n'),MOUTAIN_BLUE('o'),MOUTAIN_RED('p'),MOUTAIN_PURPLE('q'),MOUTAIN_BLANK('l'),
	/**
	 * La montagne du donjon principal
	 */
	MOUTAIN_BIG('b'),
	WALL_DOOR_UP_DOWN('w'),WALL_DOOR_LEFT_RIGHT('x'),
	WALL_UP_DOWN('y'),WALL_LEFT_RIGHT('z'),
	FOREST('f'),
	HOUSE('h');


	private char n;

	WorldTileType(char n){
		this.n = n;
	}

	public char getChar(){
		return n;
	}

	public static WorldTileType getTypeFromChar(char n){
		switch (n) {
		case 'r':
			return RIVER_UP_DOWN;
		case 's':
			return RIVER_UP_RIGHT;
		case 't':
			return RIVER_RIGHT_DOWN;
		case 'u':
			return RIVER_DOWN_LEFT;
		case 'v':
			return RIVER_LEFT_UP;
		case 'l':
			return MOUTAIN_BLANK;
		case 'm':
			return MOUTAIN_BLACK;
		case 'n':
			return MOUTAIN_GREY;
		case 'o':
			return MOUTAIN_BLUE;
		case 'p':
			return MOUTAIN_RED;
		case 'q':
			return MOUTAIN_PURPLE;
		case 'w':
			return WALL_DOOR_UP_DOWN;
		case 'x':
			return WALL_DOOR_LEFT_RIGHT;
		case 'y':
			return WALL_UP_DOWN;
		case 'z':
			return WALL_LEFT_RIGHT;
		case 'f':
			return FOREST;
		case 'h':
			return HOUSE;
		default:
			return EMPTY;
		}
	}

	public WorldTileType next(){
		switch (this) {
		case EMPTY:
			return RIVER_UP_DOWN;
		case RIVER_UP_DOWN:
			return RIVER_UP_RIGHT;
		case RIVER_UP_RIGHT:
			return RIVER_RIGHT_DOWN;
		case RIVER_RIGHT_DOWN:
			return RIVER_DOWN_LEFT;
		case RIVER_DOWN_LEFT:
			return RIVER_LEFT_UP;
		case RIVER_LEFT_UP:
			return MOUTAIN_BLANK;
		case MOUTAIN_BLANK:
			return MOUTAIN_GREY;
		case MOUTAIN_GREY:
			return MOUTAIN_BLUE;
		case MOUTAIN_BLUE:
			return MOUTAIN_RED;
		case MOUTAIN_RED:
			return MOUTAIN_PURPLE;
		case MOUTAIN_PURPLE:
			return WALL_DOOR_UP_DOWN;
		case WALL_DOOR_UP_DOWN:
			return WALL_DOOR_LEFT_RIGHT;
		case WALL_DOOR_LEFT_RIGHT:
			return WALL_UP_DOWN;
		case WALL_UP_DOWN:
			return WALL_LEFT_RIGHT;
		case WALL_LEFT_RIGHT:
			return FOREST;
		case FOREST:
			return HOUSE;
		case HOUSE:
			return EMPTY;
		default:
			break;
		}
		return null;
	}
	
	public boolean isForest(){
		boolean result = false;
		switch (this) {
		case FOREST:
			result = true;
			break;
		default:
			break;
		}
		return result;
	}
	
	public boolean isRiver(){
		boolean result = false;
		switch (this) {
		case RIVER_UP_DOWN:
		case RIVER_UP_RIGHT:
		case RIVER_RIGHT_DOWN:
		case RIVER_DOWN_LEFT:
		case RIVER_LEFT_UP:
			result = true;
			break;
		default:
			break;
		}
		return result;
	}
	
	public boolean isDoor(){
		boolean result = false;
		switch (this) {
		case WALL_DOOR_UP_DOWN:
		case WALL_DOOR_LEFT_RIGHT:
			result = true;
			break;
		default:
			break;
		}
		return result;
	}
	
	public boolean isDungeon(){
		boolean result = false;
		switch (this) {
		case MOUTAIN_GREY:
		case MOUTAIN_BLUE:
		case MOUTAIN_RED:
		case MOUTAIN_PURPLE:
			result = true;
			break;
		default:
			break;
		}
		return result;
	}
	
	
}
