package com.bb.flatworld.player;

public enum PlayerStep {

//	STABLE(6,0), FRONT_LEG_BACK(0,1), STABLE_ON_MOVE(6,2), FRONT_LEG_FRONT(6,3);
	STABLE(6,0), FRONT_LEG_BACK(6,1), STABLE_ON_MOVE(6,2), FRONT_LEG_FRONT(6,3);
	
	private PlayerStep(int dx,int renderI) {
		this.dx = dx;
		this.renderI = renderI;
	}
	private int dx;
	private int renderI;
	
	public int getDx() {
		return dx;
	}
	
	public int getRenderI() {
		return renderI;
	}
	
	public PlayerStep next(){
		switch (this) {
		case STABLE:
			return FRONT_LEG_BACK;
		case FRONT_LEG_BACK:
			return STABLE_ON_MOVE;
		case STABLE_ON_MOVE:
			return FRONT_LEG_FRONT;
		case FRONT_LEG_FRONT:
			return STABLE;
		default:
			break;
		}
		return null;
	}
	
	
}
