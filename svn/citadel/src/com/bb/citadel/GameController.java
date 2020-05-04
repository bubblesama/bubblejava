package com.bb.citadel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameController implements ActionWrapper{

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
	private List<CharacterType> chosenCaracters;
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


	public GameController(){
		this.players = new ArrayList<Player>();
		this.deck = new ArrayList<DistrictCard>();
		this.state = GameState.INIT;
		// STATE: CHOOSING_CHAR
		this.availableCharacters = new ArrayList<CharacterType>();
		this.publicDiscardedCharacters = new ArrayList<CharacterType>();
		this.privateDiscardedCharacters = new ArrayList<CharacterType>();
		// STATE: TURNING
		this.playersByCharacter = new HashMap<CharacterType,Player>();
		this.chosenCaracters = new ArrayList<CharacterType>();
		this.pickedCards = new ArrayList<DistrictCard>();
	}

	public void init(int playersNumber, List<String> playerNames) throws GamePlayException{
		checkGameState(GameState.INIT);
		this.deck = DistrictCard.getShuffledDeck("citadel/assets/base.dck");
		for (String playerName: playerNames){
			Player p = new Player(playerName);
			p.init(popDeck(4));
			players.add(p);
		}
		Collections.shuffle(players);
		crown = players.get(0);
		initDone = true;
		launchChoosingTurn();
	}

	private void launchChoosingTurn(){
		// clean des joueurs
		for (Player p: players){
			p.cleanOnPlayTurnEnd();
		}
		// clean des donnees de la manche
		playersByCharacter.clear();
		chosenCaracters.clear();
		playingCharacter = CharacterType.HITMAN;
		killedCharacter = null;
		robbedCharacter = null;
		// melange des persos
		// TODO ecartement en fonction du nombre de joueurs; pour l'instant, une ecartee face cache et une face visible
		availableCharacters.clear();
		publicDiscardedCharacters.clear();
		privateDiscardedCharacters.clear();
		availableCharacters.addAll(Arrays.asList(CharacterType.values()));
		availableCharacters.remove(CharacterType.HIDDEN);
		// ecartement public
		boolean publicDiscardDone = false;
		List<CharacterType> publicDiscardPick = null;
		while (!publicDiscardDone){
			Collections.shuffle(availableCharacters);
			publicDiscardPick = availableCharacters.subList(0, 1);
			if (!publicDiscardPick.contains(CharacterType.KING)){
				publicDiscardDone = true;
			}
		}
		publicDiscardedCharacters.addAll(publicDiscardPick);
		availableCharacters.subList(0, 1).clear();
		// ecartement caches
		privateDiscardedCharacters.addAll(availableCharacters.subList(0, 1));
		availableCharacters.subList(0, 1).clear();
		choosingPlayer = crown;
		state = GameState.CHOOSING_CHAR;
	}

	public void chooseCharacter(Player p, CharacterType character) throws GamePlayException{
		//controle
		checkChoosingPlayer(p);
		checkGameState(GameState.CHOOSING_CHAR);
		if (!availableCharacters.contains(character)){
			throw new GamePlayException();
		}
		// choix du joueur
		choosingPlayer.setCharacter(character);
		availableCharacters.remove(character);
		playersByCharacter.put(character, choosingPlayer);
		chosenCaracters.add(character);
		// passage de tour
		int indexOfPlayer = players.indexOf(choosingPlayer);
		System.out.println("Game#chooseCharacter indexOfPlayer="+indexOfPlayer);
		if (indexOfPlayer >= players.size()-1){
			// fin des choix
			clearActions();
			state = GameState.TURNING;
			playingCharacter = null;
			playingPlayer = null;
			goToNextCharacter();
		}else{
			choosingPlayer = players.get(indexOfPlayer+1);
		}
	}

	public void endTurn(Player p) throws GamePlayException{
		checkPlayingPlayer(p);
		goToNextCharacter();
	}
	
	public void pickGold(Player p) throws GamePlayException{
		// controle
		checkPlayingPlayer(p);
		checkGameState(GameState.TURNING);
		checkClassicalAction();
		if ((playingCharacter == CharacterType.HITMAN || playingCharacter == CharacterType.THIEF)&&(!specialActionDone)){
			throw new GamePlayException();
		}
		// action
		playingPlayer.addGold(2);
		classicActionDone = true;
	}

	public void pickFreeGold(Player p) throws GamePlayException{
		// controle
		checkPlayingPlayer(p);
		checkGameState(GameState.TURNING);
		checkCharacter(CharacterType.MERCHANT);
		checkSpecialAction();
		// action
		playingPlayer.addGold(1);
		specialActionDone = true;
	}

	public void pickTax(Player p) throws GamePlayException{
		// controle
		checkPlayingPlayer(p);
		checkGameState(GameState.TURNING);
		if (playingCharacter != CharacterType.KING && playingCharacter != CharacterType.BISHOP && playingCharacter != CharacterType.MERCHANT && playingCharacter != CharacterType.CONDOTTIERE){
			throw new GamePlayException();
		}
		checkTax();
		// action
		DistrictType taxType = DistrictType.ROYAL;
		if (playingCharacter == CharacterType.BISHOP){
			taxType = DistrictType.RELIGIOUS;
		}
		if (playingCharacter == CharacterType.MERCHANT){
			taxType = DistrictType.BUSINESS;
		}
		if (playingCharacter == CharacterType.CONDOTTIERE){
			taxType = DistrictType.MILITARY;
		}
		int taxBuildingAmount = 0;
		for (DistrictCard building: playingPlayer.getTable()){
			if (building.getType() == taxType){
				taxBuildingAmount++;
			}
		}
		playingPlayer.addGold(taxBuildingAmount);
		pickTaxDone = true;
	}

	public void pickForChooseTwoCards(Player p) throws GamePlayException{
		// controle
		checkPlayingPlayer(p);
		checkGameState(GameState.TURNING);
		checkClassicalAction();
		if ((playingCharacter == CharacterType.HITMAN || playingCharacter == CharacterType.THIEF)&&(!specialActionDone)){
			throw new GamePlayException("Should rob or kill before!");
		}
		//action
		pickedCards.addAll(popDeck(2));
		classicActionDone = true;
	}

	public void chooseCard(Player p, DistrictCard card) throws GamePlayException{
		// controle
		checkPlayingPlayer(p);
		checkGameState(GameState.TURNING);
		checkClassicalAction();
		if (pickedCards.isEmpty() || !pickedCards.contains(card)){
			throw new GamePlayException("Impossible to choose this card!");
		}
		//action
		playingPlayer.addToHand(card);
		pickedCards.clear();
	}

	public void pickTwoCards(Player p) throws GamePlayException{
		// controle
		checkPlayingPlayer(p);
		checkGameState(GameState.TURNING);
		checkCharacter(CharacterType.ARCHITECT);
		checkSpecialAction();
		//action
		playingPlayer.addToHand(popDeck(1).get(0));
		playingPlayer.addToHand(popDeck(1).get(0));
		specialActionDone = true;
	}

	public void constructBuilding(Player p, DistrictCard card) throws GamePlayException{
		// controle
		checkPlayingPlayer(p);
		checkGameState(GameState.TURNING);
		if ((playingCharacter==CharacterType.ARCHITECT&&buildingsDone>3) || (buildingsDone>1&&!(playingCharacter == CharacterType.ARCHITECT))){
			throw new GamePlayException("Too many buildings this turn!");
		}
		if (!playingPlayer.getHand().contains(card)){
			throw new GamePlayException("Not such card in hand!");
		}
		if (card.getCost() > playingPlayer.getGold()){
			throw new GamePlayException("Too expensive!");
		}
		// check d'une carte de meme nom deja jouee
		boolean sameName = false;
		for (DistrictCard onTable: playingPlayer.getTable()){
			if (onTable.getName().equals(card.getName())){
				sameName = true;
			}
		}
		if (sameName){
			throw new GamePlayException("Same building already done!");
		}
		// action
		playingPlayer.build(card);
		buildingsDone++;
		if (playingPlayer.getTable().size() >= 8){
			//TODO
		}
	}

	public void kill(Player p, CharacterType target) throws GamePlayException{
		// controle
		checkPlayingPlayer(p);
		checkGameState(GameState.TURNING);
		checkCharacter(CharacterType.HITMAN);
		checkSpecialAction();
		if (target == CharacterType.HITMAN){
			throw new GamePlayException("Cannot kill \""+CharacterType.HITMAN+"\"!");
		}
		// action
		killedCharacter = target;
		specialActionDone = true;
	}

	public void rob(Player p, CharacterType target) throws GamePlayException{
		// controle
		checkPlayingPlayer(p);
		checkGameState(GameState.TURNING);
		checkCharacter(CharacterType.THIEF);
		checkSpecialAction();
		if (target == CharacterType.HITMAN || target == CharacterType.THIEF ){
			throw new GamePlayException("Cannot rob \""+CharacterType.HITMAN+"\" or \""+CharacterType.THIEF+"\"!");
		}
		// action
		robbedCharacter = target;
		specialActionDone = true;
	}

	public void swapCards(Player p, Player target) throws GamePlayException{
		// controle
		checkPlayingPlayer(p);
		checkGameState(GameState.TURNING);
		checkCharacter(CharacterType.WARLOCK);
		checkSpecialAction();
		// action
		List<DistrictCard> oldTargetCards = new ArrayList<DistrictCard>();
		oldTargetCards.addAll(target.getHand());
		target.getHand().clear();
		target.getHand().addAll(playingPlayer.getHand());
		playingPlayer.getHand().clear();
		playingPlayer.getHand().addAll(oldTargetCards);
		specialActionDone = true;
	}

	public void changeCards(Player p, Player target, List<DistrictCard> cards) throws GamePlayException{

	}

	public void destroy(Player p, Player target, DistrictCard building) throws GamePlayException{
		// controle
		checkPlayingPlayer(p);
		checkGameState(GameState.TURNING);
		checkCharacter(CharacterType.CONDOTTIERE);
		checkSpecialAction();
		if (!target.getTable().contains(building)){
			throw new GamePlayException();
		}
		if (target.getTable().size() >= 8){
			throw new GamePlayException();
		}
		if (playingPlayer.getGold()<building.getCost()-1){
			throw new GamePlayException();
		}
		// action
		target.getHand().remove(building);
		playingPlayer.payGold(building.getCost()-1);
		specialActionDone = true;
	}
	
	public void addCards(String playerName, List<String> cardsTypes) throws GamePlayException{
		// controle
		throw new GamePlayException("\"addCards\" should never be called on real game model!");
	}

	public void showCharacter(String playerName, String characterName) throws GamePlayException{
		// controle
		throw new GamePlayException("\"showCharacter\" should never be called on real game model!");
	}

	private void goToNextCharacter() throws GamePlayException{
		// controle
		checkGameState(GameState.TURNING);
		if (playingCharacter != null && !classicActionDone){
			throw new GamePlayException();
		}
		if ((playingCharacter == CharacterType.HITMAN || playingCharacter == CharacterType.THIEF)&&!specialActionDone){
			throw new GamePlayException();
		}
		if (!pickedCards.isEmpty()){
			throw new GamePlayException();
		}
		//action
		boolean endCharacterRoll = false;
		while (!endCharacterRoll) {
			playingCharacter = CharacterType.next(playingCharacter);
			if (playingCharacter == null){
				// fin de la manche
				endCharacterRoll = true;
			}else{
				if (playingCharacter != killedCharacter){
					playingPlayer = playersByCharacter.get(playingCharacter);
					if (playingPlayer != null){
						endCharacterRoll = true;
					}
				}
			}
		}
		// cas de fin de tour
		if (playingCharacter == null){
			launchChoosingTurn();
		}else{
			// cas du perso cambriole
			if (playingCharacter == robbedCharacter){
				playersByCharacter.get(CharacterType.THIEF).addGold(playingPlayer.getGold());
				playingPlayer.payGold(playingPlayer.getGold());
			}
			//TODO cleaning des choix
			classicActionDone = false;
			specialActionDone = false;
			buildingsDone = 0;
			pickTaxDone = false;
		}
	}

	private List<DistrictCard> popDeck(int cards){
		List<DistrictCard> result = new ArrayList<DistrictCard>();
		result.addAll(deck.subList(0, cards));
		deck.subList(0, cards).clear();
		return result;
	}

	private void checkPlayingPlayer(Player p)throws GamePlayException{
		if (!(this.playingPlayer.getName().equals(p.getName()))){
			throw new GamePlayException("Player \""+p.getName()+"\" is not playing!");
		}
	}
	
	private void checkChoosingPlayer(Player p)throws GamePlayException{
		if (!(this.choosingPlayer.getName().equals(p.getName()))){
			throw new GamePlayException("Player \""+p.getName()+"\" is not choosing!");
		}
	}
	private void checkGameState(GameState state) throws GamePlayException{
		if (this.state != state){
			throw new GamePlayException("State \""+state+"\" is not the current state: \""+this.state+"\"!");
		}
	}

	private void checkClassicalAction() throws GamePlayException{
		if (classicActionDone){
			throw new GamePlayException("Classical action already done!");
		}
	}

	private void checkSpecialAction() throws GamePlayException{
		if (specialActionDone){
			throw new GamePlayException("Special action already done!");
		}
	}

	private void checkTax() throws GamePlayException{
		if (pickTaxDone){
			throw new GamePlayException("Tax already picked!");
		}
	}

	private void checkCharacter(CharacterType character) throws GamePlayException{
		if (playingCharacter != character){
			throw new GamePlayException("\""+character+"\" is cannot do that!");
		}
	}

	private void clearActions(){
		pickedCards.clear();
		classicActionDone = false;
		specialActionDone = false;
		pickTaxDone = false;
		buildingsDone = 0;
	}

	public List<CharacterType> getAvailableCharacters() {return availableCharacters;}
	public GameState getState() {return state;}
	public Player getChoosingPlayer() {return choosingPlayer;}
	public List<CharacterType> getPublicDiscardedCharacters() {return publicDiscardedCharacters;}
	public CharacterType getPlayingCharacter() {return playingCharacter;}
	public Player getPlayingPlayer() {return playingPlayer;}

	public void takeTax(String playerName, int amount, String characterType)
			throws GamePlayException {
		// TODO Auto-generated method stub
		
	}

	

}
