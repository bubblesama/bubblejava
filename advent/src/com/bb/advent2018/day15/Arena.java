package com.bb.advent2018.day15;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Arena {
	
	public Cell[][] map;
	public List<Mob> mobs;
	
	public int w;
	public int h;
	
	private static MobSorterByHealthThenOrder HP_SORTER = new MobSorterByHealthThenOrder();
	
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
		//TODO
		return null;
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
				neighbours.sort(HP_SORTER);
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
	
	
	
}
