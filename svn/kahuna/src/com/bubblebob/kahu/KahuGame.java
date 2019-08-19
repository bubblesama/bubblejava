package com.bubblebob.kahu;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import com.bubblebob.kahu.model.Bridge;
import com.bubblebob.kahu.model.GameManager;
import com.bubblebob.kahu.model.Island;
import com.bubblebob.kahu.model.Ownership;

public class KahuGame extends BasicGame {

	private final static String boardPath = "kahuna/assets/board.png";
	private final static String mapPath = "kahuna/assets/board.png";
	private final static String whitePawnPath = "kahuna/assets/pawn_white.png";
	private final static String blackPawnPath = "kahuna/assets/pawn_black.png";
	private SpriteSheet board;
	private SpriteSheet map;
	private SpriteSheet whitePawn;
	private SpriteSheet blackPawn;
	

	private Input input;
private boolean leftClicked = false;
	
	
	
	private final static float SCALE = 1.5f;
	private final static float PAWN_SCALE = 0.4f;

	public KahuGame() {
		super("Kahuna");
		GameManager.getInstance();
	}

	public void render(GameContainer gc, Graphics g) throws SlickException {
		// carte
		g.drawImage(board.getSprite(0, 0).getScaledCopy(SCALE), 0, 0);
		// iles
		for (Island island: GameManager.getInstance().getIslands()){
			if (island.owner != Ownership.NONE){
				g.drawImage(island.owner == Ownership.BLACK?blackPawn.getScaledCopy(PAWN_SCALE):whitePawn.getScaledCopy(PAWN_SCALE), island.centerI*SCALE, island.centerJ*SCALE);
			}
		}
		// ponts
		g.setLineWidth(10);
		for (Bridge b: GameManager.getInstance().bridges){
			switch (b.owner) {
			case BLACK:
				g.setColor(Color.black);
				break;
			case WHITE:
				g.setColor(Color.white);
				break;
			default:
				g.setColor(Color.gray);
				break;
			}
			g.drawLine(b.AI*SCALE, b.AJ*SCALE, b.BI*SCALE, b.BJ*SCALE);
		}
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		this.board = new SpriteSheet(boardPath, 500, 393);
		this.map = new SpriteSheet(mapPath, 500, 393);
		this.blackPawn = new SpriteSheet(blackPawnPath, 80, 80);
		this.whitePawn = new SpriteSheet(whitePawnPath, 80, 80);
		this.input = gc.getInput();
	}

	@Override
	public void update(GameContainer gc, int g) throws SlickException {
		if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
			if (!leftClicked){
				leftClicked = true;
//				Color pixel = map.getSprite(0, 0).getColor((int)(input.getMouseX()/SCALE),(int)(input.getMouseY()/SCALE));
//				if (pixel.getRed() >15 && pixel.getRed()<25){
//					System.out.println("KahunaGame#update BARI!");
//				}
				Bridge b = getBridge(input.getMouseX(),input.getMouseY());
				if (b != null){
					System.out.println("KahunaGame#update found bridge:"+b.i.name+" "+b.j.name);
				}
			}
		}else{
			leftClicked = false;
		}

	}
	
	/**
	 * Fournir le pont le plus près du clic de souris
	 * @param rawMouseX
	 * @param rawMouseY
	 * @return
	 */
	public Bridge getBridge(int rawMouseX, int rawMouseY){
		int mouseI = (int)(0.0f+rawMouseX/SCALE);
		int mouseJ = (int)(0.0f+rawMouseY/SCALE);
		Bridge result = null;
		int distance = 1000;
		for (Bridge b: GameManager.getInstance().bridges){
			int middleI = (b.AI+b.BI)/2;
			int middleJ = (b.AJ+b.BJ)/2;
			int tempDistance = (middleI-mouseI)*(middleI-mouseI)+(middleJ-mouseJ)*(middleJ-mouseJ);
			if (tempDistance < distance){
				result = b;
				distance = tempDistance;
			}
		}
		if (result != null){
			System.out.println("Kahugame#getBridge result=("+result.i.name+" "+result.j.name+")");
		}
		return result;
		
	}
	
	
	

}
