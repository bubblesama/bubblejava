package com.bb.advent2018.day15;

import java.util.ArrayList;
import java.util.List;

public class WarPath {

	public WarPath() {
		super();
		this.steps = new ArrayList<Step>();
	}

	private List<Step> steps;
	
	public Step getFirstStep() {
		return steps.get(0);
	}
	
	public class Step{
		public Step(int i, int j) {
			super();
			this.i = i;
			this.j = j;
		}
		public int i;
		public int j;
	}
	
	public boolean addStep(int i, int j) {
		return steps.add(new Step(i,j));
	}

	public boolean addStep(Step step) {
		return steps.add(step);
	}
	
	public void prependStep(int i, int j) {
		steps.add(0,new Step(i,j));
	}
	
	public void prependStep(Step step) {
		steps.add(0,step);
	}
}
