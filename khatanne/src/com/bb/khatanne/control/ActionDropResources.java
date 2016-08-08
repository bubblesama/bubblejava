package com.bb.khatanne.control;

import java.util.Map;

import com.bb.khatanne.model.Game;
import com.bb.khatanne.model.ImpossibleActionException;
import com.bb.khatanne.model.PlayerType;
import com.bb.khatanne.model.ResourceType;

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
