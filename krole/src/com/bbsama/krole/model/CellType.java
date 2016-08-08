package com.bbsama.krole.model;

public enum CellType {

	VOID(false),WAY(true),WALL(false),DEBUG(false),DOOR(false);
	
	public boolean passable;
	
	private CellType(boolean passable){
		this.passable = passable;
	}
	
}
