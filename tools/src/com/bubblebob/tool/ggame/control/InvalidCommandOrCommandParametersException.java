package com.bubblebob.tool.ggame.control;

/**
 * Cette erreur peut etre renvoyee par les implementations de CommandControler lorsque leur traitement particulier 
 * d'une commande n'est pas gere
 * @author a163661
 *
 */
public class InvalidCommandOrCommandParametersException extends Exception{

	private static final long serialVersionUID = 8004933617808703581L;

	public InvalidCommandOrCommandParametersException(){
		super("Invalid command name or invalid command parameters");
	}
	
	public InvalidCommandOrCommandParametersException(String msg){
		super(msg);
	}
	
}
