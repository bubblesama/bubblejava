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

	public int turn;

	private static MobSorterByHealthThenOrder MOB_HP_SORTER = new MobSorterByHealthThenOrder();
	private static MobSorterByReadingPosition MOB_POSITION_SORTER = new MobSorterByReadingPosition();

	private static CellSorterByReadingPosition CELL_POSITION_SORTER = new CellSorterByReadingPosition();
	private static final int INFINITE_DISTANCE = 10000;

	public Arena(int width, int height) {
		this.w = width;
		this.h = height;
		this.map = new Cell[w][h];
		for (int i=0;i<w;i++) {
			for (int j=0;j<h;j++) {
				map[i][j] = new Cell(CellType.ROCK,i,j);
			}
		}
		this.turn = 1;
		this.mobs = new ArrayList<Mob>();
	}

	public void doTurn() {
		mobs.sort(MOB_POSITION_SORTER);
		List<Mob> orderedWarriors = new ArrayList<Mob>();
		orderedWarriors.addAll(mobs);
		while (!orderedWarriors.isEmpty()) {
			Mob nextWarrior = orderedWarriors.get(0);
			nextWarrior.act(this);
			//clean deads
			//clearing the dead
			List<Mob> deads = new ArrayList<Mob>();
			for (Mob mob: mobs) {
				if (mob.isDead()) {
					deads.add(mob);
				}
			}
			orderedWarriors.removeAll(deads);
			orderedWarriors.remove(nextWarrior);
			mobs.removeAll(deads);
		}

		turn++;
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
		if (allEnnemySpots.isEmpty()) {
			int totalHp = 0;
			for (Mob mob: mobs) {
				totalHp+= mob.hp;
			}
			int score = totalHp*turn;
			System.out.println("Arena: battle ends with no remaining opponent! score: "+score);

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
			}
			if (allEnnemySpots.contains(exploredCell)) {
				ennemyFound = true;
				ennemySpotToReach = exploredCell;
			}
			exploredCells.add(exploredCell);
			cellsToExplore.remove(exploredCell);
		}
		if (ennemyFound) {
			//constructing retropath
			WarPath path = new WarPath();
			path.target = ennemySpotToReach; 
			path.prependStep(ennemySpotToReach.i,ennemySpotToReach.j);
			boolean startReached = false;
			while (!startReached) {
				Cell preStep = bestPreviousSteps.get(map[path.getFirstStep().i][path.getFirstStep().j]);
				if (preStep == map[fromI][fromJ]) {
					startReached = true;
				}else {
					path.prependStep(preStep.i,preStep.j);
				}
			}
			result = path;
		}else {
			//TODO managing no step
		}
		return result;
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

	public void changeCell(CellType type, int i, int j) {
		map[i][j].type = type;
	}

	public Cell getCell(int i, int j) {
		return map[i][j];
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
		//keep only spaces
		result = result.stream().filter(cell -> cell.type == CellType.SPACE).collect(Collectors.toList());
		//
		List<Cell> occupiedCells = new ArrayList<Cell>();
		for (Cell cell: result) {
			for (Mob mob: mobs) {
				if (cell.i == mob.i && cell.j == mob.j) {
					occupiedCells.add(cell);
				}
			}
		}
		result.removeAll(occupiedCells);
		result.sort(CELL_POSITION_SORTER);
		return result;
	}

}
