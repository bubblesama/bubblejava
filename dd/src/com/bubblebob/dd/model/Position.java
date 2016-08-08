package com.bubblebob.dd.model;

/**
 * Fournit les deux coordonnees de case d'un objet quelconque
 * @author Bubblebob
 *
 */
public class Position {

	
	public Position(int tileX, int tileY) {
		super();
		this.tileX = tileX;
		this.tileY = tileY;
	}
	
	private int tileX;
	private int tileY;
	
	public int getTileX() {
		return tileX;
	}
	public void setTileX(int tileX) {
		this.tileX = tileX;
	}
	public int getTileY() {
		return tileY;
	}
	public void setTileY(int tileY) {
		this.tileY = tileY;
	}
	
	
}
