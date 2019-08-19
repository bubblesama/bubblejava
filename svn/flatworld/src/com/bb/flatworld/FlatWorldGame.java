package com.bb.flatworld;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.renderer.Renderer;

public class FlatWorldGame extends BasicGame{

	private Input input;
	public UpdateManager updater;
	public RenderManager renderer;
	public RenderingPort port;
	public SpriteManager spriter;

	public FlatWorldData data;

	public FlatWorldGame() {
		super("> flatworld");
		this.updater = new UpdateManager();
		this.renderer = new RenderManager();
		this.data = loadData();
		this.port = new RenderingPort(data.player);
		//updater.add(this.port);
	}

	public void render(GameContainer gc, Graphics g) throws SlickException {
		renderer.render(g,port);
	}

	public void init(GameContainer gc) throws SlickException {
		SpriteManager.init();
		this.input = gc.getInput();
		data.player.setInput(input);
		data.registerOn(updater, renderer);
	}

	public void update(GameContainer gc, int lastUpdate) throws SlickException {
		
		updater.update();
	}

	public FlatWorldData loadData(){
		return new FlatWorldData();
	}

}
