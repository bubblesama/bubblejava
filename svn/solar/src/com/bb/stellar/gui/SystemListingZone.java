package com.bb.stellar.gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import com.bb.stellar.model.Galaxy;
import com.bb.stellar.model.Place;

public class SystemListingZone extends GuiZone{

	private Galaxy galaxy;
	private GuiState guiState;

	private final static int LINE_HEIGHT = 12;

	public SystemListingZone(Galaxy galaxy, GuiState guiState){
		super(200, 200, 750, 10);
		this.guiState = guiState;
		this.galaxy = galaxy;
	}

	public void render(GameContainer gc, Graphics g) throws SlickException {
		if (guiState.getMode() == ViewMode.SYSTEM){
			int lineJ = 0;
			for (Place place: guiState.getSelectedSystem().getPlaces()){
				if (guiState.getSelectedPlace() == place){
					g.setColor(Color.white);
				}
				g.drawString(place.getName(), i0, j0+lineJ);
				lineJ+=LINE_HEIGHT;
				g.setColor(Color.red);
			}
		}
		g.setColor(Color.red);
		g.drawRect(i0, j0, w, h);
	}

	public void updateMouse(int mouseI, int mouseJ, boolean dragging) {}

	public void drag(int deltaMouseX, int deltaMouseY) {}

	public void click(int mouseX, int mouseY, int button) {
		int i = getI(mouseX);
		int j = getJ(mouseY);
		int clickedLine = j/LINE_HEIGHT;
		// System.out.println("SystemListingZone#click IN i="+i+" j="+j+" clickedLine="+clickedLine);
		if (guiState.getMode() == ViewMode.SYSTEM){
			if (guiState.getSelectedSystem().getPlaces().size()>clickedLine){
				guiState.setSelectedPlace(guiState.getSelectedSystem().getPlaces().get(clickedLine));
			}
		}
	}

}
