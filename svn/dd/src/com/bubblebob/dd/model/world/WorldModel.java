package com.bubblebob.dd.model.world;

import com.bubblebob.dd.model.world.map.WorldMap;
import com.bubblebob.tool.ggame.GameModel;

public class WorldModel implements GameModel{

	private WorldMap map;
	private WorldPlayer player;
	private int ticker;
	
	private boolean won;
	
	
	public WorldModel(WorldMap map, WorldPlayer player){
		this.map = map;
		this.player = player;
		this.ticker = 0;
		this.won = false;
	}
	
	
	public void update() {
		ticker++;
		//<decouverte des cases voisines du joueur...
		map.discoverTileAndNeighbours(map.getTile(player.getX(),player.getY()));
		//...decouverte des cases voisines du joueur>
	}

	public WorldMap getMap() {
		return map;
	}

	//ACCESSEURS
	public int getTicker() {
		return ticker;
	}

	public WorldPlayer getPlayer() {
		return player;
	}

	public boolean isWon() {
		return won;
	}

	public void setWon(boolean won) {
		this.won = won;
	}
	
}
