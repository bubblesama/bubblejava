package com.bb.stellar.gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import com.bb.stellar.model.Galaxy;
import com.bb.stellar.model.Place;
import com.bb.stellar.model.Ship;
import com.bb.stellar.model.ai.SimpleTarget;

public class SystemRender extends ZoneRenderer{

	private Galaxy galaxy;
	private GuiState guiState;

	public SystemRender(Galaxy galaxy, GuiZone screen, GuiState guiState) {
		super(screen);
		this.galaxy = galaxy;
		this.guiState = guiState;
	}

	public void render(GameContainer gc, Graphics g) throws SlickException {
		g.setColor(Color.green);
		for (Place place: guiState.getSelectedSystem().getPlaces()){
			paintPic(g, getImage(place), place.getStellarX(), place.getStellarY());
			if (place == guiState.getSelectedPlace()){
				paintPic(g, MapZone.viewSheet.getSprite(0, 1), place.getStellarX(), place.getStellarY());
			}
		}
		for (Ship ship: galaxy.getShipManager().getShipsBySystem(guiState.getSelectedSystem())){
			paintPic(g, getImage(ship), ship.getStellarX(), ship.getStellarY());
			if (ship == guiState.getSelectedShip()){
				paintPic(g, MapZone.viewSheet.getSprite(0, 2), ship.getStellarX(), ship.getStellarY());
			}
		}
		// nettoyage hors cadre
		g.setColor(Color.black);
		g.fillRect(0, 0, gc.getScreenWidth(), j0);
		g.fillRect(0, j0+h+1, gc.getScreenWidth(), gc.getScreenHeight());
		g.fillRect(0, j0, i0,h+10);
		g.fillRect(i0+w, j0, gc.getScreenWidth(),h);
	}

	private Image getImage(Place p){
		Image result = null;
		switch (p.getType()) {
		case GATE:
			result = MapZone.viewSheet.getSprite(0, 0);
			break;
		case FACTORY:
			result = MapZone.viewSheet.getSprite(1, 0);
			break;
		case ASTEROID:
			result = MapZone.viewSheet.getSprite(1, 1);
			break;
		default:
			break;
		}
		return result;
	}

	private Image getImage(Ship ship){
		Image result = null;
		result = MapZone.viewSheet.getSprite(1, 2);
		return result;
	}



	public void setDefaultZoom(){
		double minX = 10000;
		double maxX = -10000;
		double minY = 10000;
		double maxY = -10000;
		for (Place place:guiState.getSelectedSystem().getPlaces()){
			if (place.getStellarX() > maxX){maxX = place.getStellarX();}
			if (place.getStellarX() < minX){minX = place.getStellarX();}
			if (place.getStellarY() > maxY){maxY = place.getStellarY();}
			if (place.getStellarY() < minY){minY = place.getStellarY();}
		}
		this.x0 = minX-1;
		this.y0 = minY-1;
		double zoomX = ((double)w)/(maxX-minX+2);
		double zoomY = ((double)h)/(maxY-minY+2);
		this.zoomStep = Math.min(zoomX,zoomY)/((double)minZoomLevel);
		currentZoomLevel = minZoomLevel;
		setZoom(((double)currentZoomLevel)*zoomStep);
		System.out.println("SystemRender#setDefaultZoom zoom="+currentZoom);
	}

	public void click(int mouseI, int mouseJ, int button){
		double clickedX = getX(mouseI);
		double clickedY = getY(mouseJ);
		System.out.println("SystemRenderer#click IN clickedX="+clickedX+" clickedY="+clickedY);
		// une place
		Place selectedPlace = null;
		double matchPrecision = 0.2;
		for (Place place: guiState.getSelectedSystem().getPlaces()){
			if (Math.abs(place.getStellarX()-clickedX)<matchPrecision && Math.abs(place.getStellarY()-clickedY)<matchPrecision){
				System.out.println("SystemRender#click matched: "+place.getName());
				selectedPlace = place;
			}
		}
		// click gauche
		if (button == Input.MOUSE_LEFT_BUTTON){
			Ship selectedShip = null;
			for (Ship ship: galaxy.getShipManager().getShipsBySystem(guiState.getSelectedSystem())){
				if (Math.abs(ship.getStellarX()-clickedX)<matchPrecision && Math.abs(ship.getStellarY()-clickedY)<matchPrecision){
					selectedShip = ship;
				}
			}
			guiState.setSelectedShip(selectedShip);
			if (selectedShip == null){
				guiState.setSelectedPlace(selectedPlace);
			}else{
				guiState.setSelectedPlace(null);
			}
		}
		// click droit
		if (button == Input.MOUSE_RIGHT_BUTTON){
			if (guiState.getSelectedShip() != null){
				if (!guiState.isMultiSelectionOn()){
					guiState.getSelectedShip().clearTargets();
					guiState.getSelectedShip().reconsiderPath();
				}
				if (selectedPlace == null){
					guiState.getSelectedShip().addTarget(new SimpleTarget(guiState.getSelectedSystem(), mouseX, mouseY));
				}else{
					guiState.getSelectedShip().addTarget(selectedPlace);
				}
				
			}
		}
	}

}
