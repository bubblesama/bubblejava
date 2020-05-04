package com.bubblebob.rogue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Dungeon {

	public List<Floor> floors;
	public int level = 0;

	private static final String DUNGEONS_LIST = "dungeons.save";
	private static boolean init = false;
	private static List<String> dungeonsList;

	private static void initDungeonsList(){
		dungeonsList = new ArrayList<String>();
		BufferedReader dungeonsBufferReader = null;
		try {
			String line;
			dungeonsBufferReader = new BufferedReader(new FileReader(DUNGEONS_LIST));
			while ((line = dungeonsBufferReader.readLine()) != null) {
				dungeonsList.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (dungeonsBufferReader != null){
					dungeonsBufferReader.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	public Dungeon(){
		int defaultWidth = 38;
		int defaultHeight = 27;
		level = 0;
		floors = new ArrayList<Floor>();
		floors.add(new FloorBland(defaultWidth, defaultHeight));
		floors.add(new FloorBland(defaultWidth, defaultHeight));
		floors.add(new FloorBland(defaultWidth, defaultHeight));
		floors.add(new FloorBland(defaultWidth, defaultHeight));
		floors.add(new FloorBland(defaultWidth, defaultHeight));
		floors.add(new FloorBland(defaultWidth, defaultHeight));
	}
	
	public Floor getCurrentFloor(){
		return floors.get(level);
	}


}
