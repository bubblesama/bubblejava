package com.bbsama.krole.model.action;

import com.bbsama.krole.model.Mob;

public class BaseAction implements Action {

	public BaseAction(Mob actor) {
		super();
		this.actor = actor;
	}
	public Mob actor;
	
	public boolean resolve() {
		actor.spendActionTime();
		return true;
	}

}
