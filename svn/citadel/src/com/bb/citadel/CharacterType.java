package com.bb.citadel;

public enum CharacterType {
	
	HITMAN, THIEF, WARLOCK, KING, BISHOP, MERCHANT, ARCHITECT, CONDOTTIERE,
	HIDDEN;
	
	public static CharacterType next(CharacterType character){
		if (character == null){
			return HITMAN;
		}else{
			switch (character) {
			case HITMAN:
				return THIEF;
			case THIEF:
				return WARLOCK;
			case WARLOCK:
				return KING;
			case KING:
				return BISHOP;
			case BISHOP:
				return MERCHANT;
			case MERCHANT:
				return ARCHITECT;
			case ARCHITECT:
				return CONDOTTIERE;
			case CONDOTTIERE:
				return null;
			default:
				return null;
			}
		}
	}
	
}
