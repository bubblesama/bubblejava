package com.bubblebob.tool.font;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class SlickFont implements Font{

	private SpriteSheet pics;

	public static SlickFont getFont(String picsPath){
		try {
			SpriteSheet pics = new SpriteSheet(picsPath,4,5);
			SlickFont result = new SlickFont(pics);
			return result;
		} catch (SlickException e) {e.printStackTrace();}
		return null;
	}

	private SlickFont(SpriteSheet pics){
		this.pics = pics;
	}

	public Image getChar(char c){
		int index = 26;
		switch (c) {
		case 'a':
		case 'A':	
			index = 0;
			break;
		case 'b':
		case 'B':	
			index = 1;
			break;
		case 'c':
		case 'C':	
			index = 2;
			break;
		case 'd':
		case 'D':	
			index = 3;
			break;
		case 'e':
		case 'E':	
			index = 4;
			break;
		case 'f':
		case 'F':	
			index = 5;
			break;
		case 'g':
		case 'G':	
			index = 6;
			break;
		case 'h':
		case 'H':	
			index = 7;
			break;
		case 'i':
		case 'I':	
			index = 8;
			break;
		case 'j':
		case 'J':	
			index = 9;
			break;
		case 'k':
		case 'K':	
			index = 10;
			break;
		case 'l':
		case 'L':	
			index = 11;
			break;
		case 'm':
		case 'M':	
			index = 12;
			break;
		case 'n':
		case 'N':	
			index = 13;
			break;
		case 'o':
		case 'O':	
			index = 14;
			break;
		case 'p':
		case 'P':	
			index = 15;
			break;
		case 'q':
		case 'Q':	
			index = 16;
			break;
		case 'r':
		case 'R':	
			index = 17;
			break;
		case 's':
		case 'S':	
			index = 18;
			break;
		case 't':
		case 'T':
			index = 19;
			break;
		case 'u':
		case 'U':	
			index = 20;
			break;
		case 'v':
		case 'V':	
			index = 21;
			break;
		case 'w':
		case 'W':	
			index = 22;
			break;
		case 'x':
		case 'X':	
			index = 23;
			break;
		case 'y':
		case 'Y':	
			index = 24;
			break;
		case 'z':
		case 'Z':	
			index = 25;
			break;
		default:
			index = 26;
			break;
		}
		return pics.getSprite(index, 0);
	}

	public int getWidth() {
		return pics.getWidth();
	}

	public int getHeight() {
		return pics.getHeight();
	}

}
