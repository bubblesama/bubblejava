package com.bbsama.krole.model;

import java.util.List;
import java.util.ArrayList;

public class Cell {
	
	public CellType type;
	private Level level;
	private int i;
	private int j;
	private List<Stuff> loot;
	private List<Mob> mobs;
	
	//TODO virer après le debug
	public boolean isDebug = false;
	
	public Cell(Level level, int i, int j){
		this.level = level;
		this.type = CellType.VOID;
		this.i = i;
		this.j = j;
		this.loot = new ArrayList<Stuff>();
		this.mobs = new ArrayList<Mob>();
	}
	
	public boolean isPassable(){
		return type.passable;
	}

	public int i() {
		return i;
	}

	public int j() {
		return j;
	}

	public Level level() {
		return level;
	}
	
	public void notifiedAsOut(Mob mob){
		this.mobs.remove(mob);
	}
	
	public void notifiedAsIn(Mob mob){
		this.mobs.add(mob);
	}
	
	public void notifiedAsDropped(Stuff stuff){
		this.loot.add(stuff);
		this.level.notifyAsDropped(stuff);
	}
	
	public void notifiedAsTaken(Stuff stuff){
		this.loot.remove(stuff);
		this.level.notifyAsTaken(stuff);
	}

	public List<Stuff> getLoot() {
		return loot;
	}
	
	
}
