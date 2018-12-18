package com.bb.advent2018.day15;

import java.util.Comparator;

public class MobSorterByHealthThenOrder implements Comparator<Mob>{

	private MobSorterByReadingPosition secondarySorter = new MobSorterByReadingPosition();
	
	@Override
	public int compare(Mob mob1, Mob mob2) {
		if (mob1.hp<mob2.hp) {
			return -2;
		}else if(mob1.hp>mob2.hp) {
			return 2;
		}else {
			return secondarySorter.compare(mob1, mob2);
		}
	}

}
