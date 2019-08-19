package com.bubblebob.uto.entity;

import java.util.Random;

import com.bubblebob.uto.UtoModel;

public abstract class RandomMovingSeaEntity extends MovingSeaEntity{

	public RandomMovingSeaEntity(UtoModel model, String id, int x, int y, int w, int h) {
		super(model, id,x, y, w, h);
		this.movingTicksToMove = 5;
		this.aiPathChoiceToDie = random.nextInt(aiPathChoiceRange)+aiPathChoiceMin;
	}

	private int aiPathChoiceTick;
	private int aiPathChoicePeriodMin = 50;
	private int aiPathChoicePeriodRange = 60;
	private int aiPathChoicePeriod = aiPathChoicePeriodRange;
	private int aiPathChoiceCount = 0;
	private int aiPathChoiceToDie = 2;
	private int aiPathChoiceMin = 2;
	private int aiPathChoiceRange = 10;
	private static Random random = new Random();

	public void update(){
		//System.out.println("[RandomMovingSeaEntity#update] IN");
		aiPathChoiceTick++;
		if (aiPathChoiceTick >= aiPathChoicePeriod){
			//System.out.println("[RandomMovingSeaEntity#update] aiPathChoice");
			aiPathChoiceCount++;
			if (aiPathChoiceCount>aiPathChoiceToDie){
				toKill = true;
				//System.out.println("[RandomMovingSeaEntity#update] kill:!");
			}
			aiPathChoicePeriod = random.nextInt(aiPathChoicePeriodRange)+aiPathChoicePeriodMin;
			aiPathChoiceTick = 0;
			if (random.nextInt(4) > 0){
				dx = random.nextInt(3)-1;
				dy = random.nextInt(3)-1;
			}else{
				dx = 0;
				dy = 0;
			}
		}
		super.update();
	}

}
