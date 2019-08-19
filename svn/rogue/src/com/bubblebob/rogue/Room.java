package com.bubblebob.rogue;

public interface Room {

	public int getI0();
	public int getJ0();
	public int getWidth();
	public int getHeight();
	public boolean isIn(int i, int j);
	public boolean isVisited();
	public void visit();
	public void unvisit();
	public int getId();

}
