package com.bubblebob.dd.control;

import com.bubblebob.dd.model.DdGameModel;
import com.bubblebob.dd.model.Direction;
import com.bubblebob.dd.model.GameMode;
import com.bubblebob.dd.model.dungeon.item.Item;
import com.bubblebob.dd.model.dungeon.map.DungeonTile;

public class DungeonController {

	public DungeonController(DdGameModel model) {
		this.model = model;
	}

	private DdGameModel model;
	
	public void goLeft(){
		if (!model.getDungeon().getPlayer().isDeathStarted()){
			model.getDungeon().getPlayer().makeRunLeft();
			model.getDungeon().getPlayer().stopRunRight();
		}
	}
	public void goRight(){
		if (!model.getDungeon().getPlayer().isDeathStarted()){
			model.getDungeon().getPlayer().makeRunRight();
			model.getDungeon().getPlayer().stopRunLeft();
		}
	}
	public void goUp(){
		if (!model.getDungeon().getPlayer().isDeathStarted()){
			model.getDungeon().getPlayer().makeRunUp();
			model.getDungeon().getPlayer().stopRunDown();
		}
	}
	public void goDown(){
		if (!model.getDungeon().getPlayer().isDeathStarted()){
			model.getDungeon().getPlayer().makeRunDown();
			model.getDungeon().getPlayer().stopRunUp();
		}
	}
	
	public void stopLeft(){
		model.getDungeon().getPlayer().stopRunLeft();
	}
	public void stopRight(){
		model.getDungeon().getPlayer().stopRunRight();
	}
	public void stopUp(){
		model.getDungeon().getPlayer().stopRunUp();
	}
	public void stopDown(){
		model.getDungeon().getPlayer().stopRunDown();
	}
	
	public void tryArrowLaunch(Direction direction){
		model.getDungeon().getShootManager().setShootPrepared(true);
		model.getDungeon().getShootManager().setNextShootDirection(direction);
	}
	
	public void togglePause(){
		if (model.getDungeon().isPaused()){
			model.getDungeon().resume();
		}else{
			model.getDungeon().pause();
		}
	}
	
	public void toggleMode(){
		if (model.getMode() == GameMode.DUNGEON){
			model.setMode(GameMode.WORLD);
		}else{
			model.setMode(GameMode.DUNGEON);
		}
	}
	
	public void pickItem(){
		DungeonTile currentTile = model.getDungeon().getMap().getTile(model.getDungeon().getPlayer().getPosition().getTileX(), model.getDungeon().getPlayer().getPosition().getTileY());
		if (!currentTile.getItems().isEmpty()){
			Item firstItem = currentTile.getItems().get(0);
			firstItem.doPickUp();
			currentTile.getItems().remove(0);
		}
	}
	
	
}
