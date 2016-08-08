package com.bbsama.krole;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.bbsama.krole.model.ActionState;
import com.bbsama.krole.model.Cell;
import com.bbsama.krole.model.GameStateManager;
import com.bbsama.krole.model.MobManager;
import com.bbsama.krole.model.StuffManager;
import com.bbsama.krole.model.WorldManager;
import com.bbsama.krole.view.GameRenderer;
import com.bbsama.krole.view.Logger;

public class GameState extends BasicGameState{
	
	private long lastUpdateTimeStamp;
	private static long updatePeriod = 100;
	
	private int mouseX;
	private int mouseY;
	
	private Map<Integer,Boolean> ghosts;
	private Map<Integer,Boolean> doneGhosts;
	
	
	
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		GameRenderer.getInstance().loadSpriteSheet("assets/sprites.png",10);
		ghosts = new HashMap<Integer,Boolean>();
		doneGhosts = new HashMap<Integer,Boolean>();
		StuffManager.getInstance();
	}

	public void render(GameContainer container, StateBasedGame game, Graphics graphics) throws SlickException {
		GameRenderer.getInstance().render(graphics);
	}

	public void update(GameContainer container, StateBasedGame game, int lastUpdate) throws SlickException {
		// gestion des touches
		for (Integer keyCode: ghosts.keySet()){
			if (ghosts.get(keyCode)){
				if (GameStateManager.getInstance().getCurrentState() == ActionState.BASE){
					if (keyCode == Input.KEY_S){
						MobManager.getInstance().getPlayer().planSimpleMove(0, 1);
					}
					if (keyCode == Input.KEY_Z){
						MobManager.getInstance().getPlayer().planSimpleMove(0, -1);
					}
					if (keyCode == Input.KEY_Q){
						MobManager.getInstance().getPlayer().planSimpleMove(-1, 0);
					}
					if (keyCode == Input.KEY_D){
						MobManager.getInstance().getPlayer().planSimpleMove(1, 0);
					}
					if (keyCode == Input.KEY_SPACE){
						WorldManager.getInstance().resetLevels();
					}
					if (keyCode == Input.KEY_UP){
						GameRenderer.getInstance().fromJ--;
					}
					if (keyCode == Input.KEY_DOWN){
						GameRenderer.getInstance().fromJ++;
					}
					if (keyCode == Input.KEY_LEFT){
						GameRenderer.getInstance().fromI--;
					}
					if (keyCode == Input.KEY_RIGHT){
						GameRenderer.getInstance().fromI++;
					}
					if (keyCode == Input.KEY_T){
						MobManager.getInstance().getPlayer().planLootingFirst();
					}
					if (keyCode == Input.KEY_I){
						//GameRenderer.getInstance().toggleInventoryDisplay();
						GameStateManager.getInstance().enterState(ActionState.DROPPING);
					}
				}
				if (GameStateManager.getInstance().getCurrentState() == ActionState.LOOTING){
					if (keyCode == Input.KEY_ENTER){
						//TODO: ramassage de la selection
						MobManager.getInstance().getPlayer().planLootingMulti();
					}else if (keyCode == Input.KEY_ESCAPE){
						GameStateManager.getInstance().enterState(ActionState.BASE);
					}else{
						// System.out.println(Input.getKeyName(keyCode));
						String keyName = Input.getKeyName(keyCode);
						if (keyName.length() == 1){
							char keyChar = keyName.toLowerCase().charAt(0);
							boolean isSlot = StuffManager.getInstance().isStuffSlotCharacter(keyChar);
							if (isSlot){
								StuffManager.getInstance().toggleLootSelectionByChar(keyChar);
							}
							// System.out.println("keyName ="+keyName+" slot?="+isSlot);
						}
					}
				}
				if (GameStateManager.getInstance().getCurrentState() == ActionState.DROPPING){
					if (keyCode == Input.KEY_ESCAPE){
						GameStateManager.getInstance().enterState(ActionState.BASE);
					}else{
						String keyName = Input.getKeyName(keyCode);
						if (keyName.length() == 1){
							char keyChar = keyName.toLowerCase().charAt(0);
							boolean isSlot = StuffManager.getInstance().isStuffSlotCharacter(keyChar);
							if (isSlot){
								StuffManager.getInstance().toggleDropSelectionByChar(keyChar);
							}
						}
					}
				}
				
			}
		}
		// boucle du jeu
		if (System.currentTimeMillis()-lastUpdateTimeStamp > updatePeriod){
			lastUpdateTimeStamp = System.currentTimeMillis();
			// TODO gestion complète
			MobManager.getInstance().actToPlayerInterrupt();
		}
		
	}
	public int getID() {return KrolLauncher.STATE_GAME;}

	// gestion I/O	
	public void setInput(Input input) {}
	public boolean isAcceptingInput() {return true;}
	public void inputStarted() {}
	public void inputEnded() {}
	public void keyPressed(int keyCode, char keyChar) {
		ghosts.put(keyCode, true);
		doneGhosts.put(keyCode, false);
		/*
		//TODO gestion de la remanence des touches
		if (keyCode == Input.KEY_S){
			MobManager.getInstance().getPlayer().planSimpleMove(0, 1);
		}
		if (keyCode == Input.KEY_Z){
			MobManager.getInstance().getPlayer().planSimpleMove(0, -1);
		}
		if (keyCode == Input.KEY_Q){
			MobManager.getInstance().getPlayer().planSimpleMove(-1, 0);
		}
		if (keyCode == Input.KEY_D){
			MobManager.getInstance().getPlayer().planSimpleMove(1, 0);
		}
		if (keyCode == Input.KEY_SPACE){
			WorldManager.getInstance().resetLevels();
		}
		*/
		/*
		if (keyCode == Input.KEY_UP){
			GameRenderer.getInstance().fromJ--;
		}
		if (keyCode == Input.KEY_DOWN){
			GameRenderer.getInstance().fromJ++;
		}
		if (keyCode == Input.KEY_LEFT){
			GameRenderer.getInstance().fromI--;
		}
		if (keyCode == Input.KEY_RIGHT){
			GameRenderer.getInstance().fromI++;
		}
		*/
	}
	
	public void keyReleased(int keyCode, char keyChar) {
		ghosts.put(keyCode, false);
		doneGhosts.put(keyCode, false);
	}
	
	
	public void mouseMoved(int oldx, int oldy, int newx, int newy){
		this.mouseX = newx;
		this.mouseY = newy;
	}
	public void mouseClicked(int button, int x, int y, int clickCount){
		//TODO virer le code de debug de pathfinding
		Cell mousePointedCell = GameRenderer.getInstance().getCellByScreenXY(mouseX, mouseY);
		if (mousePointedCell != null && mousePointedCell.isPassable()){
//			List<Cell> pathToClickedCell = WorldManager.getInstance().getLevelById(0).getDijsktraPath(MobManager.getInstance().getPlayer().i, MobManager.getInstance().getPlayer().j,mousePointedCell.i, mousePointedCell.j);		
//			for (Cell pathCell: pathToClickedCell){
////				System.out.println("("+pathCell.i+","+pathCell.j+")");
//				pathCell.isDebug = true;
//			}
			
			if (GameStateManager.getInstance().getCurrentState() == ActionState.BASE){
				MobManager.getInstance().getPlayer().planPathMove(mousePointedCell);
			}
		}
	}
}
