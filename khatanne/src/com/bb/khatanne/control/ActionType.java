package com.bb.khatanne.control;

public enum ActionType {

	PLACE_FREE_COLONY,
	PLACE_FREE_ROAD,
	PLACE_ROAD,
	PLACE_COLONY,
	ROLL_DICE,
	DO_ROLL,
	END_TURN,
	PLACE_CITY,
	MOVE_ROBBER,
	ROB_SPECIFIC,
	ROB,
	DROP_RESOURCES;
	
	public static ActionType parse(String actionType){
		for (ActionType result: ActionType.values()){
			if (result.toString().equalsIgnoreCase(actionType)){
//				System.out.println("ActionType#parse result="+result);
				return result;
			}
		}
		return null;
	}
	
}
