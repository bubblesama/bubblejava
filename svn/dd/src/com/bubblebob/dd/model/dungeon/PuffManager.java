package com.bubblebob.dd.model.dungeon;

import java.util.List;
import java.util.Vector;

import com.bubblebob.dd.model.dungeon.mob.Puff;

/**
 * Gestion des nuages de fumée résultant de la mort d'un mob
 * @author Bubblebob
 *
 */
public class PuffManager {

	private List<Puff> puffs;
	
	public PuffManager(){
		this.puffs = new Vector<Puff>();
	}
	
	public List<Puff> getPuffs(){
		return puffs;
	}
	
	public void addPuff(Puff puff){
		puffs.add(puff);
	}
	
	public void removePuff(Puff puff){
		puffs.remove(puff);
	}
	
	
	public void update(){
		List<Puff> puffsToDelete = new Vector<Puff>();
		for (Puff puff: puffs){
			boolean delete = puff.update();
			if (delete){
				puffsToDelete.add(puff);
			}
		}
		for (Puff puff: puffsToDelete){
			puffs.remove(puff);
		}
	}
	
}
