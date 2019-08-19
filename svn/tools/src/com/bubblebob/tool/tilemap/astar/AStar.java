package com.bubblebob.tool.tilemap.astar;

import java.util.List;
import java.util.Vector;

import com.bubblebob.tool.tilemap.model.Map;
import com.bubblebob.tool.tilemap.model.Tile;

public class AStar {

	public AStar(Map map, int startX, int startY, int endX, int endY, int maxDepth) {
		super();
		this.map = map;
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
		this.maxDepth = maxDepth;
	}
	
	public AStar(Map map, int startX, int startY, int endX, int endY, int maxDepth, int closeToVictorySoOk) {
		super();
		this.map = map;
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
		this.maxDepth = maxDepth;
		this.closeToVictorySoOk = closeToVictorySoOk;
	}

	private Map map;

	private List<TileInfo> closedList;
	private List<TileInfo> openList;

	public boolean finished = false;

	private int startX;
	private int startY;
	private int endX;
	private int endY;
	private int maxDepth;

	private int closeToVictorySoOk = 0;
	
	
	private List<Tile> path;

	public List<Tile> getPath(){
		closedList = new Vector<TileInfo>();
		TileInfo beginTileInfo = new TileInfo(startX, startY, null, 0, guessDistance(startX, startY, endX, endY));
		closedList.add(beginTileInfo);
		return runAlgorithm();
	}

	private List<Tile> runAlgorithm(){
		List<Tile> result = null;
		TileInfo endInfo = null;
		int currentStep = 0;
		while (endInfo == null && (currentStep <maxDepth)) {
			endInfo = calculateOpenList();
			updateClosedListWithNewNeigbours();
			currentStep++;
		}
		//System.out.println("fin trouvee: "+endInfo.x()+","+endInfo.y()+" distance="+endInfo.getPathLength());
		if (endInfo != null){
			result = constructBackPath(endInfo);
			finished = true;
		}
		return result;
	}


	private TileInfo calculateOpenList(){
		TileInfo firstNext = getNextNotVisitedTileFromClosedList();
		if (firstNext == null){
			return null;
		}
//		System.out.println("[AStar#calculateOpenList] case visitee: "+firstNext.x()+","+firstNext.y()+" chemin parcouru="+firstNext.getPathLength());
		openList = new Vector<TileInfo>();
		List<Tile> neighbours = map.getNeighbours(firstNext.x(), firstNext.y());
		boolean endFound = false;
		TileInfo endInfo = null;
		for (Tile neighbour: neighbours){
			if (neighbour.isAccessible()){
//				System.out.println("[AStar#calculateOpenList] voisin: ("+neighbour.getX()+","+neighbour.getY()+")");
				int guessedDistance = guessDistance(firstNext.x(), firstNext.y(),neighbour.getX(),neighbour.getY());
				TileInfo neighbourInfo = new TileInfo(neighbour.getX(), neighbour.getY(), firstNext, firstNext.getPathLength()+1, guessedDistance);
				if ((neighbourInfo.x()-endX)*(neighbourInfo.x()-endX)<=closeToVictorySoOk && (neighbourInfo.y()-endY)*(neighbourInfo.y()-endY)<=closeToVictorySoOk){
					endInfo = neighbourInfo;
					endFound = true;
					break;
				}
				openList.add(neighbourInfo);
			}
		}
		firstNext.setNeighboursVisited(true);
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
			int currentTotalPathLength = closedList.get(insertIndex).getPathLength() + closedList.get(insertIndex).getGuessedDistance();
			if (currentTotalPathLength >= totalPathLength){
				closedList.add(insertIndex,tileInfo);
				inserted = true;
			}else{
				insertIndex++;
			}
		}
		return insertIndex;
	}

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

	private TileInfo getTileInfoFromClosedList(int x, int y){
		for (TileInfo tileInfo: closedList){
			if (tileInfo.x() == x && tileInfo.y() == y){
				return tileInfo;
			}
		}
		return null;
	}

	private List<Tile> constructBackPath(TileInfo endTileInfo){
		List<Tile> path = new Vector<Tile>();
		Tile currentTile = map.getTile(endTileInfo.x(), endTileInfo.y());
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
