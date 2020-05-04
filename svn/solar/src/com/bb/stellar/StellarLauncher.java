package com.bb.stellar;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import com.bubblebob.kahu.KahuGame;

public class StellarLauncher {

	public static void main(String[] args) {
		AppGameContainer app;
		try {
			app = new AppGameContainer(new StellarGame());
			app.setDisplayMode(800,600,false);
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
