package com.bubblebob.dd.model.dungeon.mob;

// TYPE DE MONSTRES
public enum MonsterType {
	SNAKE(2,4),TROLL(2,4),RAT(1,4),DRAGON(3,4),SPIDER(2,4);
	/**
	 * La vie initiale de ce type de monstre
	 */
	private int life;
	private int ticksToMove;
	MonsterType(int life, int ticksToMove){
		this.life = life;
		this.ticksToMove = ticksToMove;
	}
	public int getInitialLife(){
		return life;
	}
	public int getTicksToMove() {
		return ticksToMove;
	}
	/**
	 * Fournit un type de monster a partir d'un entier
	 * @param n
	 * @return
	 */
	public MonsterType getMonsterByInt(int n){
		switch (n) {
		case 0:
			return SNAKE;
		case 1:
			return TROLL;
		case 2:
			return DRAGON;
		case 3:
			return SPIDER;
		default:
			return RAT;
		}
	}
}

