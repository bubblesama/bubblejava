package com.bubblebob.rogue.control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class KeyController implements KeyListener{

	private final static int KEY_UP = 38;
	private final static int KEY_DOWN = 40;
	private final static int KEY_LEFT = 37;
	private final static int KEY_RIGHT = 39;
	private final static int KEY_Z = 90;
	private final static int KEY_S = 83;
	private final static int KEY_Q = 81;
	private final static int KEY_D = 68;
	private final static int KEY_X = 88;
	private final static int KEY_C = 67;
	private final static int KEY_W = 87;
	private final static int KEY_A = 65;
	private final static int KEY_E = 69;
	private final static int KEY_PAD_0 = 96;
	private final static int KEY_PAD_1 = 97;
	private final static int KEY_PAD_2 = 98;
	private final static int KEY_PAD_3 = 99;
	private final static int KEY_PAD_4 = 100;
	private final static int KEY_PAD_5 = 101;
	private final static int KEY_PAD_6 = 102;
	private final static int KEY_PAD_7 = 103;
	private final static int KEY_PAD_8 = 104;
	private final static int KEY_PAD_9 = 105;
	private final static int KEY_CONSOLE = 0;


	private final static int CONFIGURED_KEY_UP = KEY_UP;
	private final static int CONFIGURED_KEY_DOWN = KEY_DOWN;
	private final static int CONFIGURED_KEY_LEFT = KEY_LEFT;
	private final static int CONFIGURED_KEY_RIGHT = KEY_RIGHT;

	private int lastKey = -1;


	public Controller controller;


	public void keyPressed(KeyEvent keyEvent) {
		// bloque la remanence
//		if (lastKey != keyEvent.getKeyCode()){
			lastKey = keyEvent.getKeyCode();
			int dx = 0;
			int dy = 0;
			switch (keyEvent.getKeyCode()) {
			case CONFIGURED_KEY_RIGHT:
				dx++;
				break;
			case CONFIGURED_KEY_LEFT:
				dx--;
				break;
			case CONFIGURED_KEY_UP:
				dy--;
				break;
			case CONFIGURED_KEY_DOWN:
				dy++;
				break;
			default:
				break;
			}
			if (dx != 0)
			controller.setPlayerDX(dx);
			if (dy != 0)
			controller.setPlayerDY(dy);
//		}
	}

	public void keyReleased(KeyEvent keyEvent) {
		switch (keyEvent.getKeyCode()) {
		case CONFIGURED_KEY_RIGHT:
		case CONFIGURED_KEY_LEFT:
			controller.setPlayerDX(0);
			break;
		case CONFIGURED_KEY_UP:
		case CONFIGURED_KEY_DOWN:
			controller.setPlayerDY(0);
			break;
		}
	}

	public void keyTyped(KeyEvent arg0) {

	}

}
