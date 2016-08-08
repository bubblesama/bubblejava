package com.bubblebob.tool.font;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class SlickFont implements Font{

	private int scale = 1;
	private int scaledWidth;
	private int scaledHeight;
	
	private SpriteSheet alphabetSheet;
	private SpriteSheet numbersSheet;
	
	private boolean managedNumbers = false;

	public static SlickFont getFont(String alphabetPath, int scale){
		try {
			SpriteSheet pics = new SpriteSheet(alphabetPath,4,5);
			SlickFont result = new SlickFont(pics,scale);
			return result;
		} catch (SlickException e) {e.printStackTrace();}
		return null;
	}
	
	public static SlickFont getFont(String alphabetPath, String numbersPath,  int scale){
		try {
			SpriteSheet alphabetPics = new SpriteSheet(alphabetPath,4,5);
			SpriteSheet numberPics = new SpriteSheet(numbersPath,4,5);
			SlickFont result = new SlickFont(alphabetPics,numberPics,scale);
			return result;
		} catch (SlickException e) {e.printStackTrace();}
		return null;
	}

	private SlickFont(SpriteSheet pics){
		this.alphabetSheet = pics;
		this.scale = 1;
		initDim();
	}
	
	private SlickFont(SpriteSheet pics, int scale){
		this.alphabetSheet = pics;
		this.scale = scale;
		initDim();
	}
	
	private SlickFont(SpriteSheet alphabet, SpriteSheet numbers, int scale){
		this.alphabetSheet = alphabet;
		this.numbersSheet = numbers;
		this.managedNumbers = true;
		this.scale = scale;
		initDim();
	}
	
	private void initDim(){
		this.scaledWidth = alphabetSheet.getSprite(0, 0).getWidth()*scale;
		this.scaledHeight = alphabetSheet.getSprite(0, 0).getHeight()*scale;
	}

	public Image getChar(char c){
		boolean isNumber = false;
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
		case '0':	
			index = 0;
			isNumber = true;
			break;
		case '1':	
			index = 1;
			isNumber = true;
			break;
		case '2':	
			index = 2;
			isNumber = true;
			break;
		case '3':	
			index = 3;
			isNumber = true;
			break;
		case '4':	
			index = 4;
			isNumber = true;
			break;
		case '5':	
			index = 5;
			isNumber = true;
			break;
		case '6':	
			index = 6;
			isNumber = true;
			break;
		case '7':	
			index = 7;
			isNumber = true;
			break;
		case '8':	
			index = 8;
			isNumber = true;
			break;
		case '9':	
			index = 9;
			isNumber = true;
			break;
		default:
			index = 26;
			break;
		}
		if (isNumber){
			if (managedNumbers){
				return this.numbersSheet.getSprite(index, 0).getScaledCopy(this.scale);
			}else{
				// si les nombres ne sont pas geres, un blanc à la place
				index = 26;
			}
		}
		return this.alphabetSheet.getSprite(index, 0).getScaledCopy(this.scale);
	}

	public int getWidth(char c) {
		return this.scaledWidth;
	}

	public int getHeight() {
		return this.scaledHeight;
	}

}
