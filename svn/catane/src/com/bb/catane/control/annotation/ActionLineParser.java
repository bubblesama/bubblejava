package com.bb.catane.control.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bb.catane.control.Action;
import com.bb.catane.control.ActionDoRoll;
import com.bb.catane.control.ActionType;
import com.bb.catane.model.PlayerType;

public class ActionLineParser {

	private static ActionLineParser instance;
	private ActionLineParser(){}

	public static ActionLineParser getInstance(){
		if (instance == null){
			instance = new ActionLineParser();
			instance.init();
		}
		return instance;
	}

	private Map<ActionType, Class<?>> actionsClassesByType;

	private void init(){
		try {
			actionsClassesByType = new HashMap<ActionType,Class<?>>();
			List<Class<?>> actionClasses = ClassEnumerator.getClassesForPackage(Action.class.getPackage(), false);
			for (Class<?> actionClass: actionClasses){
				if (Action.class.isAssignableFrom(actionClass)){
					String rawActionClassName = actionClass.getSimpleName();
					String actionSimpleName = rawActionClassName.substring("Action".length()).toLowerCase();
					//System.out.println("ActionLineParser#init class processed name = "+actionSimpleName);
					for (ActionType type: ActionType.values()){
						if (type.toString().replaceAll("_", "").toLowerCase().equals(actionSimpleName)){
							//System.out.println("ActionLineParser#init type found = "+actionSimpleName);
							actionsClassesByType.put(type, actionClass);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public Action parseCommandLine(String rawCommandLine){
		Action result = null;
		try{
			String[] params = rawCommandLine.split(" ");
			ActionType type = ActionType.parse(params[0]);
			Class<?> actionClass = actionsClassesByType.get(type);
			PlayerType player = PlayerType.parse(params[1]);
			result = (Action)actionClass.newInstance();
			// TODO controle de la quantite de parametres
			int currentArgumentIndex = 2;
			boolean foundForThisIndex = true;
			while (foundForThisIndex){
				foundForThisIndex = false;
				for (Field field: actionClass.getFields()){
					for (Annotation fieldAnnotation: field.getAnnotations()){
						if (fieldAnnotation instanceof CommandLineArgument){
							CommandLineArgument fieldArgument = (CommandLineArgument)fieldAnnotation;
							if (fieldArgument.order() == currentArgumentIndex){
								foundForThisIndex = true;
								if (field.getType().isAssignableFrom(Integer.TYPE)){
									//System.out.println("ActionLineParser#parseCommandLine field Integer");
									field.set(result, Integer.parseInt(params[currentArgumentIndex-1]));
								}else if (field.getType().isAssignableFrom(PlayerType.class)){
									//System.out.println("ActionLineParser#parseCommandLine field PlayerType");
									field.set(result, PlayerType.parse(params[currentArgumentIndex-1]));
								}else if (field.getType().isAssignableFrom(ActionType.class)){
									//System.out.println("ActionLineParser#parseCommandLine field ActionType");
									field.set(result, ActionType.parse(params[currentArgumentIndex-1]));
								}
								currentArgumentIndex++;
							}
						}
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
