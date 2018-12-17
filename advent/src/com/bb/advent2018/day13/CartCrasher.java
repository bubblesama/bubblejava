package com.bb.advent2018.day13;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class CartCrasher extends BasicGame{

	private static final String INPUT_FILE_NAME = "res/day13-input.txt";
	private static final int SCALE = 4;
	
	private CartMap map;
	
	//game state
	private boolean shouldTick = false;
	
	public CartCrasher(CartMap map) {
		super("advent 2018 - day 13 - cart crasher");
		this.map = map;
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		//
		g.setColor(Color.white);
		for (int i=0;i<map.w;i++) {
			for (int j=0;j<map.h;j++) {
				if (map.getCell(i, j).track != TrackType.NONE) {
					g.fillRect(i*SCALE, j*SCALE, SCALE, SCALE);
				}
			}
		}
		//
		g.setColor(Color.blue);
		for (Cart cart: map.carts) {
			if (cart.isCrashed) {
				g.setColor(Color.red);
			}
			g.fillRect((cart.i-1)*SCALE, (cart.j-1)*SCALE, 3*SCALE, 3*SCALE);
			g.setColor(Color.blue);
		}
		//
		g.setColor(Color.green);
		g.drawString("#"+map.tick+" "+map.getCrashed()+" crashed", 0, 0);
		
	}

	@Override
	public void init(GameContainer gc) throws SlickException {}

	@Override
	public void update(GameContainer gc, int spent) throws SlickException {
		int tickSpeed = 1;
		if (shouldTick) {
			for (int i=0;i<tickSpeed;i++) {
				map.tick();
			}
		}
	}
	
	public static void main(String[] args) {
		AppGameContainer app;
		try {
			CartMap map = CartMapParser.parseInputFile();
			BasicGame aligner = new CartCrasher(map);
			app = new AppGameContainer(aligner);
			app.setIcon("res/icon-fake.png");
			app.setDisplayMode(map.w*SCALE+200, map.h*SCALE, false);
			app.setMinimumLogicUpdateInterval(10);
			app.isVSyncRequested();
			app.setTargetFrameRate(50);
			app.setShowFPS(false);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public void keyPressed(int keyCode, char keyChar) {
		if (keyCode == Input.KEY_SPACE){
			shouldTick = true;
		}
	}
	
	public void keyReleased(int keyCode, char keyChar) {
		if (keyCode == Input.KEY_SPACE){
			shouldTick = false;
		}
	}
	
}
