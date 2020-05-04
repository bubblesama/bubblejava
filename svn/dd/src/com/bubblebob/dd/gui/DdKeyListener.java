	package com.bubblebob.dd.gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.bubblebob.dd.control.DungeonController;
import com.bubblebob.dd.control.WorldController;
import com.bubblebob.dd.model.DdGameModel;
import com.bubblebob.dd.model.Direction;
import com.bubblebob.dd.model.GameMode;

public class DdKeyListener implements KeyListener{

	public DdKeyListener(WorldController worldController, DungeonController dungeonController, DdGameModel model) {
		super();
		this.worldController = worldController;
		this.dungeonController = dungeonController;
		this.model = model;
	}

	private static int KEY_UP = 38;
	private static int KEY_DOWN = 40;
	private static int KEY_LEFT = 37;
	private static int KEY_RIGHT = 39;
	private static int KEY_Z = 90;
	private static int KEY_S = 83;
	private static int KEY_Q = 81;
	private static int KEY_D = 68;
	private static int KEY_X = 88;
	private static int KEY_C = 67;
	private static int KEY_W = 87;
	private static int KEY_A = 65;
	private static int KEY_E = 69;
	private static int KEY_PAD_0 = 96;
	private static int KEY_PAD_1 = 97;
	private static int KEY_PAD_2 = 98;
	private static int KEY_PAD_3 = 99;
	private static int KEY_PAD_4 = 100;
	private static int KEY_PAD_5 = 101;
	private static int KEY_PAD_6 = 102;
	private static int KEY_PAD_7 = 103;
	private static int KEY_PAD_8 = 104;
	private static int KEY_PAD_9 = 105;
	private static int KEY_CONSOLE = 0;
	

	private int CONFIGURED_KEY_UP = KEY_UP;
	private int CONFIGURED_KEY_DOWN = KEY_DOWN;
	private int CONFIGURED_KEY_LEFT = KEY_LEFT;
	private int CONFIGURED_KEY_RIGHT = KEY_RIGHT;
	
	private int CONFIGURED_KEY_NORTH = KEY_PAD_8;
	private int CONFIGURED_KEY_NORTH_EAST = KEY_PAD_9;
	private int CONFIGURED_KEY_EAST = KEY_PAD_6;
	private int CONFIGURED_KEY_SOUTH_EAST = KEY_PAD_3;
	private int CONFIGURED_KEY_SOUTH = KEY_PAD_2;
	private int CONFIGURED_KEY_SOUTH_WEST = KEY_PAD_1;
	private int CONFIGURED_KEY_WEST = KEY_PAD_4;
	private int CONFIGURED_KEY_NORTH_WEST = KEY_PAD_7;
	
	
	
	private int CONFIGURED_KEY_SHOOT_UP = KEY_PAD_8;
	private int CONFIGURED_KEY_SHOOT_DOWN = KEY_PAD_2;
	private int CONFIGURED_KEY_SHOOT_LEFT = KEY_PAD_4;
	private int CONFIGURED_KEY_SHOOT_RIGHT = KEY_PAD_6;
	
	private int CONFIGURED_KEY_SHOOT_UP_RIGHT = KEY_PAD_9;
	private int CONFIGURED_KEY_SHOOT_UP_LEFT = KEY_PAD_7;
	private int CONFIGURED_KEY_SHOOT_DOWN_LEFT = KEY_PAD_1;
	private int CONFIGURED_KEY_SHOOT_DOWN_RIGHT = KEY_PAD_3;
	
	private int CONFIGURED_KEY_PICK = KEY_PAD_0;
	
	private int CONFIGURED_KEY_TOGGLE_MODE = KEY_CONSOLE;
	
	private DungeonController dungeonController;
	private WorldController worldController;
	
	private DdGameModel model;

