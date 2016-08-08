package com.bubblebob.dd.model.dungeon.mob;

// TYPE DE MONSTRES
public enum MonsterType {
	SNAKE(2,3,false),TROLL(2,3,false),RAT(1,3,false),DRAGON(3,3,false),SPIDER(2,3,false),BLOB(1000,10,true);
	/**
	 * La vie initiale de ce type de monstre
	 */
	private int life;
	private int ticksToMove;
	private boolean alwaysVisible;
	
	MonsterType(int life, int ticksToMove, boolean alwaysVisible){
		this.life = life;
		this.ticksToMove = ticksToMove;
		this.alwaysVisible = alwaysVisible;
	}
	public int getInitialLife(){
		return life;
	}
	public int getTicksToMove() {
		return ticksToMove;
	}
	public boolean isAlwaysVisible() {
		return alwaysVisible;
	}

}

