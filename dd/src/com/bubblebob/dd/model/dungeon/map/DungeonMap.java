package com.bubblebob.dd.model.dungeon.map;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;

import com.bubblebob.dd.model.Position;

public class DungeonMap implements Serializable{

	private String name;
	
	private static final long serialVersionUID = -2933116690196748812L;

	private int width = -1;
	private int height = -1;

	private DungeonTile[][] tiles;

	public int ticker = 0;

	public DungeonMap(String name, int width, int height){
		this.name = name;
		this.width = width;
		this.height = height;
		this.tiles = new DungeonTile[width][height];
		for (int i=0; i<width; i++){
			for (int j=0; j<height; j++){
				this.tiles[i][j] = new DungeonTile(i,j,DungeonTileType.WALL);
			}
		}
	}

	/**
	 * Clone une carte et les items s'y trouvant.<br />
	 * @param initMap
	 */
	public DungeonMap(DungeonMap initMap){
		this.width = initMap.width;
		this.height = initMap.height;
		this.tiles = new DungeonTile[width][height];
		for (int i=0; i<width; i++){
			for (int j=0; j<height; j++){
				this.tiles[i][j] = new DungeonTile(initMap.getTile(i, j));
			}
		}
	}

	public List<DungeonTile> getNeighbours(int i, int j){
		//System.out.println("[DdMap#getNeighbours] i="+i+" j="+j);
		List<DungeonTile> result = new Vector<DungeonTile>();
		for (int p=-1; p<2; p++){
			for (int q=-1; q<2; q++){
				DungeonTile tile = getTile(i+p, j+q);
				if (tile != null){
					result.add(tile);
				}
			}
		}
		return result;
	}

