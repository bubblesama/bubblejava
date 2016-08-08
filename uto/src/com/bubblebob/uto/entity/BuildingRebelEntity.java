package com.bubblebob.uto.entity;

import com.bubblebob.uto.BuildingType;
import com.bubblebob.uto.UtoModel;

public class BuildingRebelEntity extends BuildingEntity{

	public BuildingRebelEntity(UtoModel model, PlayerEntity p, int x, int y) {
		super(model, p, BuildingType.REBEL, x, y);
	}
	
}
