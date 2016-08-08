package com.bb.khatanne.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

public class GameBoard {

	public List<Vertex> vertexes;
	public List<Side> sides;
	public List<Tile> tiles;
	public Tile robbers;

	public GameBoard(){
		vertexes = new ArrayList<Vertex>();
		sides = new ArrayList<Side>();
		tiles = new ArrayList<Tile>();
	}

	public static GameBoard createBoard(){
		GameBoard result = new GameBoard();
		Properties properties = new Properties();
		try{
			FileInputStream input = new FileInputStream("resources/khatanne.properties");
			properties.load(input);
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//vertexes
		int vertexNumber = Integer.parseInt(properties.getProperty("vertex.number"));
		Map<Integer,Vertex> vertexesById = new HashMap<Integer, Vertex>();
		for (int i=1;i<=vertexNumber;i++){
			Vertex vertex = new Vertex(i);
			vertexesById.put(i, vertex);
			result.vertexes.add(vertex);
		}
		//tiles
		int tileNumber = Integer.parseInt(properties.getProperty("tile.number"));
		Map<Integer,Tile> tilesById = new HashMap<Integer, Tile>();
		for (int i=1;i<=tileNumber;i++){
			Tile tile = new Tile(i);
			tilesById.put(i, tile);
			result.tiles.add(tile);
		}
		//sides
		int sideNumber = Integer.parseInt(properties.getProperty("side.number"));
		Map<Integer,Side> sidesById = new HashMap<Integer,Side>();
		for (int i=1;i<=sideNumber;i++){
			Side side = new Side(i);
			sidesById.put(i, side);
			result.sides.add(side);
		}
		// creation des liens
		for (int i=1;i<=vertexNumber;i++){
			Vertex vertex = vertexesById.get(i);
			// tiles
			String[] rawTiles = properties.getProperty("vertex."+i+".tiles").split(",");
			for(String rawTileId: rawTiles){
				Tile tile = tilesById.get(Integer.parseInt(rawTileId));
				vertex.tiles.add(tile);
				tile.vertexes.add(vertex);
			}
			// siles
			String[] rawSides = properties.getProperty("vertex."+i+".sides").split(",");
			for(String rawSideId: rawSides){
				Side side = sidesById.get(Integer.parseInt(rawSideId));
				vertex.sides.add(side);
				side.vertexes.add(vertex);
			}
		}
		//listing des terrains
		List<TileType> tileTypes = new ArrayList<TileType>();
		String[] rawLands =  properties.getProperty("tiles.types").split(",");
		for (String rawLand: rawLands){
			String[] rawLandData = rawLand.split("x");
			TileType tileType = TileType.DESERT;
			for (TileType type:TileType.values()){
				if (type.toString().equalsIgnoreCase(rawLandData[1])){
					tileType = type;
				}
			}
			for (int i=1;i<=Integer.parseInt(rawLandData[0]);i++){
				tileTypes.add(tileType);
			}
		}
		//distribution des terrains
		Random random = new Random(System.currentTimeMillis());
		List<Integer> tileIds = new ArrayList<Integer>();
		for (int i=1;i<=tileNumber;i++){
			tileIds.add(i);
		}
		for (int i=0;i<tileNumber;i++){
			// choix d'un index dans la liste des id restants
			int tileIdIndex = random.nextInt(tileNumber-i);
			// choix d'un id
			int chosenTileId = tileIds.get(tileIdIndex);
			// retrait de la liste
			tileIds.remove(tileIdIndex);
			// mise en place du terrain
			Tile chosenTile = tilesById.get(chosenTileId);
			chosenTile.type = tileTypes.get(0);
			tileTypes.remove(0);
			if (chosenTile.type == TileType.DESERT){
				chosenTile.robbers = true;
				result.robbers = chosenTile;
			}
		}
		// choix d'une liste de cases pour la distribution des valeurs
		int sequenceId = 1+random.nextInt(Integer.parseInt(properties.getProperty("tiles.sequences")));
		String[] rawSequencedIds = properties.getProperty("tiles.sequence."+sequenceId).split(",");
		//distribution des valeurs
		String[] rawTileDistribution = properties.getProperty("tiles.distribution").split(",");
		int currentDistributionId = 0;
		for (String rawSequencedId:rawSequencedIds){
			Tile currentTile = (tilesById.get(Integer.parseInt(rawSequencedId)));
			// pas de distribution pour le desert
			if (currentTile.type != TileType.DESERT){
				currentTile.resourceScore = Integer.parseInt(rawTileDistribution[currentDistributionId]);
				currentDistributionId++;
			}
		}
		// affichage de debug
		for (Tile tile: result.tiles){
//			System.out.println("[GameBoard#createBoard] "+tile.getDebugString());
		}
		return result;
	}


	public static void main(String[] args) {
		createBoard();
	}

}
