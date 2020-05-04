package com.bubblebob.dd.model.dungeon.item;

import com.bubblebob.dd.model.DdGameModel;
import com.bubblebob.dd.model.GameMode;

public class SecondCrownPartItem extends Item{

	public SecondCrownPartItem(DdGameModel model) {
		super(model);
	}

	@Override
	public void doPickUp() {
		model.getWorld().getPlayer().getInfos().setGotSecondCrownPart(true);
		if (model.getWorld().getPlayer().getInfos().isGotFirstCrownPart()){
			model.getWorld().setWon(true);
			model.setMode(GameMode.WORLD);
		}
	}
	
	@Override
	public String getName() {
		return "CROWN2";
	}
}
