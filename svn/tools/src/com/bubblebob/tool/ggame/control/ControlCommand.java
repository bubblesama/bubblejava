package com.bubblebob.tool.ggame.control;

import java.util.HashMap;
import java.util.Iterator;


/**
 * Objet de creation de requete de controle "brute".<br>
 * Cette requete peut etre envoyee a un serveur distant sous forme de texte qui peut la <i>parser</i> ensuite et fournir une reponse.
 * @author a163661
 */
public class ControlCommand {
	
	private HashMap<String, String> parameters;
	
	private String simpleCommand;
	
	public ControlCommand() {
		super();
		parameters = new HashMap<String, String>();
	}
	
	/**
	 * Cree une requete
	 * @throws ControlCommandParsingException 
	 */
	public static ControlCommand parseControlCommand(String controlCommandStream) throws ControlCommandParsingException {
		ControlCommand result = new ControlCommand();
		String[] params = controlCommandStream.split("&");
		for (int i=0; i<params.length; i++){
			String[] couple = params[i].split("=");
			if (couple.length != 2){
				throw new ControlCommandParsingException("Invalid couple parameter/value: "+params[i]);
			}
		}
		return result;
	}

	public void addParam(String param, String value){
		parameters.put(param, value);
	}
	
	public String getParameter(String param){
		return parameters.get(param);
	}
	
	public HashMap<String, String> getParameters(){
		return parameters;
	}
	
	private void initSimpleCommand(){
		String result = "";
		int i = 0;
		int j = parameters.keySet().size()-1;
		for (Iterator<String> iterator = parameters.keySet().iterator(); iterator.hasNext();) {
			String  param = iterator.next();
			result += param+"="+parameters.get(param);
			if (i < j){
				result += "&";
			}
			i++;
		}
		simpleCommand = result;
	}

	public String getRawCommand(){
		if (simpleCommand == null){
			initSimpleCommand();
		}
		return simpleCommand;
	}
	
}
