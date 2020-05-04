package com.bubblebob.uto.entity;

import com.bubblebob.uto.BoatType;
import com.bubblebob.uto.UtoModel;

public abstract class BoatEntity extends MovingSeaEntity {

	public BoatType type;
	
	public BoatEntity(UtoModel model, String id, int x, int y, BoatType type) {
		super(model, id, x, y, model.getTileSize()-1, model.getTileSize()-1);
		this.type = type;
	}
	
	public final static int BOAT_STAT_OK = 0;
	public final static int BOAT_STAT_KO1 = 1;
	public final static int BOAT_STAT_KO2 = 2;
	public final static int BOAT_STAT_KO3 = 3;
	public final static int BOAT_STATE_TICK_TO_CHANGE = 10;
	
	public boolean changingState = false;
	public int changingStateTick = 0;
	public int state = BOAT_STAT_OK;

	public void update(){
		//super.update();
		if (changingState){
			changingStateTick++;
			if (changingStateTick >= BOAT_STATE_TICK_TO_CHANGE){
				state++;
				changingStateTick = 0;
				if (state == BOAT_STAT_KO3){
					toKill = true;
				}
			}
		} 
	}
	
	public void updateMovement() {
		if (!changingState){
			super.updateMovement();
		}else{
			newX = x;
			newY = y;
//			System.out.println("[BoatEntity#updateMovement] updateMovement: not!");
		}
	}
	
	public int getGraphLayer() {
		return 0;
	}
	
}
