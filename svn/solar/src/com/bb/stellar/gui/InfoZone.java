package com.bb.stellar.gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class InfoZone extends GuiZone{

	private GuiState guiState;

	public InfoZone(GuiState guiState){
		super(700, 20, 50, 410);
		this.guiState = guiState;
	}

	public void render(GameContainer gc, Graphics g) throws SlickException {
		g.setColor(Color.red);
		g.drawRect(i0, j0, w, h);

		if (guiState.getMode() == ViewMode.GALAXY){
			if (guiState.getSelectedSystem() != null){
				String infos = "";
				infos += guiState.getSelectedSystem().getName();
				infos += " - "+guiState.getSelectedSystem().getGalacticX()+" "+guiState.getSelectedSystem().getGalacticY();
				g.drawString(infos, i0+5, j0);
			}
		}else{
			if (guiState.getSelectedPlace() != null){
				String infos = "";
				infos += guiState.getSelectedPlace().getName();
				infos += " - "+guiState.getSelectedPlace().getStellarX()+" "+guiState.getSelectedPlace().getStellarY();
				g.drawString(infos, i0+5, j0);
			}
		}

	}

	public void updateMouse(int mouseI, int mouseJ, boolean dragging) {}
	public void drag(int deltaMouseX, int deltaMouseY) {}
	public void click(int mouseX, int mouseY, int button) {}

}
