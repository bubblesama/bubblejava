package com.bubblebob.tool.font;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;


public class SlickTest extends BasicGame{

	public SlickTest() {
		super("SlickTextTest");
	}
	private SlickText text;
	

	public void render(GameContainer gc, Graphics g) throws SlickException {
		g.setColor(Color.blue);
		text.setGraphics(g);
		text.paint();
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		text = new SlickText("Heureux qui comme Ulysse a fait un beau voyage, ou comme cestui la qui conquit la Toison", SlickFont.getFont("assets/alphabet_white.png",2), 12, 3, 30,5, 1);
	}

	@Override
	public void update(GameContainer gc, int lastUpdate) throws SlickException {
		
	}

	public static void main(String[] args) {
		AppGameContainer app;
		try {
			app = new AppGameContainer(new SlickTest());
			app.setDisplayMode(800,600,false);
			app.setMinimumLogicUpdateInterval(1000/60);
			app.isVSyncRequested();
			app.setTargetFrameRate(60);
			app.setShowFPS(false);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
}
