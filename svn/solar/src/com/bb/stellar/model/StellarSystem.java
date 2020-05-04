package com.bb.stellar.model;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Graphics;

public class StellarSystem {

	public StellarSystem(String name, double galacticX, double galacticY) {
		super();
		this.name = name;
		this.galacticX = galacticX;
		this.galacticY = galacticY;
		this.places = new ArrayList<Place>();
	}
	private String name;
	private List<Place> places;
	
	private double galacticX;
	private double galacticY;
	
	public String getName() {
		return name;
	}
	public List<Place> getPlaces() {
		return places;
	}
	public double getGalacticX() {
		return galacticX;
	}
	public double getGalacticY() {
		return galacticY;
	}
	public boolean add(Place e) {
		return places.add(e);
	}
	
	public void render(Graphics g, double galacticX0, double galacticY0, double zoom){
		
	}
	
}
