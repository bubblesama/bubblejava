package com.bubblebob.tool.image;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.SpriteSheetFont;

public class SlickAnimatedPlayer extends BasicGame{

	public SlickAnimatedPlayer(String picsFilePath, int picWidth, int picHeight, int remanence) {
		super("picsy");
		this.remanence = remanence;
		this.picsFilePath = picsFilePath;
		this.picWidth = picWidth;
		this.picHeight = picHeight;
		
		
	}
	
	private String picsFilePath;
	private int picWidth;
	private int picHeight;
	
	private float scale = 1;
	
	private SpriteSheet pics;
	private int remanence;
	private long lastPicChange = 0;
	private int currentPicIndex;
	
	
	
	private SpriteSheetFont font;

	public void render(GameContainer gc, Graphics g) throws SlickException {
		g.setColor(Color.white);
		g.fillRect(0, 0, 1000, 1000);
//		g.drawImage(pics.getSprite(currentPicIndex%pics.getHorizontalCount(),currentPicIndex/pics.getHorizontalCount()).getScaledCopy(scale), 0,0);
//		g.drawString(str, x, y);
		font.drawString(20, 20, "The 0123456789 quick brown fox jumps over the lazy dog".toUpperCase(),Color.red);
		
	}

	public void init(GameContainer gc) throws SlickException {
		try {
			this.pics = new SpriteSheet(picsFilePath, picWidth, picHeight);
			String fontPath = "assets/slick_font.png";
			this.font = new SpriteSheetFont(new SpriteSheet(new Image(fontPath).getScaledCopy(scale), (int)(4*scale), (int)(5*scale)), ' ');
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	public void update(GameContainer gc, int delay) throws SlickException {
		if (System.currentTimeMillis()-lastPicChange>remanence){
			currentPicIndex = (currentPicIndex+1+pics.getHorizontalCount()*pics.getVerticalCount())%(pics.getHorizontalCount()*pics.getVerticalCount());
			lastPicChange = System.currentTimeMillis();
		}
	}
	
	public static void main(String[] args) {
		AppGameContainer app;
		try {
			BasicGame game = new SlickAnimatedPlayer("C:/perso/walker-test.png", 10, 20, 100);
//			BasicGame game = new SlickAnimatedPlayer("C:/Javatools/workspace/VVM/bubblebobtools/walker/assets/walker-V2.png", 10, 20, 100);
			
			app = new AppGameContainer(game);
//			app.setIcon("assets/icon-fake.png");
			app.setDisplayMode(800, 600, false);
			app.setMinimumLogicUpdateInterval(10);
			app.isVSyncRequested();
			app.setTargetFrameRate(60);
			app.setShowFPS(false);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
		
		
	}
	
}
