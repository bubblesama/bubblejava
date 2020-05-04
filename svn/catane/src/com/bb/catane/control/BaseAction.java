package com.bb.catane.control;

import com.bb.catane.control.annotation.CommandLineArgument;
import com.bb.catane.model.PlayerType;

public abstract class BaseAction implements Action{
	
	@CommandLineArgument(order=1)
	public ActionType type;
	
	@CommandLineArgument(order=2)
	public PlayerType playerType;
	
	
	public BaseAction() {
		super();
	}
	
	public BaseAction(PlayerType playerType, ActionType type) {
		super();
		this.playerType = playerType;
		this.type = type;
	}
	
	public String toString(){
		return type.toString();
	}
	
	public ActionType getType(){
		return type;
	}
	
}
