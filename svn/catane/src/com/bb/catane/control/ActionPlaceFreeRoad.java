package com.bb.catane.control;

import com.bb.catane.control.annotation.CommandLineArgument;
import com.bb.catane.model.Game;
import com.bb.catane.model.ImpossibleActionException;
import com.bb.catane.model.PlayerType;

public class ActionPlaceFreeRoad extends BaseAction{

	@CommandLineArgument(order=3)
	public int sideId;
	
	public ActionPlaceFreeRoad(){
		super();
	}
	
	public ActionPlaceFreeRoad(PlayerType playerType, ActionType type) {
		super(playerType,type);
	}
	
	public ActionPlaceFreeRoad(PlayerType playerType, int sideId) {
		super(playerType, ActionType.PLACE_FREE_ROAD);
		this.sideId = sideId;
	}

	public Action resolve(Game game) throws ImpossibleActionException {
		return game.placeFreeRoad(game.getPlayer(playerType), game.getSide(sideId));
	}

}
