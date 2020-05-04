package com.bubblebob.walker;

import java.util.ArrayList;
import java.util.List;

public class GameContent {

	private List<Bonk> bonks;
	private WalkerEntity walker;
	
	public boolean addBonk(Bonk bonk) {
		return bonks.add(bonk);
	}
	public void clear() {
		bonks.clear();
	}
	public boolean removeBonk(Bonk bonk) {
		return bonks.remove(bonk);
	}
	public List<Bonk> getBonks() {
		return bonks;
	}
	public WalkerEntity getWalker() {
		return walker;
	}
	
	public GameContent(){
		this.bonks = new ArrayList<Bonk>();
		this.walker = new WalkerEntity();
	}
	
}
