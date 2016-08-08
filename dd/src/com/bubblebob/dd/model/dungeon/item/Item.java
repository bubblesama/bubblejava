package com.bubblebob.dd.model.dungeon.item;

import com.bubblebob.dd.model.DdGameModel;

public abstract class Item {

	public DdGameModel model;
	
	public Item(DdGameModel model){
		this.model = model;
	}
	
	public abstract void doPickUp();
	
	public abstract String getName();
	
}