	public void keyPressed(KeyEvent keyEvent) {
//		System.out.println("[DdKeyListener#keyPressed] IN: "+keyEvent.getKeyCode());
		
		switch (model.getMode()) {
		case DUNGEON:
			if ((keyEvent.getKeyCode()) == CONFIGURED_KEY_LEFT){
				dungeonController.goLeft();
			}
			if ((keyEvent.getKeyCode()) == CONFIGURED_KEY_RIGHT){
				dungeonController.goRight();
			}
			if ((keyEvent.getKeyCode()) == CONFIGURED_KEY_UP){
				dungeonController.goUp();
			}
			if ((keyEvent.getKeyCode()) == CONFIGURED_KEY_DOWN){
				dungeonController.goDown();
			}
			if ((keyEvent.getKeyCode()) == CONFIGURED_KEY_SHOOT_UP){
				dungeonController.tryArrowLaunch(Direction.NORTH);
			}
			if ((keyEvent.getKeyCode()) == CONFIGURED_KEY_SHOOT_DOWN){
				dungeonController.tryArrowLaunch(Direction.SOUTH);
			}
			if ((keyEvent.getKeyCode()) == CONFIGURED_KEY_SHOOT_LEFT){
				dungeonController.tryArrowLaunch(Direction.WEST);
			}
			if ((keyEvent.getKeyCode()) == CONFIGURED_KEY_SHOOT_RIGHT){
				dungeonController.tryArrowLaunch(Direction.EAST);
			}
			if ((keyEvent.getKeyCode()) == CONFIGURED_KEY_SHOOT_UP_RIGHT){
				dungeonController.tryArrowLaunch(Direction.NORTH_EAST);
			}
			if ((keyEvent.getKeyCode()) == CONFIGURED_KEY_SHOOT_DOWN_RIGHT){
				dungeonController.tryArrowLaunch(Direction.SOUTH_EAST);
			}
			if ((keyEvent.getKeyCode()) == CONFIGURED_KEY_SHOOT_DOWN_LEFT){
				dungeonController.tryArrowLaunch(Direction.SOUTH_WEST);
			}
			if ((keyEvent.getKeyCode()) == CONFIGURED_KEY_SHOOT_UP_LEFT){
				dungeonController.tryArrowLaunch(Direction.NORTH_WEST);
			}
			if ((keyEvent.getKeyCode()) == 32){
				dungeonController.togglePause();
			}
			if ((keyEvent.getKeyCode()) == CONFIGURED_KEY_PICK){
				dungeonController.pickItem();
			}
			if ((keyEvent.getKeyCode()) == CONFIGURED_KEY_TOGGLE_MODE){
				dungeonController.toggleMode();
			}
			break;
		case WORLD:
			if ((keyEvent.getKeyCode()) == CONFIGURED_KEY_NORTH){
				worldController.goNorth();
			}
			if ((keyEvent.getKeyCode()) == CONFIGURED_KEY_NORTH_EAST){
				worldController.goNorthEast();
			}
			if ((keyEvent.getKeyCode()) == CONFIGURED_KEY_EAST){
				worldController.goEast();
			}
			if ((keyEvent.getKeyCode()) == CONFIGURED_KEY_SOUTH_EAST){
				worldController.goSouthEast();
			}
			if ((keyEvent.getKeyCode()) == CONFIGURED_KEY_SOUTH){
				worldController.goSouth();
			}
			if ((keyEvent.getKeyCode()) == CONFIGURED_KEY_SOUTH_WEST){
				worldController.goSouthWest();
			}
			if ((keyEvent.getKeyCode()) == CONFIGURED_KEY_WEST){
				worldController.goWest();
			}
			if ((keyEvent.getKeyCode()) == CONFIGURED_KEY_NORTH_WEST){
				worldController.goNorthWest();
			}
		default:
			break;
		}
		
		
	}

	public void keyReleased(KeyEvent keyEvent) {
		if (model.getMode() == GameMode.DUNGEON){
			if ((keyEvent.getKeyCode()) == CONFIGURED_KEY_LEFT){
				dungeonController.stopLeft();
			}
			if ((keyEvent.getKeyCode()) == CONFIGURED_KEY_RIGHT){
				dungeonController.stopRight();
			}
			if ((keyEvent.getKeyCode()) == CONFIGURED_KEY_UP){
				dungeonController.stopUp();
			}
			if ((keyEvent.getKeyCode()) == CONFIGURED_KEY_DOWN){
				dungeonController.stopDown();
			}
		}
	}

	public void keyTyped(KeyEvent arg0) {

	}

	

}
