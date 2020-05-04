package com.bb.citadel;

import java.util.ArrayList;
import java.util.List;

public class Player {
	
	private String name;
	private List<DistrictCard> hand;
	private List<DistrictCard> table;
	private int gold;
	private CharacterType character;
	
	public Player(String name){
		this.name = name;
		this.hand = new ArrayList<DistrictCard>();
		this.table = new ArrayList<DistrictCard>();
		this.gold = 0;
		this.character = null;
	}
	
	public void init(List<DistrictCard> newHand){
		this.gold = 2;
		this.hand.addAll(newHand);
	}

	public void cleanOnPlayTurnEnd(){this.character = null;}
	public CharacterType getCharacter() {return character;}
	public void setCharacter(CharacterType character) {this.character = character;}
	public void addGold(int amount){gold += amount;}
	public void payGold(int amount){gold -= amount;}
	public int getGold(){return gold;}
	public List<DistrictCard> getTable() {return table;}
	public boolean addToHand(DistrictCard e) {return hand.add(e);}
	public List<DistrictCard> getHand() {return hand;}
	public String getName() {return name;}	
	
	public void build(DistrictCard card){
		gold -= card.getCost();
		table.add(card);
		hand.remove(card);
	}
	
}
