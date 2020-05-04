package com.bb.catane.model;

public class Colony {

	public Vertex place;
	public Player player;
	public ColonyType type;
	
	
	public Colony(Vertex vertex, Player player){
		this.player = player;
		this.place = vertex;
		this.type = ColonyType.COLONY;
	}
	
	
}
