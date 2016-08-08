package com.bb.khatanne.control;

import com.bb.khatanne.control.annotation.CommandLineArgument;
import com.bb.khatanne.model.PlayerType;

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
