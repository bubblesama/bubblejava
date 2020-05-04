package com.bb.stellar;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class StellarGame extends StateBasedGame{

	public StellarGame() {
		super("Stellar");
	}

	public void initStatesList(GameContainer arg0) throws SlickException {
		addState(new SplashState());
		addState(new MenuState());
		addState(new StellarInGameState());
	}

}
