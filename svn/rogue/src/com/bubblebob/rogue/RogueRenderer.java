package com.bubblebob.rogue;


import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;


public class RogueRenderer {

	public static final int SCALE = 2;
	public static final int CELL_SIZE = 10;
	private static SpriteSheet tileSheet;
	private static SpriteSheet charSheet;
	
	private RogueGame game;

	public RogueRenderer() {}
	
	public static void initSprites(){
		try {
			tileSheet = new SpriteSheet("rogue/assets/tiles.png", CELL_SIZE, CELL_SIZE);
			charSheet = new SpriteSheet("rogue/assets/sprites.png", 8, 8);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	public void render(GameContainer gc, Graphics g){
		int dx = 20;
		int dy = 30;
		Floor floor = game.dungeon.getCurrentFloor();
		
		for (int i=0;i<floor.getWidth();i++){
			for (int j=0;j<floor.getHeight();j++){
				if (floor.getCells()[i][j].type == CellType.Floor){
					tileSheet.getSprite(1,0).getScaledCopy(SCALE).draw(dx+CELL_SIZE*(i)*SCALE, dy+CELL_SIZE*(j)*SCALE);
				}else if (floor.getCells()[i][j].type == CellType.Wall){
					tileSheet.getSprite(0,0).getScaledCopy(SCALE).draw(dx+CELL_SIZE*(i)*SCALE, dy+CELL_SIZE*(j)*SCALE);
				}else if (floor.getCells()[i][j].type == CellType.Door){
					tileSheet.getSprite(2,0).getScaledCopy(SCALE).draw(dx+CELL_SIZE*(i)*SCALE, dy+CELL_SIZE*(j)*SCALE);
				}
			}
		}
//		charSheet.getSprite(0, 0).getScaledCopy(SCALE).draw(dx+SCALE*(CELL_SIZE*(Player.i)+1), dy+SCALE*(CELL_SIZE*(Player.j)+1));
	}
	
	public void setGame(RogueGame game) {
		this.game = game;
	}
	
}
