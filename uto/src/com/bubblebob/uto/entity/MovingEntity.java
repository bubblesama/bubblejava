package com.bubblebob.uto.entity;

import com.bubblebob.uto.UtoModel;
import com.bubblebob.uto.entity.move.CanMoveStrategy;

public abstract class MovingEntity extends Entity{
	
	public MovingEntity(UtoModel model,String id, int x, int y, int w, int h,CanMoveStrategy canMoveStrategy) {
		super(model, id,x, y, w, h);
		this.newX = x;
		this.newY = y;
		this.canMoveStrategy = canMoveStrategy;
		this.canMoveStrategy.registerMob(this);
	}

	public int dx;
	public int dy;
	
	public int newX;
	public int newY;

	public int movingTicksToMove = 1;
	public int movingTicks = 0;
	
	public CanMoveStrategy canMoveStrategy;
	
	public void update(){
		movingTicks++;
		newX = x;
		newY = y;
		if (movingTicks >= movingTicksToMove){
			movingTicks = 0;
			if (canMove(x+dx, y+dy)){
				newX = x+dx;
				newY = y+dy;
			}
		}
	}
	
	public boolean canMove(int newX, int newY){
		return canMoveStrategy.canMove(newX, newY);
	}
	
	public void updateMovement(){
		model.registerNewMovingEntityPosition(this, x, y, newX, newY);
		x = newX;
		y = newY;
	}

}
