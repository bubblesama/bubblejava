package com.bb.solar;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class SolarGame extends BasicGame{
	
	private SolarModel model;
	private SpaceView view;
	
	public SolarGame() {
		super(">solar");
		this.model = new SolarModel();
		this.view = new SpaceView(100,50,0,0,10);
		initModel();
	}

	private void initModel(){
		model.add(new SolarBody(50, 100, 100, 100, 0, 0));
	}
	
	public void render(GameContainer container, Graphics g) throws SlickException {
		model.render(view,g);
	}

	public void init(GameContainer container) throws SlickException {
	}

	public void update(GameContainer container, int delta) throws SlickException {
		model.updateGravityField();
		model.updatePlaces();
	}
	
}
