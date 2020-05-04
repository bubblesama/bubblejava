package com.bb.flatworld;

import org.newdawn.slick.Input;

import com.bb.flatworld.bg.BackGround;
import com.bb.flatworld.player.Player;

public class FlatWorldData{

	private BackGround bg;
	public Player player;
	
	public FlatWorldData(){
		this.player = new Player();
		this.bg = new BackGround();
	}
	
	
	public void registerOn(UpdateManager updater, RenderManager renderer){
		bg.registerOn(updater, renderer);
		player.registerOn(updater, renderer);
	}
	
}
