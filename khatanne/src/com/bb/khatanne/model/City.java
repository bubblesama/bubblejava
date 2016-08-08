package com.bb.khatanne.model;

public class City extends Colony{

	public City(Vertex vertex, Player player){
		super(vertex,player);
		this.type = ColonyType.CITY;
	}
}
