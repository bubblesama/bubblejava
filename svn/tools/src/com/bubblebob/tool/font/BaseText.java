package com.bubblebob.tool.font;

import java.awt.Graphics;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BaseText extends AbstractText{
	
	private Graphics graphics;
	private BaseFont baseFont;
	
	//constructeur
	public BaseText(String text, BaseFont baseFont, int x, int y, int width, int height, int interline){
		super(text,baseFont,x,y,width,height,interline);
		this.baseFont = baseFont;
	}

	@Override
	public void doSpecificPaintStuff(char c, int x, int y) {
		graphics.drawImage(baseFont.getChar(c), x, y,null);
	}

	public void setGraphics(Graphics graphics) {
		this.graphics = graphics;
	}
	
	
}
