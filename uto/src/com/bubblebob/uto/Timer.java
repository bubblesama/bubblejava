package com.bubblebob.uto;

import java.awt.Graphics;

import com.bubblebob.uto.graph.Assets;
import com.bubblebob.uto.graph.Drawable;
import com.bubblebob.uto.setting.Setting;

public class Timer implements Drawable{

	// donnees internes
	private int time;
	private long lastTime = System.nanoTime();
	private static long second = 1000000000;
	private long tick = second;
	private Period period;
	private int cycles;
	
	public boolean finished = false;

	// modele pour call-back sur fin de periode
	private UtoModel model;
	
	// donnees graphiques
	private Assets assets;
	private int x;
	private int y;
	
	
	private Setting setting;
	

	public Timer(UtoModel model, Assets assets, int x, int y){
		this.period = Period.FREE;
		this.model = model;
		this.assets = assets;
		this.setting = model.getSettings();
		this.cycles = setting.getMaxCycles();
		this.time = setting.getPeriodLength(Period.FREE);
		this.x = x;
		this.y = y;
	}

	public void update(){
		if (System.nanoTime()-lastTime > second){
			time--;
			lastTime = System.nanoTime();
			if (time <0){
				if (period == Period.SHOW_SCORE){
					cycles--;
					if (cycles <= -1){
						finished = true;
					}
				}
				period = period.next();
				// nouvelle periode: on decremente le compteur global
				time = setting.getPeriodLength(period);
			}
		}
	}

	public void reset(int cycles){
		this.cycles = cycles;
		this.period = Period.FREE;
	}
	
	
	public int getTime(){
		return time;
	}

	public Period getPeriod() {
		return period;
	}
	
	public int getCycles() {
		return cycles;
	}

	public void paint(Graphics g) {
		if (period.showValue()){
//			System.out.println("[Timer#paint] x="+x);
			assets.paintNumbers(g, cycles, 2, false, x, y);
			assets.paintNumbers(g, time, 2, false, x+20, y);
			
		}
	}

}
