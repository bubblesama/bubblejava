package com.bb.khatanne.control;

import com.bb.khatanne.control.annotation.CommandLineArgument;
import com.bb.khatanne.model.Game;
import com.bb.khatanne.model.ImpossibleActionException;
import com.bb.khatanne.model.PlayerType;
import com.bb.khatanne.model.ResourceType;

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
