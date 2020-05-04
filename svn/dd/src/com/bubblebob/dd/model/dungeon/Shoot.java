package com.bubblebob.dd.model.dungeon;

import java.util.Random;

import com.bubblebob.dd.model.Direction;
import com.bubblebob.dd.model.MovementElement;
import com.bubblebob.dd.model.dungeon.map.DungeonTile;
import com.bubblebob.dd.model.dungeon.map.DungeonTileType;

public class Shoot {

	// l'identifiant unique de la fleche
	private int id;
	private DungeonModel model;
	private int tileX;
	private int tileY;
	private Direction direction;
	private boolean finished = false;
	// la duree de vie de la fleche en ticks de jeu
	private int ticksSpan;
	// l'age de la fleche en ticks
	private int currentSpanTick;
	private int ticksToMove;
	private int currentMoveTick;



	public Shoot(DungeonModel model, int tileX, int tileY,  Direction direction, int ticksSpan, int ticksToMove) {
		this.id = Math.abs(new Random(System.currentTimeMillis()).nextInt());
		this.model = model;
		this.tileX = tileX;
		this.tileY = tileY;
		this.direction = direction;
		this.finished = false;
		this.ticksSpan = ticksSpan;
		this.currentSpanTick = 0;
		this.ticksToMove = ticksToMove;
		this.currentMoveTick = 0;
	}

	public int getTileX() {
		return tileX;
	}

	public int getTileY() {
		return tileY;
	}

	public Direction getDirection() {
		return direction;
	}
	
	public boolean isFinished() {
		return finished;
	}

	public void update(){
		if (!finished){
			currentSpanTick++;
			if (currentSpanTick >= ticksSpan){
				finished = true;
			}else{
				currentMoveTick++;
				if (currentMoveTick >=  ticksToMove){
					currentMoveTick = 0;
					MovementElement movement = direction.getMovement();
					DungeonTile nextTile = model.getMap().getTile(tileX+movement.getDX(), tileY+movement.getDY());
					//System.out.println("[Shoot#update] moving: ("+tileX+","+tileY+"))=> "+direction+" && "+nextTile.getType()+" =>("+(tileX+movement.getDX())+","+(tileY+movement.getDY())+")");
					switch (nextTile.getType()) {
					case EMPTY:
						tileX += movement.getDX();
						tileY += movement.getDY();
						break;
					case WALL:
						DungeonTile downTile = model.getMap().getTile(tileX+movement.getDX(), tileY+movement.getDY()+1);
						DungeonTile leftTile = model.getMap().getTile(tileX+movement.getDX()-1, tileY+movement.getDY());
						DungeonTile rightTile = model.getMap().getTile(tileX+movement.getDX()+1, tileY+movement.getDY());
						DungeonTile upTile = model.getMap().getTile(tileX+movement.getDX(), tileY+movement.getDY()-1);
						switch (direction) {
						case NORTH:
						case EAST:
						case SOUTH:
						case WEST:
							direction = direction.opposite();
							break;
						case NORTH_EAST:
							if (downTile.getType() == DungeonTileType.EMPTY){
								tileX++;
								direction = Direction.SOUTH_EAST;
							}else{
								if (leftTile.getType() == DungeonTileType.EMPTY){
									tileY--;
									direction = Direction.NORTH_WEST;
								}else{
									direction = Direction.NONE;
									finished = true;
								}
							}
							break;
						case SOUTH_EAST:
							if (leftTile.getType() == DungeonTileType.EMPTY){
								tileY++;
								direction = Direction.SOUTH_WEST;
							}else{
								if (upTile.getType() == DungeonTileType.EMPTY){
									tileX++;
									direction = Direction.NORTH_EAST;
								}else{
									direction = Direction.NONE;
									finished = true;
								}
							}
							break;
						case SOUTH_WEST:
							if (upTile.getType() == DungeonTileType.EMPTY){
								tileX--;
								direction = Direction.NORTH_WEST;
							}else{
								if (rightTile.getType() == DungeonTileType.EMPTY){
									tileY++;
									direction = Direction.SOUTH_EAST;
								}else{
									direction = Direction.NONE;
									finished = true;
								}
							}
							break;
						case NORTH_WEST:
							if (rightTile.getType() == DungeonTileType.EMPTY){
								tileY--;
								direction = Direction.NORTH_EAST;
							}else{
								if (downTile.getType() == DungeonTileType.EMPTY){
									tileX--;
									direction = Direction.SOUTH_WEST;
								}else{
									direction = Direction.NONE;
									finished = true;
								}
							}
							break;
						default:
							direction = Direction.NONE;
							break;
						}
						break;
					case UP_RIGHT_CORNER:
						switch (direction){
						case NORTH:
							tileY--;
							direction = Direction.WEST;
							break;
						case EAST:
							tileX++;
							direction = Direction.SOUTH;
							break;
						case NORTH_EAST:
							tileX++;
							tileY--;
							direction = Direction.SOUTH_WEST;
							break;
						default:
							direction = Direction.NONE;
							finished = true;
							break;
						}
						break;
					case DOWN_RIGHT_CORNER:
						switch (direction){
						case SOUTH:
							tileY++;
							direction = Direction.WEST;
							break;
						case EAST:
							tileX++;
							direction = Direction.NORTH;
							break;
						case SOUTH_EAST:
							tileY++;
							tileX++;
							direction = Direction.NORTH_WEST;
							break;
						default:
							direction = Direction.NONE;
							finished = true;
							break;
						}
						break;
					case DOWN_LEFT_CORNER:
						switch (direction){
						case SOUTH:
							tileY++;
							direction = Direction.EAST;
							break;
						case WEST:
							tileX--;
							direction = Direction.NORTH;
							break;
						case SOUTH_WEST:
							tileY++;
							tileX--;
							direction = Direction.NORTH_EAST;
							break;
						default:
							direction = Direction.NONE;
							finished = true;
							break;
						}
						break;
					case UP_LEFT_CORNER:
						switch (direction){
						case NORTH:
							tileY--;
							direction = Direction.EAST;
							break;
						case WEST:
							tileX--;
							direction = Direction.SOUTH;
							break;
						case NORTH_WEST:
							tileY--;
							tileX--;
							direction = Direction.SOUTH_EAST;
							break;
						default:
							direction = Direction.NONE;
							finished = true;
							break;
						}
						break;
					default:
						break;
					}
				}
			}
			Collideable collideable = model.getCollided(tileX, tileY);
			// test verifiant que la flechen ne percute son lanceur
			if (collideable != null && currentSpanTick>ticksToMove){
				collideable.collide();
				finished = true;
			}
		}
	}

}
