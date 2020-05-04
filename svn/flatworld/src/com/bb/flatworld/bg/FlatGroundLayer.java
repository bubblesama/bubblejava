package com.bb.flatworld.bg;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import com.bb.flatworld.FlatWorldKeys;
import com.bb.flatworld.Renderable;
import com.bb.flatworld.RenderingPort;

public class FlatGroundLayer extends Layer implements Renderable{

	private static Color color = new Color(0,100,0);
	
	
	public FlatGroundLayer(int depth) {
		super(depth);
	}

	public void render(Graphics g, RenderingPort port) {
		g.setColor(color);
		g.fillRect(0, FlatWorldKeys.Z_LEVEL, 4000, 2000);
	}
	
	

}
