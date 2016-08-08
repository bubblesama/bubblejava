package com.bubblebob.dd.model.dungeon.item;

import com.bubblebob.dd.model.DdGameModel;

public class KeyItem extends Item{

	public KeyItem(DdGameModel model) {
		super(model);
	}

	@Override
	public void doPickUp() {
		model.getWorld().getPlayer().getInfos().setGotKey(true);
	}
	
	
	@Override
	public String getName() {
		return "KEY";
	}
}
