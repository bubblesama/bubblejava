package com.bb.fyzhique;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

public class FyzhiqueLauncher {

	
	public static void main(String[] args) {
		AppGameContainer app;
		try {
			app = new AppGameContainer(new FyzhiqueGame());
			app.setDisplayMode(1200,600,false);
			app.setMinimumLogicUpdateInterval(1000/60);
			app.isVSyncRequested();
			app.setTargetFrameRate(60);
			app.setShowFPS(true);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}
