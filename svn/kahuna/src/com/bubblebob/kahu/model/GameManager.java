package com.bubblebob.kahu.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameManager {

	private Map<String, Island> islandsByName;
	private Map<Island,Bridge> bridgesByIslands;
	public List<Bridge> bridges;
	public Player white;
	public Player black;
	
	
	private static GameManager instance;
	
	
	
	
	public static GameManager getInstance(){
		if (instance == null){
			instance = new GameManager();
		}
		return instance;
	}
	private  GameManager(){
		this.islandsByName = new HashMap<String, Island>();
		this.bridgesByIslands = new HashMap<Island, Bridge>();
		this.bridges = new ArrayList<Bridge>();
		// TODO: recuperation de la liste des iles
		init("kahuna/assets/game.txt");
	}

	public Collection<Island> getIslands(){
		return islandsByName.values();
	}

	public Collection<Bridge> getBridges(){
		return bridgesByIslands.values();
	}


	private void init(String fileName){
		try{
			// creation des iles
			BufferedReader buff = new BufferedReader(new FileReader(fileName));
			try {
				String islandDesc;
				while ((islandDesc = buff.readLine()) != null) {
					String[] infos = islandDesc.split(" ");
					if (infos.length == 4){
						String name = infos[0];
						Ownership ownership = Ownership.valueOf(infos[1]);
						int centerI = Integer.parseInt(infos[2]);
						int centerJ = Integer.parseInt(infos[3]);
						if (infos[0].length()>0){
							Island island = new Island(name, ownership,centerI, centerJ);
							islandsByName.put(name, island);
							System.out.println("IslandManager#init island:"+name+" owner="+ownership);
						}
					}
				}
			} finally {buff.close();}
		} catch (IOException ioe) {ioe.printStackTrace();}
		//creation des liens
		try{
			// creation des liens
			BufferedReader buff = new BufferedReader(new FileReader(fileName));
			try {
				String islandDesc;
				while ((islandDesc = buff.readLine()) != null) {
					String[] infos = islandDesc.split(" ");
					if (infos.length ==  7){
						Island island1 = islandsByName.get(infos[0]);
						Island island2 = islandsByName.get(infos[1]);
						Ownership ownership = Ownership.valueOf(infos[2]);
						int AI = Integer.parseInt(infos[3]);
						int AJ = Integer.parseInt(infos[4]);
						int BI = Integer.parseInt(infos[5]);
						int BJ = Integer.parseInt(infos[6]);
						if (island1 != null && island2 != null){
							Bridge bridge = new Bridge(island1,island2,ownership,AI,AJ,BI,BJ);
							bridgesByIslands.put(island1, bridge);
							bridgesByIslands.put(island2, bridge);
							bridges.add(bridge);
							System.out.println("IslandManager#init lien:"+island2.name+" "+island1.name);
						}
					}
				}
			} finally {buff.close();}
		} catch (IOException ioe) {ioe.printStackTrace();}
	}

	public static void main(String[] args) {
		GameManager.getInstance();
	}

}
