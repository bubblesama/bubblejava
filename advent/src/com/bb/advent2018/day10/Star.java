package com.bb.advent2018.day10;

public class Star {
	
	public Star(int x0, int y0, int dx, int dy) {
		super();
		this.x0 = x0;
		this.y0 = y0;
		this.dx = dx;
		this.dy = dy;
		this.x = x0;
		this.y = y0;
	}
	
	public int x0;
	public int y0;
	public int dx;
	public int dy;
	
	public int x;
	public int y;
	
	public void step() {
		this.x = x+dx;
		this.y = y+dy;
	}
	
	public void step(int gap) {
		this.x = x+dx*gap;
		this.y = y+dy*gap;
	}
	
	public void goTo(int step) {
		this.x = x0 + step*dx;
		this.y = x0 + step*dx;
	}
	
	
	public String showCoordinates() {
		return "x="+x+" y="+y;
	}
	

}
