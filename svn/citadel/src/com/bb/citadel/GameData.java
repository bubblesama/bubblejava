package com.bb.citadel;

import java.util.List;
import java.util.Map;

public class GameData {

	private List<Player> players;
	private List<DistrictCard> deck;
	private Player crown;

	private GameState state;
	// STATE: INIT
	private boolean initDone = false;

	// STATE: CHOOSING_CHAR
	private Player choosingPlayer;
	private List<CharacterType> availableCharacters;
	private List<CharacterType> publicDiscardedCharacters;
	private List<CharacterType> privateDiscardedCharacters;
	// STATE: TURNING
	// global
	private Map<CharacterType,Player> playersByCharacter;
	private List<CharacterType> chosenCharacters;
	private CharacterType playingCharacter;
	private CharacterType killedCharacter = null;
	private CharacterType robbedCharacter = null;
	private Player playingPlayer = null;
	// par joueur
	private List<DistrictCard> pickedCards;
	private boolean classicActionDone = false;
	private boolean specialActionDone = false;
	private boolean pickTaxDone = false;
	private int buildingsDone = 0;
	
	
}
