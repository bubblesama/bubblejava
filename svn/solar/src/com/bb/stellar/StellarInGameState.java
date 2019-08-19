package com.bb.stellar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.bb.stellar.gui.GuiState;
import com.bb.stellar.gui.GuiZone;
import com.bb.stellar.gui.InfoZone;
import com.bb.stellar.gui.MapZone;
import com.bb.stellar.gui.SingleInfoZone;
import com.bb.stellar.gui.SystemListingZone;
import com.bb.stellar.gui.ViewMode;
import com.bb.stellar.model.Galaxy;

public class StellarInGameState extends BasicGameState{

	// modele
	private Galaxy galaxy;

	// gui
	private MapZone mapZone;
	private InfoZone infoZone;
	private SystemListingZone systemListingZone;
	private SingleInfoZone singleInfoZone;
	private List<GuiZone> zones;
	private GuiState guiState;

	// controles
	private GuiZone dragSelectedZone = null;
	private boolean dragWasDragging = false;
	private int dragLastX = -1;
	private int dragLastY = -1;
	private int dragStartX = -1;
	private int dragStartY = -1;
	private Map<Integer,Boolean> keysDown;
	private boolean wasRightDown = false;
	

	public StellarInGameState() {
		this.galaxy = Galaxy.getDefaultGalaxy();
		this.guiState = new GuiState(ViewMode.GALAXY);
		this.mapZone = new MapZone(this.galaxy,this.guiState);
		this.infoZone = new InfoZone(this.guiState);
		this.systemListingZone = new SystemListingZone(galaxy, guiState);
		this.singleInfoZone = new SingleInfoZone(galaxy, guiState);
		this.keysDown = new HashMap<Integer, Boolean>();
		this.zones = new ArrayList<GuiZone>();
		this.zones.add(mapZone);
		this.zones.add(infoZone);
		this.zones.add(systemListingZone);
		this.zones.add(singleInfoZone);
	}

	public void init(GameContainer gc, StateBasedGame game) throws SlickException {
		mapZone.graphInit();
		// TODO charger une galaxie
		//actuellement: creation d'une galaxie fixe
		// graphique: chargement des ressources

	}

	public void update(GameContainer gc, StateBasedGame game, int lastUpdate) throws SlickException {
		updateControls(gc, lastUpdate);
		// decisions IA
		// mise a jour des vaisseaux
		galaxy.getShipManager().updateShips();
	}

	private void updateControls(GameContainer gc, int lastUpdate){
		Input input = gc.getInput();
		// prise des commandes
		// tour de roue
		if (mouseWheel != 0){
			if (mouseWheel > 0){
				mapZone.decreaseZoom(input.getMouseX(),input.getMouseY());
			}else{
				mapZone.increaseZoom(input.getMouseX(),input.getMouseY());
			}
		}
		mouseWheel = 0;
		GuiZone currentGuiZone = getGuiZone(input.getMouseX(),input.getMouseY());
		// touches
		if (input.isKeyDown(Input.KEY_LCONTROL)){
			guiState.setMultiSelectionOn(true);
		}else{
			guiState.setMultiSelectionOn(false);
		}
		// deplacement de souris
		boolean isLeftDown = input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON);
		boolean isRightDown = input.isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON);
		//			System.out.println("StellarGame#update dragging="+dragging);
		if (isLeftDown){
			if (dragWasDragging){
				if (dragLastX != -1){
					int deltaMouseX = input.getMouseX()-dragLastX;
					int deltaMouseY = input.getMouseY()-dragLastY;
					//						System.out.println("StellarGame#update dragging: "+deltaMouseX+","+deltaMouseY);
					if (deltaMouseX != 0 && deltaMouseY != 0 && dragSelectedZone != null){
						dragSelectedZone.drag(deltaMouseX, deltaMouseY);
					}
				}
			}else{
				dragStartX = input.getMouseX();
				dragStartY = input.getMouseY();
				dragSelectedZone = currentGuiZone;
			}
			// sauvegarde pour le tour suivant
			dragLastX = input.getMouseX();
			dragLastY = input.getMouseY();
			dragWasDragging = true;
		}else{
			//click?
			if (dragStartX != -1){
				if (currentGuiZone!= null &&((dragStartX-input.getMouseX())*(dragStartX-input.getMouseX())<50)&&((dragStartY-input.getMouseY())*(dragStartY-input.getMouseY())<50)){
					currentGuiZone.click(input.getMouseX(),input.getMouseY(),Input.MOUSE_LEFT_BUTTON);
				}
			}
			//reset du dragging
			dragLastX = -1;
			dragLastY = -1;
			dragStartX = -1;
			dragStartY = -1;
			dragWasDragging = false;
			dragSelectedZone = null;
		}
		if (isRightDown){
			wasRightDown = true;
		}else{
			if (wasRightDown){
				if (currentGuiZone!= null){
					currentGuiZone.click(input.getMouseX(),input.getMouseY(),Input.MOUSE_RIGHT_BUTTON);
				}
				wasRightDown = false;
			}
		}
		if (currentGuiZone != null){
			currentGuiZone.updateMouse(input.getMouseX(), input.getMouseY(), isLeftDown);
		}
		// touches
		if (input.isKeyDown(Input.KEY_COMMA)){
			if (!keysDown.get(Input.KEY_COMMA)){
				keysDown.put(Input.KEY_COMMA, true);
				mapZone.toggleViewMode();
			}
		}else{
			keysDown.put(Input.KEY_COMMA, false);
		}
	}

	private GuiZone getGuiZone(int mouseI, int mouseJ){
		GuiZone result = null;
		for (GuiZone zone: zones){
			if (zone.isIn(mouseI, mouseJ)){
				result = zone;
			}
		}
		return result;
	}

	public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
		// affichage de la galaxie ou du systeme choisi
		mapZone.render(gc, g);
		infoZone.render(gc, g);
		systemListingZone.render(gc, g);
		singleInfoZone.render(gc, g);
	}

	private int mouseWheel = 0;

	public void mouseWheelMoved(int change){
		//		System.out.println("StellarGame#mouseWheelMoved IN change="+change);
		mouseWheel = change;
	}

	public int getID() {return 3;}

}