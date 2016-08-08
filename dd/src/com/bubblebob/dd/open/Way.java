package com.bubblebob.dd.open;

/**
 * Represente les voies de sorties d'une case
 * @author Bubblebob
 *
 */
public enum Way {

	TOP,BOTTOM,LEFT,RIGHT;

	public Way opp(){
		switch (this) {
		case TOP:
			return BOTTOM;
		case BOTTOM:
			return TOP;
		case LEFT:
			return RIGHT;
		case RIGHT:
			return LEFT;
		default:
			break;
		}
		return null;
	}

	public Way getWayFromInt(int n){
		switch (n) {
		case 0:
			return TOP;
		case 1:
			return BOTTOM;	
		case 2:
			return LEFT;	
		case 3:
			return RIGHT;	
		default:
			return null;
		}
	}

}
