package com.bb.catane.control;

import com.bb.catane.model.Game;
import com.bb.catane.model.ImpossibleActionException;
import com.bb.catane.model.PlayerType;

public class ActionRollDice extends BaseAction{
	
	public ActionRollDice(){
		super();
	}
	
	public ActionRollDice(PlayerType playerType, ActionType type) {
		super(playerType,type);
	}
	
	public ActionRollDice(PlayerType playerType) {
		super(playerType, ActionType.ROLL_DICE);
	}

	public Action resolve(Game game) throws ImpossibleActionException {
		return game.rollDice(game.getPlayer(playerType));
	}
}
