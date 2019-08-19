package com.bb.solar;

public class SpaceView {
	
	public SpaceView(int w, int h, double x0, double y0, double zoom) {
		super();
		this.w = w;
		this.h = h;
		this.x0 = x0;
		this.y0 = y0;
		this.zoom = zoom;
	}

	public int w;
	public int h;

	private double x0;
	private double y0;
	
	private double zoom;

	public double getX0() {
		return x0;
	}

	public double getY0() {
		return y0;
	}

	public double getZoom() {
		return zoom;
	}
}

