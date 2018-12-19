package com.bb.advent2018.day15;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class ElfBattler extends BasicGame{
	
	private static final int SCALE = 4;

	private Arena arena;

	public ElfBattler(Arena arena) {
		super("advent 2018 - day 15 - elf battler");
		this.arena = arena;
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		//
		g.setColor(Color.white);
		for (int i=0;i<arena.w;i++) {
			for (int j=0;j<arena.h;j++) {
				if (arena.getCell(i, j).type == CellType.SPACE) {
					g.fillRect(i*SCALE, j*SCALE, SCALE, SCALE);
				}
			}
		}
		//
		g.setColor(Color.green);
		for (Mob mob: arena.mobs) {
			if (mob.type == MobType.GOBLIN) {
				g.setColor(Color.red);
			}
			g.fillRect((mob.i)*SCALE, (mob.j)*SCALE, SCALE, SCALE);
			g.setColor(Color.green);
		}
		//

	}

	@Override
	public void init(GameContainer gc) throws SlickException {}

	@Override
	public void update(GameContainer gc, int spent) throws SlickException {
		arena.doTurn();
	}

	public static void main(String[] args) {
		AppGameContainer app;
		try {
			Arena arena = ArenaFileParser.parseInputFile();
			BasicGame aligner = new ElfBattler(arena);
			app = new AppGameContainer(aligner);
			app.setIcon("res/icon-fake.png");
			app.setDisplayMode(arena.w*SCALE+200, arena.h*SCALE, false);
			app.setMinimumLogicUpdateInterval(10);
			app.isVSyncRequested();
			app.setTargetFrameRate(10);
			app.setShowFPS(false);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	public void keyPressed(int keyCode, char keyChar) {
		if (keyCode == Input.KEY_SPACE){
		}
	}

	public void keyReleased(int keyCode, char keyChar) {
		if (keyCode == Input.KEY_SPACE){
		}
	}
	
	

}
