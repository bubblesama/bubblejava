package com.bubblesama.tetrahis;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;

public class TextShow {
	
	public int x0;
	public int y0;
	public List<String> lines;
	public Color color = Color.red;
	public boolean isColored = true;
	public int zoom = 1;
	
	public TextShow(String message){
		this.x0 = 0;
		this.y0 = 0;
		this.lines = new ArrayList<String>();
		lines.add(message);
	}
	
	public TextShow(int x0, int y0, String message){
		this.x0 = x0;
		this.y0 = y0;
		this.lines = new ArrayList<String>();
		lines.add(message);
	}

	public TextShow(int x0, int y0){
		this.x0 = x0;
		this.y0 = y0;
		this.lines = new ArrayList<String>();
	}
	
	public void addLine(String line){
		this.lines.add(line);
	}
	
}
