package com.bb.advent2018.day15;

import java.util.List;

public class WarPath {

	private List<Step> steps;
	private Mob target;
	
	public Step getFirstStep() {
		return steps.get(0);
	}
	
	
	public class Step{
		public int i;
		public int j;
	}
}
