package com.bubblebob.tool.font.autofont.editor;

import org.newdawn.slick.Color;

public class SlickPalette16 {
	
	public static final Color C_00_BLACK = new Color(0,0,0);
	public static final Color C_01_MAROON = new Color(128,0,0);
	public static final Color C_02_GREEN =  new Color(0,128,0);
	public static final Color C_03_OLIVE =  new Color(128,128,0);
	public static final Color C_04_NAVY=  new Color(0,0,128);
	public static final Color C_05_PURPLE=  new Color(128,0,128);
	public static final Color C_06_TEAL=  new Color(0,128,128);
	public static final Color C_07_SILVER=  new Color(192,192,192);
	public static final Color C_08_GRAY=  new Color(128,128,128);
	public static final Color C_09_RED=  new Color(255,0,0);
	public static final Color C_10_LIME=  new Color(0,255,0);
	public static final Color C_11_YELLOW=  new Color(255,255,0);
	public static final Color C_12_BLUE=  new Color(0,0,255);
	public static final Color C_13_FUSCHIA=  new Color(255,0,255);
	public static final Color C_14_AQUA=  new Color(0,255,255);
	public static final Color C_15_WHITE=  new Color(255,255,255);
	
	public static Color[] table = {C_00_BLACK,C_01_MAROON,C_02_GREEN,C_03_OLIVE,C_04_NAVY,C_05_PURPLE,C_06_TEAL,C_07_SILVER,C_08_GRAY,C_09_RED,C_10_LIME,C_11_YELLOW,C_12_BLUE,C_13_FUSCHIA,C_14_AQUA,C_15_WHITE};
	public static String[] names = {"black","maroon","green","olive","navy","purple","teal","silver","gray","red","lime","yellow","blue","fuschia","aqua","white"};
	
	
	
	public static Color byIndex(int i){
		if (i>=0 && i<16){
			return table[i];
		}else{
			return null;
		}
	}
	
	public static String name(int i){
		if (i>=0 && i<16){
			return names[i];
		}else{
			return null;
		}
	}
}
