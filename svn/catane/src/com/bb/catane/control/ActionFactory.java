package com.bb.catane.control;

import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONObject;

import com.bb.catane.model.PlayerType;

public class ActionFactory {

	public static Pattern jsonBasePattern = Pattern.compile("\\{(|.+?)\\\"type\\\":\\\"(\\w+)\\\"(|.+?)}");
	public static Pattern jsonVertexIdPattern = Pattern.compile("\\{(|.+?)\\\"vertexId\\\":\\\"(\\d+)\\\"(|.+?)}");
	public static Pattern jsonTileIdPattern = Pattern.compile("\\{(|.+?)\\\"tileId\\\":\\\"(\\d+)\\\"(|.+?)}");
	public static Pattern jsonSideIdPattern = Pattern.compile("\\{(|.+?)\\\"sideId\\\":\\\"(\\d+)\\\"(|.+?)}");
	public static Pattern jsonPlayerPattern = Pattern.compile("\\{(|.+?)\\\"player\\\":\\\"(BLUE|RED|WHITE|YELLOW)\\\"(|.+?)}");
	public static Pattern jsonFirstDicePattern = Pattern.compile("\\{(|.+?)\\\"firstDicePattern\\\":\\\"(\\d+)\\\"(|.+?)}");
	public static Pattern jsonSecondDicePattern = Pattern.compile("\\{(|.+?)\\\"secondDicePattern\\\":\\\"(\\d+)\\\"(|.+?)}");
	
	public static Pattern commandBasePattern = Pattern.compile("(.+?)(| .+?)");
	
	private ActionFactory(){}
	private static ActionFactory instance;
	
	public static ActionFactory getInstance(){
		if (instance== null){
			instance = new ActionFactory();
			instance.init();
		}
		return instance;
	}

	private void init(){
	}
	
	public static Action parseJsonStyle(String actionString){
		Matcher baseActionMatcher = jsonBasePattern.matcher(actionString);
		if (baseActionMatcher.matches()){
			// recuperation du type d'action
			String actionTypeString = baseActionMatcher.group(2);
			System.out.println("ActionFactory#parseJsonStyle actionType="+actionTypeString);
			ActionType actionType = ActionType.parse(actionTypeString);
			PlayerType playerType = getPlayerType(actionString);
			// creation des actions suivant le type
			switch (actionType) {
			case PLACE_FREE_COLONY:
				return new ActionPlaceFreeColony(playerType, getVertexId(actionString));
			case PLACE_FREE_ROAD:
				return new ActionPlaceFreeRoad(playerType, getSideId(actionString));
			case PLACE_ROAD:
				return new ActionPlaceRoad(playerType, getSideId(actionString));
			case PLACE_COLONY:
				return new ActionPlaceColony(playerType, getVertexId(actionString));
			case ROLL_DICE:
				return new ActionRollDice(playerType);
			case DO_ROLL:
				return new ActionDoRoll(playerType, getFirstDice(actionString), getSecondDice(actionString));

				//TODO
			default:
				break;
			}
		}
		return null;
	}
	
	public static Action parseCommandStyle(String actionString){
		System.out.println("ActionFactory#parseCommandStyle IN");
		Matcher baseActionMatcher = commandBasePattern.matcher(actionString);
		if (baseActionMatcher.matches()){
			// recuperation du type d'action
			String actionTypeString = baseActionMatcher.group(1);
			ActionType actionType = ActionType.parse(actionTypeString);
			System.out.println("ActionFactory#parseCommandStyle actionTypeRaw="+actionTypeString+" actionType="+actionType);
		}
		return null;
	}
	

	public static int getVertexId(String actionString){
		return Integer.parseInt(getSimpleAttributeId(actionString, jsonVertexIdPattern));
	}

	public static int getTileId(String actionString){
		return Integer.parseInt(getSimpleAttributeId(actionString, jsonTileIdPattern));
	}

	public static int getSideId(String actionString){
		return Integer.parseInt(getSimpleAttributeId(actionString, jsonSideIdPattern));
	}

	public static PlayerType getPlayerType(String actionString){
		return PlayerType.parse(getSimpleAttributeId(actionString, jsonPlayerPattern));
	}

	public static int getFirstDice(String actionString){
		return Integer.parseInt(getSimpleAttributeId(actionString, jsonFirstDicePattern));
	}

	public static int getSecondDice(String actionString){
		return Integer.parseInt(getSimpleAttributeId(actionString, jsonSecondDicePattern));
	}

	public static String getSimpleAttributeId(String actionString, Pattern attributePattern){
		Matcher simpleAttributeMatcher = attributePattern.matcher(actionString);
		if (simpleAttributeMatcher.matches()){
			return simpleAttributeMatcher.group(2);
		}
		return "-1";
	}

	public static String getJSonString(Action action){
		JSONObject result = new JSONObject();
		Class actionClass = action.getClass();
		try {
			for (Field field: actionClass.getFields()){
				result.put(field.getName(), field.get(action).toString());
			}
		} catch (IllegalArgumentException e) {e.printStackTrace();} 
		  catch (IllegalAccessException e) {e.printStackTrace();}
		return result.toJSONString();
	}

	public static void main(String[] args) {
		String testString = "{\"type\":\"PLACE_FREE_COLONY\",\"player\":\"RED\",\"vertexId\":\"2\"}";
		Action action = ActionFactory.parseJsonStyle(testString);
		System.out.println("ActionFactory#main parsing result="+action.toString());
		System.out.println("ActionFactory#main jsonification result ="+getJSonString(action));
	}

}
