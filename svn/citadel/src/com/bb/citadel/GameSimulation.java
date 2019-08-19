package com.bb.citadel;

import java.util.ArrayList;
import java.util.List;

public class GameSimulation {

	public static void main(String[] args) {
		GameController game = new GameController();
		List<String> players = new ArrayList<String>();
		players.add("Alice");
		players.add("Bob");
		players.add("Chris");
		players.add("David");
		try {
			game.init(4, players);
			System.out.println("GameSimulation#main init done, state(CHOOSING_CHAR)="+game.getState());
			System.out.println("GameSimulation#main public discard="+game.getPublicDiscardedCharacters());
			System.out.println("GameSimulation#main first player="+game.getChoosingPlayer().getName()+" choosing between: "+game.getAvailableCharacters());
			game.chooseCharacter(game.getChoosingPlayer(), game.getAvailableCharacters().get(0));
			System.out.println("GameSimulation#main second player="+game.getChoosingPlayer().getName()+" choosing between: "+game.getAvailableCharacters());
			game.chooseCharacter(game.getChoosingPlayer(), game.getAvailableCharacters().get(0));
			System.out.println("GameSimulation#main third player="+game.getChoosingPlayer().getName()+" choosing between: "+game.getAvailableCharacters());
			game.chooseCharacter(game.getChoosingPlayer(), game.getAvailableCharacters().get(0));
			System.out.println("GameSimulation#main fourth player="+game.getChoosingPlayer().getName()+" choosing between: "+game.getAvailableCharacters());
			game.chooseCharacter(game.getChoosingPlayer(), game.getAvailableCharacters().get(0));
			System.out.println("GameSimulation#main choosing done, state(TURNING)="+game.getState());
			System.out.println("GameSimulation#main first player="+game.getChoosingPlayer().getName()+" playing as: "+game.getPlayingCharacter()+", gold="+game.getPlayingPlayer().getGold()+", hand="+game.getPlayingPlayer().getHand()+", table="+game.getPlayingPlayer().getTable());
		} catch (GamePlayException e) {
			e.printStackTrace();
		}
	}
	
}
