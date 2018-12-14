package com.bb.advent2018.day10;

import java.util.ArrayList;
import java.util.List;

public class StarCluster {
	
	
	//model
	private List<Star> stars;
	
	public StarCluster() {
		this.stars = new ArrayList<Star>();
	}
	
	public void step() {
		stars.forEach(star -> star.step());
	}
	
	public void step(int gap) {
		stars.forEach(star -> star.step(gap));
	}
	
	public void goTo(int step) {
		stars.forEach(star -> star.goTo(step));
	}

	public boolean add(Star e) {
		return stars.add(e);
	}

	public int size() {
		return stars.size();
	}
	
	public int[] getSpreading() {
		int minX = stars.get(0).x;
		int maxX = stars.get(0).x;
		int minY = stars.get(0).y;
		int maxY = stars.get(0).y;
		for (Star star : stars) {
			minX = Math.min(star.x, minX);
			maxX = Math.max(star.x, maxX);
			minY = Math.min(star.y, minY);
			maxY = Math.max(star.y, maxY);
		}
		int[] result = {minX,maxX,minY,maxY};
		return result;
	}

	public List<Star> getStars() {
		return stars;
	}
	

}
