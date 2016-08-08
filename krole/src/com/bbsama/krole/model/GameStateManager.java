package com.bbsama.krole.model;

import com.bbsama.krole.view.GameRenderer;


public class GameStateManager {
	
	private ActionState currentState = ActionState.BASE;
	private static GameStateManager instance;
	
	
	public static GameStateManager getInstance(){
		if (instance == null){
			instance = new GameStateManager();
		}
		return instance;
	}

	public ActionState getCurrentState() {
		return currentState;
	}

	public void setCurrentState(ActionState currentState) {
		this.currentState = currentState;
	}
	
	
	public void enterState(ActionState state){
		if (state == ActionState.BASE){
			//TODO
			GameRenderer.getInstance().hideInventory();
			GameRenderer.getInstance().hideLoot();
		}
		if (state == ActionState.LOOTING){
			StuffManager.getInstance().initLootByChar(MobManager.getInstance().getPlayer().getCurrentLoot());
			GameRenderer.getInstance().hideInventory();
			GameRenderer.getInstance().showLoot();
		}
		if (state == ActionState.DROPPING){
			StuffManager.getInstance().initDroppingSelection(MobManager.getInstance().getPlayer().getInventory());
			GameRenderer.getInstance().showInventory();
		}
		currentState = state;
	}
	
	
}
