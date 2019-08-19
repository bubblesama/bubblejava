package com.bubblebob.dd.model.dungeon.item;

import com.bubblebob.dd.model.DdGameModel;
import com.bubblebob.dd.model.GameMode;

public class FirstCrownPartItem extends Item{

	public FirstCrownPartItem(DdGameModel model) {
		super(model);
	}

	@Override
	public void doPickUp() {
		model.getWorld().getPlayer().getInfos().setGotFirstCrownPart(true);
		if (model.getWorld().getPlayer().getInfos().isGotSecondCrownPart()){
			model.getWorld().setWon(true);
			model.setMode(GameMode.WORLD);
		}
	}
	
	@Override
	public String getName() {
		return "CROWN1";
	}
}
