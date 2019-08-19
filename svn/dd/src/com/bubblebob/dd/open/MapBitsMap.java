package com.bubblebob.dd.open;

import java.security.SecureRandom;
import java.util.List;
import java.util.Vector;

import com.bubblebob.tool.ggame.GameModel;

/**
 * Carte faite de morceaux de carte<br/>
 * Genere des cartes donc chaque morceau possede un et un seul cote "ouvert" (ou ferme)<br />
 * Possede une fonction d'ouverture qui peut eventuellement echouer <br/>
 * @author Bubblebob
 *
 */
public class MapBitsMap implements GameModel{
	
	private MapBit[][] mapBits;
	
	public MapBitsMap(int width, int height){
		this.mapBits = new MapBit[width][height];
		for (int i=0;i<width;i++){
			for (int j=0;j<height;j++){
				mapBits[i][j] = new MapBit();
			}
		}
	}

	public int getWidth(){
		return mapBits.length;
	}
	
	public int getHeight(){
		return mapBits[0].length;
		
	}
	
	
	public MapBit getMapBit(int i, int j){
		return mapBits[i][j];
	}
	
	public void openWay(int i, int j, Way way) throws AlreadyOpenedBitException{
		int width = mapBits.length;
		int height = mapBits[0].length;
		switch (way) {
		case TOP:
			mapBits[i][j].openWay(Way.TOP);
			mapBits[i][(j-1+height)%height].openWay(Way.BOTTOM);
			break;
		case BOTTOM:
			mapBits[i][j].openWay(Way.BOTTOM);
			mapBits[i][(j+1+height)%height].openWay(Way.TOP);
			break;
		case LEFT:
			mapBits[i][j].openWay(Way.LEFT);
			mapBits[(i-1+width)%width][j].openWay(Way.RIGHT);
			break;
		case RIGHT:
			mapBits[i][j].openWay(Way.RIGHT);
			mapBits[(i+1+width)%width][j].openWay(Way.LEFT);
			break;
		default:
			break;
		}
	}
	
	public void open() throws NoOpenableWayException, AlreadyOpenedBitException{
		int width = mapBits.length;
		int height = mapBits[0].length;
		String seed = System.currentTimeMillis()+"-seed";
		SecureRandom random = new SecureRandom(seed.getBytes());
		for (int i=0;i<width;i++){
			for (int j=0;j<height;j++){
				MapBit currentBit = mapBits[i][j];
				if (currentBit.getOpenWay() == null){
					// si la case n'est pas ouverte, il va falloir le faire
					List<Way> openableWays = new Vector<Way>();
					
					MapBit topBit = mapBits[i][(j-1+height)%height];
					if (topBit.getOpenWay() == null){
						openableWays.add(Way.TOP);
					}
					MapBit bottomBit = mapBits[i][(j+1+height)%height];
					if (bottomBit.getOpenWay() == null){
						openableWays.add(Way.BOTTOM);
					}
					MapBit rightBit = mapBits[(i+1+width)%width][j];
					if (rightBit.getOpenWay() == null){
						openableWays.add(Way.RIGHT);
					}
					MapBit leftBit = mapBits[(i-1+width)%width][j];
					if (leftBit.getOpenWay() == null){
						openableWays.add(Way.LEFT);
					}
					if (openableWays.isEmpty()){
						throw new NoOpenableWayException(i,j);
					}
					Way wayToOpen = openableWays.get(random.nextInt(openableWays.size()));
					openWay(i, j, wayToOpen);
				}
			}
		}
	}

	/**
	 * Genere une carte ouverte aleatoire de la taille desiree
	 * @param width
	 * @param height
	 * @return
	 */
	public static MapBitsMap getRandomMap(int width, int height){
		boolean found = false;
		int tries = 0;
		MapBitsMap result = null;
		while(!found){
			tries++;
			result = new MapBitsMap(width, height);
			try {
				result.open();
				found = true;
			} catch (NoOpenableWayException e) {
				e.printStackTrace();
			} catch (AlreadyOpenedBitException e) {
				e.printStackTrace();
			}
		}
		System.out.println("[MapBits#getRandomMap] tries="+tries);
		return result;
	}


	public void update(){
		
		
	}
	
}
