package com.bb.stellar.model;

import java.util.ArrayList;
import java.util.List;

import com.bb.stellar.model.ai.Target;

public abstract class Place implements Target{
	
	public Place(String name, TypePlace type, StellarSystem system, double x, double y) {
		super();
		this.name = name;
		this.system = system;
		this.stellarX = x;
		this.stellarY = y;
		this.type = type;
	}
	
	private String name;
	private StellarSystem system;
	private double stellarX;
	private double stellarY;
	private TypePlace type;
	
	public String getName() {
		return name;
	}
	public StellarSystem getSystem() {
		return system;
	}
	public double getStellarX() {
		return stellarX;
	}
	public double getStellarY() {
		return stellarY;
	}
	public TypePlace getType() {
		return type;
	}
	
	private List<String> description;
	public List<String> getDescription(){
		if (description == null){
			description = generateDescription();
		}
		return description;
	}
	public abstract List<String> generateDescription();
	
	public List<String> generateDefaultDescription(){
		ArrayList<String> desc = new ArrayList<String>();
		desc.add(""+this.getName());
		return desc;
	}
	

}
