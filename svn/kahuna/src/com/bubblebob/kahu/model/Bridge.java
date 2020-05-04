package com.bubblebob.kahu.model;

public class Bridge {
	public Ownership owner;
	public Island i;
	public Island j;
	public int AI;
	public int AJ;
	public int BI;
	public int BJ;
	
	public Bridge(Island i, Island j, Ownership owner, int aI, int aJ, int bI, int bJ) {
		this.owner = owner;
		this.i = i;
		this.j = j;
		AI = aI;
		AJ = aJ;
		BI = bI;
		BJ = bJ;
	}

}
