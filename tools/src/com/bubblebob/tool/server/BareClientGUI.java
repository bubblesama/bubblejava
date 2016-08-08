package com.bubblebob.tool.server;


import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.TextField;

public class BareClientGUI extends BasicGame{

	public BareClientGUI() {
		super("GameServer - client");
	}

	private TextField textField;
	private String consoleIn;
	private List<String> consoleOut;
	
	public void render(GameContainer container, Graphics g) throws SlickException {
		g.setColor(Color.red);
		textField.render(container, g);
		consoleOut = new ArrayList<String>();
	}
	
	private void concat(String line){
		consoleOut.add(line);
	}
	
	@Override
	public void init(GameContainer container) throws SlickException {
		textField = new TextField(container,container.getDefaultFont(), 10,50,500,100, new ComponentListener() {
			public void componentActivated(AbstractComponent source) {
				textField.setFocus(true);
				consoleIn = textField.getText();
				textField.setText("");
			}
		});
	}

	@Override
	public void update(GameContainer gc, int lastUpdate) throws SlickException {
		
	}
	
	public static void main(String[] args) {
		AppGameContainer app;
		try {
			app = new AppGameContainer(new BareClientGUI());
			app.setDisplayMode(600,480,false);
			app.setMinimumLogicUpdateInterval(1000/60);
			app.isVSyncRequested();
			app.setTargetFrameRate(60);
			app.setShowFPS(true);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

}
