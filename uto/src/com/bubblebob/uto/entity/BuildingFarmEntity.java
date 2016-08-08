package com.bubblebob.uto.entity;

import com.bubblebob.uto.BuildingType;
import com.bubblebob.uto.UtoModel;
import com.bubblebob.uto.PlayerType;

public class BuildingFarmEntity extends BuildingEntity{

	private final static int rainToCash = 10;
	private int rain = 0;
	public boolean cashThisTurn = false;
	
	public int cycles = 3;
	
	public BuildingFarmEntity(UtoModel model, PlayerEntity p, int x, int y) {
		super(model, p, BuildingType.FARM, x, y);
	}
	
	public void touched(Entity touchingEntity) {
		if (touchingEntity instanceof RainCloudEntity) {
			rain++;
			if (rain >= rainToCash){
				player.creditWealth(1);
				rain = 0;
				cashThisTurn = true;
				cycles = 3;
			}
		}
	}
	
}
