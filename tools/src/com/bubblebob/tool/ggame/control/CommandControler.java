package com.bubblebob.tool.ggame.control;

import java.util.HashMap;


/**
 * Interface implementee par les controleurs gerant des commandes de controle
 * @author a163661
 *
 */
public interface CommandControler{
	
	/**
	 * Gere une commande passee, et fournit le statut correspondant a l'execution
	 * @param command la commande a exectuer
	 * @return les retours de la commande sous forme d'une liste de couples (parametre/valeur)
	 */
	public HashMap<String,String> executeCommand(ControlCommand command) throws InvalidCommandOrCommandParametersException;
	
}
