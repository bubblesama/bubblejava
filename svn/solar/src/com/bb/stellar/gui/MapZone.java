package com.bb.stellar.gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import com.bb.stellar.model.Galaxy;

public class MapZone extends GuiZone{

	private Galaxy galaxy;
	
	private GalacticRender galacticRender;
	private SystemRender systemRender;
	private GuiState guiState;

	private final static String viewSheetpath = "solar/assets/model.png";
	public static SpriteSheet viewSheet;
	public final static int viewPicWidth = 10;
	public final static int viewPicHeight = 10;

	public void graphInit(){
		try {
			viewSheet = new SpriteSheet(viewSheetpath, viewPicWidth, viewPicHeight);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	public MapZone(Galaxy galaxy, GuiState guiState){
		super(700, 400, 50, 10);
		this.galaxy = galaxy;
		this.galacticRender = new GalacticRender(this.galaxy, this, guiState);
		this.galacticRender.setDefaultZoom();
		this.systemRender = new SystemRender(this.galaxy,this,guiState);
		this.guiState = guiState;
	}
	
	public void render(GameContainer gc, Graphics g) throws SlickException {
		if (guiState.getMode() == ViewMode.GALAXY){
			galacticRender.render(gc, g);
		}else if(guiState.getMode() == ViewMode.SYSTEM){
			systemRender.render(gc, g);
		}
		g.setColor(Color.red);
		g.drawRect(i0, j0, w, h);
	}

	public void increaseZoom(int mouseI, int mouseJ){
		if (guiState.getMode() == ViewMode.GALAXY){
			galacticRender.increaseZoom(mouseI,mouseJ);
		}else{
			systemRender.increaseZoom(mouseI,mouseJ);
		}
	}
	
	public void decreaseZoom(int mouseI, int mouseJ){
		if (guiState.getMode() == ViewMode.GALAXY){
			galacticRender.decreaseZoom(mouseI,mouseJ);
		}else{
			systemRender.decreaseZoom(mouseI,mouseJ);
		}
	}
	
	public void updateMouse(int mouseI, int mouseJ, boolean dragging){
		if (guiState.getMode() == ViewMode.GALAXY){
			galacticRender.updateMouse(mouseI,mouseJ,dragging);
		}else{
			systemRender.updateMouse(mouseI,mouseJ,dragging);
		}
	}
	
	public void drag(int deltaMouseX, int deltaMouseY){
		if (guiState.getMode() == ViewMode.GALAXY){
			galacticRender.drag(deltaMouseX,deltaMouseY);
		}else{
			systemRender.drag(deltaMouseX,deltaMouseY);
		}
	}
	
	public void click(int mouseX, int mouseY, int button){
		if (guiState.getMode() == ViewMode.GALAXY){
			galacticRender.click(mouseX,mouseY,button);
		}else{
			systemRender.click(mouseX, mouseY,button);
		}
	}
	
	public void toggleViewMode(){
		guiState.toggleViewMode();
		if (guiState.getMode() == ViewMode.SYSTEM){
			systemRender.setDefaultZoom();
		}
	}
	
}
