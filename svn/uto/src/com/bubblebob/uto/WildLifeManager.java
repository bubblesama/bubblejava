package com.bubblebob.uto;

import java.util.Random;

import com.bubblebob.uto.entity.FishEntity;
import com.bubblebob.uto.entity.PirateBoatEntity;
import com.bubblebob.uto.entity.RainCloudEntity;

public class WildLifeManager {

	private UtoModel model;
	private Random random = new Random(System.currentTimeMillis());
	
	private long nextCloudSpawn;
	private static long CLOUD_SPAWN_MIN = 5000;
	private static long CLOUD_SPAWN_RANGE = 15000;
	
	private long nextPirateSpawn;
	private static long PIRATE_SPAWN_MIN = 5000;
	private static long PIRATE_SPAWN_RANGE = 25000;
	
	private long nextFishSpawn;
	private static long FISH_SPAWN_MIN = 3000;
	private static long FISH_SPAWN_RANGE = 15000;
	
	public WildLifeManager(UtoModel model) {
		super();
		this.model = model;
	}
	
	public void udpate(){
		long current = System.currentTimeMillis();
		if (nextCloudSpawn < current){
			nextCloudSpawn = current+CLOUD_SPAWN_MIN+random.nextInt((int)CLOUD_SPAWN_RANGE);
			System.out.println("[WildLifeManager#update] SPAM, next: "+nextCloudSpawn);
			boolean found = false;
			int cloudI = random.nextInt(model.getMapWidth());
			int cloudJ = random.nextInt(model.getMapHeight());
			while (!found) {
				cloudI = random.nextInt(model.getMapWidth());
				cloudJ = random.nextInt(model.getMapHeight());
				found = true;
			}
			model.add(new RainCloudEntity(model, cloudI*model.getTileSize(), cloudJ*model.getTileSize(), model.getTileSize()-1, model.getTileSize()-1));
		}
		if (nextPirateSpawn < current){
			nextPirateSpawn = current+PIRATE_SPAWN_MIN+random.nextInt((int)PIRATE_SPAWN_RANGE);
			boolean northSouth = random.nextBoolean();
			int randomK = random.nextInt(northSouth?model.getMapWidth():model.getMapHeight());
			boolean firstSecond = random.nextBoolean();
			int i = northSouth?(randomK):(firstSecond?0:model.getMapWidth()-1);
			int j = northSouth?(firstSecond?0:model.getMapHeight()-1):(randomK);
			model.add(new PirateBoatEntity(model, i*model.getTileSize(), j*model.getTileSize(), model.getTileSize()-1, model.getTileSize()-1));
		}
		if (nextFishSpawn < current){
			nextFishSpawn = current+FISH_SPAWN_MIN+random.nextInt((int)FISH_SPAWN_RANGE);
			int fishAmount = random.nextInt(3);
			for (int k=0;k<fishAmount;k++){
				boolean found = false;
				int i = random.nextInt(model.getMapWidth());
				int j = random.nextInt(model.getMapHeight());
				while (!found) {
					i = random.nextInt(model.getMapWidth());
					j = random.nextInt(model.getMapHeight());
					if (model.map.grid[i][j].type == SquareType.SEA){
						found = true;
					}
				}
				model.add(new FishEntity(model, i*model.getTileSize(), j*model.getTileSize(), model.getTileSize()-1, model.getTileSize()-1));
			}
		}
	}
	
}
