package com.bb.stellar.gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import com.bb.stellar.model.Galaxy;
import com.bb.stellar.model.Gate;
import com.bb.stellar.model.StellarSystem;

public class GalacticRender extends ZoneRenderer{

	public GalacticRender(Galaxy galaxy, GuiZone screen, GuiState guiState) {
		super(screen);
		this.galaxy = galaxy;
		this.screen = screen;
		this.guiState = guiState;
	}

	private Galaxy galaxy;
	private GuiZone screen;
	private GuiState guiState;
	
	public void render(GameContainer gc, Graphics g) throws SlickException {
		g.setColor(Color.green);
		for (Gate gate: galaxy.getGates()){
			g.drawLine((float)(getZoomedI(gate.getSystem().getGalacticX())),(float)(getZoomedJ(gate.getSystem().getGalacticY())),(float)(getZoomedI(gate.getDestination().getGalacticX())),(float)(getZoomedJ(gate.getDestination().getGalacticY())));
		}
		for (StellarSystem system: galaxy.getSystems()){
			paintPic(g, MapZone.viewSheet.getSprite(4, 0), system.getGalacticX(), system.getGalacticY());
			if (system == guiState.getSelectedSystem()){
				paintPic(g, MapZone.viewSheet.getSprite(0, 1), system.getGalacticX(), system.getGalacticY());
			}
		}
		// nettoyage hors cadre
		g.setColor(Color.black);
		g.fillRect(0, 0, gc.getScreenWidth(), screen.j0);
		g.fillRect(0, screen.j0+screen.h+1, gc.getScreenWidth(), gc.getScreenHeight());
		g.fillRect(0, screen.j0, screen.i0,screen.h+10);
		g.fillRect(screen.i0+screen.w, screen.j0, gc.getScreenWidth(),screen.h+10);
	}
	
	public void setDefaultZoom(){
		double minX = 10000;
		double maxX = -10000;
		double minY = 10000;
		double maxY = -10000;
		for (StellarSystem system: galaxy.getSystems()){
			if (system.getGalacticX() > maxX){maxX = system.getGalacticX();}
			if (system.getGalacticX() < minX){minX = system.getGalacticX();}
			if (system.getGalacticY() > maxY){maxY = system.getGalacticY();}
			if (system.getGalacticY() < minY){minY = system.getGalacticY();}
		}
		this.x0 = minX-1;
		this.y0 = minY-1;
		double zoomX = ((double)screen.w)/(maxX-minX+2);
		double zoomY = ((double)screen.h)/(maxY-minY+2);
		this.zoomStep = Math.min(zoomX,zoomY)/((double)minZoomLevel);
		currentZoomLevel = minZoomLevel;
		setZoom(((double)currentZoomLevel)*zoomStep);
		System.out.println("GalacticRender#setDefaultZoom zoom="+currentZoom);
	}
	
	public void resetViewPoint(){
		double minX = 10000;
		double maxX = -10000;
		double minY = 10000;
		double maxY = -10000;
		for (StellarSystem system: galaxy.getSystems()){
			if (system.getGalacticX() > maxX){maxX = system.getGalacticX();}
			if (system.getGalacticX() < minX){minX = system.getGalacticX();}
			if (system.getGalacticY() > maxY){maxY = system.getGalacticY();}
			if (system.getGalacticY() < minY){minY = system.getGalacticY();}
		}
		this.x0 = minX-1;
		this.y0 = minY-1;
		setZoom(((double)currentZoomLevel)*zoomStep);
	}

	public void changeZoom(int mouseI, int mouseJ, int deltaZoom){
		double lastZoomLevel = currentZoomLevel;
		if (deltaZoom < 0){
			currentZoomLevel = Math.max(currentZoomLevel+deltaZoom,minZoomLevel);
		}else{
			currentZoomLevel = Math.min(currentZoomLevel+deltaZoom,maxZoomLevel);
		}
		x0 = x0 + screen.getI(mouseI)/(lastZoomLevel*zoomStep) - screen.getI(mouseI)/(currentZoomLevel*zoomStep);
		y0 = y0 + screen.getJ(mouseJ)/(lastZoomLevel*zoomStep) - screen.getJ(mouseJ)/(currentZoomLevel*zoomStep);
		setZoom(((double)currentZoomLevel)*zoomStep);
	}
	
	public void click(int mouseI, int mouseJ, int button){
		double clickedX = getX(mouseI);
		double clickedY = getY(mouseJ);
		System.out.println("GalacticRender#click IN clickedX="+clickedX+" clickedY="+clickedY);
		StellarSystem selectedSystem = null;
		double matchPrecision = 0.2;
		for (StellarSystem system: galaxy.getSystems()){
			if (Math.abs(system.getGalacticX()-clickedX)<matchPrecision && Math.abs(system.getGalacticY()-clickedY)<matchPrecision){
				System.out.println("GalacticRender#click matched: "+system.getName());
				selectedSystem = system;
			}
		}
		guiState.setSelectedSystem(selectedSystem);
	}
	
}
