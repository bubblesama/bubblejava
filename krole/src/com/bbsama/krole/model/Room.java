package com.bbsama.krole.model;

public class Room {
	
	int i;
	int j;
	int w;
	int h;

	public Room(int i, int j, int w, int h) {
		super();
		this.i = i;
		this.j = j;
		this.w = w;
		this.h = h;
	}
	
	public boolean overlaps(Room other){
		boolean result = false;
		if (((i<=other.i && i+w>=other.i)||(other.i<=i && other.i+other.w>=i)) && ((j<=other.j && j+h>=other.j)||(other.j<=j && other.j+other.h>=j))){
			result = true;
		}
		return result;
	}
	
	
	public String toString(){
		return "("+i+" "+j+" "+w+" "+h+")";
	}
}
