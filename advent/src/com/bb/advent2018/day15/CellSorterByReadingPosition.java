package com.bb.advent2018.day15;

import java.util.Comparator;

public class CellSorterByReadingPosition implements Comparator<Cell>{

	@Override
	public int compare(Cell cell1, Cell cell2) {
		if (cell1.j<cell2.j) {
			return -1;
		}else if (cell1.j>cell2.j) {
			return 1;
		}else {//same j
			if (cell1.i<cell2.i) {
				return -1;
			}else if (cell1.i>cell2.i) {
				return 1;
			}else {
				return 0;
			}
		}
	}

}
