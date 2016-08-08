package com.bubblebob.uto.entity;

import com.bubblebob.uto.UtoModel;
import com.bubblebob.uto.SquareType;
import com.bubblebob.uto.entity.move.CanMoveOnWaterStrategy;

public abstract class MovingSeaEntity extends MovingEntity{

	public MovingSeaEntity(UtoModel model, String id, int x, int y, int w, int h) {
		super(model, id, x, y, w, h,new CanMoveOnWaterStrategy());
	}
	
	public int getGraphLayer() {
		return 0;
	}
	
}
