package com.bubblebob.uto.entity;

import java.awt.Graphics;

import com.bubblebob.uto.BoatType;
import com.bubblebob.uto.UtoModel;

public class PirateBoatEntity extends RandomMovingSeaEntity{

	public final static int BOAT_STAT_OK = 0;
	public final static int BOAT_STAT_KO1 = 1;
	public final static int BOAT_STAT_KO2 = 2;
	public final static int BOAT_STAT_KO3 = 3;
	public final static int BOAT_STATE_TICK_TO_CHANGE = 10;
	
	public boolean changingState = false;
	public int changingStateTick = 0;
	public int state = BOAT_STAT_OK;
	
	public PirateBoatEntity(UtoModel model, int x, int y, int w, int h) {
		super(model, "pirate",x, y, w, h);
	}

	public void paint(Graphics g){
		g.drawImage(assets.getBoat(null, BoatType.PIRATE,state),x*assets.getScale(), y*assets.getScale(), null);
	}
	
	public void update() {
		super.update();
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
	
	public int getGraphLayer() {
		return 5;
	}
	
}
