package com.bubblebob.walker;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class SimplePlayer  extends BasicGame{

	public SimplePlayer() {
		super("player");
	}

	public void render(GameContainer arg0, Graphics arg1) throws SlickException {
		
	}

	public void init(GameContainer arg0) throws SlickException {
		
	}

	public void update(GameContainer arg0, int arg1) throws SlickException {
		
	}

	
	public static void main(String[] args) {
		try {
			int width = 200;
			int height = 80;
			SimplePlayer walker = new SimplePlayer();
			AppGameContainer app = new AppGameContainer(walker);
			app.setDisplayMode(width,height,false);
			int minLogicInterval = 10;
			app.setMinimumLogicUpdateInterval(minLogicInterval);
			app.setTargetFrameRate(61);
			app.setVSync(true);
			app.setShowFPS(false);
			app.setIcon("walker/assets/ico-fake.png");
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
}
