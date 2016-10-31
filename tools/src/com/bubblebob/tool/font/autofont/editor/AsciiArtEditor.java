package com.bubblebob.tool.font.autofont.editor;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import com.bubblebob.tool.font.autofont.SlickAutoCharacter;
import com.bubblebob.tool.font.autofont.SlickAutoFont;

public class AsciiArtEditor extends BasicGame{

	public int artX0=8;
	public int artY0=8;
	public int artW = 500;
	public int artH = 500;
	public Color artBorderColor = Color.cyan;
	public int artCharW = 50;
	public int artCharH = 10;
	public SlickAutoCharacter[][] artCharMap;
	
	public SlickAutoFont font;
	
	public int colorGridX0 = 600;
	public int colorGridY0= 100;
	public int colorGridElementW = 30;
	public int colorGridElementH = 30;
	
	
	private char currentChar = 'A';
	private Color currentColor = SlickPalette16.C_03_OLIVE;
	private Color currentBackgroundColor = SlickPalette16.C_05_PURPLE;

	private SlickAutoCharacter currentAutoCharacter;
	
	
	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		// zone de dessin
		for (int i=0;i<artCharW;i++){
			for (int j=0;j<artCharH;j++){
//				System.out.println("AsciiArtEditor#render font="+font+" artCharMap[i][j]="+artCharMap[i][j]);
				g.setColor(artCharMap[i][j].background);
				g.fillRect(artX0+i*artCharMap[i][j].glyph.w,artY0+j*font.getHeight(), artCharMap[i][j].glyph.w, font.getHeight());
				g.drawImage(artCharMap[i][j].glyph.pic,artX0+i*artCharMap[i][j].glyph.w,artY0+j*font.getHeight(),artCharMap[i][j].color);
			}
		}
		//cadre
//		g.setLineWidth(10);
//		g.setColor(artBorderColor);
//		g.drawRect(artX0, artY0, artW, artH);
		
		// currentChar
		g.setColor(currentAutoCharacter.background);
		g.fillRect(700,10, currentAutoCharacter.glyph.w, font.getHeight());
		g.drawImage(currentAutoCharacter.glyph.pic,700,10,currentAutoCharacter.color);
		
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
//		this.font.initGlyphMap("assets/cp437.txt", "assets/cp437.png", true);
		this.currentAutoCharacter = new SlickAutoCharacter(font, currentChar, currentColor, currentBackgroundColor);
		for (int i=0;i<artCharW;i++){
			for (int j=0;j<artCharH;j++){
				this.artCharMap[i][j] = new SlickAutoCharacter(font, currentChar, currentColor, currentBackgroundColor);
			}
		}
	}

	@Override
	public void update(GameContainer gc, int lastUpdate) throws SlickException {
		
	}
	
	boolean leftButtonPressed = false;
	boolean rightButtonPressed = false;
	
	public void mouseClicked(int button, int x, int y){
		int i = (x-artX0)/font.getWidth('0');
		int j = (y-artY0)/font.getHeight();
		// zone de dessin
		if (i>=0 && i< artCharW && j>=0 && j< artCharH){
			artCharMap[i][j] = new SlickAutoCharacter(font, currentChar);
		}
	}
	
	public void mousePressed(int button, int x, int y){
		if (button == Input.MOUSE_LEFT_BUTTON){
			leftButtonPressed = true;
		}
		if (button == Input.MOUSE_RIGHT_BUTTON){
			rightButtonPressed = true;
		}
		// zone de palette
		if (x-colorGridX0>0 && x-colorGridX0 < colorGridElementW && y-colorGridY0>0 && y-colorGridY0 < SlickPalette16.table.length*colorGridElementH){
			System.out.println("AsciiArtEditor#mousePressed IN colorGrid");
			int colorIndex = (y-colorGridY0)/colorGridElementH;
			if (leftButtonPressed){
				currentColor = SlickPalette16.table[colorIndex];
			}
			if (rightButtonPressed){
				currentBackgroundColor = SlickPalette16.table[colorIndex];
			}
			currentAutoCharacter = new SlickAutoCharacter(font, currentChar, currentColor, currentBackgroundColor);
		}
	}
	
	
	public void mouseReleased(int button, int x, int y){
		leftButtonPressed = false;
		rightButtonPressed = false;
	}
	
	public void mouseDragged(int oldx, int oldy, int x, int y){
//		System.out.println("x="+x+" y="+y);
		if (leftButtonPressed){
			int i = (x-artX0)/font.getWidth('A');
			int j = (y-artY0)/font.getHeight();
			if (i>=0 && i< artCharW && j>=0 && j< artCharH){
				artCharMap[i][j] = new SlickAutoCharacter(font, currentChar,currentColor,currentBackgroundColor);
			}
		}
	}
	
	
	public void keyPressed(int keyCode, char keyChar) {
		if (font.getGlyph(keyChar) != null){
			currentChar = keyChar;
			currentAutoCharacter = new SlickAutoCharacter(font, currentChar, currentColor, currentBackgroundColor);
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
		this.artCharMap = new SlickAutoCharacter[artCharW][artCharH];
	}
}
