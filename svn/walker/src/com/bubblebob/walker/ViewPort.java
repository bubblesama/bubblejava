package com.bubblebob.walker;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SpriteSheet;


/**
 * L'objet representant l'ecran, avec son (x0,y0), largeur, hauteur, zoom
 * @author NewBubble
 *
 */
public class ViewPort {

	public static int  ZOOM = 3;
	//<vue> 
	//gestion de la fenetre de vue
	public static int VIEW_W = 200;
	public static int VIEW_H = 160;
	private int viewX0 = 0;
	private int viewY0 = 0;
	// mise a jour auto
	private static int VIEW_AUTO_FULL_DX = VIEW_W-50;
	private static int VIEW_AUTO_FULL_DY = VIEW_H-60;
	private boolean viewAutoOn = false;
	private int viewAutoElementalDX = 15;
	private int viewAutoElementalDY = 10;
	private int viewAutoRemainingDX = 0;
	private int viewAutoRemainingDY = 0;
	
	// valeur limite de declenchement du scrolling
	private final static int BORDER_START = 20;
	
	//
	private long nanoLastViewMove = 0; // dernier update
	private long VIEW_MILLI_MOVE = 100;
	//</vue>
	
	public void render(GameContainer gameContainer, Graphics g, GameContent content){
		content.getWalker().render(g,this);
		for (Bonk bonk: content.getBonks()){
			bonk.render(g, this);
		}
	}

	public void update(GameContent content){
		if (viewAutoOn){
			// deplacement de la vue
			if (viewAutoRemainingDX != 0){
				if (viewAutoRemainingDX > 0){
					viewX0 += viewAutoElementalDX;
					viewAutoRemainingDX -=viewAutoElementalDX;
				}else{
					viewX0 -= viewAutoElementalDX;
					viewAutoRemainingDX +=viewAutoElementalDX;
				}
			}
			else if (viewAutoRemainingDY != 0){
				if (viewAutoRemainingDY > 0){
					viewY0 += viewAutoElementalDY;
					viewAutoRemainingDY -=viewAutoElementalDY;
				}else{
					viewY0 -= viewAutoElementalDY;
					viewAutoRemainingDY +=viewAutoElementalDY;
				}
			}else{
				viewAutoOn = false;
			}
		}else{
			// deplacement de la vue?
			//up?
			if (content.getWalker().getX()<viewX0+BORDER_START){
				viewAutoRemainingDX = -VIEW_AUTO_FULL_DX;
				viewAutoOn = true;
			}
			//down?
			if (content.getWalker().getX()+content.getWalker().getW()>viewX0+VIEW_W-BORDER_START){
				viewAutoRemainingDX = VIEW_AUTO_FULL_DX;
				viewAutoOn = true;
			}
			//right?
			if (content.getWalker().getY()+content.getWalker().getH()>viewY0+VIEW_H-BORDER_START){
				viewAutoRemainingDY = VIEW_AUTO_FULL_DY;
				viewAutoOn = true;
			}
			//left
			if (content.getWalker().getY()<viewY0+BORDER_START){
				viewAutoRemainingDY = -VIEW_AUTO_FULL_DY;
				viewAutoOn = true;
			}
		}
	}

	public int getX0() {
		return viewX0;
	}


	public int getY0() {
		return viewY0;
	}
}
