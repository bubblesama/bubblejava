package com.bb.catane.model;

public enum TileType {

	DESERT,GRASSLAND,PIT,MOUNTAIN,WOOD,PLAIN;
	
	public ResourceType getResource(){
		switch (this) {
		case DESERT:
			return null;
		case GRASSLAND:
			return ResourceType.WOOL;
		case PIT:
			return ResourceType.CLAY;
		case MOUNTAIN:
			return ResourceType.STONE;
		case WOOD:
			return ResourceType.WOOD;
		case PLAIN:
			return ResourceType.WHEAT;
		default:
			return null;
		}
	}
	
	
}
