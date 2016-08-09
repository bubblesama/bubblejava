package com.bubblebob.tool.font.autofont;

import org.newdawn.slick.Image;

public class Glyph {

	public int w;
	public int h;
	public int i;
	public int j;
	public Image pic;

	public Glyph(char c, int i, int j, boolean monoWidth, Image basePic) {
		this.pic = basePic;
		this.i = i;
		this.j = j;
		this.w = basePic.getWidth();
		this.h = basePic.getHeight();
		//decoupage en largeur
		int minX = w;
		int maxX = 0;
		for (int x=0;x<w;x++){
			for (int y=0;y<h;y++){
				if (basePic.getColor(x, y).getAlpha()== 255){
//					System.out.println("Glyph#new some white");
					minX = Math.min(minX, x);
					maxX = Math.max(maxX, x);
				}
			}
		}
//		System.out.println("Glyph#new char="+c+" maxX-minX="+(maxX-minX)+" minX="+minX+" maxX="+maxX);
		
		if (!monoWidth){
			this.w = Math.min(Math.max(maxX-minX, 1)+1,w);
			minX = Math.min(w-1, minX);
			this.pic = basePic.getSubImage(minX, 0, w, h);
		}
 	}


}
