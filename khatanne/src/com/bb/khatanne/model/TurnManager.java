package com.bb.khatanne.model;

import java.util.List;

public class TurnManager {

	private int freeTurns = 2;
	private List<PlayerType> order;
	private int currentTurn = 0;
	private int currentIndex = 0;
	
	public TurnManager(List<PlayerType> order) {
		super();
		this.order = order;
	}
	
	public PlayerType nextPlayer(){
		System.out.println("TurnManager#nextPlayer IN freeTurns="+freeTurns);
		if (freeTurns == 2){
			if (currentIndex >= order.size()-1){
				freeTurns--;
			}else{
				currentIndex++;
			}
		}else if (freeTurns == 1){
			if (currentIndex <0){
				freeTurns--;
				currentTurn = 1;
			}else{
				currentIndex--;
			}
		}else{
			if (currentIndex >= order.size()-1){
				currentIndex = 0;
				currentTurn++;
			}else{
				currentIndex++;
			}
		}
		return order.get(currentIndex);
	}


	public int getCurrentTurn() {
		return currentTurn;
	}
	
	
}
