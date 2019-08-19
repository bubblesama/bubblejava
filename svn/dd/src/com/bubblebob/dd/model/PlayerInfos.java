package com.bubblebob.dd.model;


public class PlayerInfos {

	private boolean gotBoat;
	private boolean gotAxe;
	private boolean gotKey;
	private boolean gotFirstCrownPart;
	private boolean gotSecondCrownPart;
	
	private int arrows;
	private int lives;
	
	public PlayerInfos(int arrows, boolean gotBoat, boolean gotAxe, boolean gotKey, int lives){
		this.arrows = arrows;
		this.gotBoat = gotBoat;
		this.gotAxe = gotAxe;
		this.gotKey = gotKey;
		this.gotFirstCrownPart = false;
		this.gotSecondCrownPart = false;
		this.lives = lives;
	}

	public static PlayerInfos getDefaultInfos(){
		return new PlayerInfos(5, false, false, false,3);
	}

	
	public boolean hasGotBoat() {
		return gotBoat;
	}

	public void setGotBoat(boolean getBoat) {
		this.gotBoat = getBoat;
	}

	public boolean hasGotAxe() {
		return gotAxe;
	}

	public void setGotAxe(boolean getAxe) {
		this.gotAxe = getAxe;
	}

	public boolean hasGotKey() {
		return gotKey;
	}

	public void setGotKey(boolean getKey) {
		this.gotKey = getKey;
	}

	public int getArrows() {
		return arrows;
	}

	public void addArrows(int arrows) {
		this.arrows += arrows;
	}
	
	public void resetArrows() {
		this.arrows = 0;
	}
	
	public void removeArrow() {
		this.arrows -= 1;
	}

	public int getLives() {
		return lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}

	public boolean isGotFirstCrownPart() {
		return gotFirstCrownPart;
	}

	public void setGotFirstCrownPart(boolean gotFirstCrownPart) {
		this.gotFirstCrownPart = gotFirstCrownPart;
	}

	public boolean isGotSecondCrownPart() {
		return gotSecondCrownPart;
	}

	public void setGotSecondCrownPart(boolean gotSecondCrownPart) {
		this.gotSecondCrownPart = gotSecondCrownPart;
	}
	
	
}
