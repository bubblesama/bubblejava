package com.bubblebob.kahu;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

public class KahuLauncher {

	public static void main(String[] args) {
		AppGameContainer app;
		try {
			app = new AppGameContainer(new KahuGame());
			app.setDisplayMode(740,580,false);
			app.setMinimumLogicUpdateInterval(1000/60);
			app.isVSyncRequested();
			app.setTargetFrameRate(60);
			app.setShowFPS(false);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}
