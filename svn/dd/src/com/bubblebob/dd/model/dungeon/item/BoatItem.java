package com.bubblebob.dd.model.dungeon.item;

import com.bubblebob.dd.model.DdGameModel;

public class BoatItem extends Item{

	public BoatItem(DdGameModel model) {
		super(model);
	}

	@Override
	public void doPickUp() {
		model.getWorld().getPlayer().getInfos().setGotBoat(true);
	}
	
	@Override
	public String getName() {
		return "BOAT";
	}
}
