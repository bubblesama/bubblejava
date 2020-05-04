package com.bubblebob.uto.entity;

import com.bubblebob.uto.BoatType;
import com.bubblebob.uto.UtoModel;

public abstract class PlayerBoatEntity extends BoatEntity{

	public PlayerBoatEntity(UtoModel model, int x, int y, BoatType type, PlayerEntity player) {
		super(model, "boat:"+type+":"+player,x, y,type);
		System.out.println("[PlayerBoatEntity#new] IN x="+x+" y="+y);
		this.player = player;
		this.controlled = false;
	}
	
	public PlayerEntity player;
	public boolean controlled = false;
	
	public void update(){
		if (controlled){
			dx = player.dx;
			dy = player.dy;
		}else{
			dx = 0;
			dy = 0;
		}
		super.update();
	}
	
	public int getGraphLayer() {
		return 5;
	}
	
}
