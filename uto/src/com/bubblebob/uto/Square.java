package com.bubblebob.uto;


public class Square {

	protected SquareType type;
	protected PlayerType owner;
	
	public int x;
	public int y;
	public int i;
	public int j;
	
	public BuildingType buildingType;

	public Square(int i, int j, int x, int y){
		this.type = SquareType.SEA;
		this.owner = PlayerType.NO;
		this.i = i;
		this.j = j;
		this.x = x;
		this.y = y;
		this.buildingType = BuildingType.NO_BUILDING;
	}

	public SquareType getType() {
		return type;
	}

	public PlayerType getOwner() {
		return owner;
	}
	
}
