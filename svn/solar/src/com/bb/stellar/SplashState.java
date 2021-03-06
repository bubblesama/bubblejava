package com.bb.stellar;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class SplashState extends BasicGameState{

	private long startTime = -1;
	private static final long splashTime = (long)(1000*0.5);

	public void init(GameContainer gc, StateBasedGame game) throws SlickException {}

	public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
		g.drawString("Splash",10,10);
	}

	public void update(GameContainer gc, StateBasedGame game, int lastUpdate) throws SlickException {
		long currentTime = System.currentTimeMillis();
		if (startTime == -1){
			startTime = currentTime;
		}else{
			if (currentTime-startTime > splashTime){
				game.enterState(3);
			}
		}
	}

	public int getID() {
		return 1;
	}
	
}
