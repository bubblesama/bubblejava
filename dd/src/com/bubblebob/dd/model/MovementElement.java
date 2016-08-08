package com.bubblebob.dd.model;

public class MovementElement {

	private int dX;
	private int dY;
	
	public MovementElement(int dX, int dY) {
		this.dX = dX;
		this.dY = dY;
	}
	
	public int getDX() {
		return dX;
	}
	public int getDY() {
		return dY;
	}
}
