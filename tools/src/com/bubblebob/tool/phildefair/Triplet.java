package com.bubblebob.tool.phildefair;

public class Triplet {

	public double x;
	public double y;
	public double z;
	
	public Triplet(double x, double y, double z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public double scale(Triplet other){
		return x*other.x+y*other.y+other.z*z;
	}

	public Triplet time(double k){
		return new Triplet(k*x,k*y,k*z);
	}
	
	public Triplet getVector(Triplet other){
		return new Triplet(other.x-x, other.y-y,other.z-z);
	}
	
}
