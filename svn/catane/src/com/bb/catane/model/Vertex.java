package com.bb.catane.model;

import java.util.ArrayList;
import java.util.List;

public class Vertex {
	
	public List<Side> sides;
	public List<Tile> tiles;
	public Colony colony;
	public int name;
	
	public List<Side> getSides(){
		return sides;
	}

	
	public boolean oneNeighbouringVertexIsNotEmpty(){
		boolean neighbourColony = false;
		for (Side side: sides){
			for (Vertex vertex : side.vertexes){
				if (vertex != this && vertex.colony != null){
					neighbourColony = true;
				}
			}
		}
		return neighbourColony;
	}
	
	public Vertex(int id){
		this.sides = new ArrayList<Side>();
		this.tiles = new ArrayList<Tile>();
		this.name=id;
	}
	
	
	
	
}
