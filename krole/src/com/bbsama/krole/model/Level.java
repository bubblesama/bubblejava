package com.bbsama.krole.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Level {
	
	
	// le niveau
	private int w;
	private int h;
	private Cell[][] cells;
	//TODO gerer les rooms
	public List<Room> rooms;
	private int id;
	public List<Stuff> loot;
	
	public Level (int id,int width,int height){
		this.id = id;
		this.w = width;
		this.h = height;
		this.cells = new Cell[width][height];
		for (int i=0;i<w;i++){
			for (int j=0;j<h;j++){
				this.cells[i][j] = new Cell(this,i, j);
			}
		}
		this.rooms = new ArrayList<Room>();
		this.loot = new ArrayList<Stuff>();
	}
	
	public Cell getCell(int i, int j){
		return cells[i][j];
	}

	public int w() {
		return w;
	}

	public int h() {
		return h;
	}
	
	private final static int INFINITE_DISTANCE = 10000;
	
	
	public List<Cell> getDijsktraPath(Cell A, Cell B){
		return getDijsktraPath(A.i(),A.j(),B.i(),B.j());
	}
	
	public List<Cell> getDijsktraPath(int iA,int jA, int iB, int jB){
		long start = System.currentTimeMillis();
		// principe general: parcours de tous l'arbre en minimisant les chemins
		Map<Cell,Integer> distancesFromStartByCell = new HashMap<Cell,Integer>();
		Map<Cell,Cell> fathersByCell = new HashMap<Cell,Cell>();
		List<Cell> notVisitedCells = new ArrayList<Cell>();
		for (int i=0;i<w;i++){
			for (int j=0;j<h;j++){
				if (cells[i][j].isPassable()){
					distancesFromStartByCell.put(cells[i][j], INFINITE_DISTANCE);
					notVisitedCells.add(cells[i][j]);
				}
			}
		}
		distancesFromStartByCell.put(cells[iA][jA], 0);
		Cell visitingCell = cells[iA][jA];
		//TODO diagonale ou pas? pas pour l'instant
//		int[][] deltaNeighbours = new int[][]{{-1,-1},{-1,0},{-1,1},{0,-1},{0,1},{1,-1},{1,0},{1,1},};
		int[][] deltaNeighbours = new int[][]{{-1,0},{0,-1},{0,1},{1,0}};
		
		while(!notVisitedCells.isEmpty()){
			int visitingCellDistance = distancesFromStartByCell.get(visitingCell);
			// recuperation des voisins
			List<Cell> neighbours = new ArrayList<Cell>();
			for (int[] delta: deltaNeighbours){
				int neighbourI = visitingCell.i()+delta[0];
				int neighbourJ = visitingCell.j()+delta[1];
				if (neighbourI>=0 && neighbourI<w && neighbourJ>=0 && neighbourJ<h && cells[neighbourI][neighbourJ].isPassable()){
					neighbours.add(cells[neighbourI][neighbourJ]);
				}
			}
			// maj des distances
			for (Cell neighbour: neighbours){
				if (distancesFromStartByCell.get(neighbour)>visitingCellDistance+1){
					distancesFromStartByCell.put(neighbour, visitingCellDistance+1);
					fathersByCell.put(neighbour, visitingCell);
				}
			}
			notVisitedCells.remove(visitingCell);
			// recuperation la prochaine cellule proche
			int minDistanceOnNotVisitedCell = INFINITE_DISTANCE;
			for (Cell notVisitedCell: notVisitedCells){
				if (distancesFromStartByCell.get(notVisitedCell)<minDistanceOnNotVisitedCell){
					minDistanceOnNotVisitedCell = distancesFromStartByCell.get(notVisitedCell);
					visitingCell = notVisitedCell;
				}
			}
		}
		// trace du chemin
		List<Cell> path = new ArrayList<Cell>();
		Cell currentCell = cells[iB][jB];
		while (fathersByCell.get(currentCell) != null) {
			path.add(currentCell);
			currentCell = fathersByCell.get(currentCell);
		}
		Collections.reverse(path);
		long duration = System.currentTimeMillis()-start;
//		System.out.println("duration="+duration);
		return path;
	}

	public int id() {
		return id;
	}
	
	public void notifyAsDropped(Stuff stuff){
		this.loot.add(stuff);
	}
	
	
	public void notifyAsTaken(Stuff stuff){
		this.loot.remove(stuff);
	}
	
	public Cell getFirstPassableCell(){
		for (int i=0;i<w;i++){
			for (int j=0;j<h;j++){
				if (cells[i][j].isPassable()){
					return cells[i][j];
				}
			}
		}
		return null;
	}
	
}
