package com.bb.flatworld.bg;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.newdawn.slick.Graphics;

import com.bb.flatworld.FlatWorldKeys;
import com.bb.flatworld.Renderable;
import com.bb.flatworld.RenderingPort;
import com.bb.flatworld.SpriteManager;
import com.bb.flatworld.Updatable;

public class CloudLayer extends Layer implements Updatable, Renderable {

	private List<Cloud> clouds;
	private int wind = -1;
	private int ticks = 0;
	private int period = 1;

	public CloudLayer(int depth) {
		super(depth);
		clouds = new ArrayList<Cloud>();
		//INIT
		Random random = new Random();
		int cloudAmount = 30;
		for (int i=0;i<cloudAmount;i++){
			Cloud c = new Cloud(CloudType.getCloud(random.nextInt(3)),random.nextInt(1000), random.nextInt(FlatWorldKeys.Z_LEVEL/4));
			this.clouds.add(c);
		}
	}

	public void render(Graphics g, RenderingPort port){
		for (Cloud c: clouds){
			g.drawImage(SpriteManager.getCloud(c.type).getScaledCopy(FlatWorldKeys.SCALE), (c.x-port.getX()/depth)*FlatWorldKeys.SCALE,c.y*FlatWorldKeys.SCALE);
		}
	}

	public void update(){
		ticks++;
		if (ticks >= period){
			ticks = 0;
			for (Cloud c: clouds){
				c.update();
			}
		}
	}

	public class Cloud{
		public Cloud(CloudType type, int x, int y) {
			super();
			this.x = x;
			this.y = y;
			this.type = type;
		}
		private int x;
		private int y;
		private CloudType type;
		public void update(){
			x += wind;
		}
	}

}
