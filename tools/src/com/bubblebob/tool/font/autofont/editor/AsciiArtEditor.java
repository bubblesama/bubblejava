package com.bubblebob.tool.font.autofont.editor;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import com.bubblebob.tool.font.autofont.SlickAutoFont;

public class AsciiArtEditor extends BasicGame{

	public int artX0=8;
	public int artY0=8;
	public int artW = 500;
	public int artH = 500;
	public Color artBorderColor = Color.cyan;
	public int artCharW = 50;
	public int artCharH = 10;
	public char[][] artCharMap;
	
	public SlickAutoFont font;
	
	
	
	public int colorGridX0 = 600;
	public int colorGridY0= 100;
	public int colorGridElementW = 30;
	public int colorGridElementH = 30;
	
	
	private char currentChar = '0';
	
	
	
	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		// zone de dessin
		for (int i=0;i<artCharW;i++){
			for (int j=0;j<artCharH;j++){
//				System.out.println("AsciiArtEditor#render font="+font+" artCharMap[i][j]="+artCharMap[i][j]);
				g.drawImage(font.getChar(artCharMap[i][j]),artX0+i*font.getWidth(artCharMap[i][j]),artY0+j*font.getHeight());
			}
		}
		//cadre
//		g.setLineWidth(10);
//		g.setColor(artBorderColor);
//		g.drawRect(artX0, artY0, artW, artH);
		
		// currentChar
		g.drawImage(font.getChar(currentChar),700,10);
		
		
		//color grid
		
		for (int i=0;i<SlickPalette16.table.length;i++){
			g.setColor(SlickPalette16.table[i]);
			g.fillRect(colorGridX0, colorGridY0+i*colorGridElementH, colorGridElementW, colorGridElementH);
		}
		
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		System.out.println("init");
		this.font = new SlickAutoFont();
		this.font.initGlyphMap("assets/ascii.txt", "assets/ascii.png", true);
	}

	@Override
	public void update(GameContainer gc, int lastUpdate) throws SlickException {
		
	}
	
	boolean pressed = false;
	
	public void mouseClicked(int button, int x, int y){
		int i = (x-artX0)/font.getWidth('0');
		int j = (y-artY0)/font.getHeight();
		if (i>=0 && i< artCharW && j>=0 && j< artCharH){
			artCharMap[i][j] = currentChar;
		}
	}
	
	public void mousePressed(int button, int x, int y){
		pressed = true;
	}
	public void mouseReleased(int button, int x, int y){
		pressed = false;
	}
	
	public void mouseDragged(int oldx, int oldy, int x, int y){
//		System.out.println("x="+x+" y="+y);
		if (pressed){
			int i = (x-artX0)/font.getWidth('0');
			int j = (y-artY0)/font.getHeight();
			if (i>=0 && i< artCharW && j>=0 && j< artCharH){
				artCharMap[i][j] = currentChar;
			}
		}
	}
	
	
	public void keyPressed(int keyCode, char keyChar) {
		if (font.getGlyph(keyChar) != null){
			currentChar = keyChar;
		}
	}
	
	public static void main(String[] args) {
		AppGameContainer app;
		try {
			BasicGame game = new AsciiArtEditor();
			app = new AppGameContainer(game);
			app.setIcon("assets/icon-fake.png");
			app.setDisplayMode(800,600, false);
			app.setMinimumLogicUpdateInterval(300);
			app.isVSyncRequested();
			app.setTargetFrameRate(10);
			app.setShowFPS(false);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	
	
	public AsciiArtEditor() {
		super("ASCII art editor");
		this.artCharMap = new char[artCharW][artCharH];
		for (int i=0;i<artCharW;i++){
			for (int j=0;j<artCharH;j++){
				this.artCharMap[i][j] = 33;
			}
		}
	}
}
