package com.bubblebob.dd.model.world;

import com.bubblebob.dd.model.PlayerInfos;
import com.bubblebob.dd.model.Position;

public class WorldPlayer {

	private int x;
	private int y;

	private PlayerInfos infos;

	public WorldPlayer(int x, int y, PlayerInfos infos) {
		super();
		this.x = x;
		this.y = y;
		this.infos = infos;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	
	public Position getPosition(){
		return new Position(x, y);
	}

	public boolean hasGotAxe(){
		return infos.hasGotAxe();
	}

	public boolean hasGotKey(){
		return infos.hasGotKey();
	}

	public boolean hasGotBoat(){
		return infos.hasGotBoat();
	}

	public PlayerInfos getInfos(){
		return infos;
	}

	public static WorldPlayer getDefaultPlayer(){
		return new WorldPlayer(0, 5, PlayerInfos.getDefaultInfos());
	}



}
