package com.bubblebob.uto.entity.move;

import com.bubblebob.uto.entity.MovingEntity;

public interface CanMoveStrategy {
	public boolean canMove(int newX, int newY);
	public void registerMob(MovingEntity mob);
}
