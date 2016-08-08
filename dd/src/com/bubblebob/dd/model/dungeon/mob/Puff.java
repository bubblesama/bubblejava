package com.bubblebob.dd.model.dungeon.mob;

/**
 * Represente le nuage de fumee laisse par un ennemi mort
 * @author Bubblebob
 *
 */
public class Puff {
	
	public Puff(int x, int y, int lifeSpan) {
		super();
		this.x = x;
		this.y = y;
		this.lifeSpan = lifeSpan;
		this.tick = 0;
	}

	private int x;
	private int y;
	
	private int lifeSpan;
	private int tick;
	
	public boolean update(){
		tick++;
		if (tick %3 == 0){
			y--;
		}
		if (tick > lifeSpan){
			//model.getPuffManager().removePuff(this);
			return true;
		}
		return false;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}

}
