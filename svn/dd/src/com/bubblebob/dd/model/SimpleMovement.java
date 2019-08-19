package com.bubblebob.dd.model;

import com.bubblebob.dd.DdConstants;

/**
 * Represente un mouvement simple
 */
public class SimpleMovement {

	// la direction du mouvement
	private Direction direction;
	// la longueur totale du mouvement (en pixels)
	private int xTotalLength;
	private int yTotalLength;
	// la longueur deja parcourue (en pixels)
	private int xLengthDone;
	private int yLengthDone;
	// le nombre de cycles de jeux pour effectuer un pas
	private int ticksToMove;
	// le nombre de cycles depuis le dernier pas
	private int currentTicks;
	// la longueur d'un pas(en pixels)
	private int stepWidth;
	private int stepHeight;


	public SimpleMovement(Direction direction, int xTotalLength,int yTotalLength, int xLengthDone, int yLengthDone, int ticksToMove, int currentTicks, int stepWidth, int stepHeight) {
		this.direction = direction;
		this.xTotalLength = xTotalLength;
		this.yTotalLength = yTotalLength;
		this.xLengthDone = xLengthDone;
		this.yLengthDone = yLengthDone;
		this.ticksToMove = ticksToMove;
		this.currentTicks = currentTicks;
		this.stepWidth = stepWidth;
		this.stepHeight = stepHeight;
	}

	public boolean isDone(){
		return xLengthDone >= xTotalLength && yLengthDone >= yTotalLength;
	}


	public static SimpleMovement getSimpleMovement(Direction direction){
		int ticksToMove = 1;
		switch (direction) {
		case NORTH:
		case SOUTH:
			return new SimpleMovement(direction,0,DdConstants.TILE_HEIGHT,0,0,ticksToMove,0,0,DdConstants.STEP_Y);
		case EAST:
		case WEST:
			return new SimpleMovement(direction,DdConstants.TILE_WIDTH,0,0,0,ticksToMove,0,DdConstants.STEP_X,0);
		case NONE:
			return new SimpleMovement(direction,0,0,0,0,ticksToMove,0,0,0);
		case NORTH_EAST:
		case SOUTH_EAST:
		case SOUTH_WEST:
		case NORTH_WEST:
			return new SimpleMovement(direction,DdConstants.TILE_WIDTH,DdConstants.TILE_HEIGHT,0,0,ticksToMove,0,DdConstants.STEP_X,DdConstants.STEP_Y);
		default:
			return null;
		}
	}
	
	public static SimpleMovement getSimpleMovement(Direction direction, int ticksToMove, int dX, int dY){
		switch (direction) {
		case NORTH:
		case SOUTH:
			return new SimpleMovement(direction,0,DdConstants.TILE_HEIGHT,0,0,ticksToMove,0,dX,dY);
		case EAST:
		case WEST:
			return new SimpleMovement(direction,DdConstants.TILE_WIDTH,0,0,0,ticksToMove,0,dX,0);
		case NONE:
			return new SimpleMovement(direction,0,0,0,0,ticksToMove,0,0,0);
		case NORTH_EAST:
		case SOUTH_EAST:
		case SOUTH_WEST:
		case NORTH_WEST:
			return new SimpleMovement(direction,DdConstants.TILE_WIDTH,DdConstants.TILE_HEIGHT,0,0,ticksToMove,0,dX,dY);
		default:
			return null;
		}
	}

	public MovementElement tick(){
		
		int maxAbsDX = Math.min(stepWidth, xTotalLength-xLengthDone);
		int maxAbsDY =  Math.min(stepHeight, yTotalLength-yLengthDone);
		int dY = 0;
		int dX = 0;
		if (!isDone()){
			currentTicks++;
			if (currentTicks>= ticksToMove){
				currentTicks = 0;
				// mouvement
				switch (direction) {
				case NORTH:
					dY = -maxAbsDY;
					break;
				case EAST:
					dX = maxAbsDX;
					break;
				case SOUTH:
					dY = maxAbsDY;
					break;
				case WEST:
					dX = -maxAbsDX;
					break;
				case NONE:
					break;
				case NORTH_EAST:
					dY = -maxAbsDY;
					dX = maxAbsDX;
					break;
				case SOUTH_EAST:
					dY = maxAbsDY;
					dX = maxAbsDX;
					break;
				case SOUTH_WEST:
					dY = maxAbsDY;
					dX = -maxAbsDX;
					break;
				case NORTH_WEST:
					dY = -maxAbsDY;
					dX = -maxAbsDX;
					break;
				default:
				}
				xLengthDone += Math.abs(dX);
				yLengthDone += Math.abs(dY);
			}
		}
		return new MovementElement(dX, dY);
	}

	public Direction getDirection() {
		return direction;
	}
	

	public String toString(){
		return "(SimpleMovement:"+direction+")";
	}
	
	

}
