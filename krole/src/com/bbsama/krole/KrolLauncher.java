package com.bbsama.krole;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.bbsama.krole.splash.SplashState;

public class KrolLauncher extends StateBasedGame{

	public static final int STATE_SPLASH = 0;
	public static final int STATE_MENU = 1;
	public static final int STATE_GAME = 2;
	
	public KrolLauncher() {
		super("krole");
	}

	public void initStatesList(GameContainer arg0) throws SlickException {
		addState(new SplashState());
		addState(new GameState());
	}
	
	public static void main(String[] args) {
		AppGameContainer app;
		try {
			StateBasedGame game = new KrolLauncher();
			app = new AppGameContainer(game);
			app.setIcon("assets/icon-fake.png");
			app.setDisplayMode(1400, 900, false);
			app.setMinimumLogicUpdateInterval(100);
			app.isVSyncRequested();
			app.setTargetFrameRate(60);
			app.setShowFPS(false);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}

	}

}
