package com.bubblebob.rogue;

import java.util.List;


public class Cell {

	public CellType type;
	public int i;
	public int j;
	public List<Item> items;
	
	public Cell(int i,int j){
		this.type = CellType.Floor;
		this.i = i;
		this.j = j;
	}
	
}
