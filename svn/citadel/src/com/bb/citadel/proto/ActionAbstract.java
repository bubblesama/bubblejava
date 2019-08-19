package com.bb.citadel.proto;

public abstract class ActionAbstract implements Action{
	
	private boolean finished = false;
	
	public boolean isFinished(){
		return finished;
	}
	
}
