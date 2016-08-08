package com.bbsama.krole.model.action;

import com.bbsama.krole.model.Mob;

public class SimpleMoveAction extends BaseAction implements Action{

	public int di;
	public int dj;
	
	public SimpleMoveAction(Mob actor,int di, int dj) {
		super(actor);
		this.di = di;
		this.dj = dj;
	}

	public boolean resolve() {
		return actor.tryMoving(di, dj);
	}

}
