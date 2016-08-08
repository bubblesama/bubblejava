package com.bubblebob.tool.font;

import org.newdawn.slick.Graphics;

import com.bubblebob.tool.font.autofont.SlickAutoFont;

public class SlickAutoText extends AbstractText{

	private Graphics graphics;
	private SlickAutoFont autoFont;
	
	
	public SlickAutoText(String text, SlickAutoFont font, int x, int y, int width, int height, int interline) {
		super(text, font, x, y, width, height, interline);
		this.autoFont = font;
	}
	
	
	public void doSpecificPaintStuff(char c, int x, int y) {
		graphics.drawImage(autoFont.getChar(c), x, y,null);
	}

	public void setGraphics(Graphics graphics) {
		this.graphics = graphics;
	}
	


}
