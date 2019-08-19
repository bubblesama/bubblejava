package com.bubblebob.rogue;


public class RoomSimple implements Room{

	private int id;
	private int i0;
	private int j0;
	private int w;
	private int h;
	private boolean visited = false;

	public RoomSimple(int id, int i0, int j0, int w, int h, boolean visited) {
		super();
		this.id = id;
		this.i0 = i0;
		this.j0 = j0;
		this.w = w;
		this.h = h;
		this.visited = visited;
	}

	public static RoomSimple getDefaultRoom(){
		return new RoomSimple(1,2, 3, 4, 5, true);
	}

	public int getI0() {return i0;}
	public int getJ0() {return j0;}
	public int getWidth() {return w;}
	public int getHeight() {return h;}
	public boolean isIn(int i, int j) {return (i>=getI0())&&(i<getI0()+w)&&(j>=getJ0())&&(j<getJ0()+h);}
	public boolean isVisited(){return visited;}
	public void visit() {visited = true;}
	public void unvisit() {visited = false;}
	public int getId() {return id;}


	public String toString() {
		return "R(id="+id+" i0=" + i0 + " j0=" + j0 + " w=" + w + " h=" + h+ ")";
	}


}
