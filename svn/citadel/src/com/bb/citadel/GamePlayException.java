package com.bb.citadel;

public class GamePlayException extends Exception{

	private static final long serialVersionUID = -3065351409265131664L;
	

	public GamePlayException(){
		super("Boo!");
	}
	
	public GamePlayException(String msg){
		super("GamePlayException! "+msg);
	}
	
}
