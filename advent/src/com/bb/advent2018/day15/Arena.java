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

	private List<Cell> getNeighbourHood(int fromI, int fromJ){
		List<Cell> result = new ArrayList<Cell>();
		for (int i=-1;i<2;i++) {
			for (int j=-1;j<2;j++) {
				if (i!=0&&j!=0) {
					int x = i+fromI;
					int y = j+fromJ;
					if (x>=0&&x<w&&y>=0&&y<h) {
						result.add(map[x][y]);
					}
				}
			}
		}
		return result;
	}
	
	
	
}
