package com.bb.advent2018.day15;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Arena {
	
	public Cell[][] map;
	public List<Mob> mobs;
	
	public int w;
	public int h;
	
	private static MobSorterByHealthThenOrder MOB_HP_SORTER = new MobSorterByHealthThenOrder();
	private static CellSorterByReadingPosition CELL_POSITION_SORTER = new CellSorterByReadingPosition();
	private static final int INFINITE_DISTANCE = 10000;
	
	public Arena(int width, int height) {
		this.w = width;
		this.h= height;
		this.map = new Cell[w][h];
		for (int i=0;i<w;i++) {
			for (int j=0;j<h;j++) {
				
			}
		}
	}
	
	public WarPath getShortestPathToBlood(int fromI, int fromJ, MobType ennemy) {
		WarPath result = null;
		//finding ennemy spots 
		Set<Cell> allEnnemySpots = new HashSet<Cell>();
		for (Mob mob: mobs) {
			if (ennemy == mob.type.ennemy()) {
				allEnnemySpots.addAll(getOrderedFreeNeighbourHood(mob.i, mob.j));
			}
		}
		//going through cells until reaching an ennemy
		//init 
		boolean ennemyFound = false;
		Cell ennemySpotToReach = null;
		Set<Cell> cellsToExplore = new HashSet<Cell>();
		Map<Cell,Integer> currentDistanceByCell = new HashMap<Cell,Integer>();
		Map<Cell,Cell> bestPreviousSteps = new HashMap<Cell,Cell>();
		Set<Cell> exploredCells = new HashSet<Cell>();
		// init
		exploredCells.add(map[fromI][fromJ]);
		currentDistanceByCell.put(map[fromI][fromJ], 0);
		List<Cell> firstCells = getOrderedFreeNeighbourHood(fromI, fromJ);
		for (Cell cell: firstCells) {
			cellsToExplore.add(cell);
			currentDistanceByCell.put(cell,1);
			bestPreviousSteps.put(cell, map[fromI][fromJ]);
		}
		//while cells to exlore
		while (!cellsToExplore.isEmpty()&&!ennemyFound) {
			//finding next cell to explore
			Cell exploredCell = null;
			int exploredCellDistance = INFINITE_DISTANCE;
			for (Cell cell: cellsToExplore) {
				int currentDist = currentDistanceByCell.get(cell);
				if (currentDist < exploredCellDistance || currentDist == exploredCellDistance && cell.compareReadingPosition(exploredCell) < 0) {
					exploredCell = cell;
					exploredCellDistance = currentDist;
				}
			}
			//looking for neighbour
			List<Cell> neighbourhood = getOrderedFreeNeighbourHood(exploredCell.i, exploredCell.j);
			for (Cell neighbour: neighbourhood) {
				if (!ennemyFound) {
					//checking only not explored cells
					if (!exploredCells.contains(neighbour)) {
						//new cell
						if (cellsToExplore.contains(neighbour)) {
							int checkedDistance = currentDistanceByCell.get(neighbour);
							if (checkedDistance > exploredCellDistance+1 || checkedDistance == exploredCellDistance+1 && exploredCell.compareReadingPosition(bestPreviousSteps.get(neighbour)) < 0) {
								currentDistanceByCell.put(neighbour, exploredCellDistance+1);
								bestPreviousSteps.put(neighbour, exploredCell);
							}
						}else {
							//already seen cell
							cellsToExplore.add(neighbour);
							currentDistanceByCell.put(neighbour,exploredCellDistance+1);
							bestPreviousSteps.put(neighbour, exploredCell);
						}
					}
					if (allEnnemySpots.contains(neighbour)) {
						ennemyFound = true;
						ennemySpotToReach = neighbour;
					}
				}
			}
			exploredCells.add(exploredCell);
			cellsToExplore.remove(exploredCell);
		}
		if (ennemyFound) {
			//constructing retropath
			WarPath path = new WarPath();
			path.prependStep(ennemySpotToReach.i,ennemySpotToReach.j);
			boolean startReached = false;
			while (!startReached) {
				Cell preStep = bestPreviousSteps.get(path.getFirstStep());
				path.prependStep(preStep.i,preStep.j);
				if (preStep == map[fromI][fromJ]) {
					startReached = true;
				}
			}
			result = path;
		}else {
			//TODO managing no step
		}
		return result;
		
		/*
		//pathing to each reachable place
		Set<Cell> cellsToExplore = new HashSet<Cell>();
		Map<Cell,Integer> currentDistanceByCell = new HashMap<Cell,Integer>();
		Map<Cell,Cell> bestPreviousSteps = new HashMap<Cell,Cell>();
		Set<Cell> exploredCells = new HashSet<Cell>();
		// init
		exploredCells.add(map[fromI][fromJ]);
		currentDistanceByCell.put(map[fromI][fromJ], 0);
		List<Cell> firstCells = getFreeNeighbourHood(fromI, fromJ);
		for (Cell cell: firstCells) {
			cellsToExplore.add(cell);
			currentDistanceByCell.put(cell,1);
			bestPreviousSteps.put(cell, map[fromI][fromJ]);
		}
		//while cells to exlore
		while (!cellsToExplore.isEmpty()) {
			//finding first cell to explore
			Cell nextCell = null;
			int minDist = INFINITE_DISTANCE;
			for (Cell cell: cellsToExplore) {
				int currentDist = currentDistanceByCell.get(cell);
				if (currentDist< minDist) {
					nextCell = cell;
					minDist = currentDist;
				}
			}
			//looking for neighbour
			List<Cell> neighbourhood = getFreeNeighbourHood(nextCell.i, nextCell.j);
			for (Cell cell: neighbourhood) {
				//checking only not explored cells
				if (!exploredCells.contains(cell)) {
					if (cellsToExplore.contains(cell)) {
						int checkedDistance = currentDistanceByCell.get(cell);
						if (checkedDistance > minDist+1) {
							currentDistanceByCell.put(cell, minDist);
							bestPreviousSteps.put(cell, nextCell);
						}
					}else {
						cellsToExplore.add(cell);
						currentDistanceByCell.put(cell,minDist+1);
						bestPreviousSteps.put(cell, nextCell);
					}
				}
			}
			exploredCells.add(nextCell);
			cellsToExplore.remove(nextCell);
		}
		//finding ennemy spots 
		Set<Cell> allSpots = new HashSet<Cell>();
		for (Mob mob: mobs) {
			if (ennemy == mob.type.ennemy()) {
				allSpots.addAll(getFreeNeighbourHood(mob.i, mob.j));
			}
		}
		// filtering 
		Set<Cell> reachableSpots = allSpots.stream().filter(spot -> exploredCells.contains(spot)).collect(Collectors.toSet());
		//finding best cell to go to
		int minDistanceToEnnemySpot = INFINITE_DISTANCE;
		Cell bestReachableEnnemySpot = null;
		for (Cell cell: reachableSpots) {
			int currentDist = currentDistanceByCell.get(cell);
			if (currentDist< minDistanceToEnnemySpot) {
				bestReachableEnnemySpot = cell;
				minDistanceToEnnemySpot = currentDist;
			}
		}
		//TODO la suite
		return null;
		
		*/
	}
	
	public Mob getWeakestEnnemyNeighbour(int fromI, int fromJ, MobType ennemy) {
		Mob result = null;
		List<Cell> cells = getNeighbourHood(fromI,fromJ);
		if (!cells.isEmpty()) {
			List<Mob> neighbours = new ArrayList<Mob>();
			for (Cell cell: cells) {
				neighbours.addAll(mobs.stream().filter(mob -> (mob.i == cell.i && mob.j == cell.j)).collect(Collectors.toList()));
			}
			if (!neighbours.isEmpty()) {
				neighbours.sort(MOB_HP_SORTER);
				result = neighbours.get(0);
			}
		}
		return result;
	}
	
	public void pop(MobType type, int i, int j) {
		mobs.add(new Mob(type, i, j));
	}

	
	private static int[][] DELTA_NEAR = {{0,-1},{1,0},{0,1},{-1,0}};
	private List<Cell> getNeighbourHood(int fromI, int fromJ){
		List<Cell> result = new ArrayList<Cell>();
		for (int[] didj : DELTA_NEAR) {
			int x = didj[0]+fromI;
			int y = didj[1]+fromJ;
			if (x>=0&&x<w&&y>=0&&y<h) {
				result.add(map[x][y]);
			}
		}
		return result;
	}
	
	private List<Cell> getOrderedFreeNeighbourHood(int fromI, int fromJ){
		List<Cell> result = getNeighbourHood(fromI, fromJ);
		List<Cell> occupiedCells = new ArrayList<Cell>();
		for (Cell cell: result) {
			for (Mob mob: mobs) {
				if (cell.i == mob.i && cell.j == cell.j) {
					occupiedCells.add(cell);
				}
			}
		}
		result.removeAll(occupiedCells);
		result.sort(CELL_POSITION_SORTER);
		return result;
	}
	
	
	
	
	
	
}
