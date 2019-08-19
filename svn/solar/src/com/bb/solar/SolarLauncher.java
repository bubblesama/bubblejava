package com.bb.solar;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

public class SolarLauncher {

	public static void main(String[] args) {
		AppGameContainer app;
		try {
			app = new AppGameContainer(new SolarGame());
			app.setDisplayMode(200, 200, false);
			app.setMinimumLogicUpdateInterval(100);
			app.isVSyncRequested();
			app.setTargetFrameRate(60);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

}
