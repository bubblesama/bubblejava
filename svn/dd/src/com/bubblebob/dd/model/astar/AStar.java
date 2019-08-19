package com.bubblebob.dd.model.astar;

import java.util.List;
import java.util.Vector;

import com.bubblebob.dd.model.dungeon.map.DungeonMap;
import com.bubblebob.dd.model.dungeon.map.DungeonTile;
import com.bubblebob.dd.model.dungeon.map.DungeonTileType;

public class AStar {

	private DungeonMap map;

	private List<TileInfo> closedList;
	private List<TileInfo> openList;

	private boolean finished = false;

	private int startX;
	private int startY;
	private int endX;
	private int endY;

	private List<DungeonTile> path;

	public AStar(DungeonMap map, int startX, int startY, int endX, int endY) {
		super();
		this.map = map;
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
	}
	
	public List<DungeonTile> getPath(){
		closedList = new Vector<TileInfo>();
		TileInfo beginTileInfo = new TileInfo(startX, startY, null, 0, guessDistance(startX, startY, endX, endY));
		closedList.add(beginTileInfo);
		return runAlgorithm();
	}

	private List<DungeonTile> runAlgorithm(){
		System.out.println("[AStar#runAlgorithm] IN");
		List<DungeonTile> result = null;
		TileInfo endInfo = null;
		while (endInfo == null) {
			endInfo = calculateOpenList();
			updateClosedListWithNewNeigbours();
		}
		System.out.println("[AStar#runAlgorithm] fin trouvee: "+endInfo.x()+","+endInfo.y()+" distance="+endInfo.getPathLength());
		result = constructBackPath(endInfo);
		
		//map.pause();
//		for (Tile tile: result){
//			System.out.println("[AStar#runAlgorithm] tile.x="+tile.getX()+" tile.y="+tile.getY());
//		}
		System.out.println("[AStar#runAlgorithm] OUT");
		return result;
	}


	private TileInfo calculateOpenList(){
		System.out.println("[AStar#calculateOpenList] IN");
		TileInfo firstNext = getNextNotVisitedTileFromClosedList();
		TileInfo endInfo = null;
		if (firstNext == null){
			//return null;
		}else{
			//System.out.println("[AStar#calculateOpenList] case visitee: "+firstNext.x()+","+firstNext.y()+" chemin parcouru="+firstNext.getPathLength());
			//<calcul des infos des voisins...
			openList = new Vector<TileInfo>();
			List<DungeonTile> neighbours = map.getNeighbours(firstNext.x(), firstNext.y());
			for (DungeonTile neighbour: neighbours){
				if (neighbour.getType() == DungeonTileType.EMPTY){
					//System.out.println("[AStar#calculateOpenList] voisin: ("+neighbour.getX()+","+neighbour.getY()+")");
					int guessedDistance = guessDistance(firstNext.x(), firstNext.y(),neighbour.getX(),neighbour.getY());
					TileInfo neighbourInfo = new TileInfo(neighbour.getX(), neighbour.getY(), firstNext, firstNext.getPathLength()+1, guessedDistance);
					if (neighbourInfo.x() == endX && neighbourInfo.y() == endY){
						endInfo = neighbourInfo;
						break;
					}
					openList.add(neighbourInfo);
				}
			}
			firstNext.setNeighboursVisited(true);
			//...calcul des infos des voisins>
		}
		System.out.println("[AStar#calculateOpenList] OUT endInfo="+endInfo);
		return endInfo;
	}

	private TileInfo getNextNotVisitedTileFromClosedList(){
		for (TileInfo tileInfo: closedList){
			if (!tileInfo.isNeighboursVisited()){
				return tileInfo;
			}
		}
		return null;
	}

	private static int guessDistance(int x1,int y1,int x2,int y2){
		return ((x2-x1)*(x2-x1)+ (y2-y1)*(y2-y1));
	}

	private int insertInSortedClosedList(TileInfo tileInfo){
		int insertIndex = 0;
		boolean inserted = false;
		int totalPathLength = tileInfo.getPathLength() + tileInfo.getGuessedDistance();
		while (!inserted && insertIndex<closedList.size()) {
			int currentTotalPathLength = closedList.get(insertIndex).getPathLength() +  closedList.get(insertIndex).getGuessedDistance();
			if (currentTotalPathLength >= totalPathLength){
				closedList.add(insertIndex,tileInfo);
				inserted = true;
			}else{
				insertIndex++;
			}
		}
		return insertIndex;
	}

	/**
	 * Met a jour la "closed list" avec les voisins obtenus dans l'"open list"
	 */
	private void updateClosedListWithNewNeigbours(){
		for (TileInfo newTileInfo: openList){
			TileInfo previousSameTileInfo = getTileInfoFromClosedList(newTileInfo.x(),newTileInfo.y());
			if (previousSameTileInfo != null){
				// cas ou la case est deja recensee
				if (previousSameTileInfo.getPathLength() > newTileInfo.getPathLength()){
					// cas ou le nouveau chemin est plus interessant
					closedList.remove(previousSameTileInfo);
					insertInSortedClosedList(newTileInfo);
				}
			}else{
				// cas ou la case n'a jamais ete atteinte
				insertInSortedClosedList(newTileInfo);
			}
		}
	}

	/**
	 * Fournit la case de la "closed list" aux coordonnees specifiees si elle y appartient, ou null sinon
	 * @param x
	 * @param y
	 * @return
	 */
	private TileInfo getTileInfoFromClosedList(int x, int y){
		for (TileInfo tileInfo: closedList){
			if (tileInfo.x() == x && tileInfo.y() == y){
				return tileInfo;
			}
		}
		return null;
	}

	/**
	 * Creer la liste des cases du chemin a partir de la "closed list"
	 * @param endTileInfo
	 * @return
	 */
	private List<DungeonTile> constructBackPath(TileInfo endTileInfo){
		List<DungeonTile> path = new Vector<DungeonTile>();
		DungeonTile currentTile = map.getTile(endTileInfo.x(), endTileInfo.y());
		path.add(currentTile);
		TileInfo previousTileInfo = endTileInfo.getTileInfoFrom();
		while (previousTileInfo != null){
			currentTile = map.getTile(previousTileInfo.x(), previousTileInfo.y());
			path.add(0,currentTile);
			previousTileInfo = previousTileInfo.getTileInfoFrom();
		}
		return path;
	}

}
