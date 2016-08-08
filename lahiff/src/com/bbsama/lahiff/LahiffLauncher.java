package com.bbsama.lahiff;

import org.lwjgl.Sys;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import com.bbsama.krole.model.Cell;
import com.bbsama.krole.model.MobManager;
import com.bbsama.krole.model.WorldManager;
import com.bbsama.krole.view.GameRenderer;

public class LahiffLauncher extends BasicGame{

		public LahiffLauncher() {
			super("lahiff");
			this.grid = new int[w][h];
			this.grid[2][2] = LIFE;
		}

		private static final int DEATH = 0;
		private static final int LIFE = 1;
		
		
		private int w = 100;
		private int h = 100;
		
		private int[][] grid;
		
		
		private long lastUpdate = System.currentTimeMillis();
		private static long UPDATE_PERIOD = 50;
		private boolean paused = false;
		
		
		private static final int ZOOM = 10;
		
		public static void main(String[] args) {
			AppGameContainer app;
			try {
				BasicGame game = new LahiffLauncher();
				app = new AppGameContainer(game);
				app.setIcon("assets/icon-fake.png");
				app.setDisplayMode(1200, 1200, false);
				app.setMinimumLogicUpdateInterval(10);
				app.isVSyncRequested();
				app.setTargetFrameRate(60);
				app.setShowFPS(false);
				app.start();
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
		
		public void render(GameContainer gc, Graphics g) throws SlickException {
			g.setColor(Color.gray);
			g.fillRect(0, 0, 10000, 10000);
			g.setColor(Color.white);
			g.fillRect(0, 0, w*ZOOM, h*ZOOM);
			g.setColor(Color.red);
			for (int i=0;i<w;i++){
				for (int j=0;j<h;j++){
					if (grid[i][j] == LIFE){
						g.fillRect(i*ZOOM, j*ZOOM, ZOOM, ZOOM);
					}
				}
			}
		}

		public void init(GameContainer gc) throws SlickException {
			
		}

		public void update(GameContainer gc, int delay) throws SlickException {
			if (System.currentTimeMillis()-lastUpdate> UPDATE_PERIOD){
				if (!paused){
					int[][] newGrid = new int[w][h];
					for (int i=0;i<w;i++){
						for (int j=0;j<h;j++){
							int life = getNeighbourLife(i, j);
							if (grid[i][j] == LIFE){
								if (life == 2 || life == 3){
									newGrid[i][j] = LIFE;
								}else{
									newGrid[i][j] = DEATH;
								}
							}else{//DEATH
								if (life == 3){
									newGrid[i][j] = LIFE;
								}else{
									newGrid[i][j] = DEATH;
								}
							}
						}
					}
					this.grid = newGrid;
					//maj lastUpdate
					lastUpdate = System.currentTimeMillis();
				}
			}
			
		}
		
		private int getNeighbourLife(int i, int j){
			int result = 0;
			for (int m=-1;m<2;m++){
				for (int n=-1;n<2;n++){
					if (m!=0 || n!=0){
						if (grid[(i+m+w)%w][(j+n+h)%h] == LIFE){
							result++;
						}
					}
				}
			}
			return result;
		}

		public void mouseClicked(int button, int x, int y, int clickCount){
			int i = x/ZOOM;
			int j = y/ZOOM;
			if (i>=0&&i<w&&j>=0&&j<h){
				grid[i][j] = (grid[i][j]+3)%2;
			}
			
		}
		
		public void keyPressed(int keyCode, char keyChar) {
			
			
			if (keyCode == Input.KEY_SPACE){
				paused = !paused;
			}
			if (keyCode == Input.KEY_DELETE){
				for (int i=0;i<w;i++){
					for (int j=0;j<h;j++){
						grid[i][j] = DEATH;
					}
				}
			}
		}

}
