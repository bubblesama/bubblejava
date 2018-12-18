package com.bb.advent2018.day15;

public enum MobType {

	ELF,GOBLIN;


	public MobType ennemy() {
		MobType result = null;
		switch (this) {
		case ELF:
			result = GOBLIN;
			break;
		case GOBLIN:
			result = ELF;
			break;
		default:
			break;
		}
		return result;
	}


}
