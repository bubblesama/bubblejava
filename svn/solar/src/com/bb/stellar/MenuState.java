package com.bb.stellar;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class MenuState extends BasicGameState{
	
	public void init(GameContainer gc, StateBasedGame game) throws SlickException {
		
	}

	public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
		g.drawString("Test",10,10);
	}

	public void update(GameContainer gc, StateBasedGame game, int lastUpdate) throws SlickException {
		
	}

	public int getID() {
		return 2;
	}

}
