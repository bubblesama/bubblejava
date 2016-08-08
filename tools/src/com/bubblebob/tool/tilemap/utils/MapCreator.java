package com.bubblebob.tool.tilemap.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import com.bubblebob.tool.tilemap.model.SimpleMap;

public class MapCreator {

	private List<Character> accessibleCharacters;
	private HashMap<Character, Integer> typesByCharacter;
	
	

	public MapCreator(){
		this.accessibleCharacters = new Vector<Character>();
		accessibleCharacters.add(' ');
		accessibleCharacters.add('0');
		this.typesByCharacter = new HashMap<Character, Integer>();
		typesByCharacter.put(' ', 0);
		typesByCharacter.put('X', 1);
		typesByCharacter.put('1', 1);
		typesByCharacter.put('2', 2);
		typesByCharacter.put('3', 3);
		typesByCharacter.put('4', 4);
		typesByCharacter.put('5', 5);
	}

	public SimpleMap getMapFromFile(String filePath){
		try {
			BufferedReader mapFileReader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
			int mapWidth = Integer.parseInt(mapFileReader.readLine().trim());
			int mapHeight =  Integer.parseInt(mapFileReader.readLine().trim());
			System.out.println("[MapCreator#getMapFromFile] mapWidth="+mapWidth+" mapHeight="+mapHeight);
			SimpleMap result = new SimpleMap(mapWidth, mapHeight);
			for (int j=0; j<mapHeight; j++){
				String yLine = mapFileReader.readLine();
				if (yLine != null){
					for (int i=0; i<mapWidth; i++){
						if (i<yLine.length()){
							char tileCharacter = yLine.charAt(i);
							if (typesByCharacter.get(tileCharacter) != null){
								result.getTile(i,j).setType(typesByCharacter.get(tileCharacter));
							}
							if (!accessibleCharacters.contains(tileCharacter)){
								result.getTile(i,j).setAccessible(false);
							}
						}
					}
				}
			}
			return result;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
		}  catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	

}
