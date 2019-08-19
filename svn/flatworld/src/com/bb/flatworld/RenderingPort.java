package com.bb.flatworld;

import com.bb.flatworld.player.Player;


public class RenderingPort {

	public RenderingPort(Player player) {
		super();
		this.player = player;
	}

	private Player player;

	public int getX() {
		return player.x-100;
	}

	public void update() {}
	
}
