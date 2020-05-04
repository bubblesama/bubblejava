package com.bubblebob.rogue.control;

import com.bubblebob.rogue.RogueMap;

public class Controller {

	public RogueMap map;
	
	public void setPlayerDX(int dx){
		map.playerDI = dx;
	}
	
	public void setPlayerDY(int dy){
		map.playerDJ = dy;
	}
	
}
