package com.bubblebob.dd.control;

import com.bubblebob.dd.model.DdGameModel;
import com.bubblebob.dd.model.GameMode;
import com.bubblebob.dd.model.Position;
import com.bubblebob.dd.model.dungeon.map.DungeonMap;
import com.bubblebob.dd.model.dungeon.map.DungeonMapFactory;
import com.bubblebob.dd.model.world.map.WorldTile;
import com.bubblebob.dd.model.world.map.WorldTileType;

public class WorldController {

	private DdGameModel model;

	public WorldController(DdGameModel model) {
		this.model = model;
	}
	
	public void goNorth(){
		if (!model.getWorld().isWon()){
			tryMoving(0,-1);
		}
	}
	
	public void goNorthEast(){
		if (!model.getWorld().isWon()){
			tryMoving(1,-1);
		}

	}
	
	public void goEast(){
		if (!model.getWorld().isWon()){
			tryMoving(1,0);
		}

	}
	
	public void goSouthEast(){
		if (!model.getWorld().isWon()){
			tryMoving(1,1);
		}

	}
	
	public void goSouth(){
		if (!model.getWorld().isWon()){
			tryMoving(0,1);
		}

	}
	
	public void goSouthWest(){
		if (!model.getWorld().isWon()){
			tryMoving(-1,1);
		}

	}
	
	public void goWest(){
		if (!model.getWorld().isWon()){
			tryMoving(-1,0);
		}

	}
	
	public void goNorthWest(){
		if (!model.getWorld().isWon()){
			tryMoving(-1,-1);
		}
	}

	private void tryMoving(int dX, int dY){
		Position nextPosition = new Position(model.getWorld().getPlayer().getX()+dX, model.getWorld().getPlayer().getY()+dY);
		WorldTile nextTile = model.getWorld().getMap().getTile(nextPosition);
		//System.out.println("[WorldController#tryMoving] IN nextTile.getType()="+nextTile.getType());
		boolean doMove = false;
		if (nextTile != null){
			if (nextTile.getType().isForest()){
				if (model.getWorld().getPlayer().hasGotAxe()){
					doMove = true;
				}
			}else if (nextTile.getType().isRiver()){
				if (model.getWorld().getPlayer().hasGotBoat()){
					doMove = true;
				}
			}else if (nextTile.getType().isDoor()){
				if (model.getWorld().getPlayer().hasGotKey()){
					doMove = true;
				}
			}else if(model.getWorld().getMap().isMainDungeon(nextTile.getX(),nextTile.getY()) || nextTile.getType().isDungeon()){
				//				DungeonMap newMap = DungeonMapFactory.getInstance().generateMap(9, 9, 4, 4);
				try{
					if (!nextTile.isVisited()){

						if (model.getWorld().getMap().isMainDungeon(nextTile.getX(),nextTile.getY())){
							DungeonMap newMap = DungeonMapFactory.getInstance().generateMapFromRandomBitMap(6,6);
							model.startDungeon(newMap,WorldTileType.MOUTAIN_BIG);
						}else{
							DungeonMap newMap = DungeonMapFactory.getInstance().generateMapFromRandomBitMap(4,4);
							model.startDungeon(newMap,nextTile.getType());
						}
						model.setMode(GameMode.DUNGEON);
						model.getWorld().getMap().coverMountains();
					}
					doMove = true;
				}catch (Exception e){
					e.printStackTrace();
				}
			}else if (nextTile.getType() == WorldTileType.EMPTY || nextTile.getType() == WorldTileType.HOUSE ){
				doMove = true;
			}
			if (doMove){
				model.getWorld().getPlayer().setX(model.getWorld().getPlayer().getX()+dX);
				model.getWorld().getPlayer().setY(model.getWorld().getPlayer().getY()+dY);
			}
		}
	}
	
}
