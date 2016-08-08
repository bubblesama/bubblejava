package com.bubblebob.uto;

public enum BoatType {

	FISHING(25),PATROL(40),PIRATE(0);

	public int price;

	private BoatType(int price){
		this.price = price;
	}
}
