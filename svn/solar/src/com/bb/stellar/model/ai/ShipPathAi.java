package com.bb.stellar.model.ai;

import java.util.ArrayList;
import java.util.List;

import com.bb.stellar.model.Place;
import com.bb.stellar.model.Ship;

public class ShipPathAi {

	private List<Target> targets;
	private Ship ship;
	private Target target;
	private double targetX;
	private double targetY;
	private boolean reconsider = false;



	private static final int AI_PACE = 10;
	private int currentAiTick = 0;

	public ShipPathAi(Ship ship) {
		this.ship = ship;
		this.targets = new ArrayList<Target>();
	}

	public void clearTargets(){
		targets.clear();
	}

	public void addTarget(Target target){
		this.targets.add(target);
	}

	public void update(){
		// calcul d'approche
		if (target != null){
			boolean come = false;
			double targetDist = Math.max((targetX-ship.getStellarX())*(targetX-ship.getStellarX()),(targetY-ship.getStellarY())*(targetY-ship.getStellarY()));
			come = targetDist < 1.1 * (ship.SPEED);
			if (come){
				target = null;
				ship.setSpeed(0, 0);
			}
		}
		// decision
		currentAiTick++;
		if (reconsider){
			reconsider = false;
			if (!targets.isEmpty()){
				setTargetAndSpeedUp(targets.get(0));
				targets.remove(0);
			}
		}
		if (currentAiTick> AI_PACE || reconsider){
			currentAiTick = 0;
			choosePath();
		}
	}

	public void choosePath(){
		if (target!=null){
			if (target.getStellarX() != targetX || target.getStellarY() != targetY){
				// calcul nouvelle vitesse TODO
				calculateSpeed();
			}
		}else{
			if (!targets.isEmpty()){
				target = targets.get(0);
				targets.remove(0);
				calculateSpeed();
			}
		}
	}		

	private void calculateSpeed(){
		double newDx = target.getStellarX()-ship.getStellarX();
		double newDy = target.getStellarY()-ship.getStellarY();
		double total = Math.sqrt(newDx*newDx+newDy*newDy);
		ship.setSpeed(newDx/total*ship.SPEED,newDy/total*ship.SPEED);
		targetX = target.getStellarX();
		targetY = target.getStellarY();
	}

	public void choosePath2(){
		if (target == null){
			// pas de cible
			if (!targets.isEmpty()){
				// nouvelle cible
				target = targets.get(0);
			}
		}else{
			// cible en cours
			if (ship.dx == 0 && ship.dy == 0 || targetX != target.getStellarX() || targetY != target.getStellarY()){
				setTargetAndSpeedUp(target);
			}
		}
	}


	public void reconsider(){
		reconsider = true;
	}

	private void setTargetAndSpeedUp(Target t){
		target = t;
		targetX = t.getStellarX();
		targetY = t.getStellarY();
		// vitesse a choisir car pas de vitesse ou cible a bouge
		double newDx = target.getStellarX()-ship.getStellarX();
		double newDy = target.getStellarY()-ship.getStellarY();
		double total = Math.sqrt(newDx*newDx+newDy*newDy);
		ship.setSpeed(newDx/total*ship.SPEED,newDy/total*ship.SPEED);
	}

}
