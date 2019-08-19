package com.bubblebob.rogue;

import java.awt.Graphics;

import com.bubblebob.tool.ggame.GameModel;
import com.bubblebob.tool.ggame.gui.GraphicalModel;
import com.bubblebob.tool.image.MonoSizedBundledImage;

public class RogueMap implements GameModel,GraphicalModel{
	
	public RogueMap(int width, int height) {
		super();
		this.width = width;
		this.height = height;
		this.walls = new boolean[width][height];
	}

	
	static {
		tileSet = new MonoSizedBundledImage(2, 1, "rogue/assets/tiles.png").zoom(Constants.ZOOM);
		oryxCharSet = new MonoSizedBundledImage(16, 31, "rogue/assets/sprites.png").zoom(Constants.ZOOM);
		characters = new MonoSizedBundledImage(1,2,"rogue/assets/characters.png").zoom(Constants.ZOOM);
	}
	
	public boolean[][] walls;
	
	public int width;
	public int height;
	
	public int screenTopLeftI = 0;
	public int screenTopLeftJ = 0;
	public int screenWidth = 42;
	public int screenHeight = 32;
	public int screenScale = Constants.SCALE;
	public int screenPreview = 2;
	
	public int playerI = 1;
	public int playerJ = 1;
	public int playerDI = 0;
	public int playerDJ = 0;
	public boolean playerFaceLeft;
	
	public int playerMoveTick = 0;
	public static int PLAYER_TICKS_TO_MOVE = 3;
	
	
	public void update() {
		updatePlayer();
		//center
		if (playerI-screenPreview<screenTopLeftI){
			screenTopLeftI = playerI-screenPreview;
		}else if (playerI+screenPreview>screenTopLeftI+screenWidth){
			screenTopLeftI = playerI+screenPreview-screenWidth;
		}
		if (playerJ-screenPreview<screenTopLeftJ){
			screenTopLeftJ = playerJ-screenPreview;
		}else if (playerJ+screenPreview>screenTopLeftJ+screenHeight){
			screenTopLeftJ = playerJ+screenPreview-screenHeight;
		}
		
	}

	public void updatePlayer(){
		playerMoveTick++;
		if (playerMoveTick >= PLAYER_TICKS_TO_MOVE){
			playerMoveTick = 0;
			int newPlayerI = playerI + playerDI;
			int newPlayerJ = playerJ + playerDJ;
			if (newPlayerI<0){
				newPlayerI = 0;
			}else if (newPlayerI >= width){
				newPlayerI = width -1;
			}
			if (newPlayerJ<0){
				newPlayerJ = 0;
			}else if (newPlayerJ >= height){
				newPlayerJ = height-1;
			}
			if (walls[newPlayerI][newPlayerJ]){
//				playerDI = 0;
//				playerDJ = 0;
			}else{
				playerI = newPlayerI;
				playerJ = newPlayerJ;
			}
			if (playerDI < 0){
				playerFaceLeft = true;
			}else if (playerDI > 0){
				playerFaceLeft = false;
			}
		}
	}
	
	
	public void paint(Graphics g) {
		paintMap(g);
		paintPlayer(g);
	}

	public int getWidth() {
		return screenWidth*screenScale+6;
	}
	public int getHeight() {
		return screenHeight*screenScale+8;
	}
	
	private void paintMap(Graphics g){
		for (int i=0;i<screenWidth;i++){
			if (0 <= i+screenTopLeftI && i+screenTopLeftI < width){
				for (int j=0;j<screenHeight;j++){
					if (0 <= j+screenTopLeftJ && j+screenTopLeftJ < height){
						g.drawImage(tileSet.getImage(walls[i+screenTopLeftI][j+screenTopLeftJ]?0:1,0),i*screenScale, j*screenScale,null);
					}
				}
			}
		}
	}
	
	private void paintPlayer(Graphics g){
			g.drawImage(oryxCharSet.getImage(1,0), (playerI-screenTopLeftI)*screenScale+3, (playerJ-screenTopLeftJ)*screenScale+3, null);
	}
	
	public void setWall(int i, int j, boolean wall){
		walls[i][j] = wall;
	}
	
	private static MonoSizedBundledImage tileSet;// = new MonoSizedBundledImage(2, 1, "rogue/assets/tiles.png").zoom(Constants.ZOOM);
	private static MonoSizedBundledImage oryxCharSet;// = new MonoSizedBundledImage(16, 31, "rogue/assets/sprites.png").zoom(Constants.ZOOM);
	private static MonoSizedBundledImage characters;// = new MonoSizedBundledImage(1,2,"rogue/assets/characters.png").zoom(Constants.ZOOM);
	
	
}
