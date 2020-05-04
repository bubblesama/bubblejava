package com.bubblebob.dd.model.dungeon.item;

import com.bubblebob.dd.model.DdGameModel;
import com.bubblebob.dd.model.GameMode;

public class LadderItem  extends Item{

	public LadderItem(DdGameModel model) {
		super(model);
	}

	@Override
	public void doPickUp() {
		model.setMode(GameMode.WORLD);
		model.getWorld().getMap().getTile(model.getWorld().getPlayer().getPosition()).setVisited(true);
	}
	
	@Override
	public String getName() {
		return "LADDER";
	}
}
