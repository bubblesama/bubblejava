package com.bubblebob.dd.model.dungeon.map;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.security.SecureRandom;
import java.util.List;
import java.util.Vector;

import com.bubblebob.dd.model.dungeon.item.Item;
import com.bubblebob.dd.model.dungeon.item.RandomItem;
import com.bubblebob.dd.open.MapBitsMap;

/**
 * Fournit les outils de sauvegarde des cartes et de generation de cartes a partir de fragments
 * @author Bubblebob
 *
 */
public class DungeonMapFactory {

	private static DungeonMapFactory instance;

	private List<DungeonMap> maps;

	public DungeonMapFactory(){
		scanMapsDir();
	}

	public static DungeonMapFactory getInstance(){
		if (instance == null){
			instance = new DungeonMapFactory();
		}
		return instance;
	}

	/**
	 * Parcourt le dossier des cartes et supprime les maps corrompues
	 */
	private void scanMapsDir(){
		maps = new Vector<DungeonMap>();
		File mapsDir = new File(getTextMapTemplatesDir());
		String[] mapsNames = mapsDir.list();
		for (String mapName: mapsNames){
			if (mapName.endsWith(".tmap")){
				String shortName = mapName.substring(0, mapName.length()-5);
				//System.out.println("[DungeonMapFactory#scanMapsDir] shortname="+shortName);
				DungeonMap map = DungeonMapFactory.loadTextMap(shortName);
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
		return "assets/maps/dungeon/text/";
	}

	/**
	 * Genere un fichier texte representant une carte
	 * @param name
	 * @param map
	 */
	public static void saveTextMap(String name, DungeonMap map){
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
					line.append(map.getTile(i, j).getType().getNumber());
				}
				writer.println(line.toString());
			}
			//...types des cases>
			//<groupes des cases...
			writer.println("#tiles group");
			for (int j=0; j<map.getHeight(); j++){
				StringBuffer line = new StringBuffer();
				for (int i=0; i<map.getWidth(); i++){
					line.append(map.getTile(i, j).getGroup());
				}
				writer.println(line.toString());
			}
			//...groupes des cases>
			//<items...
			writer.println("#items");
			for (int i=0; i<map.getWidth(); i++){
				for (int j=0; j<map.getHeight(); j++){
					DungeonTile t = map.getTile(i,j);
					if (!t.getItems().isEmpty()){
						writer.println(i+","+j+":"+t.getItems().get(0).getName());
					}
				}
			}
			//...items>
			writer.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			writer.close();
		}
	}

	/**
	 * Genere une carte a partir du fichier texte la representant
	 * @param textMapName
	 * @return
	 */
	public static DungeonMap loadTextMap(String textMapName){
		BufferedReader mapFileReader = null;
		try{
			String mapFilePath = getTextMapTemplatesDir()+textMapName+".tmap";
			//System.out.println("[DungeonMapFactory#getMapFromText]IN mapFilePath="+mapFilePath);
			// creation d'un lecteur de fichier
			mapFileReader = new BufferedReader(new InputStreamReader(new FileInputStream(mapFilePath)));
			// lecture de la ligne de commentaire
			mapFileReader.readLine();
			//<dimensions de la carte...
			//lecture de la ligne des dimensions
			String dimLine;

			dimLine = mapFileReader.readLine();
			String[] dims = dimLine.split("x");
			if (dims.length != 2){
				//System.out.println("[DungeonMapFactory#getMapFromText][ERREUR] probleme lors de lecture des dimensions de la carte! ligne="+dimLine);
				return null;
			}
			int mapWidth = -1;
			try {
				mapWidth = Integer.parseInt(dims[0]);
			}catch(Exception e){
				e.printStackTrace();
			}
			if (mapWidth<=0){
				//System.out.println("[DungeonMapFactory#getMapFromText][ERREUR] probleme lors de lecture de la largeur de la carte! donnée="+dims[0]);
				return null;
			}
			int mapHeight = -1;
			try{
				mapHeight = Integer.parseInt(dims[1]);
			}catch(Exception e){
				e.printStackTrace();
			}
			if (mapHeight<=0){
				//System.out.println("[DungeonMapFactory#getMapFromText][ERREUR] probleme lors de lecture de la hauteur de la carte! donnée="+dims[1]);
				return null;
			}
			//System.out.println("[DungeonMapFactory#getMapFromText][DEBUG] dimensions de la carte: "+mapWidth+"x"+mapHeight);
			//...dimensions de la carte>
			DungeonMap result = new DungeonMap(textMapName,mapWidth, mapHeight);
			//lecture des commmentaires
			mapFileReader.readLine();
			//<lecture des types de cases...
			for (int j=0; j<mapHeight; j++){
				String mapLine = mapFileReader.readLine();
				if (mapLine.length() != mapWidth){
					//System.out.println("[DungeonMapFactory#getMapFromText][ERREUR] la ligne "+j+" des types de cases ne contient pas le bon nombre de caractères: "+mapLine.length()+" au lieu de "+mapHeight);
				}else{
					for (int i=0; i<mapWidth; i++){
						DungeonTileType type = DungeonTileType.getTypeFromInt(Integer.parseInt(mapLine.charAt(i)+""));
						result.getTile(i, j).setType(type);
					}
				}
			}

			//...lecture des types de cases>
			//lecture des commmentaires
			mapFileReader.readLine();
			//<lecture des groupes de cases...
			for (int j=0; j<mapHeight; j++){
				String mapLine = mapFileReader.readLine();
				if (mapLine.length() != mapWidth){
					//System.out.println("[DungeonMapFactory#getMapFromFile][ERREUR] la ligne "+j+" des groupes de cases ne contient pas le bon nombre de caractères: "+mapLine.length()+" au lieu de "+mapHeight);
				}else{
					for (int i=0; i<mapWidth; i++){
						int group = Integer.parseInt(mapLine.charAt(i)+"");
						result.getTile(i, j).setGroup(group);
					}
				}
			}

			//...lecture des groupes de cases>
			//lecture des commmentaires
			mapFileReader.readLine();
			//<lecture des items...
			String itemLine = mapFileReader.readLine();
			while (itemLine != null) {
				String[] itemInfos = itemLine.split(":");
				String[] itemPositionInfos = itemInfos[0].split(",");
				String itemName = itemInfos[1];
				Item newItem = null;
				if (itemName.equals("RANDOM")){
					newItem = new RandomItem(null);
				}
				int x = Integer.parseInt(itemPositionInfos[0]);
				int y = Integer.parseInt(itemPositionInfos[1]);
				result.getTile(x,y).addItem(newItem);
				//System.out.println("[DungeonMapFactory#loadTextMap] new item: "+newItem.getName()+" at "+x+","+y);
				itemLine = mapFileReader.readLine();
			}
			//...lecture des items>
			return result;
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				mapFileReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * Genere une carte <i>width</i>x<i>height</i> faite de cartes de taille <i>elementWidth</i>x<i>elementHeight</i>
	 * @param elementWidth
	 * @param elementHeight
	 * @param width
	 * @param height
	 * @return
	 */
	public DungeonMap generateMap(int elementWidth, int elementHeight, int width, int height){
		//System.out.println("[DungeonMapFactory#generateMap] IN elementWidth="+elementWidth+" elementHeight="+elementHeight+" width="+width+" height="+height);
		//<tri des cartes de bonne taille...
		List<DungeonMap> correctMaps = new Vector<DungeonMap>();
		for (DungeonMap map: maps){
			//System.out.println("[DungeonMapFactory#generateMap] MAP OK? map="+map);
			if (map != null){
				//System.out.println("[DungeonMapFactory#generateMap] map dims: map.getWidth()="+map.getWidth()+" map.getHeight()="+map.getHeight());
				
				if( map.getWidth() == elementWidth && map.getHeight() == elementHeight){
					//System.out.println("[DungeonMapFactory#generateMap] MAP OK");
					correctMaps.add(map);
				}
			}
		}
		int mapsTotal = correctMaps.size();
		//System.out.println("[DungeonMapFactory#generateMap] available ("+elementWidth+"x"+elementHeight+") maps: "+mapsTotal);
		//...tri des cartes de bonne taille>
		//<generation de la carte resultante...
		String time = System.currentTimeMillis()+"-seed";
		SecureRandom random = new SecureRandom(time.getBytes());
		DungeonMap resultMap = null;
		for (int i=0; i<width; i++){
			DungeonMap mapColumn = new DungeonMap(correctMaps.get(random.nextInt(mapsTotal)));
			for (int j=0; j<height; j++){
				DungeonMap nextMap = new DungeonMap(correctMaps.get(random.nextInt(mapsTotal)));
				mapColumn.setTileMap(concat(mapColumn.getTileMap(), nextMap.getTileMap(), Direction.DOWN));
			}
			if (resultMap == null){
				resultMap = mapColumn;
			}else{
				resultMap.setTileMap(concat(resultMap.getTileMap(), mapColumn.getTileMap(), Direction.RIGHT));
			}
		}
		//...generation de la carte resultante>
		
//		for (DungeonTile border: resultMap.getBorder()){
//			border.setType(DungeonTileType.WALL);
//		}
		
		//System.out.println("[DungeonMapFactory#generateMap] available ("+elementWidth+"x"+elementHeight+") maps: "+mapsTotal);
		
		return resultMap;
	}


	
	
	
	
	private int constraintOnSide(DungeonMap[][] bitsMap, int i, int j){
		int width = bitsMap.length;
		int height = bitsMap[0].length;
		DungeonMap northernMap = bitsMap[i][(j-1+height)%height];
		DungeonMap southernMap = bitsMap[i][(j+1+height)%height];
		DungeonMap easternMap = bitsMap[(i+1+width)%width][j];
		DungeonMap westernMap = bitsMap[(i-1+width)%width][j];
		int result = 0;
		if (northernMap != null && !northernMap.openedOnSouth()){
			result++;
		}
		if (southernMap != null && !southernMap.openedOnNorth()){
			result++;
		}
		if (easternMap != null && !easternMap.openedOnWest()){
			result++;
		}
		if (westernMap != null && !westernMap.openedOnEast()){
			result++;
		}
		return result;
	}
	
	
	
	public DungeonMap generateConnectedMap(){
		//System.out.println("[DungeonMapFactory#generateConnectedMap] IN");
		
		int bitsOnWidth = 6;
		int bitsOnHeight = 6;
		
		DungeonMap[][] bitsMap = new DungeonMap[bitsOnWidth][bitsOnHeight];
		String seed = System.currentTimeMillis()+"-seed";
		SecureRandom random = new SecureRandom(seed.getBytes());
		//<generation des morceaux de cartes avec controle des raccords...
		for (int i=0;i<bitsOnWidth;i++){
			for (int j=0;j<bitsOnHeight;j++){
				//<calcul des contraintes...
				//<contrainte nord...
				boolean forceOpenOnNorth = false;
				boolean forceNotOpenOnNorth = false;
				DungeonMap northernMap = bitsMap[i][(j-1+bitsOnHeight)%bitsOnHeight];
				if (northernMap != null){
					forceOpenOnNorth = northernMap.openedOnSouth();
					forceNotOpenOnNorth = !northernMap.openedOnSouth();
				}else{
					// dans le cas ou la case n'est pas encore definie, il faut observer les voisines de celle-ci
					int constraintOnNorth = constraintOnSide(bitsMap, i, (j-1+bitsOnHeight)%bitsOnHeight);
					if (constraintOnNorth>0){
						forceOpenOnNorth = true;
						if (constraintOnNorth>1){
							System.out.println("NO WAY!!!");
						}
					}
				}
				//...contrainte nord>
				//<contrainte sud...
				boolean forceOpenOnSouth = false;
				boolean forceNotOpenOnSouth = false;
				DungeonMap southernMap = bitsMap[i][(j+1+bitsOnHeight)%bitsOnHeight];
				if (southernMap != null){
					forceOpenOnSouth = southernMap.openedOnNorth();
					forceNotOpenOnSouth = !southernMap.openedOnNorth();
				}else{
					// dans le cas ou la case n'est pas encore definie, il faut observer les voisines de celle-ci
					int constraintOnSouth = constraintOnSide(bitsMap, i, (j+1+bitsOnHeight)%bitsOnHeight);
					if (constraintOnSouth>0){
						forceOpenOnSouth = true;
						if (constraintOnSouth>1){
							System.out.println("NO WAY!!!");
						}
					}
				}
				//...contrainte sud>
				//<contrainte est...
				boolean forceOpenOnEast = false;
				boolean forceNotOpenOnEast = false;
				DungeonMap easternMap = bitsMap[(i+1+bitsOnWidth)%bitsOnWidth][j];
				if (easternMap != null){
					forceOpenOnEast = easternMap.openedOnWest();
					forceNotOpenOnEast = !easternMap.openedOnWest();
				}else{
					// dans le cas ou la case n'est pas encore definie, il faut observer les voisines de celle-ci
					int constraintOnEast = constraintOnSide(bitsMap, (i+1+bitsOnWidth)%bitsOnWidth, j);
					if (constraintOnEast>0){
						forceOpenOnEast = true;
						if (constraintOnEast>1){
							System.out.println("NO WAY!!!");
						}
					}
				}
				//...contrainte est>
				//<contrainte ouest...
				boolean forceOpenOnWest = false;
				boolean forceNotOpenOnWest = false;
				DungeonMap westernMap = bitsMap[(i-1+bitsOnWidth)%bitsOnWidth][j];
				if (westernMap != null){
					forceOpenOnWest = westernMap.openedOnEast();
					forceNotOpenOnWest = !westernMap.openedOnEast();
				}else{
					// dans le cas ou la case n'est pas encore definie, il faut observer les voisines de celle-ci
					int constraintOnWest = constraintOnSide(bitsMap, (i-1+bitsOnWidth)%bitsOnWidth, j);
					if (constraintOnWest>0){
						forceNotOpenOnWest = true;
						if (constraintOnWest>1){
							System.out.println("NO WAY!!!");
						}
					}
				}
				//...contrainte ouest>
				//...calcul des contraintes>
				
				System.out.println("[DungeonMapFactory#generateConnectedMap] ("+i+","+j+")N("+forceOpenOnNorth+"."+forceNotOpenOnNorth+") S("+forceOpenOnSouth+"."+forceNotOpenOnSouth+") E("+forceOpenOnEast+"."+forceNotOpenOnEast+") W("+forceOpenOnWest+"."+forceNotOpenOnWest+")");
				//<parcours des cartes a la recherche des candidats
				List<DungeonMap> correctsMap = new Vector<DungeonMap>();
				for (DungeonMap mapToTry: maps){
					boolean ok = true;
					ok = mapToTry.openedOnNorth()?(forceOpenOnNorth||!forceNotOpenOnNorth):(forceNotOpenOnNorth||!forceOpenOnNorth);
					ok = ok && (mapToTry.openedOnSouth()?(forceOpenOnSouth||!forceNotOpenOnSouth):(forceNotOpenOnSouth||!forceOpenOnSouth));
					ok = ok && (mapToTry.openedOnWest()?(forceOpenOnWest||!forceNotOpenOnWest):(forceNotOpenOnWest||!forceOpenOnWest));
					ok = ok && (mapToTry.openedOnEast()?(forceOpenOnEast||!forceNotOpenOnEast):(forceNotOpenOnEast||!forceOpenOnEast));
					if (ok){
						correctsMap.add(mapToTry);
					}
				}
				//...parcours des cartes a la recherche d'un candidat>
				//<choix du candidat...
				DungeonMap selectedMap = correctsMap.get(random.nextInt(correctsMap.size()));
				bitsMap[i][j] = selectedMap;
				System.out.println("[DungeonMapFactory#generateConnectedMap] ("+i+","+j+")"+selectedMap.getName()+"  openedOnNorth="+selectedMap.openedOnNorth()+"  (forceOpenOnNorth||!forceNotOpenOnNorth)="+(forceOpenOnNorth||!forceNotOpenOnNorth)+"  (forceNotOpenOnNorth||!forceOpenOnNorth)="+(forceNotOpenOnNorth||!forceOpenOnNorth));
				//...choix du candidat>
			}
		}
		//...generation des morceaux de cartes avec controle des raccords>
		DungeonMap resultMap = getMapFromMaps(bitsMap);
		return resultMap;
	}
	
	/**
	 * Cree un carte en se fondant sur l'alogorithme des cases a un cote ouvert.<br />
	 * Ici le cote ouvert correspond au cote non ouvert
	 * @param width
	 * @param height
	 * @return
	 */
	public DungeonMap generateMapFromRandomBitMap(int width, int height){
		MapBitsMap mapBitsMap = MapBitsMap.getRandomMap(width, height);
		DungeonMap[][] mapsMatrix = new DungeonMap[width][height];
		String seed = System.currentTimeMillis()+" - seed";
		SecureRandom random = new SecureRandom(seed.getBytes());
		for (int i=0;i<width;i++){
			for (int j=0;j<height;j++){
				//<parcours des cartes pour qualification...
				List<DungeonMap> okMaps = new Vector<DungeonMap>();
				for (DungeonMap map: maps){
					switch (mapBitsMap.getMapBit(i, j).getOpenWay()) {
					case TOP:
						if (!map.openedOnNorth()){
							okMaps.add(map);
						}
						break;
					case BOTTOM:
						if (!map.openedOnSouth()){
							okMaps.add(map);
						}
						break;
					case LEFT:
						if (!map.openedOnWest()){
							okMaps.add(map);
						}
						break;
					case RIGHT:
						if (!map.openedOnEast()){
							okMaps.add(map);
						}
						break;

					default:
						break;
					}
					
				}
				//...parcours des cartes pour qualification>
				//<choix de la map au hasard...
				DungeonMap randomChoiceMap = okMaps.get(random.nextInt(okMaps.size()));
				//System.out.println("[DungeonMapFactory#generateMapFromRandomBitMap] ("+i+","+j+") : "+randomChoiceMap.getName());
				mapsMatrix[i][j] = randomChoiceMap;
				//...choix de la map au hasard>
			}
		}
		return getMapFromMaps(mapsMatrix);
	}
	
	private static DungeonMap getMapFromMaps(DungeonMap[][] maps){
		//<calcul de la taille de la carte finale...
		int bitWidth = maps[0][0].getWidth();
		int bitHeight = maps[0][0].getHeight();
		int bitsOnWidth = maps.length;
		int bitsOnHeight = maps[0].length;
		DungeonMap result = new DungeonMap("generated", bitWidth*bitsOnWidth, bitHeight*bitsOnHeight);
		int widthOffset = 0;
		for (int i=0;i<bitsOnWidth;i++){
			int heightOffset = 0;
			for (int j=0;j<bitsOnHeight;j++){
				DungeonMap mapBit = maps[i][j];
				for (int k=0;k<bitWidth;k++){
					for (int l=0;l<bitHeight;l++){
						DungeonTile newTile = new DungeonTile(mapBit.getTile(k,l));
						newTile.setPosition(widthOffset+k, heightOffset+l);
						result.getTileMap()[widthOffset+k][heightOffset+l] = newTile;
						
					}
				}
				heightOffset += bitHeight;
			}
			widthOffset += bitWidth;
		}
		//...calcul de la taille de la carte finale>
		
		
		
		return result;
	}
	
	/**
	 * Cree une nouvelle matrice de cases a partir de deux existantes
	 * @param A
	 * @param B
	 * @param direction; la direction dans laquelle B va etre ajoutee a A
	 * @return
	 */
	private static DungeonTile[][] concat(DungeonTile[][] A, DungeonTile[][] B, Direction direction){
		int newWidth = 0;
		int newHeight = 0;	
		int aWidth = A.length;
		int aHeight = A[0].length;
		int bWidth = B.length;
		int bHeight = B[0].length;
		switch (direction) {
		case UP:
		case DOWN:
			newWidth = Math.max(aWidth, bWidth);
			newHeight = aHeight + bHeight;
			break;
		case LEFT:
		case RIGHT:
			newWidth = aWidth+bWidth;
			newHeight = Math.max(aHeight,bHeight);
		default:
			break;
		}
		//<initialisation du tableau de cases...
		DungeonTile[][] result = new DungeonTile[newWidth][newHeight];
		for (int i=0; i<newWidth; i++){
			for (int j=0; j<newHeight; j++){
				result[i][j] = new DungeonTile(i, j, DungeonTileType.WALL);
			}
		}
		//...initialisation du tableau de cases>
		//<calcul des decalages de copies...
		int startAX = 0;
		int startAY = 0;
		int startBX = 0;
		int startBY = 0;
		switch (direction) {
		case UP:
			startAY = bHeight;
			break;
		case DOWN:
			startBY = aHeight;
			break;
		case LEFT:
			startAX = bWidth;
			break;
		case RIGHT:
			startBX = aWidth;
			break;
		}
		//...calcul des decalages de copies>

		//<copie des cases...
		for (int i=0; i<aWidth; i++){
			for (int j=0; j<aHeight; j++){
				result[i+startAX][j+startAY] = new DungeonTile(A[i][j]);
				result[i+startAX][j+startAY].setPosition(i+startAX, j+startAY);
			}
		}
		for (int i=0; i<bWidth; i++){
			for (int j=0; j<bHeight; j++){
				result[i+startBX][j+startBY] = new DungeonTile(B[i][j]);
				result[i+startBX][j+startBY].setPosition(i+startBX, j+startBY);
			}
		}
		//...copie des cases>
		return result;
	}
	public enum Direction{
		UP,DOWN,LEFT,RIGHT
	}







}
