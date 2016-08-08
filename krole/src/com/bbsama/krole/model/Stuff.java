package com.bbsama.krole.model;

public class Stuff {
	
	public StuffType type;
	//TODO a garder?
	private boolean dropped;
	private Mob owner;
	private Cell place;
	
	public Stuff(StuffType type) {
		super();
		this.type = type;
	}
	
	public void notifyAsPicked(Mob mob){
		this.owner = mob;
		this.dropped = false;
		this.place.notifiedAsTaken(this);
		this.place = null;
	}
	
	public void notifyAsDropped(){
		this.place = owner.cell();
		this.owner = null;
		this.dropped = true;
		this.place.notifiedAsDropped(this);
	}

	public void dropFromThinAir(Cell cell){
		this.place = cell;
		this.dropped = true;
		this.place.notifiedAsDropped(this);
	}
	
	public boolean isDropped() {
		return dropped;
	}

	public int i() {
		return place.i();
	}

	public int j() {
		return place.j();
	}


	public Level level() {
		return place.level();
	}
	
	
}
