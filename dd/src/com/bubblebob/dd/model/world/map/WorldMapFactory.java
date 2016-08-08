package com.bubblebob.dd.model.world.map;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

/**
 * Fournit les outils de sauvegarde des cartes et de generation de cartes a partir de fragments
 * @author Bubblebob
 *
 */
public class WorldMapFactory {

	private static WorldMapFactory instance;

	private List<WorldMap> maps;

	public WorldMapFactory(){
		scanMapsDir();
	}

	public static WorldMapFactory getInstance(){
		if (instance == null){
			instance = new WorldMapFactory();
		}
		return instance;
	}

	/**
	 * Parcourt le dossier des cartes et supprime les maps corrompues
	 */
	private void scanMapsDir(){
		maps = new Vector<WorldMap>();
		File mapsDir = new File(getTextMapTemplatesDir());
		String[] mapsNames = mapsDir.list();
		for (String mapName: mapsNames){
			if (mapName.endsWith(".tmap")){
				String shortName = mapName.substring(0, mapName.length()-5);
				System.out.println("shortname="+shortName);
				WorldMap map = WorldMapFactory.loadTextMap(shortName);
				if (map != null){
					maps.add(map);
				}else{
					//suppression des maps corrompues
					//					File toDeleteMapFile = new File(getSerializedMapTemplatesDir()+"/"+mapName);
					//					toDeleteMapFile.delete();
				}
			}
		}
	}

	/**
	 * Fournit le dossier ou sont textualisees les cartes
	 * @return
	 */
	public static String getTextMapTemplatesDir(){
		//		String appdata = System.getenv("appdata");
		//		return appdata+"/dd/maps/world/text/";
		return "assets/maps/world/text/";

	}

	/**
	 * Genere un fichier texte representant une carte
	 * @param name
	 * @param map
	 */
	public static void saveTextMap(String name, WorldMap map){
		String mapFilePath = getTextMapTemplatesDir()+name+".tmap"; 
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(new FileWriter(mapFilePath,false));
			//<dimensions de la carte...
			writer.println("#dimensions: width x height");
			writer.println(map.getWidth()+"x"+map.getHeight());
			//...dimensions de la carte>
			//<types des cases...
			writer.println("#tiles type");
			for (int j=0; j<map.getHeight(); j++){
				StringBuffer line = new StringBuffer();
				for (int i=0; i<map.getWidth(); i++){
					line.append(map.getTile(i, j).getType().getChar());
				}
				writer.println(line.toString());
			}
			//...types des cases>
			writer.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			writer.close();
		}
	}


	public static WorldMap loadRandomTextMap(){
		File mapDir = new File(getTextMapTemplatesDir());
		List<File> textMaps = new ArrayList<File>();
		if (mapDir.isDirectory()){
			for (File potentialTextMap: mapDir.listFiles()){
				if (potentialTextMap.getPath().contains("done") && potentialTextMap.getPath().endsWith(".tmap")){
					textMaps.add(potentialTextMap);
				}
			}
			if (!textMaps.isEmpty()){
				Random random = new Random();
				int chosenIndex = random.nextInt(textMaps.size());
				return loadTextMapFile(textMaps.get(chosenIndex));
			}
		}
		return null;
	}


	private static WorldMap loadTextMapFile(File textMapFile){
		try {
			BufferedReader mapFileReader = null;
			// creation d'un lecteur de fichier
			mapFileReader = new BufferedReader(new InputStreamReader(new FileInputStream(textMapFile)));
			// lecture de la ligne de commentaire
			mapFileReader.readLine();
			//<dimensions de la carte...
			//lecture de la ligne des dimensions
			String dimLine;
			dimLine = mapFileReader.readLine();
			String[] dims = dimLine.split("x");
			if (dims.length != 2){
				System.out.println("[WorldMapFactory#getMapFromText][ERREUR] probleme lors de lecture des dimensions de la carte! ligne="+dimLine);
				return null;
			}
			int mapWidth = -1;
			try {
				mapWidth = Integer.parseInt(dims[0]);
			}catch(Exception e){
				e.printStackTrace();
			}
			if (mapWidth<=0){
				System.out.println("[DdMapFactory#getMapFromText][ERREUR] probleme lors de lecture de la largeur de la carte! donnée="+dims[0]);
				return null;
			}
			int mapHeight = -1;
			try{
				mapHeight = Integer.parseInt(dims[1]);
			}catch(Exception e){
				e.printStackTrace();
			}
			if (mapHeight<=0){
				System.out.println("[DdMapFactory#getMapFromText][ERREUR] probleme lors de lecture de la hauteur de la carte! donnée="+dims[1]);
				return null;
			}
			// System.out.println("[DdMapFactory#getMapFromText][DEBUG] dimensions de la carte: "+mapWidth+"x"+mapHeight);
			//...dimensions de la carte>
			WorldMap result = new WorldMap(mapWidth, mapHeight);
			//lecture des commmentaires
			mapFileReader.readLine();
			//<lecture des types de cases...
			for (int j=0; j<mapHeight; j++){
				String mapLine = mapFileReader.readLine();
				if (mapLine.length() != mapWidth){
					System.out.println("[DdMapFactory#getMapFromText][ERREUR] la ligne "+j+" des types de cases ne contient pas le bon nombre de caractères: "+mapLine.length()+" au lieu de "+mapHeight);
				}else{
					for (int i=0; i<mapWidth; i++){
						WorldTileType type = WorldTileType.getTypeFromChar(mapLine.charAt(i));
						result.getTile(i, j).setType(type);
					}
				}
			}
			//...lecture des types de cases>
			return result;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static WorldMap loadTextMap(String textMapName){
		BufferedReader mapFileReader = null;
		String mapFilePath = getTextMapTemplatesDir()+textMapName+".tmap";
		System.out.println("[WorldMapFactory#getMapFromText]IN mapFilePath="+mapFilePath);
		File mapFile = new File(getTextMapTemplatesDir()+textMapName+".tmap");
		return loadTextMapFile(mapFile);
	}

}
