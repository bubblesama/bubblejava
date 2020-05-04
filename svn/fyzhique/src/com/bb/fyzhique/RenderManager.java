package com.bb.fyzhique;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Graphics;

public class RenderManager {
	
	public static final float SCALE = 4.0f;
	
	private List<Renderable> renderables;

	public RenderManager(){
		this.renderables = new ArrayList<Renderable>();
	}
	
	public boolean add(Renderable e) {
		return renderables.add(e);
	}
	
	public void render(Graphics g){
		for (Renderable r: renderables){
			r.render(g);
		}
	}

	public boolean remove(Renderable r) {
		return renderables.remove(r);
	}
	
}
