package com.bubblebob.dd.editor.dungeon;

import java.util.Map;

import com.bubblebob.dd.model.Position;
import com.bubblebob.dd.model.dungeon.map.DungeonMap;
import com.bubblebob.dd.model.dungeon.map.DungeonMapFactory;
import com.bubblebob.dd.model.dungeon.map.DungeonTile;
import com.bubblebob.dd.model.dungeon.map.DungeonTileType;


public class DungeonEditorController {

	private DungeonEditorModel model;

	public DungeonEditorController(DungeonEditorModel model) {
		this.model = model;
	}


	public void goLeft(){
		model.upperLeftShownTilePosition = new Position(model.upperLeftShownTilePosition.getTileX()-1, model.upperLeftShownTilePosition.getTileY());
	}
	public void goRight(){
		model.upperLeftShownTilePosition = new Position(model.upperLeftShownTilePosition.getTileX()+1, model.upperLeftShownTilePosition.getTileY());
	}
	public void goUp(){
		model.upperLeftShownTilePosition = new Position(model.upperLeftShownTilePosition.getTileX(), model.upperLeftShownTilePosition.getTileY()-1);
	}
	public void goDown(){
		model.upperLeftShownTilePosition = new Position(model.upperLeftShownTilePosition.getTileX(), model.upperLeftShownTilePosition.getTileY()+1);
	}

	public void stopLeft(){
	}
	public void stopRight(){
	}
	public void stopUp(){
	}
	public void stopDown(){
	}

	public void saveMapAsText(String fileName){
		DungeonMapFactory.saveTextMap(fileName, model.getMap());
	}


	public void loadTextMap(String fileName){
		DungeonMap newMap = DungeonMapFactory.loadTextMap(fileName);
		if (newMap != null){
			model.setMap(newMap);
		}
	}


	public void newMap(int width, int height){
		DungeonMap newMap = new DungeonMap("blank", width, height);
		model.setMap(newMap);
	}


	public void toggleMode(){
		switch (model.getMode()) {
		case PATTERN:
			model.setMode(DungeonEditorMode.GROUP);
			break;
		case GROUP:
			model.setMode(DungeonEditorMode.ITEM);
			break;
		case ITEM:
			model.setMode(DungeonEditorMode.PATTERN);
			break;
		}
	}


	public void hollowMap(){
		for (int i=0; i<model.getMap().getWidth();i++){
			for (int j=0; j<model.getMap().getHeight();j++){
				model.getMap().getTile(i, j).setGroup(0);
				model.getMap().getTile(i, j).setType(DungeonTileType.EMPTY);
			}
		}
		for (DungeonTile t: model.getMap().getBorder()){
			t.setType(DungeonTileType.WALL);
		}

	}
	
	public void quarterRotate(){
		model.setMap(model.getMap().quarterRotate());
	}
	

}
