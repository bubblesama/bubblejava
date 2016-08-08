package com.bubblebob.tool.phildefair;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.ejml.simple.SimpleMatrix;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Scene extends BasicGame{

	public Triplet pov;
	
	public Triplet screenO;
	// screenI et screenJ definissent la largeur et hauteur de l'ecree
	public Triplet screenI;
	public Triplet screenJ;

	// l'equation du plan
	public double a;
	public double b;
	public double c;
	public double d;
	
	public static int iRes=600;
	public static int jRes=480;

	public Map<String,Triplet> points;
	public Map<String,ProjectedPoint> projections;
	
	public List<NamedLine> lines;
	
	public Scene(){
		super("phildefair");
		this.a = 0;
		this.b = 0;
		this.c = 1;
		this.d = 0;
		this.screenO = new Triplet(-1, 0, 0);
		this.screenI = new Triplet(1,0,0);
		this.screenJ = new Triplet(0, 1, 0);
		this.pov = new Triplet(3,2,4);
		this.points = new HashMap<String,Triplet>();
		this.projections = new HashMap<String,ProjectedPoint>();
		this.lines = new LinkedList<NamedLine>();
	}

	public SimpleMatrix getProjected(double x, double y, double z) {
		double xA= x;
		double yA= y;
		double zA= z;
		SimpleMatrix A = new SimpleMatrix(4,4);
		A.set(0, 0, 1);
		A.set(0, 1, 0);
		A.set(0, 2, 0);
		A.set(0, 3, pov.x-xA);
		A.set(1, 0, 0);
		A.set(1, 1, 1);
		A.set(1, 2, 0);
		A.set(1, 3, pov.y-yA);
		A.set(2, 0, 0);
		A.set(2, 1, 0);
		A.set(2, 2, 0);
		A.set(2, 3, pov.z-zA);
		A.set(3, 0, a);
		A.set(3, 1, b);
		A.set(3, 2, c);
		A.set(3, 3, 0);

		SimpleMatrix B = new SimpleMatrix(4,1);

		B.set(0,0,pov.x);
		B.set(1,0,pov.y);
		B.set(2,0,pov.z);
		B.set(3,0,d);

		SimpleMatrix X = A.solve(B);
		return X;
	}

	public void render(GameContainer gc, Graphics g) throws SlickException {
		g.setColor(Color.white);
		g.fillRect(0, 0, iRes, jRes);
		g.setColor(Color.black);
		for (NamedLine line: lines){
			g.drawLine(projections.get(line.begin).i, projections.get(line.begin).j, projections.get(line.end).i, projections.get(line.end).j);
		}
		for (Entry<String,ProjectedPoint> e: projections.entrySet()){
			g.drawString(e.getKey(), e.getValue().i, e.getValue().j);
		}
	}

	private void populateScene(){
		Triplet A = new Triplet(1,1,-5);
		points.put("A", A);
		Triplet B = new Triplet(2,2,-5);
		points.put("B", B);
		NamedLine AB = new NamedLine("A", "B");
//		lines.add(AB);
		points.put("O",new Triplet(0, 0, 0));
		for (Entry<String,Triplet> M: points.entrySet()){
			for (Entry<String,Triplet> N: points.entrySet()){
				if (!M.getKey().equals(N.getKey())){
					lines.add(new NamedLine(M.getKey(), N.getKey()));
				}
			}
		}
		
		
		
	}
	
	public void init(GameContainer gc) throws SlickException {
		//
		populateScene();
		// generation des points
		for (Entry<String,Triplet> e: points.entrySet()){
			Triplet p = e.getValue();
			String name = e.getKey();
			SimpleMatrix projected = getProjected(p.x, p.y, p.z);
//			System.out.println(""+projected.get(0,0)+","+projected.get(1,0)+","+projected.get(2,0));
			Triplet M = new Triplet(projected.get(0,0),projected.get(1,0),projected.get(2,0));
			Triplet vector = screenO.getVector(M);
			int i = (int)(vector.scale(screenI)*iRes/10);
			int j = (int)(vector.scale(screenJ)*jRes/10);
			ProjectedPoint projection = new ProjectedPoint(i, j);
			projections.put(name,projection);
		}
		
	}

	public void update(GameContainer gc, int lastUpdate) throws SlickException {
		
	}
	
	
	public static void main(String[] args) {
		try {
			Scene scene = new Scene();
			AppGameContainer app = new AppGameContainer(scene);
			app.setDisplayMode(iRes,jRes,false);
			int minLogicInterval = 10;
			app.setMinimumLogicUpdateInterval(minLogicInterval);
			//app.setMaximumLogicUpdateInterval(minLogicInterval);
			app.setTargetFrameRate(61);
			app.setVSync(true);
			app.setShowFPS(false);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}

	}
	
	
	
}
