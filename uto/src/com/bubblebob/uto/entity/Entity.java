package com.bubblebob.uto.entity;

import java.awt.Graphics;

import com.bubblebob.uto.UtoModel;
import com.bubblebob.uto.graph.Assets;

public abstract class Entity {
	
	public Entity(UtoModel model, String id, int x, int y,int w,int h) {
		super();
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.model = model;
		this.id = id;
	}
	
	public String id;
	public UtoModel model;
	public int x;
	public int y;
	public int w;
	public int h;
	public int tickToDie = 1;
	private int dyingTick = 0;
	public static Assets assets = new Assets(5);
	public boolean toKill = false;
	
	public void update(){
		if (toKill){
			dyingTick++;
			if (dyingTick >= tickToDie){
				
			}
		}
	}
	
	public void paint(Graphics g){}
	
	public void touched(Entity touchingEntity){
		
	}
	
	public abstract int getGraphLayer();
	
}
