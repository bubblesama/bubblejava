package com.bb.catane.control;

import java.util.Map;

import com.bb.catane.model.Game;
import com.bb.catane.model.ImpossibleActionException;
import com.bb.catane.model.PlayerType;
import com.bb.catane.model.ResourceType;

public class ActionDropResources extends BaseAction{

	public Map<ResourceType,Integer> drops;
	
	public ActionDropResources(){
		super();
	}
	
	public ActionDropResources(PlayerType playerType, ActionType type) {
		super(playerType,type);
	}
	
	public ActionDropResources(PlayerType playerType,Map<ResourceType,Integer> drops) {
		super(playerType, ActionType.DROP_RESOURCES);
		this.drops = drops;
	}

	public Action resolve(Game game) throws ImpossibleActionException {
		return game.dropResource(game.getPlayer(playerType), drops);
	}

}
