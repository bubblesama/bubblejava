package com.bubblebob.uto;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.bubblebob.uto.entity.Entity;

public class GraphOrderManager {

	private HashMap<Integer,List<Entity>> entitiesByGraphLayer;

	private static int graphLayers = 10;
	
	public GraphOrderManager(){
		entitiesByGraphLayer = new HashMap<Integer,List<Entity>>();
		for (int i=0;i<graphLayers;i++){
			entitiesByGraphLayer.put(i, new ArrayList<Entity>());
		}
	}
	
	public void addEntity(Entity entity){
		entitiesByGraphLayer.get(entity.getGraphLayer()).add(entity);
	}
	
	public void removeEntity(Entity entity){
		entitiesByGraphLayer.get(entity.getGraphLayer()).remove(entity);
	}

	public void paintInOrder(Graphics g){
		for (int i=0;i<graphLayers;i++){
			for (Entity entity: entitiesByGraphLayer.get(i)){
				entity.paint(g);
			}
		}
	}

}
