package com.bb.khatanne.control;

import com.bb.khatanne.control.annotation.CommandLineArgument;
import com.bb.khatanne.model.Game;
import com.bb.khatanne.model.ImpossibleActionException;
import com.bb.khatanne.model.PlayerType;

public class ActionDoRoll extends BaseAction{

	@CommandLineArgument(order=3)
	public int firstDice;
	@CommandLineArgument(order=4)
	public int secondDice;
	
	
	public ActionDoRoll() {
		super();
	}
	
	public ActionDoRoll(PlayerType playerType, ActionType type) {
		super(playerType,type);
	}
	
	public ActionDoRoll(PlayerType playerType, int firstDice, int secondDice) {
		super(playerType, ActionType.DO_ROLL);
		this.firstDice = firstDice;
		this.secondDice = secondDice;
	}

	public Action resolve(Game game) throws ImpossibleActionException {
		return game.doRoll(game.getPlayer(playerType), firstDice, secondDice);
	}

}
