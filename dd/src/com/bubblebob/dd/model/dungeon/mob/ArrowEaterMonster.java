package com.bubblebob.dd.model.dungeon.mob;

import com.bubblebob.dd.model.dungeon.DungeonModel;

public class ArrowEaterMonster extends Monster{

	public ArrowEaterMonster(DungeonModel model, int x, int y,MonsterType type) {
		super(model, x, y, type);
	}

	@Override
	public void hitPlayer() {
		getModel().getPlayer().tryToHit();
	}

}
