package com.bubblebob.uto.entity.move;

import com.bubblebob.uto.entity.MovingEntity;

public abstract class CanMoveAbstractStrategy implements CanMoveStrategy{
	public MovingEntity entity;
	public CanMoveAbstractStrategy(){}
	public void registerMob(MovingEntity mob){
		this.entity = mob;
	}
}
