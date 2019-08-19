package com.bb.solar;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class SolarModel {
	
	// G = 6,7 	 84(80) \times 10^{-11} \ \mbox{m}^3 \ \mbox{kg}^{-1} \ \mbox{s}^{-2}
	public static final double G =0.000000000067 ;

	private int maxVelocity = 100;
	
	private List<SolarBody> bodies;

	public SolarModel() {
		super();
		this.bodies = new ArrayList<SolarBody>();
	}
	
	public void render(SpaceView view, Graphics g){
		g.setColor(Color.red);
		for (SolarBody body: bodies){
			body.render(view,g);
		}
	}

	public boolean add(SolarBody body) {
		return bodies.add(body);
	}
	
	public void updateGravityField(){
		
	}
	
	public void updatePlaces(){
		
	}
	
}
