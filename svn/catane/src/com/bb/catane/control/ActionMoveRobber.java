package com.bb.catane.control;

import com.bb.catane.control.annotation.CommandLineArgument;
import com.bb.catane.model.Game;
import com.bb.catane.model.ImpossibleActionException;
import com.bb.catane.model.PlayerType;

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
