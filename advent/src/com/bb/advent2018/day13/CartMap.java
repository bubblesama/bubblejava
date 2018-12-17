package com.bb.advent2018.day13;

import java.util.List;

public class CartMap {
	
	//model
	private Cell[][] map;
	private List<Cart> carts;
	public int w;
	public int h;
	
	
	public CartMap(int width, int height) {
		super();
		this.w = width;
		this.h= height;
		this.map = new Cell[w][h];
	}

	public Cell getCell(int i, int j) {
		return map[i][j];
	}
	
	public boolean add(Cart e) {
		return carts.add(e);
	}
	
	
	
	

}
