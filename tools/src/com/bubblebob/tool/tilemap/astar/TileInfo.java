package com.bubblebob.tool.tilemap.astar;

/**
 * Informations sur une case de carte necessaires a l'algorithme A*
 */
public class TileInfo {
	
	public TileInfo(int x, int y, TileInfo tileFrom, int pathLength, int guessedDistance) {
		this.x = x;
		this.y = y;
		this.tileFrom = tileFrom;
		this.pathLength = pathLength;
		this.guessedDistance = guessedDistance;
		this.neighboursVisited = false;
	}

	private int x;
	private int y;
	
	private TileInfo tileFrom;
	private int pathLength;
	private int guessedDistance;
	private boolean neighboursVisited;
	
	public boolean samePlace(TileInfo tile){
		return ((tile != null)&&(this.x == tile.x)&&(this.y == tile.y));
	}

	public int x() {
		return x;
	}

	public int y() {
		return y;
	}

	public TileInfo getTileInfoFrom() {
		return tileFrom;
	}

	public void setTileInfoFrom(TileInfo tileInfoFrom) {
		this.tileFrom = tileInfoFrom;
	}

	public int getPathLength() {
		return pathLength;
	}

	public void setPathLength(int pathLength) {
		this.pathLength = pathLength;
	}

	public int getGuessedDistance() {
		return guessedDistance;
	}

	public void setGuessedDistance(int guessedDistance) {
		this.guessedDistance = guessedDistance;
	}

	public boolean isNeighboursVisited() {
		return neighboursVisited;
	}

	public void setNeighboursVisited(boolean neighboursVisited) {
		this.neighboursVisited = neighboursVisited;
	}
	

}
