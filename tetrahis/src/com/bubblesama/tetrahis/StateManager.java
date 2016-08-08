package com.bubblesama.tetrahis;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class StateManager extends StateBasedGame {

	protected static final int STATE_MENU = 0;
	protected static final int STATE_GAME = 1;

	public StateManager() {
		super("tetrahis by bb ");
	}

	public static void main(String[] args) {
		AppGameContainer app;
		try {
			StateBasedGame game = new StateManager();
			app = new AppGameContainer(game);
			app.setDisplayMode(800, 600, false);
			app.setMinimumLogicUpdateInterval(10);
			app.isVSyncRequested();
			app.setTargetFrameRate(60);
			app.setShowFPS(false);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		MenuState menuState = new MenuState();
		GameState gameState = new GameState();
		addState(menuState);
		addState(gameState);
	}
}
