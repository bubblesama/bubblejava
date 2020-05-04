package com.bb.catane.model;

import java.util.ArrayList;
import java.util.List;

public class Tile {

	public List<Side> sides;
	public List<Vertex> vertexes;
	public int resourceScore;
	public TileType type;
	public int name;
	public boolean robbers;
	
	public List<Vertex> getVertexes(){
		return vertexes;
	}

	
	public Tile(int id) {
		this.sides = new ArrayList<Side>();
		this.vertexes = new ArrayList<Vertex>();
		this.name = id;
	}
	
	
	
	
	public String getDebugString(){
		return "tile("+name+"):"+type+":"+resourceScore;
	}
	
}
