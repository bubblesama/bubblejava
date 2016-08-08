package com.bubblebob.uto.entity;

import java.awt.Graphics;
import java.util.Random;

import com.bubblebob.uto.PlayerType;
import com.bubblebob.uto.UtoModel;

public class FishEntity extends RandomMovingSeaEntity {
	
	public FishEntity(UtoModel model, int x, int y, int w, int h) {
		super(model, "fish",x, y, w, h);
		movingTicksToMove = 10;
		currentBlinkMaxTick = random.nextInt(moveBlinkRange)+minBlinkTick;
	}
	
	public int minBlinkTick = 10;
	public int moveBlinkRange = 60;
	public int currentBlinkMaxTick;
	public int currentBlinkTick = 0;
	public boolean otherBlink = false;
	public final static Random random = new Random(System.currentTimeMillis());
	private final static int timeToGive = 30;
	private int redGive = 0;
	private int greenGive = 0;
	
	public void update(){
		super.update();
		currentBlinkTick++;
		if (currentBlinkTick >= currentBlinkMaxTick){
			currentBlinkMaxTick = random.nextInt(moveBlinkRange)+minBlinkTick;
			currentBlinkTick = 0;
			otherBlink = !otherBlink;
		}
	}
	
	public void touched(Entity touchingEntity){
		if (touchingEntity instanceof FishBoatEntity) {
			FishBoatEntity touchingBoat = (FishBoatEntity) touchingEntity;
//			System.out.println("TOUCHED");
			if (touchingBoat.player.getType() == PlayerType.GREEN){
				greenGive++;
				if (greenGive >= timeToGive){
					greenGive = 0;
					touchingBoat.player.creditWealth(2);
				}
			}else{
				redGive++;
				if (redGive >= timeToGive){
					redGive = 0;
					touchingBoat.player.creditWealth(2);
				}
			}
		}
	}
	
	public int getGraphLayer() {
		return 4;
	}
	
	public void paint(Graphics g){
		g.drawImage(assets.getFish(otherBlink),x*assets.getScale(), y*assets.getScale(), null);
	}
	
}
