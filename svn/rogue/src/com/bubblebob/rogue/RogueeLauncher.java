package com.bubblebob.rogue;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

public class RogueeLauncher {

	public static void main(String[] args) {
		AppGameContainer app;
		try {
			app = new AppGameContainer(new RogueGameState());
			app.setDisplayMode(800, 600, false);
			MapGen gen = new MapGen(8,4,53,40,10,70,50);
			Floor floor = gen.getFloor();
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
