package com.bb.khatanne.control;

import com.bb.khatanne.control.annotation.CommandLineArgument;
import com.bb.khatanne.model.Game;
import com.bb.khatanne.model.ImpossibleActionException;
import com.bb.khatanne.model.PlayerType;

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
