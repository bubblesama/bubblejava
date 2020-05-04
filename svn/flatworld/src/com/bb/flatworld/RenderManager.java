package com.bb.flatworld;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Graphics;

public class RenderManager {
	
	private List<Renderable> renderables;

	public RenderManager(){
		this.renderables = new ArrayList<Renderable>();
	}
	
	public boolean add(Renderable e) {
		return renderables.add(e);
	}
	
	public void render(Graphics g, RenderingPort port){
		for (Renderable r: renderables){
			r.render(g,port);
		}
	}

}
