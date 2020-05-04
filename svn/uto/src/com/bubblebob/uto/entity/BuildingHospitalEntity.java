package com.bubblebob.uto.entity;

import com.bubblebob.uto.BuildingType;
import com.bubblebob.uto.UtoModel;
import com.bubblebob.uto.PlayerType;

public class BuildingHospitalEntity extends BuildingEntity{

	public BuildingHospitalEntity(UtoModel model, PlayerEntity p, int x, int y) {
		super(model, p, BuildingType.HOSPITAL, x, y);
	}
	
}
