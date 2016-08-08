package com.bb.khatanne.control;

import com.bb.khatanne.model.Game;
import com.bb.khatanne.model.ImpossibleActionException;

public interface Action {

	public Action resolve(Game game)  throws ImpossibleActionException ;
	
	public ActionType getType();
	
}
