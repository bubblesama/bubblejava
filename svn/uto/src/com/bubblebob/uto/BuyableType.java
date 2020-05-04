package com.bubblebob.uto;

import com.bubblebob.uto.setting.SettingsManager;


public enum BuyableType {

	FORT(),FACTORY(),FARM(),SCHOOL(),HOSPITAL(),HOUSE(),REBEL(),FISHING(),PATROL();
	
	private BuyableType(){ }
	
	public int getCost(){
		return SettingsManager.getInstance().getPrice(this); 
	}
	
	public BuyableType getSwitch(){
		switch (this) {
		case FORT:
			return FACTORY;
		case FACTORY:
			return FARM;
		case FARM:
			return SCHOOL;
		case SCHOOL:
			return HOSPITAL;
		case HOSPITAL:
			return HOUSE;
		case HOUSE:
			return REBEL;
		case REBEL:
			return FISHING;
		case FISHING:
			return PATROL;
		case PATROL:
			return FORT;
		default:
			return null;
		}
	}
	
}
