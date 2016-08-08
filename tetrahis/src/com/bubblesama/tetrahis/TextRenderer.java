package com.bubblesama.tetrahis;


import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;


public class TextRenderer {

	public static final int SCALE = 2;
	private static SpriteSheet alphabetSheet;
	private static SpriteSheet numberSheet;
	private static boolean init = false;

	public List<TextShow> shows;

	public TextRenderer() {
		this.shows = new ArrayList<TextShow>();
		initSprites();
	}

	
	public void resetShows(){
		shows.clear();
	}
	
	public static void initSprites(){
		if (!init){
			try {
				alphabetSheet = new SpriteSheet("assets/alphabet_white.png", 4, 5);
				numberSheet = new SpriteSheet("assets/numbers_white.png", 4, 5);
			} catch (SlickException e) {
				e.printStackTrace();
			}
			init = true;
		}
	}

	public void renderGame(GameContainer gc, Graphics g){
		renderTexts(g);
	}

	private void renderTexts(Graphics g){
		for (TextShow text: shows){
			if (text.isColored){
				g.setColor(text.color); 	
			}
			int delta = 0;
			for (String line: text.lines){
				for (int i=0;i<line.length();i++){
					g.drawImage(getLetter(line.charAt(i)).getScaledCopy(SCALE*text.zoom), text.x0+i*5*SCALE*text.zoom, text.y0+delta*6*SCALE*text.zoom, null);
				}
				delta++;
			}
		}
	}

	public void renderMenu(GameContainer gc, Graphics g){
		renderTexts(g);
	}

	
	public void render(GameContainer gc, Graphics g){
		renderTexts(g);
	}

	public Image getLetter(char c){
		int index = 26;
		boolean number = false;
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
			number = true;
			index = 0;
			break;
		case '1':
			number = true;
			index = 1;
			break;
		case '2':
			number = true;
			index = 2;
			break;
		case '3':
			number = true;
			index = 3;
			break;
		case '4':
			number = true;
			index = 4;
			break;
		case '5':
			number = true;
			index = 5;
			break;
		case '6':
			number = true;
			index = 6;
			break;
		case '7':
			number = true;
			index = 7;
			break;
		case '8':
			number = true;
			index = 8;
			break;
		case '9':
			number = true;
			index = 9;
			break;
		default:
			index = 26;
			break;
		}
		if (number){
			return numberSheet.getSprite(index, 0);
		}else{
			return alphabetSheet.getSprite(index, 0);
		}
		
	}

}
