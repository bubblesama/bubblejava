package com.bb.stellar.model;

import java.util.ArrayList;
import java.util.List;

public class Gate extends Place{
	
	private StellarSystem destination;
	
	public Gate(String name, StellarSystem system, double x, double y, StellarSystem destination) {
		super(name, TypePlace.GATE, system, x, y);
		this.destination = destination;
	}
	
	public StellarSystem getDestination() {
		return destination;
	}
	
	public List<String> generateDescription(){
		ArrayList<String> result = new ArrayList<String>();
		result.addAll(generateDefaultDescription());
		result.add("target "+destination.getName());
		return result;
	}
	
	
}
