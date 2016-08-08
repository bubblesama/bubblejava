package com.bbsama.krole.model.action;

import java.util.List;

import com.bbsama.krole.model.ActionState;
import com.bbsama.krole.model.GameStateManager;
import com.bbsama.krole.model.Mob;
import com.bbsama.krole.model.Stuff;

public class MultiLootingAction extends BaseAction implements Action{

	private List<Stuff> loot;
	
	public MultiLootingAction(Mob actor,List<Stuff> loot) {
		super(actor);
		this.loot = loot;
	}

	public boolean resolve() {
		boolean success = true;
		int index = 0;
		while (success = true && index < loot.size()){
			success = actor.resolveTaking(loot.get(index));
			index++;
		}
		GameStateManager.getInstance().enterState(ActionState.BASE);
		return success;
	}

}
