package com.bubblebob.dd.model.dungeon.mob;

import com.bubblebob.dd.model.Position;
import com.bubblebob.dd.model.dungeon.Collideable;
import com.bubblebob.dd.model.dungeon.DungeonModel;

public abstract class Monster extends MobileObject implements Collideable {

	// ATTRIBUTS
	private MonsterType type;
	
	private int lastPerformHitTick = 0;
	private int ticksToReperformHit = 10;
	

	// CONSTRUCTEUR
	public Monster(DungeonModel model, int x, int y, MonsterType type) {
		super(model,x,y,type.getTicksToMove(),type.getInitialLife());
		this.type = type;
	}

	// ACCESSEURS
	public int getX(){
		return x;
	}

	public int getY(){
		return y;
	}

	public MonsterType getType(){
		return type;
	}

	// METHODES
	public void collide(){
		tryToHit();
		if (getHitPoints() <= 0){
			getModel().getMonsterManager().removeMonster(this);
			getModel().getPuffManager().addPuff(new Puff(x,y,20));
		}
	}

	public void update(){
		if (getModel().getMap().getTile(getPosition()).isDiscovered()|| getType().isAlwaysVisible()){
			Position playerPosition = getModel().getPlayer().getPosition();
			Position myPosition = getPosition();
			if (myPosition.getTileX() == playerPosition.getTileX() &&  myPosition.getTileY() == playerPosition.getTileY() && (getModel().getTicker()-lastPerformHitTick > ticksToReperformHit)){
				lastPerformHitTick = getModel().getTicker();
				hitPlayer();
				//System.out.println("[Monster#update] hit on "+playerPosition.getTileX()+" "+playerPosition.getTileY());
			}
			updateMobileObject();
		}
	}
	
	public abstract void hitPlayer();

	@Override
	public void chooseDirection() {
		//System.out.println("[Monster#chooseDirection] IN");
		stopRunUp();
		stopRunRight();
		stopRunDown();
		stopRunLeft();
		Position playerPosition = getModel().getPlayer().getPosition();
		Position myPosition = getPosition();

		//<choix de la direction selon X...
		int myX = myPosition.getTileX();
		int playerX = playerPosition.getTileX();
		if (myX != playerX){
			int directXDistance = Math.abs(myX-playerX);
			int indirectXDistance = getModel().getMap().getWidth()-directXDistance;
			if (myPosition.getTileX() > playerPosition.getTileX()){
				if (directXDistance > indirectXDistance){
					makeRunRight();
				}else{
					makeRunLeft();
				}
			}else{
				if (directXDistance > indirectXDistance){
					makeRunLeft();
				}else{
					makeRunRight();
				}
			}
		}
		//...choix de la direction selon X>
		
		//<choix de la direction selon Y...
		int myY = myPosition.getTileY();
		int playerY = playerPosition.getTileY();
		if (myY != playerY){
			int directYDistance = Math.abs(myY-playerY);
			int indirectYDistance = getModel().getMap().getHeight()-directYDistance;
			if (myPosition.getTileY() > playerPosition.getTileY()){
				if (directYDistance > indirectYDistance){
					makeRunDown();
				}else{
					makeRunUp();
				}
			}else{
				if (directYDistance > indirectYDistance){
					makeRunUp();
				}else{
					makeRunDown();
				}
			}
		}
		//...choix de la direction selon X>
	}
	

}
