package com.bubblebob.uto.control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

public class InputManager implements KeyListener{

	private final static int KEY_Z = 90;
	private final static int KEY_Q = 81;
	private final static int KEY_S = 83;
	private final static int KEY_D = 68;
	private final static int KEY_A = 65;
	private final static int KEY_E = 69;
	private final static int KEY_W = 87;
	private final static int KEY_X = 88;
	private final static int KEY_C = 67;
	private final static int KEY_T = 84;
	private final static int KEY_Y = 89;
	private final static int KEY_U = 85;
	
	private final static int KEY_ARROW_LEFT = 37;
	private final static int KEY_ARROW_UP = 38;
	private final static int KEY_ARROW_RIGHT = 39;
	private final static int KEY_ARROW_DOWN = 40;
	private final static int KEY_NUMPAD_UP_LEFT = 103;
	private final static int KEY_NUMPAD_UP_CENTER = 104;
	private final static int KEY_NUMPAD_UP_RIGHT = 105;
	private final static int KEY_NUMPAD_CENTER = 101;
	
	private final static int KEY_TOP_0 = 48;
	private final static int KEY_TOP_1 = 49;
	private final static int KEY_TOP_2 = 50;
	private final static int KEY_TOP_3 = 51;
	private final static int KEY_TOP_4 = 52;
	private final static int KEY_TOP_5 = 53;
	private final static int KEY_TOP_6 = 54;
	private final static int KEY_TOP_7 = 55;
	private final static int KEY_TOP_8 = 56;
	private final static int KEY_TOP_9 = 57;
	
	private final static int KEY_NUMPAD_CENTER_RIGHT = 100;
	
	private final static int KEY_ENTER = 10;
	
	private final static int RED_MOVE_UP = KEY_Z;
	private final static int RED_MOVE_LEFT = KEY_Q;
	private final static int RED_MOVE_RIGHT = KEY_D;
	private final static int RED_MOVE_DOWN = KEY_S;
	private final static int RED_BUY_SWITCH = KEY_W;
	private final static int RED_BUY_CANCEL = KEY_C;
	private final static int RED_BUY = KEY_X;
	private final static int RED_SWITCH_MOVE_MODE = KEY_A;
	private final static int RED_SHOW_SCORE = KEY_E;
	
	private final static int GREEN_MOVE_UP = KEY_ARROW_UP;
	private final static int GREEN_MOVE_LEFT = KEY_ARROW_LEFT;
	private final static int GREEN_MOVE_RIGHT = KEY_ARROW_RIGHT;
	private final static int GREEN_MOVE_DOWN = KEY_ARROW_DOWN;
	private final static int GREEN_BUY_SWITCH = KEY_NUMPAD_UP_LEFT;
	private final static int GREEN_BUY_CANCEL = KEY_NUMPAD_UP_RIGHT;
	private final static int GREEN_BUY = KEY_NUMPAD_UP_CENTER;
	private final static int GREEN_SWITCH_MOVE_MODE = KEY_NUMPAD_CENTER_RIGHT;
	private final static int GREEN_SHOW_SCORE = KEY_NUMPAD_CENTER;
	
	private final static int DEBUG_SPAWN_FISH = KEY_T;
	private final static int DEBUG_SPAWN_CLOUD = KEY_Y;
	private final static int DEBUG_SPAWN_PIRATE = KEY_U;
	
	private final static int INT_0 = KEY_TOP_0;
	private final static int INT_1 = KEY_TOP_1;
	private final static int INT_2 = KEY_TOP_2;
	private final static int INT_3 = KEY_TOP_3;
	private final static int INT_4 = KEY_TOP_4;
	private final static int INT_5 = KEY_TOP_5;
	private final static int INT_6 = KEY_TOP_6;
	private final static int INT_7 = KEY_TOP_7;
	private final static int INT_8 = KEY_TOP_8;
	private final static int INT_9 = KEY_TOP_9;
	
	private Map<InputType,Boolean> keys;

	public InputManager(){
		keys = new HashMap<InputType, Boolean>();
	}

