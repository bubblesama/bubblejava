package com.bubblebob.tool.ggame.control;

public class ControlCommandParsingException extends Exception{
	
	private static final long serialVersionUID = 5365475125141666960L;

	public ControlCommandParsingException(String msg){
		super(msg);
	}

	public ControlCommandParsingException(){
		super("ControlCommandParsingException");
	}
}
