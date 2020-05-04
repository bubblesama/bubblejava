package com.bb.fyzhique;

import org.jbox2d.dynamics.Body;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class RenderedCircle implements Renderable{
	
	private Color color;
	private Body body;
	private float r;

	public RenderedCircle(Color c, Body body, float r) {
		super();
		this.body = body;
		this.color = c;
		this.r = r;
	}
	
	public void render(Graphics g) {
		int i = (int)(body.getPosition().x * RenderManager.SCALE);
		int j = (int)(body.getPosition().y * RenderManager.SCALE);
		g.setColor(color);
		g.fillOval(i-r*RenderManager.SCALE, -j-r*RenderManager.SCALE, 2*r*RenderManager.SCALE, 2*r*RenderManager.SCALE);
	}
	
}
