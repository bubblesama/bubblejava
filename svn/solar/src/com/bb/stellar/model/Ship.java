package com.bb.stellar.model;

import com.bb.stellar.model.ai.ShipPathAi;
import com.bb.stellar.model.ai.Target;

public class Ship implements Target{

	private String name;
	private double stellarX;
	private double stellarY;
	private Galaxy galaxy;
	private StellarSystem system;
	private ShipPathAi ai;
	public double dx = 0;
	public double dy = 0;
	public final static double SPEED = 0.02;

	public Ship(String name, double stellarX, double stellarY, Galaxy galaxy) {
		super();
		this.name = name;
		this.stellarX = stellarX;
		this.stellarY = stellarY;
		this.galaxy = galaxy;
		this.ai = new ShipPathAi(this);
	}

	public void update(){
		ai.update();
		stellarX += dx;
		stellarY += dy;
	}
	public StellarSystem getSystem() {
		return system;
	}
	public void setSystem(StellarSystem system) {
		this.system = system;
	}
	public double getStellarX() {
		return stellarX;
	}
	public double getStellarY() {
		return stellarY;
	}
	public void setSpeed(double dx, double dy){
		this.dx = dx;
		this.dy = dy;
	}
	public void addTarget(Target target) {
		ai.addTarget(target);
	}
	public void clearTargets() {
		ai.clearTargets();
	}

	public void reconsiderPath() {
		ai.reconsider();
	}

}
