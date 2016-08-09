package com.bubblebob.tool.font.autofont;

import org.newdawn.slick.Color;

import com.bubblebob.tool.font.autofont.editor.SlickPalette16;

public class SlickAutoCharacter {
	public char c;
	public Glyph glyph;
	public Color color;
	public Color background; 
	
	public SlickAutoCharacter(SlickAutoFont font, char c){
		this.c = c;
		this.glyph = font.getGlyph(c);
		this.color = SlickPalette16.C_00_BLACK;
		this.background = SlickPalette16.C_15_WHITE;
	}
	
	public SlickAutoCharacter(SlickAutoFont font, char c, Color color,Color background){
		this.c = c;
		this.glyph = font.getGlyph(c);
		this.color = color;
		this.background = background;
	}
}
