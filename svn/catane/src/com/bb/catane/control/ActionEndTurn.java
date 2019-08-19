package com.bb.catane.control;

import com.bb.catane.model.Game;
import com.bb.catane.model.ImpossibleActionException;
import com.bb.catane.model.PlayerType;

public class ActionEndTurn extends BaseAction{

	public ActionEndTurn(){
		super();
	}

	public ActionEndTurn(PlayerType playerType, ActionType type) {
		super(playerType,type);
	}
	
	public ActionEndTurn(PlayerType playerType) {
		super(playerType, ActionType.END_TURN);
	}
	
	public Action resolve(Game game) throws ImpossibleActionException {
		return game.endTurn(game.getPlayer(playerType));
	}

}
