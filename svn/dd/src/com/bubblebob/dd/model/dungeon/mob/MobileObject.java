package com.bubblebob.dd.model.dungeon.mob;

import com.bubblebob.dd.DdConstants;
import com.bubblebob.dd.model.Direction;
import com.bubblebob.dd.model.MovementElement;
import com.bubblebob.dd.model.Position;
import com.bubblebob.dd.model.SimpleMovement;
import com.bubblebob.dd.model.dungeon.DungeonModel;
import com.bubblebob.dd.model.dungeon.map.DungeonTile;

public abstract class MobileObject {

	private DungeonModel model;
	protected int x;
	protected int y;
	private boolean runsUp;
	private boolean runsRight;
	private boolean runsDown;
	private boolean runsLeft;
	private SimpleMovement currentMovement;
	private int consecutiveSteps;
	private boolean facesRight;
	private int ticksToMove;
	private int hitPoints;
	private int ticksSinceLastHit;
	private int ticksToRehit;
	
	
	
	public MobileObject(DungeonModel model, int x, int y, int ticksToMove, int hitPoints){
		this.model = model;
		//System.out.println("[MobileObject#init] model = "+this.model+" x="+this.x+" y="+this.y);
		this.x = x;
		this.y = y;
		this.runsLeft = false;
		this.runsRight = false;
		this.runsUp = false;
		this.runsDown = false;
		this.consecutiveSteps = 0;
		this.currentMovement = SimpleMovement.getSimpleMovement(Direction.NONE);
		this.facesRight = true;
		this.ticksToMove = ticksToMove;
		this.hitPoints = hitPoints;
		this.ticksToRehit = DdConstants.TICK_TO_HIT;
		this.ticksSinceLastHit = ticksToRehit+1;
		
	}
	// ACCESSEURS
	
	public DungeonModel getModel(){
		return model;
	}
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	
	public int getConsecutiveSteps() {
		return consecutiveSteps;
	}
	public void addConsecutiveStep() {
		this.consecutiveSteps++;
	}
	
	public void resetConsecutiveSteps() {
		this.consecutiveSteps = 0;
	}
	
	public boolean facesRight() {
		return facesRight;
	}

	public boolean facesLeft() {
		return !facesRight;
	}
	public void makeFaceRight() {
		this.facesRight = true;
	}
	public void makeFaceLeft() {
		this.facesRight = false;
	}
	
	public boolean runsLeft(){
		return runsLeft;
	}
	public void makeRunLeft() {
		this.runsLeft = true;
	}
	public void stopRunLeft() {
		this.runsLeft = false;
	}
	public boolean runsRight(){
		return runsRight;
	}
	public void makeRunRight() {
		this.runsRight = true;
	}
	public void stopRunRight() {
		this.runsRight = false;
	}
	public boolean runsUp(){
		return runsUp;
	}
	public void makeRunUp() {
		this.runsUp = true;
	}
	public void stopRunUp() {
		this.runsUp = false;
	}
	public boolean runsDown(){
		return runsDown;
	}
	public void makeRunDown() {
		this.runsDown = true;
	}

	public void stopRunDown() {
		this.runsDown = false;
	}
	
	public int getHitPoints() {
		return hitPoints;
	}

	// METHODES METIER
	
	/**
	 * Fournit la position en coordonnees de case
	 */
	public Position getPosition(){
		return (new Position(getXTile(),getYTile()));
	}
	
	public int getXTile(){
		//System.out.println("[Player#getXTile] model = "+model);
		return x/model.getTileWidth();
	}

	public int getYTile(){
		return  y/model.getTileHeight();
	}
	
	private SimpleMovement nextMovement(){
		SimpleMovement result = null;
		Direction direction =  Direction.NONE;
		if (runsUp){
			if (runsRight){
				direction = Direction.NORTH_EAST;
			}else if (runsLeft){
				direction = Direction.NORTH_WEST;
			}else{
				direction = Direction.NORTH;
			}
		}else if (runsDown){
			if (runsRight){
				direction = Direction.SOUTH_EAST;
			}else if (runsLeft){
				direction = Direction.SOUTH_WEST;
			}else{
				direction = Direction.SOUTH;
			}
		}else if (runsRight){
			direction = Direction.EAST;
		}else if (runsLeft){
			direction = Direction.WEST;
		}else{
			resetConsecutiveSteps();
		}
		//System.out.println("[MobileObject#nextMovement] x="+x+" y="+y);
		result = nextMovement(x/model.getTileWidth(), y/model.getTileHeight(), direction, ticksToMove, DdConstants.STEP_X, DdConstants.STEP_Y);
		if (result.getDirection() == Direction.NONE){
			resetConsecutiveSteps();
		}
		return result;
	}
	
