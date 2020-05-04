package com.bb.citadel.action;

public interface Action {

	public String getName();
	public String getPlayerName();
	public String getLog();
	public Reaction execute();
	
}
