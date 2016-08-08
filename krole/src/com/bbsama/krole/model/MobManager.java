package com.bbsama.krole.model;

import java.util.ArrayList;
import java.util.List;

public class MobManager {
	
	private static MobManager instance;
	private List<Mob> mobs;
	private Mob player;
	
	public static synchronized MobManager getInstance(){
		if (instance == null){
			instance = new MobManager();
		}
		return instance;
	}
	
	public MobManager(){
		this.mobs = new ArrayList<Mob>();
		//TODO loading
		
		//TODO insertion du joueur dans un endroit libre
		int i=0;
		int j=0;
		while(!WorldManager.getInstance().getLevelById(0).getCell(i, j).isPassable()){
			j++;
			if (j>= WorldManager.getInstance().getLevelById(0).h()){
				j=0;
				i++;
			}
		}
		this.mobs.add(new Mob(WorldManager.getInstance().getLevelById(0).getFirstPassableCell(), MobType.MONSTER, 1, 1, 1));
		this.mobs.add(new Mob(WorldManager.getInstance().getLevelById(0).getFirstPassableCell(), MobType.MONSTER, 1, 1, 1));
		this.mobs.add(new Mob(WorldManager.getInstance().getLevelById(0).getFirstPassableCell(), MobType.MONSTER, 1, 1, 1));
		this.mobs.add(new Mob(WorldManager.getInstance().getLevelById(0).getFirstPassableCell(), MobType.MONSTER, 1, 1, 1));
		this.player = new Mob(WorldManager.getInstance().getLevelById(0).getFirstPassableCell(), MobType.PLAYER, 1, 1, 1);
		this.mobs.add(this.player);
		
	}
	
	public List<Mob> getMobs(){
		return mobs;
	}
	
	public Mob getPlayer(){
		return player;
	}
	
	public boolean canPlayerAct(){
		return getPriorityMobs().contains(player);
	}
	
	public void actToPlayerInterrupt(){
		List<Mob> priorityMobs = getPriorityMobs();
		int minTimer = priorityMobs.get(0).timer;
		// joueur occupe ou pas en train de jouer
		while (player.isBusy()||!priorityMobs.contains(player)) {
			for (Mob mob: priorityMobs){
				mob.act();
			}
			advanceAllMobTimers(minTimer);
			priorityMobs = getPriorityMobs();
			minTimer = priorityMobs.get(0).timer;
		}
		
	}
	
	private List<Mob> getPriorityMobs(){
		int minTimer = 1000;
		for (Mob mob: mobs){
			if (mob.timer<minTimer){
				minTimer = mob.timer;
			}
		}
		List<Mob> result = new ArrayList<Mob>();
		for (Mob mob: mobs){
			if (mob.timer==minTimer){
				result.add(mob);
			}
		}
		return result;
	}
	
	private void advanceAllMobTimers(int advance){
		for (Mob mob: mobs){
			mob.timer-= advance;
		}
	}
	

}
