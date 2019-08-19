package com.bb.solar;

import org.newdawn.slick.Graphics;

public class SolarBody {

	private double mass;
	private double x;
	private double y;
	private double r;
	
	private double vX;
	private double vY;
	private double aX;
	private double aY;
	
	private double currentGravityInfluence;
	
	public SolarBody(double mass, double r, double x, double y, double vX, double vY) {
		super();
		this.mass = mass;
		this.x = x;
		this.y = y;
		this.r = r;
		this.vX = vX;
		this.vY = vY;
	}
	
	public void render(SpaceView view, Graphics g){
		int size = (int)(2*r/view.getZoom());
		g.fillOval((int)((x-r-view.getX0())/view.getZoom()), (int)((y-r-view.getY0())/view.getZoom()), size, size);
	}
	
	public double getGravityInfluence(SolarBody other){
		double result = other.mass/((other.x-x)*(other.x-x)+(other.y-y)*(other.y-y));
		return result;
	}
	
	public void resetGravityInfluence(){
		this.currentGravityInfluence = 0;
	}
	
	public void addGraviticInfluence(SolarBody other){
		this.currentGravityInfluence += getGravityInfluence(other);
	}
	
}
