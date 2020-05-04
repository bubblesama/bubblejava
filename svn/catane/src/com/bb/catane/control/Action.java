package com.bb.catane.control;

import com.bb.catane.model.Game;
import com.bb.catane.model.ImpossibleActionException;

public interface Action {

	public Action resolve(Game game)  throws ImpossibleActionException ;
	
	public ActionType getType();
}
