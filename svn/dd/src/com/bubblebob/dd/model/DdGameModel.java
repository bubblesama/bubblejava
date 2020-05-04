package com.bubblebob.dd.model;

import com.bubblebob.dd.model.dungeon.DungeonModel;
import com.bubblebob.dd.model.dungeon.map.DungeonMap;
import com.bubblebob.dd.model.world.WorldModel;
import com.bubblebob.dd.model.world.map.WorldTileType;
import com.bubblebob.tool.ggame.Game;
import com.bubblebob.tool.ggame.GameModel;

public class DdGameModel implements GameModel {

	private Game game;
	
	private WorldModel world;
	private DungeonModel dungeon;
	private GameMode mode;
	
	// compteurs des FPS
	private long lastSecond;
	
	public DdGameModel(WorldModel world, DungeonModel dungeon) {
		super();
		this.world = world;
		this.dungeon = dungeon;
		dungeon.subscribeToGame(this);
		this.mode = GameMode.WORLD;
		this.lastSecond = System.currentTimeMillis();
	}
	
	public void update(){
		switch (mode) {
		case WORLD:
			world.update();
			break;
		case DUNGEON:
			dungeon.update();
			break;
		default:
			break;
		}
	}

	public WorldModel getWorld() {
		return world;
	}

	public DungeonModel getDungeon() {
		return dungeon;
	}

	public GameMode getMode() {
		return mode;
	}

	public void setMode(GameMode mode) {
		this.mode = mode;
	}

	public void setGame(Game game) {
		this.game = game;
	}
	
	public void startDungeon(DungeonMap map, WorldTileType dungeonType){
		dungeon.launch(map, world.getPlayer().getInfos(),dungeonType);
	}
	

}
