package com.bubblebob.dd.model.world.map;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;

import com.bubblebob.dd.model.dungeon.item.Item;

public class WorldTile implements Serializable{
	
	private static final long serialVersionUID = -2391270175725815094L;
	
	private WorldTileType type;
	private int i;
	private int j;
	private List<Item> items;
	
	private int group;
	private boolean discovered;
	private boolean visited;

	public WorldTile(WorldTile seed){
		this.i = seed.i;
		this.j = seed.j;
		this.type = seed.type;
		this.items = new Vector<Item>();
		for (Item item: seed.items){
			this.items.add(item);
		}
		this.group = seed.group;
		this.discovered = false;
		this.visited = false;
	}
	
	public WorldTile(int i, int j, WorldTileType type){
		this.i = i;
		this.j = j;
		this.type = type;
		this.items = new Vector<Item>();
		this.group = 0;
		this.discovered = false;
	}
	
	public WorldTileType getType(){
		return type;
	}
	
	public void setType(WorldTileType type){
		this.type = type;
	}
	
	public void setPosition(int i, int j){
		this.i = i;
		this.j = j;
	}
	
	public void addItem(Item item){
		items.add(item);
	}
	
	public List<Item> getItems(){
		return items;
	}
	
	public boolean isWalkable(){
		switch (type) {
		case EMPTY:
			return true;
		default:
			return false;
		}
	}
	
	public int getX(){
		return i;
	}
	
	public int getY(){
		return j;
	}

	public int getGroup() {
		return group;
	}

	public void setGroup(int group) {
		this.group = group;
	}

	public boolean isDiscovered() {
		return discovered;
	}

	public void setDiscovered(boolean discovered) {
		this.discovered = discovered;
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}
	
}
