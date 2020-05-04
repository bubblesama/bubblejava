package com.bb.catane.control;

import com.bb.catane.control.annotation.CommandLineArgument;
import com.bb.catane.model.Game;
import com.bb.catane.model.ImpossibleActionException;
import com.bb.catane.model.PlayerType;
import com.bb.catane.model.ResourceType;

public class ActionRobSpecific extends BaseAction{
	
	@CommandLineArgument(order=3)
	public PlayerType robbedType;
	@CommandLineArgument(order=4)
	public ResourceType robbedResource;
	
	public ActionRobSpecific(){
		super();
	}
	
	public ActionRobSpecific(PlayerType playerType, ActionType type) {
		super(playerType,type);
	}
	
	public ActionRobSpecific(PlayerType playerType, PlayerType robbedType, ResourceType robbedResource) {
		super(playerType, ActionType.ROB_SPECIFIC);
		this.robbedType = robbedType;
		this.robbedResource = robbedResource;
	}
	
	public Action resolve(Game game) throws ImpossibleActionException {
		return game.robPlayerAndSpecificResource(game.getPlayer(playerType), game.getPlayer(robbedType), robbedResource);
	}
	
}
