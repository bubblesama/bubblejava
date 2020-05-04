package com.bb.fyzhique;


import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Polygon;

public class RenderedPolygon implements Renderable{

	private Polygon polygon;
	
	public RenderedPolygon(){
		float[] points = new float[6];
		points[0] = 10f;
		points[1] = 10f;
		points[2] = 10f;
		points[3] = 50f;
		points[4] = 50f;
		points[5] = 10f;
		polygon = new Polygon(points);
	}
	
	
	public void render(Graphics g) {
		g.setColor(Color.cyan);
		g.fill(polygon);
	}

}
