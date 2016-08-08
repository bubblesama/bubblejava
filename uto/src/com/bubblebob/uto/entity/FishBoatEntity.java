package com.bubblebob.uto.entity;

import java.awt.Graphics;

import com.bubblebob.uto.BoatType;
import com.bubblebob.uto.MovMode;
import com.bubblebob.uto.PlayerType;
import com.bubblebob.uto.UtoModel;

public class FishBoatEntity extends PlayerBoatEntity{
	
	public FishBoatEntity(UtoModel model, int x, int y, PlayerEntity player) {
		super(model,x,y,BoatType.FISHING,player);
		System.out.println("[FishBoatEntity#new] x="+x);
	}
	
	public void paint(Graphics g){
		g.drawImage(assets.getBoat(player.getType(), BoatType.FISHING,state),x*assets.getScale(), y*assets.getScale(), null);
	}

	public void touched(Entity touchingEntity){
		if (touchingEntity instanceof PatrolBoatEntity) {
			PatrolBoatEntity touchingBoat = (PatrolBoatEntity) touchingEntity;
			if (touchingBoat.player.getType() != player.getType()){
				launchDestroy();
			}
		}
		if (touchingEntity instanceof PirateBoatEntity) {
			PirateBoatEntity pirateBoat = (PirateBoatEntity) touchingEntity;
			launchDestroy();
		}
	}
	
	public void launchDestroy(){
		changingState = true;
		if (this == player.currentBoat){
			player.mode = MovMode.CURSOR;
			player.currentBoat = null;
		}
	}
	
}
