package com.bubblesama.tetrahis;

import org.newdawn.slick.Color;

public class Palette {

	public final static Color COLOR_03_DARK_BLUE = new Color(48, 52, 109);
	public final static Color COLOR_04_DARK_GRAY = new Color(78, 74, 78);
	public final static Color COLOR_07_DARK_PINK = new Color(208, 70, 72);
	public final static Color COLOR_08_MEDIUM_GRAY = new Color(117, 113, 97);
	public final static Color COLOR_09_LIGHT_BLUE = new Color(89, 125, 206);
	public final static Color COLOR_10_LIGHT_ORANGE = new Color(210, 125, 44);
	public final static Color COLOR_11_LIGHT_GRAY = new Color(133, 149, 161);
	public final static Color COLOR_12_LIGHT_GREEN= new Color(109, 170, 44);
	public final static Color COLOR_13_LIGHT_PINK= new Color(210, 170, 153);
	public final static Color COLOR_14_LIGHT_CYAN= new Color(109, 194, 202);
	public final static Color COLOR_15_LIGHT_LEMON= new Color(218, 212, 94);
	public final static Color COLOR_16_WHITEY = new Color(222, 238, 214);



	public static Color getColor(int id){
		switch (id) {
		case 3:
			return  COLOR_03_DARK_BLUE;
		case 4:
			return  COLOR_04_DARK_GRAY;
		case 7:
			return  COLOR_07_DARK_PINK;
		case 8:
			return  COLOR_08_MEDIUM_GRAY;
		case 9:
			return  COLOR_09_LIGHT_BLUE;
		case 10:
			return  COLOR_10_LIGHT_ORANGE;
		case 11:
			return  COLOR_11_LIGHT_GRAY;
		case 12:
			return  COLOR_12_LIGHT_GREEN;
		case 13:
			return  COLOR_13_LIGHT_PINK;
		case 14:
			return  COLOR_14_LIGHT_CYAN;
		case 15:
			return  COLOR_15_LIGHT_LEMON;
		case 16:
			return  COLOR_16_WHITEY;
		default:
			return null;
		}
	}



}
