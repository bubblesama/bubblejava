package com.bb.stellar.gui;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public abstract class ZoneRenderer extends GuiZone{

	public ZoneRenderer(GuiZone screen) {
		super(screen);
	}

	protected double x0 = 0;
	protected double y0 = 0;
	protected double currentZoom;
	protected double zoomStep;
	protected final static int maxZoomLevel = 6;
	protected final static int minZoomLevel = 1;
	protected int currentZoomLevel;
	protected double mouseX;
	protected double mouseY;

	public double getX(int mouseI){
		return ((double)getI(mouseI))/currentZoom+x0;
	}

	public double getY(int mouseJ){
		return ((double)getJ(mouseJ))/currentZoom+y0;
	}

	public void drag(int deltaMouseX, int deltaMouseY){
		x0 = x0-deltaMouseX/(currentZoomLevel*zoomStep);
		y0 = y0-deltaMouseY/(currentZoomLevel*zoomStep);
	}

	public void increaseZoom(int mouseI, int mouseJ){
		changeZoom(mouseI, mouseJ, 1);
	}

	public void decreaseZoom(int mouseI, int mouseJ){
		changeZoom(mouseI, mouseJ, -1);
	}

	public void updateMouse(int mouseI, int mouseJ, boolean dragging){
		this.mouseX = getX(mouseI);
		this.mouseY = getY(mouseJ);
	}

	public void changeZoom(int mouseI, int mouseJ, int deltaZoom){
		double lastZoomLevel = currentZoomLevel;
		if (deltaZoom < 0){
			currentZoomLevel = Math.max(currentZoomLevel+deltaZoom,minZoomLevel);
		}else{
			currentZoomLevel = Math.min(currentZoomLevel+deltaZoom,maxZoomLevel);
		}
		x0 = x0 + getI(mouseI)/(lastZoomLevel*zoomStep) - getI(mouseI)/(currentZoomLevel*zoomStep);
		y0 = y0 + getJ(mouseJ)/(lastZoomLevel*zoomStep) - getJ(mouseJ)/(currentZoomLevel*zoomStep);
		setZoom(((double)currentZoomLevel)*zoomStep);
	}

	public int getZoomedI(double x){
		return i0+(int)((x-x0)*currentZoom);
	}

	public int getZoomedJ(double y){
		return j0+(int)((y-y0)*currentZoom);
	}

	protected void paintPic(Graphics g, Image image, double x, double y){
		g.drawImage(image.getScaledCopy(currentZoomLevel),getZoomedI(x)-image.getWidth()*currentZoomLevel/2,getZoomedJ(y)-image.getHeight()*currentZoomLevel/2);
	}

	public void setZoom(double zoom){
		this.currentZoom = zoom;
	}

}