	public boolean getInput(InputType input){
		return (keys.containsKey(input)?keys.get(input):false);
	}

	public void resetInput(InputType input){
		keys.put(input, false);
	}

	public void setInput(InputType input){
		keys.put(input, true);
	}

	public void keyPressed(KeyEvent keyEvent) {
//		System.out.println("[InputManager#keyPressed] key="+keyEvent.getKeyCode()+" size="+keys.size());
		toggle(keyEvent.getKeyCode(),true);
	}

	public void keyReleased(KeyEvent keyEvent) {
		toggle(keyEvent.getKeyCode(),false);
	}

	private void toggle(int keyCode, boolean pressed){
		switch (keyCode) {
		case RED_MOVE_UP:keys.put(InputType.RED_MOVE_UP, pressed);break;
		case RED_MOVE_DOWN:keys.put(InputType.RED_MOVE_DOWN, pressed);break;
		case RED_MOVE_RIGHT:keys.put(InputType.RED_MOVE_RIGHT, pressed);break;
		case RED_MOVE_LEFT:keys.put(InputType.RED_MOVE_LEFT, pressed);break;
		case RED_BUY_SWITCH:keys.put(InputType.RED_BUY_SWITCH, pressed);break;
		case RED_BUY_CANCEL:keys.put(InputType.RED_BUY_CANCEL, pressed);break;
		case RED_BUY:keys.put(InputType.RED_BUY, pressed);break;
		case RED_SWITCH_MOVE_MODE:keys.put(InputType.RED_SWITCH_MOVE_MODE, pressed);break;
		case RED_SHOW_SCORE:keys.put(InputType.RED_SHOW_SCORE, pressed);break;
		case GREEN_MOVE_UP:keys.put(InputType.GREEN_MOVE_UP, pressed);break;
		case GREEN_MOVE_DOWN:keys.put(InputType.GREEN_MOVE_DOWN, pressed);break;
		case GREEN_MOVE_RIGHT:keys.put(InputType.GREEN_MOVE_RIGHT, pressed);break;
		case GREEN_MOVE_LEFT:keys.put(InputType.GREEN_MOVE_LEFT, pressed);break;
		case GREEN_BUY_SWITCH:keys.put(InputType.GREEN_BUY_SWITCH, pressed);break;
		case GREEN_BUY_CANCEL:keys.put(InputType.GREEN_BUY_CANCEL, pressed);break;
		case GREEN_BUY:keys.put(InputType.GREEN_BUY, pressed);break;
		case GREEN_SWITCH_MOVE_MODE:keys.put(InputType.GREEN_SWITCH_MOVE_MODE, pressed);break;
		case GREEN_SHOW_SCORE:keys.put(InputType.GREEN_SHOW_SCORE, pressed);break;
		case DEBUG_SPAWN_FISH:keys.put(InputType.DEBUG_SPAWN_FISH, pressed);break;
		case DEBUG_SPAWN_CLOUD:keys.put(InputType.DEBUG_SPAWN_CLOUD, pressed);break;
		case DEBUG_SPAWN_PIRATE:keys.put(InputType.DEBUG_SPAWN_PIRATE, pressed);break;
		case INT_0:keys.put(InputType.INT_0, pressed);break;
		case INT_1:keys.put(InputType.INT_1, pressed);break;
		case INT_2:keys.put(InputType.INT_2, pressed);break;
		case INT_3:keys.put(InputType.INT_3, pressed);break;
		case INT_4:keys.put(InputType.INT_4, pressed);break;
		case INT_5:keys.put(InputType.INT_5, pressed);break;
		case INT_6:keys.put(InputType.INT_6, pressed);break;
		case INT_7:keys.put(InputType.INT_7, pressed);break;
		case INT_8:keys.put(InputType.INT_8, pressed);break;
		case INT_9:keys.put(InputType.INT_9, pressed);break;
		case KEY_ENTER:keys.put(InputType.KEY_ENTER, pressed);break;
		default:
			break;
		}
	}

	public void keyTyped(KeyEvent arg0) {
	}

}
