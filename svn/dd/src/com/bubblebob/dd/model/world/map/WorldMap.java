package com.bubblebob.dd.model.world.map;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;

import com.bubblebob.dd.model.Position;

public class WorldMap implements Serializable{

	private static final long serialVersionUID = 1181048979380117538L;
	
	private int width = -1;
	private int height = -1;

	
	private int lowLeftLastDungeonX = 16;
	private int lowLeftLastDungeonY = 5;
	
	private WorldTile[][] tiles;

	public int ticker = 0;

	public WorldMap(int width, int height){
		this.width = width;
		this.height = height;
		this.tiles = new WorldTile[width][height];
		for (int i=0; i<width; i++){
			for (int j=0; j<height; j++){
				this.tiles[i][j] = new WorldTile(i,j,WorldTileType.EMPTY);
			}
		}
	}

	/**
	 * Cache toutes les cases. L'effet graphique n'est visible que sur les montagnes
	 */
	public void coverMountains(){
		for (int i=0;i<getWidth();i++ ){
			for (int j=0;j<getHeight();j++ ){
				getTileWithoutControl(i, j).setDiscovered(false);
			}
		}
		
	}
	
	/**
	 * Fournit les cases voisines
	 * @param i
	 * @param j
	 * @return
	 */
	public List<WorldTile> getNeighbours(int i, int j){
		//System.out.println("[DdMap#getNeighbours] i="+i+" j="+j);
		List<WorldTile> result = new Vector<WorldTile>();
		for (int p=-1; p<2; p++){
			for (int q=-1; q<2; q++){
				WorldTile tile = getTile(i+p, j+q);
				if (tile != null){
//					System.out.println("[DdMap#getNeighbours]i="+i+" j="+j+" tile.x="+tile.getX()+" tile.y="+tile.getY());
					result.add(tile);
				}
			}
		}
		return result;
	}

	/**
	 * Fournit les voisins directs: haut bas gauche droite
	 * @param i
	 * @param j
	 * @return
	 */
	public List<WorldTile> getContiguousNeighbours(int i, int j){
		//System.out.println("[DdMap#getNeighbours] i="+i+" j="+j);
		List<WorldTile> result = new Vector<WorldTile>();
		int[][] neighbours = {{i,j-1},{i-1,j},{i+1,j},{i,j+1}};
		for (int n=0; n<neighbours.length; n++){
			WorldTile tile = getTile(neighbours[n][0], neighbours[n][1]);
			if (tile != null){
//				System.out.println("[DdMap#getContiguousNeighbours] i="+i+" j="+j+" tile.x="+tile.getX()+" tile.y="+tile.getY());
				result.add(tile);
			}
		}
		return result;
	}

	public int getWidth(){
		return width;
	}

	public int getHeight(){
		return height;
	}
	
	public int getTileWidth(){
		return tiles.length;
	}

	public int getTileHeight(){
		return tiles[0].length;
	}

	public WorldTile getTile(int i, int j){
		if (i>=0 && i<width && j>=0 && j<height){
			return tiles[i][j];
		}else{
			return null;
		}
	}

	public WorldTile getTile(Position position){
		int i = position.getTileX();
		int j = position.getTileY();
		return getTile(i,j);

	}

	public WorldTile getTileWithoutControl(int i, int j){
		return tiles[i][j];
	}

	/**
	 * Devoile une case et les voisines de celle-ci( non recursivement)
	 * @param t
	 */
	public void discoverTileAndNeighbours(WorldTile t){
		for (int i=-1;i<2;i++){
			for (int j=-1;j<2;j++){
				if (getTile(t.getX()+i, t.getY()+j) != null){
					getTile(t.getX()+i, t.getY()+j).setDiscovered(true);
				}
			}
		}
		
	}
	
	/**
	 * Fournit la frontiere nord de la carte, c'est-a-dire les cases sur le bord haut
	 * @return
	 */
	public List<WorldTile> getNorthBorderLine(){
		List<WorldTile> result = new Vector<WorldTile>();
		for (int i=0; i<getWidth(); i++){
			result.add(getTileWithoutControl(i, 0));
		}
		return result;
	}
	
	/**
	 * Fournit la frontiere sud de la carte, c'est-a-dire les cases sur le bord bas
	 * @return
	 */
	public List<WorldTile> getSouthBorderLine(){
		List<WorldTile> result = new Vector<WorldTile>();
		for (int i=0; i<getWidth(); i++){
			result.add(getTileWithoutControl(i, getHeight()-1));
		}
		return result;
	}
	
	/**
	 * Fournit la frontiere ouest de la carte, c'est-a-dire les cases sur le bord gauche
	 * @return
	 */
	public List<WorldTile> getWestBorderLine(){
		List<WorldTile> result = new Vector<WorldTile>();
		for (int j=0; j<getHeight(); j++){
			result.add(getTileWithoutControl(0, j));
		}
		return result;
	}
	
	/**
	 * Fournit la frontiere ouest de la carte, c'est-a-dire les cases sur le bord gauche
	 * @return
	 */
	public List<WorldTile> getEastBorderLine(){
		List<WorldTile> result = new Vector<WorldTile>();
		for (int j=0; j<getHeight(); j++){
			result.add(getTileWithoutControl(getWidth()-1, j));
		}
		return result;
	}
	
	public List<WorldTile> getBorder(){
		List<WorldTile> result = new Vector<WorldTile>();
		for (int i=0; i<getWidth(); i++){
			result.add(getTileWithoutControl(i, 0));
			result.add(getTileWithoutControl(i, getHeight()-1));
		}
		for (int j=1; j<getHeight()-1; j++){
			result.add(getTileWithoutControl(0, j));
			result.add(getTileWithoutControl(getWidth()-1, j));
		}
		return result;
	}
	
	/**
	 * 
	 * Change le contenu de cases, et donc largeur et longueur
	 * @param newTileMap
	 */
	public void setTileMap(WorldTile[][] newTileMap){
		this.tiles = newTileMap;
		this.width = newTileMap.length;
		this.height = newTileMap[0].length;
	}
	
	public WorldTile[][] getTileMap(){
		return tiles;
	}
	
	public static WorldMap getDefaultMap(){
		WorldMap result = new WorldMap(19, 11);
		result.tiles[0][5].setType(WorldTileType.HOUSE);
		return result;
	}

	public int getLowLeftLastDungeonX() {
		return lowLeftLastDungeonX;
	}

	public int getLowLeftLastDungeonY() {
		return lowLeftLastDungeonY;
	}
	
	/**
	 * Indique si une case fait partie du dongeon principal
	 * @param i
	 * @param j
	 * @return
	 */
	public boolean isMainDungeon(int i, int j){
		for (int p=0;p<2;p++){
			int currentI = lowLeftLastDungeonX + p;
			if (currentI == i){
				for (int q=0;q<2;q++){
					int currentJ = lowLeftLastDungeonY - q;
					if (currentJ == j){
						return true;
					}
				}
			}
		}
		return false;
	}
	
}
