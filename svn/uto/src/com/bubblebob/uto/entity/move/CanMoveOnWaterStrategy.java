package com.bubblebob.uto.entity.move;

import com.bubblebob.uto.SquareType;
import com.bubblebob.uto.entity.MovingEntity;

public class CanMoveOnWaterStrategy extends CanMoveAbstractStrategy{
	public CanMoveOnWaterStrategy() {
		super();
	}
	public boolean canMove(int newX, int newY) {
		return !entity.model.collideType(SquareType.LAND, newX,  newY, entity.w, entity.h);
	}
}
