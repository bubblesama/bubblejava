package com.bb.khatanne.control;

import com.bb.khatanne.control.annotation.CommandLineArgument;
import com.bb.khatanne.model.Game;
import com.bb.khatanne.model.ImpossibleActionException;
import com.bb.khatanne.model.PlayerType;

public class ActionPlaceColony extends BaseAction{

	@CommandLineArgument(order=3)
	public int vertexId;
	
	public ActionPlaceColony(){
		super();
	}
	
	public ActionPlaceColony(PlayerType playerType, ActionType type) {
		super(playerType,type);
	}
	
	public ActionPlaceColony(PlayerType player,  int vertexId) {
		super(player, ActionType.PLACE_COLONY);
		this.vertexId = vertexId;
	}

	public Action resolve(Game game) throws ImpossibleActionException {
		return game.placeColony(game.getPlayer(playerType), game.getVertex(vertexId));
	}
}
