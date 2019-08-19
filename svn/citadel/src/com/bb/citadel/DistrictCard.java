package com.bb.citadel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DistrictCard {

	
	public static final String UNKNOWN_CARD = "null";
	
	
	public DistrictCard(String name, DistrictType type, int cost) {
		super();
		this.name = name;
		this.type = type;
		this.cost = cost;
	}

	private String name;
	private DistrictType type;
	private int cost;

	// STRING: Palais ROYAL 4 x2

	//TODO
	public static List<DistrictCard> getShuffledDeck(String fileName){
		List<String> daStrings = null;
		List<DistrictCard> result = new ArrayList<DistrictCard>();
		try{
			BufferedReader buff = new BufferedReader(new FileReader(fileName));
			try {
				String cardDesc;
				while ((cardDesc = buff.readLine()) != null) {
					String[] infos = cardDesc.split(" ");
					String name = infos[0];
					DistrictType type = DistrictType.parse(infos[1]);
					int cost = Integer.parseInt(infos[2]);
					int amount = Integer.parseInt(infos[3].substring(1));
					for (int i=0;i<amount;i++){
						result.add(new DistrictCard(name, type, cost));
					}
				}
			} finally {buff.close();}
		} catch (IOException ioe) {ioe.printStackTrace();}
		Collections.shuffle(result);
		System.out.println("DistrictCard#getShuffledDeck deck size: "+result.size());
		for (DistrictCard card: result){
			System.out.println("DistrictCard#getShuffledDeck card: "+card.toReadable());
		}
		return result;
	}

	public String getName() {return name;}

	public DistrictType getType() {return type;}

	public int getCost(){return cost;}

	public String toReadable(){return name+" "+type.toString().toUpperCase()+" "+cost;}
	
	public String toString(){return toReadable();}
	
	public static void main(String[] args) {
		List<DistrictCard> deck = DistrictCard.getShuffledDeck("citadel/assets/base.dck");
		System.out.println("DistrictCard#main deck size: "+deck);
		for (DistrictCard card: deck){
			System.out.println("DistrictCard#main card: "+card.toReadable());
		}
	}
	


}
