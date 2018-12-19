package com.bb.advent2018.day15;

public class Cell {

	public int i;
	public int j;
	public CellType type;
	
	public Cell(CellType type, int i, int j) {
		super();
		this.i = i;
		this.j = j;
		this.type = type;
	}
	
	public boolean equals(Object cell) {
		return (i == ((Cell)(cell)).i && ((Cell)(cell)).j == j);
	}
	
	public int compareReadingPosition(Cell other) {
		if (j<other.j) {
			return -1;
		}else if (this.j>other.j) {
			return 1;
		}else {//same j
			if (this.i<other.i) {
				return -1;
			}else if (this.i>other.i) {
				return 1;
			}else {
				return 0;
			}
		}
	}
	
}
