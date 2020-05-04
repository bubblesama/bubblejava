package com.bb.citadel.proto;

import java.util.List;

public class View {

	private List<Action> actions;
	
	public void update(){
		if (!actions.isEmpty()){
			actions.get(0).print();
			if (actions.get(0).isFinished()){
				actions.remove(0);
			}
		}
	}
	
	
	
}
