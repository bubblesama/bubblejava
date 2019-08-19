package com.bubblebob.tool.font;

import org.newdawn.slick.Graphics;

public class SlickText extends AbstractText{
	
	private Graphics graphics;
	private SlickFont slickFont;
	
	//constructeur
	public SlickText(String text, SlickFont slickFont, int x, int y, int width, int height, int interline){
		super(text,slickFont,x,y,width,height,interline);
		this.slickFont = slickFont;
	}

	@Override
	public void doSpecificPaintStuff(char c, int x, int y) {
		graphics.drawImage(slickFont.getChar(c), x, y);
	}

	public void setGraphics(Graphics graphics) {
		this.graphics = graphics;
	}
	
	
}
