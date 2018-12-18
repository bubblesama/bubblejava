package com.bb.advent2018.day15;

public class Cell {

	public int i;
	public int j;
	public CellType type;
	
	public boolean equals(Object cell) {
		return (i == ((Cell)(cell)).i && ((Cell)(cell)).j == j);
	}
	
	
}
