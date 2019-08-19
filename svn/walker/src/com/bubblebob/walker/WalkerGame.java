package com.bubblebob.walker;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class WalkerGame extends BasicGame{
	
	// gestion de la vitesse de marche
	private long nanoLastStep = 0; // dernier update
	private long milliStep = 40; // periode update logic
	private static long million = 1000000; // un million
	// touche d'acceleration
	private boolean speedUpPressed = false;
	private ViewPort viewPort;
	
	private GameContent content;
	// gestion des entrees
	private Input input;

	public WalkerGame() {
		super(">walker");
		this.nanoLastStep = System.nanoTime();
		this.content = new GameContent();
		boolean[][] map = SquareMapBitmapFactory.getMap("walker/assets/map/test.png");
		int widthSize = 8;
		int heightSize = 22;
		for (int j=0;j<map.length;j++){
			for (int i=0;i<map[0].length;i++){
				if (map[i][j]){
					content.addBonk(new Bonk(i*widthSize,j*heightSize,widthSize,heightSize));
				}
			}
		}
		this.viewPort = new ViewPort();
	}

	public void render(GameContainer gameContainer, Graphics g) throws SlickException {
		viewPort.render(gameContainer,g,content);
	}

	public void init(GameContainer container) throws SlickException {
		WalkerEntity.graphInit();
		this.input = container.getInput();
	}

	public void update(GameContainer gameContainer, int arg1) throws SlickException {
		// controle du temps ecoule pour l'update
		long nanoNow = System.nanoTime();
		long milliDelta = (nanoNow-nanoLastStep)/million;
		if (milliDelta > milliStep){
			nanoLastStep = nanoNow;
			//<marche>
			if (input.isKeyDown(Input.KEY_Q)){
				content.getWalker().turnLeft();
			}else if (input.isKeyDown(Input.KEY_D)){
				content.getWalker().turnRight();
			}
			if (input.isKeyDown(Input.KEY_Z)){
				content.getWalker().turnUp();
			}else if (input.isKeyDown(Input.KEY_S)){
				content.getWalker().turnDown();
			}
			if (input.isKeyDown(Input.KEY_LSHIFT)){
				if (!speedUpPressed){
					if (milliStep == 60){
						milliStep = 80;
					}else{
						milliStep = 60;
					}
					speedUpPressed = true;
				}
			}else{
				speedUpPressed = false;
			}
			//</marche>
			//<vue>
			viewPort.update(content);
			//</vue>
			
			// objets
			content.getWalker().update(content);
			
		}
	}

	public static void main(String[] args) {
		int width = ViewPort.VIEW_W*ViewPort.ZOOM;
		int height = ViewPort.VIEW_H*ViewPort.ZOOM;
		try {
			WalkerGame walker = new WalkerGame();
			AppGameContainer app = new AppGameContainer(walker);
			app.setDisplayMode(width,height,false);
			int minLogicInterval = 10;
			app.setMinimumLogicUpdateInterval(minLogicInterval);
			//app.setMaximumLogicUpdateInterval(minLogicInterval);
			app.setTargetFrameRate(61);
			app.setVSync(true);
			app.setShowFPS(false);
			app.setIcon("walker/assets/ico-fake.png");
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}

	}

}
