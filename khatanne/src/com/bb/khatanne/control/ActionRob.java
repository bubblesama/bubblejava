package com.bb.khatanne.control;

import com.bb.khatanne.control.annotation.CommandLineArgument;
import com.bb.khatanne.model.Game;
import com.bb.khatanne.model.ImpossibleActionException;
import com.bb.khatanne.model.PlayerType;

public class ActionRob extends BaseAction{
	
	@CommandLineArgument(order=3)
	public PlayerType robbedType;
	
	public ActionRob(){
		super();
	}
	
	public ActionRob(PlayerType playerType, ActionType type) {
		super(playerType,type);
	}
	
	public ActionRob(PlayerType playerType, PlayerType robbedType) {
		super(playerType, ActionType.ROB);
		this.robbedType = robbedType;
	}
	
	public Action resolve(Game game) throws ImpossibleActionException {
		return game.robPlayer(game.getPlayer(playerType), game.getPlayer(robbedType));
	}
	
}
