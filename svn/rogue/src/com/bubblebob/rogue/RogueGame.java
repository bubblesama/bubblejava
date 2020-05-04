package com.bubblebob.rogue;

public class RogueGame {

	public Dungeon dungeon;
	public Mob player; 
	
	public RogueGame(){
		this.dungeon = new Dungeon();
		this.player =  new Mob("Bob",2,2);
	}
	
	
}
