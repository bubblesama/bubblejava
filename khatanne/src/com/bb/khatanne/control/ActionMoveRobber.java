package com.bb.khatanne.control;

import com.bb.khatanne.control.annotation.CommandLineArgument;
import com.bb.khatanne.model.Game;
import com.bb.khatanne.model.ImpossibleActionException;
import com.bb.khatanne.model.PlayerType;

public class ActionMoveRobber extends BaseAction{

	@CommandLineArgument(order=3)
	public int tileId;
	
	public ActionMoveRobber(){
		super();
	}
	
	public ActionMoveRobber(PlayerType playerType, ActionType type) {
		super(playerType,type);
	}
	
	public ActionMoveRobber(PlayerType playerType, int tileId) {
		super(playerType, ActionType.MOVE_ROBBER);
		this.tileId = tileId;
	}

	public Action resolve(Game game) throws ImpossibleActionException {
		return game.moveRobber(game.getPlayer(playerType), game.getTile(tileId));
	}
	
}
