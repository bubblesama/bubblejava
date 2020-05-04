package com.bubblebob.tool.tilemap.model;

import java.util.List;
import java.util.Vector;

import com.bubblebob.tool.ggame.GameModel;



public class SimpleMap implements Map{

	private int height;
	private int width;
	private Tile[][] tiles;

	public SimpleMap(int width, int height){
		this.width = width;
		this.height = height;
		this.tiles = new Tile[width][height];
		for (int i=0; i<width; i++){
			for (int j=0; j<height; j++){
				tiles[i][j] = new Tile(i,j,true);
			}
		}
	}

	public Tile getTile(int x, int y){
		return tiles[x][y];
	}

	public List<Tile> getNeighbours(int x, int y){
		Vector<Tile> result = new Vector<Tile>(); 
		for (int i=-1; i<2; i++){
			if (x+i<this.width && x+i>-1){
				for (int j=-1; j<2; j++){
					if (y+j<this.height && y+j>-1 && ((i!=0 || j!=0))){
						result.add(tiles[x+i][y+j]);
					}
				}
			}
		}
		return result;
	}
	
	public int getWidth(){
		return this.width;
	}
	
	public int getHeight(){
		return this.height;
	}

	
}
