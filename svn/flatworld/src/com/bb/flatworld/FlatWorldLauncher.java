package com.bb.flatworld;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

public class FlatWorldLauncher {

		public static void main(String[] args) {
			AppGameContainer app;
			try {
				app = new AppGameContainer(new FlatWorldGame());
				app.setDisplayMode(400,300,false);
				app.setMinimumLogicUpdateInterval(100);
				app.isVSyncRequested();
				app.setTargetFrameRate(60);
				app.setShowFPS(true);
				app.start();
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}

	
}
