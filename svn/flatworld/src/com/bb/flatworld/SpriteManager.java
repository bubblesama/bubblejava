package com.bb.flatworld;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import com.bb.flatworld.bg.CloudType;
import com.bb.flatworld.bg.TreeType;
import com.bb.flatworld.player.PlayerStep;

public class SpriteManager {

	private static boolean init = false;
	private static SpriteSheet sprites;

	public static void init(){
		if (!init){
			try {
				sprites = new SpriteSheet("flatworld/assets/sprites.png", FlatWorldKeys.QUANTUM_CELL, FlatWorldKeys.QUANTUM_CELL);
			} catch (SlickException e) {e.printStackTrace();}
		}
		init = true;
	}

	public static Image getPlayerBottom(boolean facesRight, PlayerStep step){
		return sprites.getSprite(step.getRenderI(),facesRight?6:4);
	}
	public static Image getPlayerTop(boolean facesRight, PlayerStep step){
		return sprites.getSprite(step.getRenderI(),facesRight?5:3);
	}

	public static Image getCloud(CloudType cloud){
		int cloudJ = 0;
		int cloudLength = 2;
		switch (cloud) {
		case FAT2:
			cloudJ = 1;
			break;
		case LONG:
			cloudJ = 2;
			cloudLength=4;
			break;
		default:
			break;
		}
		return sprites.getSubImage(0, cloudJ*FlatWorldKeys.QUANTUM_CELL, cloudLength*FlatWorldKeys.QUANTUM_CELL, FlatWorldKeys.QUANTUM_CELL);
	}

	public static Image getTree(TreeType treeType){
		int x0 = 4;
		switch(treeType){
		case TREE_2:
			x0 = 6;
			break;
		case TREE_3:
			x0 = 8;
			break;
		default:
			break;
		}
		return sprites.getSubImage(x0*FlatWorldKeys.QUANTUM_CELL, 0, 2*FlatWorldKeys.QUANTUM_CELL, 4*FlatWorldKeys.QUANTUM_CELL);
	}

}
