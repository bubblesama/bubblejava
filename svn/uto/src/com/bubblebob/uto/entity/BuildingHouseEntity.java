package com.bubblebob.uto.entity;

import com.bubblebob.uto.BuildingType;
import com.bubblebob.uto.UtoModel;
import com.bubblebob.uto.PlayerType;

public class BuildingHouseEntity extends BuildingEntity{

	public BuildingHouseEntity(UtoModel model, PlayerEntity p, int x, int y) {
		super(model, p, BuildingType.HOUSE, x, y);
	}
	
}
