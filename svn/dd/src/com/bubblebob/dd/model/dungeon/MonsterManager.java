package com.bubblebob.dd.model.dungeon;

import java.util.List;
import java.util.Vector;

import com.bubblebob.dd.model.dungeon.mob.ArrowEaterMonster;
import com.bubblebob.dd.model.dungeon.mob.HitMonster;
import com.bubblebob.dd.model.dungeon.mob.Monster;
import com.bubblebob.dd.model.dungeon.mob.MonsterType;

public class MonsterManager {

	private List<Monster> monsters;
	private DungeonModel model;

	public MonsterManager(DungeonModel model){
		this.model = model;
		this.monsters = new Vector<Monster>();
	}

	public List<Monster> getMonsters(){
		return monsters;
	}

	public void addMonster(Monster monster){
		monsters.add(monster);
	}

	public void removeMonster(Monster monster){
		monsters.remove(monster);
	}

	public void update(){
		for (Monster monster: monsters){
			monster.update();
		}
	}

	/**
	 * Cree un monstre et l'ajoute au manager
	 * @param type
	 * @param i
	 * @param j
	 */
	public void createMonster(MonsterType type, int tileX, int tileY){
		Monster newMonster = null;
		switch (type) {
		case RAT:
			newMonster = new HitMonster(model, tileX*model.getTileWidth(), tileY*model.getTileHeight(),type);
			break;
		case SNAKE:
			newMonster = new HitMonster(model, tileX*model.getTileWidth(), tileY*model.getTileHeight(),type);
			break;
		case TROLL:
			newMonster = new HitMonster(model, tileX*model.getTileWidth(), tileY*model.getTileHeight(),type);
			break;
		case DRAGON:
			newMonster = new HitMonster(model, tileX*model.getTileWidth(), tileY*model.getTileHeight(),type);
			break;
		case SPIDER:
			newMonster = new ArrowEaterMonster(model, tileX*model.getTileWidth(), tileY*model.getTileHeight(),type);
			break;
		default:
			break;
		}
		addMonster(newMonster);
	}


}
