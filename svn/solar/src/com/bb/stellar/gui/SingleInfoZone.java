package com.bb.stellar.gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import com.bb.stellar.model.Galaxy;
import com.bb.stellar.model.Place;

public class SingleInfoZone extends GuiZone{

	private Galaxy galaxy;
	private GuiState guiState;

	private final static int LINE_HEIGHT = 12;

	public SingleInfoZone(Galaxy galaxy, GuiState guiState){
		super(200, 220, 750, 210);
		this.guiState = guiState;
		this.galaxy = galaxy;
	}

	public void render(GameContainer gc, Graphics g) throws SlickException {
		if (guiState.getMode() == ViewMode.SYSTEM){
			if (guiState.getSelectedPlace() != null){
				int currentLineJ = 0;
				for (String line: guiState.getSelectedPlace().getDescription()){
					g.drawString(line, i0, j0+currentLineJ);
					currentLineJ+=LINE_HEIGHT;
				}
			}
		}
		g.setColor(Color.red);
		g.drawRect(i0, j0, w, h);
	}

	public void updateMouse(int mouseI, int mouseJ, boolean dragging) {}

	public void drag(int deltaMouseX, int deltaMouseY) {}

	public void click(int mouseX, int mouseY, int button) {


		
	}

}