	/**
	 * Calcule le prochain mouvement de l'objet mobile en fonction des cases environnantes et de la direction actuelle de l'objet
	 * @param currentXTile
	 * @param currentYTile
	 * @param direction
	 * @param ticksToMove
	 * @param dX
	 * @param dY
	 * @return
	 */
	public SimpleMovement nextMovement(int currentXTile, int currentYTile, Direction direction, int ticksToMove, int dX, int dY){
		int dXTile = 0;
		int dYTile = 0;
		switch (direction) {
		case NORTH:
			dYTile--;
			break;
		case NORTH_EAST:
			dXTile++;
			dYTile--;
			break;
		case EAST:
			dXTile++;
			break;
		case SOUTH_EAST:
			dXTile++;
			dYTile++;
			break;
		case SOUTH:
			dYTile++;
			break;
		case SOUTH_WEST:
			dXTile--;
			dYTile++;
			break;
		case WEST:
			dXTile--;
			break;
		case NORTH_WEST:
			dXTile--;
			dYTile--;
			break;
		default:
			break;
		}
		int nextTileX = (currentXTile+dXTile);
		int nextTileY = (currentYTile+dYTile);
		
		DungeonTile nextTile = model.getMap().getTile(nextTileX,nextTileY);
//		if (dXTile != 0 || dYTile != 0){
//			System.out.println("[MobileObject#nextMovement] nextTile("+nextTileX+","+nextTileY+")="+nextTile.getType());
//		}
		switch (nextTile.getType()) {
		case EMPTY:
			return SimpleMovement.getSimpleMovement(direction,ticksToMove,dX,dY);
		case WALL:
			return SimpleMovement.getSimpleMovement(Direction.NONE,ticksToMove,dX,dY);
		case UP_RIGHT_CORNER:
			switch (direction){
			case NORTH:
				return SimpleMovement.getSimpleMovement(Direction.NORTH_WEST,ticksToMove,dX,dY);
			case EAST:
				return SimpleMovement.getSimpleMovement(Direction.SOUTH_EAST,ticksToMove,dX,dY);
			default:
				return SimpleMovement.getSimpleMovement(Direction.NONE,ticksToMove,dX,dY);
			}
		case DOWN_RIGHT_CORNER:
			switch (direction){
			case SOUTH:
				return SimpleMovement.getSimpleMovement(Direction.SOUTH_WEST,ticksToMove,dX,dY);
			case EAST:
				return SimpleMovement.getSimpleMovement(Direction.NORTH_EAST,ticksToMove,dX,dY);
			default:
				return SimpleMovement.getSimpleMovement(Direction.NONE,ticksToMove,dX,dY);
			}
		case DOWN_LEFT_CORNER:
			switch (direction){
			case SOUTH:
				return SimpleMovement.getSimpleMovement(Direction.SOUTH_EAST,ticksToMove,dX,dY);
			case WEST:
				return SimpleMovement.getSimpleMovement(Direction.NORTH_WEST,ticksToMove,dX,dY);
			default:
				return SimpleMovement.getSimpleMovement(Direction.NONE,ticksToMove,dX,dY);
			}
		case UP_LEFT_CORNER:
			switch (direction){
			case NORTH:
				return SimpleMovement.getSimpleMovement(Direction.NORTH_EAST,ticksToMove,dX,dY);
			case WEST:
				return SimpleMovement.getSimpleMovement(Direction.SOUTH_WEST,ticksToMove,dX,dY);
			default:
				return SimpleMovement.getSimpleMovement(Direction.NONE,ticksToMove,dX,dY);
			}
		default:
			return SimpleMovement.getSimpleMovement(Direction.NONE,ticksToMove,dX,dY);
		}
	}
	
	private void move(int dX, int dY){
		x = (x+dX+model.getPixelWidth())%model.getPixelWidth();
		y = (y+dY+model.getPixelHeight())%model.getPixelHeight();
//		x+= dX;
//		y+= dY;
	}
	
	/**
	 * Methode appelee entre chaque mouvement pour choisir le deplacement
	 */
	public abstract void chooseDirection();
	
	public void updateMobileObject(){
		ticksSinceLastHit++;
		if (ticksSinceLastHit%2 == 0){
			consecutiveSteps++;
		}
		//<gestion du mouvement...
		if (currentMovement.isDone()){
			chooseDirection();
			currentMovement = nextMovement();
		}else{
			MovementElement	movementElement = currentMovement.tick();
			if (movementElement.getDX()!=0 ||  movementElement.getDY() != 0){
//				consecutiveSteps++;
			}
			if (movementElement.getDX()>0){
				makeFaceRight();
			}else
				if (movementElement.getDX()<0){
					makeFaceLeft();
				}
			move(movementElement.getDX(), movementElement.getDY());
		}
		//...gestion du mouvement>
	}
	
	public void tryToHit(){
		if (ticksSinceLastHit >= ticksToRehit && hitPoints > 0){
			System.out.println("[MobileObject#tryToHit] success on hit");
			hitPoints--;
			ticksSinceLastHit = 0;
		}
	}
	
	public DungeonTile getCurrentTile(){
		return model.getMap().getTile(getPosition());
	}

	public void setHitPoints(int hitPoints){
		this.hitPoints = hitPoints;
	}
	
}
