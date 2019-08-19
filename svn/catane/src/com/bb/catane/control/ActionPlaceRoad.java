package com.bb.catane.control;

import com.bb.catane.control.annotation.CommandLineArgument;
import com.bb.catane.model.Game;
import com.bb.catane.model.ImpossibleActionException;
import com.bb.catane.model.PlayerType;

public class ActionPlaceRoad extends BaseAction{
	
	@CommandLineArgument(order=3)
	public int sideId;
	
	public ActionPlaceRoad(){
		super();
	}
	
	public ActionPlaceRoad(PlayerType playerType, ActionType type) {
		super(playerType,type);
	}
	
	public ActionPlaceRoad(PlayerType playerType, int sideId) {
		super(playerType, ActionType.PLACE_ROAD);
		this.sideId = sideId;
	}

	public Action resolve(Game game) throws ImpossibleActionException {
		return game.placeRoad(game.getPlayer(playerType), game.getSide(sideId));
	}


}
