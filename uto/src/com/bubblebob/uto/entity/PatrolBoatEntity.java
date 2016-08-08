package com.bubblebob.uto.entity;

import java.awt.Graphics;

import com.bubblebob.uto.BoatType;
import com.bubblebob.uto.UtoModel;

public class PatrolBoatEntity extends PlayerBoatEntity{
	
	public PatrolBoatEntity(UtoModel model, int x, int y, PlayerEntity player) {
		super(model,x,y,BoatType.PATROL,player);
	}
	
	public void paint(Graphics g){
		g.drawImage(assets.getBoat(player.getType(), BoatType.PATROL,state),x*assets.getScale(), y*assets.getScale(), null);
	}
	
}
