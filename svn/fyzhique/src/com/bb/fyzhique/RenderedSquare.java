package com.bb.fyzhique;

import org.jbox2d.dynamics.Body;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class RenderedSquare implements Renderable{
	
	private Color color;
	private Body body;
	private float w;
	private float h;

	public RenderedSquare(Color c, Body body, float w, float h) {
		super();
		this.body = body;
		this.w = w;
		this.h = h;
		this.color = c;
	}
	
	public void render(Graphics g) {
		int i = (int)(body.getPosition().x * RenderManager.SCALE);
		int j = (int)(body.getPosition().y * RenderManager.SCALE);
		g.setColor(color);
		g.fillRect(i-w*RenderManager.SCALE,-j-h*RenderManager.SCALE,2*w*RenderManager.SCALE,2*h*RenderManager.SCALE);
	}
	
}
