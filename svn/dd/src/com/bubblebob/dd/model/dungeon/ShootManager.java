package com.bubblebob.dd.model.dungeon;

import java.util.List;
import java.util.Vector;

import com.bubblebob.dd.model.Direction;
import com.bubblebob.dd.model.PlayerInfos;
import com.bubblebob.dd.model.dungeon.mob.Player;

/**
 * Gestion des projectiles
 */
public class ShootManager {

	// compteur
	private int ticksSinceLastShoot = 0;
	private int ticksToShoot = 4;

	private boolean prepareShoot = false;
	private Direction nextShootDirection = Direction.NONE;

	private Player player;
	private DungeonModel model;

	private int lifeSpan;
	private int ticksToMove;
	
	private PlayerInfos infos;
	
	private List<Shoot> shoots;

	public ShootManager(DungeonModel model, Player player, int lifeSpan, int ticksToMove, PlayerInfos infos){
		this.player = player;
		this.model = model;
		this.lifeSpan = lifeSpan;
		this.ticksToMove = ticksToMove;
		this.infos = infos;
		
		this.shoots = new Vector<Shoot>();
	}

//	public void setAvalaibleArrowsCounter(int counter){
//		this.avalaibleArrowsCounter = counter;
//	}
	
	public void addArrows(int arrows){
		infos.addArrows(arrows);
	}
	

	public int getTicksSinceLastShoot() {
		return ticksSinceLastShoot;
	}


	public void setStepsSinceLastShoot(int stepsSinceLastShoot) {
		this.ticksSinceLastShoot = stepsSinceLastShoot;
	}


	public int getStepsToShoot() {
		return ticksToShoot;
	}


	public void setStepsToShoot(int stepsToShoot) {
		this.ticksToShoot = stepsToShoot;
	}

	public boolean isShootPrepared(){
		return prepareShoot;
	}

	public void setShootPrepared(boolean prepareShoot){
		this.prepareShoot = prepareShoot;
	}

	public void update(){

		//System.out.println("[ShootManager#update] shoots: "+shoots.size());
		//<maj des tirs en cours...
		for (Shoot shoot: shoots){
			shoot.update();
		}
		//...maj des tirs en cours>
		//<nettoyage des tirs termines...
		List<Shoot> shootsToRemove = new Vector<Shoot>();
		for (Shoot shoot: shoots){
			if (shoot.isFinished()){
				shootsToRemove.add(shoot);
			}
		}
		for (Shoot shootToRemove: shootsToRemove){
			shoots.remove(shootToRemove);
		}
		//...nettoyage des tirs termines>

		//<creation eventuelle d'un nouveau tir...
		if (prepareShoot){
			if  (infos.getArrows()>0){
				if (ticksSinceLastShoot >= ticksToShoot){
					prepareShoot = false;
					ticksSinceLastShoot = 0;
					Shoot shoot = new Shoot(model,player.getXTile(), player.getYTile(),nextShootDirection,lifeSpan, ticksToMove);
					shoots.add(shoot);
					infos.removeArrow();
					System.out.println("[ShootManager#update] new arrow, available: "+infos.getArrows());
				}
			}else{
				prepareShoot = false;
				ticksSinceLastShoot++;
			}
		}else{
			ticksSinceLastShoot++;
		}
		//...creation eventuelle d'un nouveau tir>
	}

	public void setNextShootDirection(Direction nextShootDirection) {
		this.nextShootDirection = nextShootDirection;
	}

	public List<Shoot> getShoots(){
		return shoots;
	}

}
