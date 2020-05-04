package com.bubblebob.uto.entity.move;

import com.bubblebob.uto.entity.MovingEntity;

public class CanMoveEverywhereStrategy extends CanMoveAbstractStrategy{
	public CanMoveEverywhereStrategy() {
	}
	public boolean canMove(int newX, int newY) {
		int firstX = newX/entity.model.getTileSize();
		int lastX = (newX+entity.w)/entity.model.getTileSize();
		int firstY = newY/entity.model.getTileSize();
		int lastY = (newY+entity.h)/entity.model.getTileSize();
		if (firstX<0 || firstY<0 || lastX >= entity.model.getMapWidth() || lastY >= entity.model.getMapHeight()){
			return false;
		}
		return true;
	}

}
