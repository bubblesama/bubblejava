package com.bb.khatanne.model;

import java.util.ArrayList;
import java.util.List;

public class Side {
	
	public List<Vertex> vertexes;
	public Road road;
	public int name;
	
	
	public Side(int id){
		this.name = id;
		this.vertexes = new ArrayList<Vertex>();
	}
	
}
