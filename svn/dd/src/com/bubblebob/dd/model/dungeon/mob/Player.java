package com.bubblebob.dd.model.dungeon.mob;

import com.bubblebob.dd.model.PlayerInfos;
import com.bubblebob.dd.model.Position;
import com.bubblebob.dd.model.dungeon.Collideable;
import com.bubblebob.dd.model.dungeon.DungeonModel;

public class Player extends MobileObject implements Collideable{

	public static int DEFAULT_HIT_POINTS = 3;
	private static int MAX_DEATH_SPAN = 50;
	private PlayerInfos infos;
	private boolean deathStarted;
	private int deathSpan;

	public Player(DungeonModel model, int x, int y, int ticksToMove,int maxLifePoints, PlayerInfos infos) {
		super(model,x,y,ticksToMove,maxLifePoints);
		this.infos = infos;
		this.deathStarted = false;
	}
	
	public boolean isDeathStarted() {
		return deathStarted;
	}

	public void update(){
		updateMobileObject();
		if (getHitPoints() == 0){
			if (!deathStarted){
				//demarrage de la gestion de la mort
				getModel().getPuffManager().addPuff(new Puff(x,y,20));
				deathStarted = true;
				deathSpan = 0;
				stopRunDown();
				stopRunUp();
				stopRunLeft();
				stopRunRight();
			}else{
				deathSpan++;
				if (deathSpan == MAX_DEATH_SPAN){
					deathStarted = false;
					// fin de la mort
					if (infos.getLives() > 0){
						// il reste des vies
						infos.setLives(infos.getLives()-1);
						setHitPoints(DEFAULT_HIT_POINTS);
						Position firstTile = getModel().getMap().getFirstEmptyTile();
						int playerX = firstTile.getTileX()*getModel().getTileWidth()+1;
						int playerY = firstTile.getTileY()*getModel().getTileHeight()+1;
						setX(playerX);
						setY(playerY);
					}else{
						// plus de vie...
						getModel().lost = true;
						getModel().pause();
					}
				}
			}
		}
	}

	public void collide() {
		tryToHit();
	}

	@Override
	public void chooseDirection() {
		// au moment du choix de direction, on regarde sa case
		getModel().getMap().discoverTile(getCurrentTile());

	}

	public void setGotBoat(boolean getBoat) {
		infos.setGotBoat(getBoat);
	}

	public void setGotAxe(boolean getAxe) {
		infos.setGotAxe(getAxe);
	}

	public void setGotKey(boolean getKey) {
		infos.setGotKey(getKey);
	}

	public int getArrows() {
		return infos.getArrows();
	}

	public void addArrows(int arrows) {
		infos.addArrows(arrows);
	}

	public void resetArrows() {
		infos.resetArrows();
	}

	public void removeArrow() {
		infos.removeArrow();
	}

}
