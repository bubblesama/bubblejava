package com.bubblebob.walker;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class WalkerEntity extends Bonk{

	public WalkerEntity() {
		super(20, 24, 8, 20);
	}
	// gestion de l'etat du marcheur
	private boolean walking = false;
	private boolean goLeft = false;
	private boolean walkX = false;
	private boolean walkY = false;
	private boolean goDown = false;
	private int step = 0;
	private int maxStep = 9;
	//	private int[] rightDxByStep = {6,3,3,0,0,6,3,3,0,0};
	private static final int[] rightDxByStep = {4,2,2,0,0,4,2,2,0,0};
	//	private int[] leftDxByStep = {-6,-3,-3,0,0,-6,-3,-3,0,0};
	private static final int[] leftDxByStep = {-4,-2,-2,0,0,-4,-2,-2,0,0};
	private static final double runZoom = 1.5;

	
	//graphics
	private static String walkerSheetpath = "walker/assets/walker.png";
	private static SpriteSheet walkerSheet;
	// gestion de l'image du marcheur
	private static int walkerPicWidth = 8;
	private static int walkerPicHeight = 20;

	public static void graphInit(){
		try {
			walkerSheet = new SpriteSheet(walkerSheetpath, walkerPicWidth, walkerPicHeight);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	public void render(Graphics g, ViewPort viewPort){
		walkerSheet.getSprite(step %5, step/5 + (goLeft?0:2)).draw((x-viewPort.getX0())*ViewPort.ZOOM,(y-viewPort.getY0())*ViewPort.ZOOM,ViewPort.ZOOM);
	}

	public void update(GameContent content){
		// gestion de la marche
		if (walking){
			step++;
			int dx = walkX?(goLeft?leftDxByStep[step-1]:rightDxByStep[step-1]):0;
			int dy = walkY?(!goDown?leftDxByStep[step-1]:rightDxByStep[step-1]):0;
			Bonklision targetLision = new Bonklision(x+dx, y+dy, false);
			Bonk newWalker = new Bonk(x+dx, y+dy, w, h);
			for (Bonk b: content.getBonks()){
				Bonklision collision = b.collide(this, newWalker,false);
				if (collision.collide){
					targetLision = this.closest(targetLision, collision);
				}
			}
			moveTo(targetLision);
			// arret a la fin d'un cycle court de marche
			if (step == maxStep/2+1){
				walking = false;
				walkX = false;
				walkY = false;
			}
			// arret a la fin du cycle long
			if (step > maxStep){
				step = 0;
				walking = false;
				walkX = false;
				walkY = false;
			}
		}
	}

	public void turnLeft(){
		goLeft = true;
		walking = true;
		walkX = true;
	}

	public void turnRight(){
		goLeft = false;
		walking = true;
		walkX = true;
	}

	public void turnUp(){
		goDown = false;
		walking = true;
		walkY = true;
	}

	public void turnDown(){
		goDown = true;
		walking = true;
		walkY = true;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getW() {
		return w;
	}

	public int getH() {
		return h;
	}

}
