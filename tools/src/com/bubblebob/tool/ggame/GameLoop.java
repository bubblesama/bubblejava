package com.bubblebob.tool.ggame;

import com.bubblebob.tool.ggame.gui.FrameGui;

public class GameLoop implements Runnable {

	private Game game;
	
	
	public GameLoop(int frameDelay, FrameGui frame, GameModel model, Game game) {
		super();
		this.frameDelay = frameDelay;
		this.frame = frame;
		this.model = model;
		this.game = game;
		// si pas de modele, pas d'update necessaire dans la boucle
		if(model == null){
			isRunning = false;
		}
	}

	public int frameDelay;
	public FrameGui frame;
	public GameModel model;
	private boolean onPause = false;

	boolean isRunning = true;

	private int fps = 0;
	private long lastTime=System.currentTimeMillis();
	
	
	public void run() {
		long cycleTime = System.currentTimeMillis();
		while(isRunning) {
			if (!onPause){
				model.update();
				frame.repaint();
			}
			cycleTime = cycleTime + frameDelay;
			long difference = cycleTime - System.currentTimeMillis();
			if (difference > 0){
				try {
					Thread.sleep(difference);
				}catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
			if (System.currentTimeMillis()-lastTime>1000){
				game.setTitle(game.getName()+" - "+fps+" fps");
				fps=0;
				lastTime = System.currentTimeMillis();
			}else{
				fps++;
			}

		}
	}
	
	public void pause(){
		onPause = true;
	}
	
	public void resume(){
		onPause = false;
	}
	
}
