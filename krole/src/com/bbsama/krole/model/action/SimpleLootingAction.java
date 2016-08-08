package com.bbsama.krole.model.action;

import com.bbsama.krole.model.Mob;
import com.bbsama.krole.model.MobManager;
import com.bbsama.krole.model.Stuff;
import com.bbsama.krole.model.StuffManager;

public class SimpleLootingAction extends BaseAction implements Action{

	private Stuff loot;
	
	public SimpleLootingAction(Mob actor,Stuff loot) {
		super(actor);
		this.loot = loot;
	}

	public boolean resolve() {
		boolean success = actor.resolveTaking(loot);
		return success;
	}

}
