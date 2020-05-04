package com.bubblebob.dd.editor.world;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
public class WorldEditorKeyListener implements KeyListener{



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
	

	private int CONFIGURED_KEY_UP = KEY_UP;
	private int CONFIGURED_KEY_DOWN = KEY_DOWN;
	private int CONFIGURED_KEY_LEFT = KEY_LEFT;
	private int CONFIGURED_KEY_RIGHT = KEY_RIGHT;
	
	private WorldEditorController controller;

	public WorldEditorKeyListener(WorldEditorController controller) {
		super();
		this.controller = controller;
	}
	

	public void keyPressed(KeyEvent keyEvent) {
//		System.out.println("[DdEditorKeyListener#keyPressed] IN: "+keyEvent.getKeyCode());
		if ((keyEvent.getKeyCode()) == 32){
			//controller.toggleMode();
		}
		
	}

	public void keyReleased(KeyEvent keyEvent) {
	}

	public void keyTyped(KeyEvent arg0) {

	}

	

}
