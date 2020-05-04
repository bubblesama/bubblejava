package com.bubblebob.dd.model.dungeon.item;

import com.bubblebob.dd.model.DdGameModel;

public class AxeItem extends Item{

	public AxeItem(DdGameModel model) {
		super(model);
	}

	@Override
	public void doPickUp() {
		model.getWorld().getPlayer().getInfos().setGotAxe(true);
	}

	@Override
	public String getName() {
		return "AXE";
	}
}
