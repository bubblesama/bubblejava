package com.bb.citadel;

import java.util.List;

public interface ActionWrapper {

		// actions gameplay
		public void chooseCharacter(Player p, CharacterType character) throws GamePlayException;
		public void endTurn(Player p) throws GamePlayException;
		public void pickGold(Player p) throws GamePlayException;
		public void pickFreeGold(Player p) throws GamePlayException;
		public void pickTax(Player p) throws GamePlayException;
		public void pickForChooseTwoCards(Player p) throws GamePlayException;
		public void chooseCard(Player p,DistrictCard card) throws GamePlayException;
		public void pickTwoCards(Player p) throws GamePlayException;
		public void constructBuilding(Player p,DistrictCard card) throws GamePlayException;
		public void kill(Player p,CharacterType target) throws GamePlayException;
		public void rob(Player p,CharacterType target) throws GamePlayException;
		public void swapCards(Player p,Player target) throws GamePlayException;
		public void changeCards(Player p, Player target, List<DistrictCard> cards) throws GamePlayException;
		public void destroy(Player p, Player target, DistrictCard building) throws GamePlayException;
		// actions de synchro
		public void addCards(String playerName, List<String> cardsTypes) throws GamePlayException;
		public void showCharacter(String playerName, String characterName) throws GamePlayException;
		public void takeTax(String playerName, int amount, String characterType) throws GamePlayException;
		

}
