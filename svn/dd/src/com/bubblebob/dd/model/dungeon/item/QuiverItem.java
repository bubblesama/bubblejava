package com.bubblebob.dd.model.dungeon.item;

import com.bubblebob.dd.model.DdGameModel;

public class QuiverItem extends Item{

	public QuiverItem(DdGameModel model) {
		super(model);
	}

	private int arrowsCounter = 5;
	
	@Override
	public void doPickUp() {
		model.getDungeon().getShootManager().addArrows(arrowsCounter);
	}
	
	@Override
	public String getName() {
		return "QUIVER";
	}
	

}
