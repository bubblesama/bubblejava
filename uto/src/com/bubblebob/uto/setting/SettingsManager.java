package com.bubblebob.uto.setting;

import java.util.ResourceBundle;

import com.bubblebob.uto.BuyableType;
import com.bubblebob.uto.Period;

public class SettingsManager implements Setting{

	private static SettingsManager instance;
	private ResourceBundle props;
	
	
	public static SettingsManager getInstance(){
		if (instance == null){
			instance = new SettingsManager();
		}
		return instance;
	}
	
	
	private SettingsManager(){
		props = ResourceBundle.getBundle("uto-conf");
	}
	
	public int getMaxCycles(){
		return Integer.parseInt(props.getString("time.cycles"));
	}
	
	public int getPeriodLength(Period p){
		switch (p) {
		case FREE:
			return Integer.parseInt(props.getString("time.free"));
		case SHOW_POP:
			return 1;
		case SHOW_SCORE:
			return 1;

		default:
			break;
		}
		return -1;
	}

	public int getPrice(BuyableType b){
		switch (b) {
		case FACTORY:
			return Integer.parseInt(props.getString("price.factory"));
		case FARM:
			return Integer.parseInt(props.getString("price.farm"));
		case FISHING:
			return Integer.parseInt(props.getString("price.fishing"));
		case FORT:
			return Integer.parseInt(props.getString("price.fort"));
		case HOSPITAL:
			return Integer.parseInt(props.getString("price.hospital"));
		case HOUSE:
			return Integer.parseInt(props.getString("price.house"));
		case PATROL:
			return Integer.parseInt(props.getString("price.patrol"));
		case REBEL:
			return Integer.parseInt(props.getString("price.rebel"));
		case SCHOOL:
			return Integer.parseInt(props.getString("price.school"));
		}
		return 0;

	}
	
	public static void main(String[] args) {
		System.out.println(SettingsManager.getInstance().getPeriodLength(Period.FREE));
	}
	
}