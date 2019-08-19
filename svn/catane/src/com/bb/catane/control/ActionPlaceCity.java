package com.bb.catane.control;

import com.bb.catane.control.annotation.CommandLineArgument;
import com.bb.catane.model.Game;
import com.bb.catane.model.ImpossibleActionException;
import com.bb.catane.model.PlayerType;

public class ActionPlaceCity extends BaseAction{

	@CommandLineArgument(order=3)
	public int vertexId;
	
	public ActionPlaceCity(){
		super();
	}
	
	public ActionPlaceCity(PlayerType playerType, ActionType type) {
		super(playerType,type);
	}
	
	public ActionPlaceCity(PlayerType playerType, int vertexId) {
		super(playerType, ActionType.PLACE_CITY);
		this.vertexId = vertexId;
	}

	public Action resolve(Game game) throws ImpossibleActionException {
		return game.placeCity(game.getPlayer(playerType), game.getVertex(vertexId));
	}

}
