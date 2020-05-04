package com.bb.flatworld.bg;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.newdawn.slick.Graphics;

import com.bb.flatworld.FlatWorldKeys;
import com.bb.flatworld.Renderable;
import com.bb.flatworld.RenderingPort;
import com.bb.flatworld.SpriteManager;

public class TreeLayer extends Layer implements Renderable {

	private List<Tree> trees;

	public TreeLayer(int depth) {
		super(depth);
		trees = new ArrayList<Tree>();
		//INIT
		Random random = new Random();
		int treeAmount = 30;
		for (int i=0;i<treeAmount;i++){
			Tree tree = new Tree(TreeType.getTree(random.nextInt(3)),random.nextInt(1000));
			this.trees.add(tree);
		}
	}

	public void render(Graphics g, RenderingPort port){
		for (Tree t: trees){
			g.drawImage(SpriteManager.getTree(t.type).getScaledCopy(FlatWorldKeys.SCALE), (t.x-port.getX()/depth)*FlatWorldKeys.SCALE,FlatWorldKeys.Z_LEVEL-FlatWorldKeys.SCALE*4*FlatWorldKeys.QUANTUM_CELL);
		}
	}

	public class Tree{
		public Tree(TreeType type, int x) {
			super();
			this.x = x;
			this.type = type;
		}
		private int x;
		private TreeType type;
	}

}
