package com.bubblebob.tool.font;

import org.newdawn.slick.Graphics;

public class SlickText extends AbstractText{
	
	private Graphics graphics;
	private SlickFont slickFont;
	
	//constructeur
	/**
	 * 
	 * @param text: texte
	 * @param slickFont: police choisie
	 * @param x: coordonness sur l'ecran (px)
	 * @param y:coordonness sur l'ecran (px)
	 * @param width: largeur (caracteres)
	 * @param height: hauteur (caracteres)
	 * @param interline: interligne (px)
	 */
	public SlickText(String text, SlickFont slickFont, int x, int y, int width, int height, int interline){
		super(text,slickFont,x,y,width,height,interline);
		this.slickFont = slickFont;
	}

	public void doSpecificPaintStuff(char c, int x, int y) {
		graphics.drawImage(slickFont.getChar(c), x, y,null);
	}

	public void setGraphics(Graphics graphics) {
		this.graphics = graphics;
	}
	
}
