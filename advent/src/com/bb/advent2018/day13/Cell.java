package com.bb.advent2018.day13;

public class Cell {
	
	public Cell(int i, int j, TrackType track) {
		super();
		this.i = i;
		this.j = j;
		this.track = track;
	}
	
	public int i;
	public int j;
	public TrackType track;
	
	public void setTrack(TrackType track) {
		this.track = track;
	}
	
}
