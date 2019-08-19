package com.bubblebob.walker;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Bonk {

	public Bonk(int x, int y, int w, int h) {
		super();
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	public int x;
	public int y;
	public int w;
	public int h;

	public void render(GameContainer gameContainer, Graphics g, int x0, int y0, int zoom) throws SlickException {
		g.fillRect((x-x0)*zoom,(y-y0)*zoom,w*zoom,h*zoom);
	}

	public void render(Graphics g, ViewPort viewPort){
		g.setColor(Color.red);
		g.fillRect((x-viewPort.getX0())*ViewPort.ZOOM,(y-viewPort.getY0())*ViewPort.ZOOM,w*ViewPort.ZOOM,h*ViewPort.ZOOM);
	}

	public boolean equals(Object o){
		if (o == null){
			return false;
		}
		if (o instanceof Bonk) {
			Bonk b = (Bonk) o;
			return b.w == w && b.h == h && b.x == x && b.y == y;
		}else{
			return false;
		}
	}

	// collision du bonk avec les etats initiaux et finaux d'un autre bonk
	public Bonklision collide(Bonk from, Bonk to,boolean alreadyColliding){
		if (!isColliding(to)){
			return (new Bonklision(to.x,to.y,alreadyColliding));
		}else{
			int dx = (to.x-from.x)/2;
			int dy = (to.y-from.y)/2;
			if (dx == 0 && dy == 0){
				return (new Bonklision(from.x,from.y,alreadyColliding));
			}else{
				return collide(from, new Bonk(from.x+dx, from.y+dy, from.w, from.h),true);
			}
		}
	}

	public Bonk nearestBonk(Bonk a, Bonk b){
		if (abs(a.x-x) < abs(b.x-x)){
			return a;
		}else if ( a.x == b.x){
			if (abs(a.y-y) < abs(b.y-y)){
				return a;
			}else{
				return b;
			}
		}else{
			return b;
		}
	}

	public Bonklision closest(Bonklision b1, Bonklision b2){
		if (((b1.x-x)*(b1.x-x)+(b1.y-y)*(b1.y-y))>((b2.x-x)*(b2.x-x)+(b2.y-y)*(b2.y-y))){
			return b2;
		}else{
			return b1;
		}
	}

	public void move(int dx, int dy){
		x += dx;
		y += dy;
	}
	
	public void moveTo(Bonklision collision){
		x = collision.x;
		y = collision.y;
	}

	private boolean isColliding(Bonk b){
		return (x<b.x+b.w && x+w>b.x)&&(y<b.y+b.h && y+h>b.y);
	}

	private static int abs(int a){
		if (a>-1){
			return a;
		}else{
			return -a;
		}
	}

	public static void main(String[] args) {
		System.out.println("-4/2 = "+(-4/2));
		System.out.println("-5/2 = "+(-5/2));
		System.out.println("abs(-1) = "+(abs(-1)));
		System.out.println("abs(0) = "+(abs(0)));
		System.out.println("abs(3) = "+(abs(3)));
		System.out.println("abs(-2) = "+(abs(-2)));
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

}
