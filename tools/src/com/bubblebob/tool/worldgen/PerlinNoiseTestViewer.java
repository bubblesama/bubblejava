package com.bubblebob.tool.worldgen;

import java.io.File;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.imageout.ImageOut;

public class PerlinNoiseTestViewer extends BasicGame{ 

	// viewing - heightmap
	double[] heightMapColorLadder = {0.25,0.3,0.35,0.36,0.4,0.5,0.6,0.7,0.75,0.8,0.85,100};
	int[][] heightMapColorsMatrix = {{0,110,200},{0,130,220},{0,150,250},{255,220,0},{0,200,30},{0,150,20},{0,120,20},{0,80,10},{140,140,140},{150,150,150},{180,180,180},{200,200,200}};
	Color[] heightMapColors;

	// viewing - temperature
	double[] temperatureMapColorLadder = {0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9,10};
	int[][] temperatureMapColorsMatrix = {{0,20,50},{0,20,100},{0,20,150},{0,20,200},{200,20,00},{150,20,0},{100,20,0},{50,20,0},{100,100,100},{200,200,200}};
	Color[] temperatureMapColors;

	// map
	static int w = 800;
	static int h = 600;
	double[][] heightmap;
	private ComposedPerlinNoise heightNoise;
	private ComposedPerlinNoise temperatureNoise;
	double[][] temperatureMap;

	// input
	Input input;

	// viewing 
	static float scale = 1.3f;
	
	
	public PerlinNoiseTestViewer() {
		super("Perlin pin pin");
		// relief
		heightNoise = new ComposedPerlinNoise();
		heightNoise.harmos = 4;
		double[][] heightNoiseDistortions = {{100,100},{20,20},{8,8},{0.5,0.5}};
		heightNoise.distorsions=heightNoiseDistortions;
		double[] heightNoisePowers = {10,3,1,0};
		heightNoise.powers=heightNoisePowers;
		//temperature
		temperatureNoise = new ComposedPerlinNoise();
		temperatureNoise.harmos = 1;
		double[][] temperatureNoiseDistortions = {{1000,100},{2000,20},{800,8},{1000,0.5}};
		temperatureNoise.distorsions=temperatureNoiseDistortions;
		double[] temperatureNoisePowers = {10,3,1,0};
		temperatureNoise.powers=temperatureNoisePowers;
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		for (int i=0;i<w;i++){
			for (int j=0;j<h;j++){
				int colorIndex = 0;
				boolean found = false;
				while (!found){
					if ((heightmap[i][j]>heightMapColorLadder[colorIndex])&&(colorIndex<=(heightMapColorLadder.length-1))){
						//					if ((temperatureMap[i][j]>temperatureMapColorLadder[colorIndex])&&(colorIndex<=(temperatureMapColorLadder.length-1))){
						colorIndex++;
					}else{
						found = true;
					}
				}
				g.setColor(heightMapColors[colorIndex]);
				//				g.setColor(temperatureMapColors[colorIndex]);
				g.fillRect(i*scale, j*scale, scale, scale);
			}
		}
	}

	private void genBitmap(){
		heightmap = heightNoise.generateOldAttenuatedBitmap(w, h);
		//temperatureMap = temperatureNoise.generateAttenuatedBitmap(w, h);
	}

	@Override	
	public void init(GameContainer gc) throws SlickException {
		// colors preparation
		this.heightMapColors = new Color[heightMapColorsMatrix.length];
		for (int k=0;k<this.heightMapColorsMatrix.length;k++){
			this.heightMapColors[k] = new Color(this.heightMapColorsMatrix[k][0], this.heightMapColorsMatrix[k][1], this.heightMapColorsMatrix[k][2]);
		}
		this.temperatureMapColors = new Color[temperatureMapColorsMatrix.length];
		for (int k=0;k<this.temperatureMapColorsMatrix.length;k++){
			this.temperatureMapColors[k] = new Color(this.temperatureMapColorsMatrix[k][0], this.temperatureMapColorsMatrix[k][1], this.temperatureMapColorsMatrix[k][2]);
		}
		// bitmap
		heightmap = heightNoise.generateAttenuatedBitmap(w, h);
		//temperatureMap = temperatureNoise.generateAttenuatedBitmap(w, h);
		// input 
		this.input = gc.getInput();
	}

	@Override 
	public void update(GameContainer gc, int lastUpdate) throws SlickException {
		if (input.isKeyDown(Input.KEY_SPACE)){
			//saveScreenShot(gc);
			genBitmap();
		}
	}
	public static void main(String[] args) {
		AppGameContainer app;
		try {
			BasicGame game = new PerlinNoiseTestViewer();
			app = new AppGameContainer(game);
			app.setIcon("assets/icon-fake.png");
			app.setDisplayMode((int)(w*scale), (int)(h*scale), false);
			app.setMinimumLogicUpdateInterval(300);
			app.isVSyncRequested();
			app.setTargetFrameRate(10);
			app.setShowFPS(true);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	private static final String SCREENSHOTS_FOLDER = "screenshots";
	private void saveScreenShot(GameContainer gc){
		try {
			String screenshotFileName = SCREENSHOTS_FOLDER+"/shot_"+System.currentTimeMillis()+".png";
			Image target = new Image(w, h);
			Graphics graphics = gc.getGraphics();
			graphics.copyArea(target, 0, 0);
			ImageOut.write(target, screenshotFileName) ;
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

}


