package com.bubblebob.dd.model.dungeon.item;

import com.bubblebob.dd.model.DdGameModel;

public class RandomItem extends Item{

	public RandomItem(DdGameModel model) {
		super(model);
	}

	@Override
	public void doPickUp() {
		
	}
	
	@Override
	public String getName() {
		return "RANDOM";
	}
}
