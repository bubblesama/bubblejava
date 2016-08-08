package com.bb.khatanne.model;


public enum PlayerType {

	RED,YELLOW,WHITE,BLUE;

	public static PlayerType parse(String playerTypeString) {
		for (PlayerType result: PlayerType.values()){
			if (result.toString().equalsIgnoreCase(playerTypeString)){
				return result;
			}
		}
		return null;
	}

}
