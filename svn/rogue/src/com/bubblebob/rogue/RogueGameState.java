package com.bubblebob.rogue;


import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.TextField;

public class RogueGameState extends BasicGame{

	private RogueRenderer renderer;
	private Input input;
	private RogueGame game;
	
	
	private static final int STATE_ASK_NAME = 0;
	private static final int STATE_IN_GAME = 1;
	private static final int STATE_ASKING = 2;

	private TextField nameInput;

	public RogueGameState(){
		super("rogueee");
		RogueGame game = new RogueGame();
		renderer = new RogueRenderer();
		renderer.setGame(game);
	}

	public void init(GameContainer gc) throws SlickException {
		RogueRenderer.initSprites();
		this.input = gc.getInput();
		
		// gestion des champs d'entree
//		Font defaultFont = new UnicodeFont(new java.awt.Font("Arial", java.awt.Font.ITALIC, 10));
//		nameInput = new TextField(gc, defaultFont, 20, 20, 300, 200, new ComponentListener(){
//			public void componentActivated(AbstractComponent source) {
//				System.out.println("RogueGameState.nameInput#componentActivated value="+nameInput.getText());
//			}
//		});
//		nameInput.setText("name");
	}

	public void render(GameContainer gc, Graphics g) throws SlickException {
		renderer.render(gc,g);
//		nameInput.render(gc, g);
	}

	public void update(GameContainer gc, int lastUpdate) throws SlickException {
		if (input.isKeyDown(Input.KEY_Z)){
			movePlayerIfPossible(Way.N);
		}
		if (input.isKeyDown(Input.KEY_S)){
			movePlayerIfPossible(Way.S);
		}
		if (input.isKeyDown(Input.KEY_Q)){
			movePlayerIfPossible(Way.W);
		}
		if (input.isKeyDown(Input.KEY_D)){
			movePlayerIfPossible(Way.E);
		}
	}



	private void movePlayerIfPossible(Way way){
		int testI = 0;
		int testJ = 0;
		switch (way) {
		case N:
			testJ--;
			break;
		case NE:
			testI++;
			testJ--;
			break;
		case E:
			testI++;
			break;
		case SE:
			testI++;
			testJ++;
			break;
		case S:
			testJ++;
			break;
		case SW:
			testI--;
			testJ++;
			break;
		case W:
			testI--;
			break;
		case NW:
			testI--;
			testJ--;
			break;
		default:
			break;
		}
//		Floor currentFloor = Dungeon.currentFloor();
//		if (testI<0||testI>=currentFloor.getWidth()||testJ<0||testJ>=currentFloor.getHeight()){
//			//TODO log console
//		}else{
//			if (currentFloor.getCells()[testI][testJ].type == CellType.Floor){
//				Player.i = testI;
//				Player.j = testJ;
//			}
//		}


	}



}
