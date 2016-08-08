package com.bubblebob.dd.editor.world;

import com.bubblebob.dd.model.Position;
import com.bubblebob.dd.model.dungeon.map.DungeonMap;
import com.bubblebob.dd.model.world.map.WorldMap;
import com.bubblebob.tool.ggame.GameModel;

public class WorldEditorModel implements GameModel{

	private WorldMap map;
	private WorldEditorMode mode;
	public Position upperLeftShownTilePosition;
	
	private int tileWidth;
	private int tileHeight;
	
	
	public WorldEditorModel(WorldMap map, int tileWidth, int tileHeight){
		this.map = map;
		this.upperLeftShownTilePosition = new Position(0,0);
		this.mode = WorldEditorMode.PATTERN;
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
	}
	
	public void update() {
	}
	
	public WorldMap getMap(){
		return map;
	}
	
	public void setMap(WorldMap map){
		this.map = map;
	}

	public WorldEditorMode getMode() {
		return mode;
	}

	public void setMode(WorldEditorMode mode) {
		this.mode = mode;
	}

	public int getTileWidth() {
		return tileWidth;
	}

	public int getTileHeight() {
		return tileHeight;
	}


}
