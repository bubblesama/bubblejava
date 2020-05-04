package com.bubblebob.kahu.model;

import java.util.ArrayList;
import java.util.List;

public class Island {

	public String name;
	public Ownership owner;
	public List<Bridge> bridges;
	public int centerI;
	public int centerJ;

	public Island(String name, Ownership owner, int centerI, int centerJ) {
		super();
		this.name = name;
		this.owner = owner;
		this.bridges = new ArrayList<Bridge>();
		this.centerI = centerI;
		this.centerJ = centerJ;
	}
	
	
	
	
	
}
