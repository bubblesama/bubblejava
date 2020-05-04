package com.bb.flatworld.player;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import com.bb.flatworld.FlatWorldKeys;
import com.bb.flatworld.RenderManager;
import com.bb.flatworld.Renderable;
import com.bb.flatworld.RenderingPort;
import com.bb.flatworld.SpriteManager;
import com.bb.flatworld.Updatable;
import com.bb.flatworld.UpdateManager;

public class Player implements Updatable, Renderable{

	public int x;
	private boolean facesRight;
	private PlayerStep step = PlayerStep.STABLE;
	private Input input;
	
	
	
	
	public Player(){
		this.x = 0;
		this.facesRight = true;
	}
	
	public void render(Graphics g, RenderingPort port) {
		g.drawImage(SpriteManager.getPlayerBottom(facesRight,step).getScaledCopy(FlatWorldKeys.SCALE),x-port.getX(),FlatWorldKeys.Z_LEVEL-FlatWorldKeys.QUANTUM_CELL*FlatWorldKeys.SCALE);
		g.drawImage(SpriteManager.getPlayerTop(facesRight,step).getScaledCopy(FlatWorldKeys.SCALE),x-port.getX(),FlatWorldKeys.Z_LEVEL-2*FlatWorldKeys.QUANTUM_CELL*FlatWorldKeys.SCALE);
//		System.out.println("Player.render step="+step);
	}

	public void update() {
		int xDir = 0;
		if (input.isKeyDown(Input.KEY_Q)){
			xDir = -1;
			facesRight = false;
		}else if (input.isKeyDown(Input.KEY_D)){
			xDir = 1;
			facesRight = true;
		}
		x+= step.getDx()*xDir;
		if (xDir == 0){
			step = PlayerStep.STABLE;
		}else{
			step = step.next();
		}
//		System.out.println("Player.update x="+x);
	}
	
	public void registerOn(UpdateManager updater, RenderManager renderer){
		updater.add(this);
		renderer.add(this);
	}

	public void setInput(Input input) {this.input = input;}
	
}
