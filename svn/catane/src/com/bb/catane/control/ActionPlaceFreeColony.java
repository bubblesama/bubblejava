package com.bb.catane.control;

import com.bb.catane.control.annotation.CommandLineArgument;
import com.bb.catane.model.Game;
import com.bb.catane.model.ImpossibleActionException;
import com.bb.catane.model.PlayerType;


public class ActionPlaceFreeColony extends BaseAction{
	
	@CommandLineArgument(order=3)
	public int vertexId;
	
	public ActionPlaceFreeColony(){
		super();
	}
	
	public ActionPlaceFreeColony(PlayerType playerType, ActionType type) {
		super(playerType,type);
	}
	
	public ActionPlaceFreeColony(PlayerType player, int vertexId) {
		super(player, ActionType.PLACE_FREE_COLONY);
		this.vertexId = vertexId;
	}

	public Action resolve(Game game) throws ImpossibleActionException {
		return game.placeFreeColony(game.getPlayer(playerType), game.getVertex(vertexId));
	}

	
}
