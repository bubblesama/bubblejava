package com.bb.advent2018.day10;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class StarAligner extends BasicGame{

	//graphic
	private final static int VIEW_WIDTH = 800;
	private final static int VIEW_HEIGHT = 600;

	//model
	private StarCluster cluster;
	private int step = 0;
	
	//utils
	private static final String PRELOG = "StarAligner#";
	private static final String INPUT_FILE_NAME = "res/day10-input.txt";

	public StarAligner() {
		super("advent 2018 - day - part1");
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		//calculating how to spread the graphical cluster
		int[] spreading = cluster.getSpreading();
		int minX = spreading[0];
		int maxX = spreading[1];
		int minY = spreading[2];
		int maxY = spreading[3];
		int w = maxX - minX;
		int h = maxY - minY;
		int meterByPixelX = w/VIEW_WIDTH;
		int meterByPixelY = h/VIEW_HEIGHT;
		int scale = Math.max(meterByPixelX, meterByPixelY);
		scale++;
		g.setColor(Color.white);
		for (Star star: cluster.getStars()) {
			g.fillRect((star.x-minX)/scale, (star.y-minY)/scale, 1, 1);
		}
		g.drawString("step: "+step+" scale="+scale, 0, 40);
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		log("init IN, parsing coordinates");
		this.cluster = new StarCluster();
		//reading input file to initiate star cluster
		BufferedReader br = null;
		FileReader fr = null;
		try {
			fr = new FileReader(INPUT_FILE_NAME);
			br = new BufferedReader(fr);
			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null) {
//				var x0 = +(line.substring(10,16));
//				var y0 = +(line.substring(18,24));
//				var dx = +(line.substring(36,38));
//				var dy = +(line.substring(40,42));
				int x0 = Integer.parseInt(sCurrentLine.substring(10, 16).trim());
				int y0 = Integer.parseInt(sCurrentLine.substring(18, 24).trim());
				int dx = Integer.parseInt(sCurrentLine.substring(36, 38).trim());
				int dy = Integer.parseInt(sCurrentLine.substring(40, 42).trim());
				Star star = new Star(x0, y0, dx, dy);
				cluster.add(star);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
				if (fr != null) {
					fr.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		log("init cluster initiated, stars="+cluster.size());
	}

	@Override
	public void update(GameContainer gc, int time) throws SlickException {}

	public static void main(String[] args) {
		AppGameContainer app;
		try {
			BasicGame aligner = new StarAligner();
			app = new AppGameContainer(aligner);
			app.setIcon("res/icon-fake.png");
			app.setDisplayMode(VIEW_WIDTH+200, VIEW_HEIGHT, false);
			app.setMinimumLogicUpdateInterval(10);
			app.isVSyncRequested();
			app.setTargetFrameRate(60);
			app.setShowFPS(false);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}


	private static final void log(String log) {
		System.out.println(PRELOG+log);
	}

	public void keyPressed(int keyCode, char keyChar) {
		boolean todo = false;
		int step = 0;
		
		if (keyCode == Input.KEY_Z){
			todo = true;
			step = 1000;
		}
		if (keyCode == Input.KEY_A){
			todo = true;
			step = -1000;
		}
		if (keyCode == Input.KEY_S){
			todo = true;
			step = 100;
		}
		if (keyCode == Input.KEY_Q){
			todo = true;
			step = -100;
		}
		if (keyCode == Input.KEY_X){
			todo = true;
			step = 10;
		}
		if (keyCode == Input.KEY_W){
			todo = true;
			step = -10;
		}
		if (keyCode == Input.KEY_SPACE){
			todo = true;
			step = 1;
		}
		if (todo) {
			cluster.step(step);
			this.step += step;
		}
	}

}
