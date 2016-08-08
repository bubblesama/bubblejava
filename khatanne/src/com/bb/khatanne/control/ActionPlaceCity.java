package com.bb.khatanne.control;

import com.bb.khatanne.control.annotation.CommandLineArgument;
import com.bb.khatanne.model.Game;
import com.bb.khatanne.model.ImpossibleActionException;
import com.bb.khatanne.model.PlayerType;

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
