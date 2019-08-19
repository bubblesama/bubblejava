package com.bubblebob.dd.open;

/**
 * Represente un morceau de carte, et son cote ouvert
 * @author Bubblebob
 *
 */
public class MapBit {
	
	private Way openWay;
	
	
	public MapBit(){
		openWay = null;
	}
	
	/**
	 * Ouvre une voie et une seule
	 * @param way
	 */
	public void openWay(Way way) throws AlreadyOpenedBitException{
		if (openWay != null){
			throw new AlreadyOpenedBitException();
		}else{
			this.openWay = way;
		}
	}
	
	public Way getOpenWay(){
		return openWay;
		
	}
	
	
	

}
