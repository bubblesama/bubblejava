package com.bb.flatworld;

import java.util.ArrayList;
import java.util.List;

public class UpdateManager {

	private List<Updatable> updatables;
	
	public UpdateManager(){
		this.updatables = new ArrayList<Updatable>();
	}
	
	public void update(){
		for (Updatable u: updatables){
			u.update();
		}
	}

	public boolean add(Updatable e) {
		return updatables.add(e);
	}
	
}
