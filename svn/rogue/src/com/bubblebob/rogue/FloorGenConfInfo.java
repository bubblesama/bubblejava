package com.bubblebob.rogue;

public class FloorGenConfInfo {
	
	public FloorGenConfInfo(int roomsMax, int width, int height, int roomWidthRange, int roomMinWidth, int roomMaxArea,
			int roomMinArea) {
		super();
		this.roomsMax = roomsMax;
		this.width = width;
		this.height = height;
		this.roomWidthRange = roomWidthRange;
		this.roomMinWidth = roomMinWidth;
		this.roomMaxArea = roomMaxArea;
		this.roomMinArea = roomMinArea;
	}
	
	public int width = 20;
	public int height = 20;
	public int roomsMax = 4;
	// largeur de la plage des largeur/longueur des salles
	public int roomWidthRange=8;
	// largeur/longueur maximale des salles
	public int roomMinWidth=3;
	// surface maximale d'une salle donnee
	public int roomMaxArea = 30;
	// surface minimale d'une salle
	public int roomMinArea = 20;

	
	public void setRoomWidthRange(int roomWidthRange) {
		this.roomWidthRange = roomWidthRange;
	}
	public void setRoomMinWidth(int roomMinWidth) {
		this.roomMinWidth = roomMinWidth;
	}
	public void setRoomMaxArea(int roomMaxArea) {
		this.roomMaxArea = roomMaxArea;
	}
	public void setRoomMinArea(int roomMinArea) {
		this.roomMinArea = roomMinArea;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public void setRoomsMax(int roomsMax) {
		this.roomsMax = roomsMax;
	}
	
}
