package com.bb.advent2018.day15;

public class Mob {
	
	public int i;
	public int j;
	public MobType type;
	public int hp;
	
	private static final int GRAVITY = 3;
	private static final int DEFAULT_HP = 200;
	
	public Mob(MobType type, int i, int j) {
		super();
		this.i = i;
		this.j = j;
		this.type = type;
		this.hp = DEFAULT_HP;
	}
	
	public boolean isDead() {
		return hp <= 0; 
	}
	
	public void act(Arena arena) {
		if (!isDead()) {
			//check direct neighbourhood
			Mob target = arena.getWeakestEnnemyNeighbour(i, j, type.ennemy());
			if (target != null) {
				//hit ennemies
				attack(target, GRAVITY);
			}else {
				//check distant available ennemies
				WarPath path = arena.getShortestPathToBlood(i, j, type.ennemy());
				if (path != null) {
					//move
					step(path);
				}else {
					//TODO log nothing to do
				}
			}
		}
		
	}
	
	public void step(WarPath path) {
		this.i = path.getFirstStep().i;
		this.j = path.getFirstStep().j;
	}
	
	public void attack(Mob ennemy, int strength) {
		ennemy.wound(strength);
	}
	
	public void wound(int gravity) {
		this.hp -= gravity;
	}
	
	
}
