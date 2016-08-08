package com.bbsama.krole.model;

import java.util.HashMap;
import java.util.Map;

public class WorldManager {
	
	private Map<Integer,Level> levelsById;
	
	private static WorldManager instance;
	
	//TODO a valider
	private static final int LEVEL_SIZE_W = 90;
	private static final int LEVEL_SIZE_H = 55;
	
	public synchronized static WorldManager getInstance(){
		if (instance == null){
			instance = new WorldManager();
		}
		return instance;
	}
	
	public WorldManager(){
		this.levelsById = new HashMap<Integer,Level>();
		//TODO initialisation des levels et pas que d'un seul
		this.levelsById.put(0, LevelGenerator.generateLevel(LEVEL_SIZE_W, LEVEL_SIZE_H));
	}
	
	public Level getLevelById(int levelId){
		return levelsById.get(levelId);
	}
	
	//TODO a supprimer car pour les tests
	public void resetLevels(){
		this.levelsById.clear();
		this.levelsById.put(0, LevelGenerator.generateLevel(LEVEL_SIZE_W, LEVEL_SIZE_H));
	}
	
}
