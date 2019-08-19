package com.bb.citadel.action;

public interface Reaction {

	public Action getAction();
	public void apply();
	public String getLog();
	
}
