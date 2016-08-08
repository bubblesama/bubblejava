package com.bubblebob.dd.editor.dungeon;

import com.bubblebob.dd.model.Position;
import com.bubblebob.dd.model.dungeon.map.DungeonMap;
import com.bubblebob.tool.ggame.GameModel;

public class DungeonEditorModel implements GameModel{

	private DungeonMap map;
	private DungeonEditorMode mode;
	public Position upperLeftShownTilePosition;
	
	private int tileWidth;
	private int tileHeight;
	
	
	public DungeonEditorModel(DungeonMap map, int tileWidth, int tileHeight){
		this.map = map;
		this.upperLeftShownTilePosition = new Position(0,0);
		this.mode = DungeonEditorMode.PATTERN;
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
	}
	
	public void update() {
	}
	
	public DungeonMap getMap(){
		return map;
	}
	
	public void setMap(DungeonMap map){
		this.map = map;
	}

	public DungeonEditorMode getMode() {
		return mode;
	}

	public void setMode(DungeonEditorMode mode) {
		this.mode = mode;
	}

	public int getTileWidth() {
		return tileWidth;
	}

	public int getTileHeight() {
		return tileHeight;
	}


}