	/**
	 * Fournit les voisins d'une case, sans modulo
	 * @param i
	 * @param j
	 * @return
	 */
	public List<DungeonTile> getContiguousNeighbours(int i, int j){
		//System.out.println("[DdMap#getNeighbours] i="+i+" j="+j);
		List<DungeonTile> result = new Vector<DungeonTile>();
		int[][] neighbours = {{i,j-1},{i-1,j},{i+1,j},{i,j+1}};
		for (int n=0; n<neighbours.length; n++){
			DungeonTile tile = getTile(neighbours[n][0], neighbours[n][1]);
			if (tile != null){
//				System.out.println("[DdMap#getContiguousNeighbours] i="+i+" j="+j+" tile.x="+tile.getX()+" tile.y="+tile.getY()+" group="+tile.getGroup());
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

	public DungeonTile getTile(int i, int j){
		return tiles[(i+width)%width][(j+height)%height];
	}

	public DungeonTile getTile(Position position){
		int i = position.getTileX();
		int j = position.getTileY();
		return getTile(i,j);

	}

	public DungeonTile getTileWithoutControl(int i, int j){
		return tiles[i][j];
	}

	public DungeonTile getTileWithoutModulo(int i, int j){
		if (i>-1 && i<width && j>-1 && j<height){
			return tiles[i][j];
		}else{
			return null;
		}
	}


	/**
	 * Devoile une case et les voisines de celle-ci si elles sont du meme groupe (recursivement)
	 * @param t
	 */
	public void discoverTile(DungeonTile t){
		if (t!= null && !t.isDiscovered()){
//			System.out.println("[DungeonMap#discoverTile] IN x="+t.getX()+" y="+t.getY()+" group="+t.getGroup());
			t.setDiscovered(true);
			for (DungeonTile neighbour: getContiguousNeighbours(t.getX(), t.getY())){
				//System.out.println("[DungeonMap#discoverTile] neighbour="+neighbour.getX()+neighbour.getType()+" "+neighbour.getGroup()+" "+(neighbour.getType() != DungeonTileType.WALL && neighbour.getGroup() == t.getGroup()));
				if (neighbour.getType() != DungeonTileType.WALL && neighbour.getGroup() == t.getGroup()){
					discoverTile(neighbour);
				}
			}
		}
	}

	/**
	 * Fournit la frontiere nord de la carte, c'est-a-dire les cases sur le bord haut
	 * @return
	 */
	public List<DungeonTile> getNorthBorderLine(){
		List<DungeonTile> result = new Vector<DungeonTile>();
		for (int i=0; i<getWidth(); i++){
			result.add(getTileWithoutControl(i, 0));
		}
		return result;
	}

	/**
	 * Fournit la frontiere sud de la carte, c'est-a-dire les cases sur le bord bas
	 * @return
	 */
	public List<DungeonTile> getSouthBorderLine(){
		List<DungeonTile> result = new Vector<DungeonTile>();
		for (int i=0; i<getWidth(); i++){
			result.add(getTileWithoutControl(i, getHeight()-1));
		}
		return result;
	}

	/**
	 * Fournit la frontiere ouest de la carte, c'est-a-dire les cases sur le bord gauche
	 * @return
	 */
	public List<DungeonTile> getWestBorderLine(){
		List<DungeonTile> result = new Vector<DungeonTile>();
		for (int j=0; j<getHeight(); j++){
			result.add(getTileWithoutControl(0, j));
		}
		return result;
	}

	/**
	 * Fournit la frontiere ouest de la carte, c'est-a-dire les cases sur le bord gauche
	 * @return
	 */
	public List<DungeonTile> getEastBorderLine(){
		List<DungeonTile> result = new Vector<DungeonTile>();
		for (int j=0; j<getHeight(); j++){
			result.add(getTileWithoutControl(getWidth()-1, j));
		}
		return result;
	}

	/**
	 * Fournit les cases frontieres d'une carte
	 * @return
	 */
	public List<DungeonTile> getBorder(){
		List<DungeonTile> result = new Vector<DungeonTile>();
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
	public void setTileMap(DungeonTile[][] newTileMap){
		this.tiles = newTileMap;
		this.width = newTileMap.length;
		this.height = newTileMap[0].length;
	}

	public DungeonTile[][] getTileMap(){
		return tiles;
	}

	/**
	 * Creer une nouvelle carte par une rotation d'un quart de tour dans le sens anti-horaire
	 * @return
	 */
	public DungeonMap quarterRotate(){
		DungeonMap result = new DungeonMap(name+"-quarter",height, width);
		for (int i=0; i<getWidth(); i++){
			for (int j=0; j<getWidth(); j++){
				result.tiles[i][j] = new DungeonTile(tiles[height-j-1][i]);
				result.tiles[i][j].setType(result.tiles[i][j].getType().quarterRotate());
			}
		}
		return result;
	}


	/**
	 * Fournit la premiere case libre en commencant par en haut a gauche
	 * @return
	 */
	public Position getFirstEmptyTile(){
		int i=0;
		boolean found = false;
		Position result = null;
		while(!found){
			for (int j=0; j<i; j++){
				if (tiles[j][i-j].getType()== DungeonTileType.EMPTY){
					found = true;
					result = new Position(j, i-j);
				}
			}
			i++;
		}
		//System.out.println("[DungeonMap#getFirstEmptyTile] empty tile: "+result.getTileX()+","+result.getTileY());
		return result;
	}
	
	/**
	 * Indique s'il existe une case vide sur la bordure nord de la carte
	 * @return
	 */
	public boolean openedOnNorth(){
		for (DungeonTile t: getNorthBorderLine()){
			if (t.getType() == DungeonTileType.EMPTY){
				return true;
			}
		}
		return false;
	}
	
	public boolean openedOnSouth(){
		for (DungeonTile t: getSouthBorderLine()){
			if (t.getType() == DungeonTileType.EMPTY){
				return true;
			}
		}
		return false;
	}
	public boolean openedOnWest(){
		for (DungeonTile t: getWestBorderLine()){
			if (t.getType() == DungeonTileType.EMPTY){
				return true;
			}
		}
		return false;
	}
	public boolean openedOnEast(){
		for (DungeonTile t: getEastBorderLine()){
			if (t.getType() == DungeonTileType.EMPTY){
				return true;
			}
		}
		return false;
	}

	public String getName() {
		return name;
	}


}
