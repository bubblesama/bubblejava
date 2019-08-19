package com.bb.stellar.gui;

public abstract class GuiZone{

	public int w;
	public int h;
	public int i0;
	public int j0;
	
	public GuiZone(int w, int h, int i0, int j0) {
		super();
		this.w = w;
		this.h = h;
		this.i0 = i0;
		this.j0 = j0;
	}

	public GuiZone(GuiZone zone){
		this.w = zone.w;
		this.h = zone.h;
		this.i0 = zone.i0;
		this.j0 = zone.j0;
	}
	
	public int getI(int i){
		return i-i0;
	}
	
	public int getJ(int j){
		return j-j0;
	}
	
	public boolean isIn(int i, int j){
		return (i>=i0 && i<=i0+w && j>=j0 && j<j0+h);
	}
	
	public abstract void updateMouse(int mouseI, int mouseJ, boolean dragging);
	public abstract void drag(int deltaMouseX, int deltaMouseY);
	public abstract void click(int mouseX, int mouseY, int button);
	
}
