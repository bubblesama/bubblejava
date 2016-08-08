package com.bb.khatanne.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Player {
	
	public PlayerType type;
	public List<Road> roads;
	public List<Colony> colonies;
	public List<City> cities;
	public Map<ResourceType,Integer> resources;
	public int remainingFreeColonies = 2;
	public int remainingFreeRoads = 2;
	public int remainingFreeTurns = 2;
	
	public int resourcesToDrop = 0;
	public boolean mustDropResource = false;
	
	public Player(PlayerType type){
		this.type = type;
		this.roads = new ArrayList<Road>();
		this.colonies = new ArrayList<Colony>();
		this.cities = new ArrayList<City>();
		//resources
		this.resources = new HashMap<ResourceType, Integer>();
		resources.put(ResourceType.WOOD, 0);
		resources.put(ResourceType.CLAY, 0);
		resources.put(ResourceType.STONE, 0);
		resources.put(ResourceType.WHEAT, 0);
		resources.put(ResourceType.WOOL, 0);
	}
	
	public int totalResource(){
		int result = 0;
		for (Integer resource: resources.values()){
			result += resource;
		}
		return result;
	}
	
	
}

