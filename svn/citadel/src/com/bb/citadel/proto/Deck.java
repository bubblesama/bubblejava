package com.bb.citadel.proto;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Deck {

	private List<Card> cards;
	
	public Deck(){
		cards = new ArrayList<Card>();
	}

	public void add(Card e) {
		cards.add(e);
	}
	
	public void shuffle(){
		Collections.shuffle(cards);
	}
	
	public static Deck getRandomDeck(int minValue, int range, int size){
		Deck result = new Deck();
		Random random = new SecureRandom();
		for (int i=0;i<size;i++){
			CardType value;
			int typeValue = random.nextInt(3);
			switch (typeValue) {
			case 0:
				value = CardType.BLUE;
				break;
			case 1:
				value = CardType.GREEN;
				break;
			default:
				value = CardType.RED;
				break;
			}
			result.add(new Card(minValue+random.nextInt(range),value));
		}
		result.shuffle();
		return result;
	}
	
}