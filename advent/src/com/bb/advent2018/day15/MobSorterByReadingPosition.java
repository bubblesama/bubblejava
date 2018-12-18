package com.bb.advent2018.day15;

import java.util.Comparator;

public class MobSorterByReadingPosition implements Comparator<Mob>{

	@Override
	public int compare(Mob mob1, Mob mob2) {
		if (mob1.j<mob2.j) {
			return -1;
		}else if (mob1.j>mob2.j) {
			return 1;
		}else {//same j
			if (mob1.i<mob2.i) {
				return -1;
			}else if (mob1.i>mob2.i) {
				return 1;
			}else {
				return 0;
			}
		}
	}

}
