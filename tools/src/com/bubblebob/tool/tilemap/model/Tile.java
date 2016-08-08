package com.bubblebob.tool.tilemap.model;

/**
 * Une case de la carte
 * @author bubblebob
 *
 */
public class Tile {
	
	private int x;
	private int y;
	
	private int type;
	
	private boolean accessible;

	public Tile(int x, int y, boolean accessible){
		this.x = x;
		this.y = y;
		this.accessible = accessible;
	}
	
	public boolean isAccessible() {
		return accessible;
	}

	public void setAccessible(boolean accessible) {
		this.accessible = accessible;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public boolean equals(Tile t){
		return (this.x == t.getX() && this.y == t.getY());
	}


	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
