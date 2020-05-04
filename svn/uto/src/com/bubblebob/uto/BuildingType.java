package com.bubblebob.uto;

public enum BuildingType {

//	NO_BUILDING(0),FORT(50),FACTORY(50),FARM(10),SCHOOL(25),HOSPITAL(75),HOUSE(60),REBEL(30);
	
	NO_BUILDING(0),FORT(0),FACTORY(0),FARM(0),SCHOOL(0),HOSPITAL(0),HOUSE(0),REBEL(0);
	
	public int price;
	
	private BuildingType(int price){
		this.price = price;
	}
	
}
