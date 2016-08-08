package com.bubblebob.dd.editor.world;

import com.bubblebob.dd.model.dungeon.map.DungeonMap;
import com.bubblebob.dd.model.dungeon.map.DungeonMapFactory;
import com.bubblebob.dd.model.world.map.WorldMap;
import com.bubblebob.dd.model.world.map.WorldMapFactory;


public class WorldEditorController {

	private WorldEditorModel model;

	public WorldEditorController(WorldEditorModel model) {
		this.model = model;
	}


	public void newMap(){
		WorldMap newMap = WorldMap.getDefaultMap();
		model.setMap(newMap);
	}
	
	public void loadTextMap(String fileName){
		WorldMap newMap = WorldMapFactory.loadTextMap(fileName);
		if (newMap != null){
			model.setMap(newMap);
		}
	}
	
	public void saveMapAsText(String fileName){
		WorldMapFactory.saveTextMap(fileName, model.getMap());
	}
	

}
