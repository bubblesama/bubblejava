package com.bb.stellar.model.ai;

import com.bb.stellar.model.StellarSystem;

public class SimpleTarget implements Target{

	public SimpleTarget(StellarSystem system, double stellarX, double stellarY) {
		super();
		this.system = system;
		this.stellarX = stellarX;
		this.stellarY = stellarY;
	}
	
	private StellarSystem system;
	private double stellarX;
	private double stellarY;
	
	public StellarSystem getSystem() {
		return system;
	}
	public double getStellarX() {
		return stellarX;
	}
	public double getStellarY() {
		return stellarY;
	}

}
