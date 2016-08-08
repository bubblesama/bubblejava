package com.bubblebob.dd.open;

/**
 * Exception levee lors de l'ouverture d'une carte si elle ne peut etre totalement ouverte
 * @author Bubblebob
 *
 */
public class NoOpenableWayException extends Exception{
	
	private static final long serialVersionUID = 4789309755045775167L;

	public NoOpenableWayException(int i, int j){
		super("No openable way for "+i+","+j);
	}
	

}
