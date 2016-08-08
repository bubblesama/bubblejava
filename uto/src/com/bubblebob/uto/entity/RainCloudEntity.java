package com.bubblebob.uto.entity;

import java.awt.Graphics;

import com.bubblebob.uto.PlayerType;
import com.bubblebob.uto.UtoModel;
import com.bubblebob.uto.entity.move.CanMoveEverywhereStrategy;

public class RainCloudEntity extends RandomMovingSeaEntity{

	private int pics = 4;
	private int picIndex = 0;
	private final static int tickToChangePic = 5;
	private int currentTick = 0;

	public RainCloudEntity(UtoModel model, int x, int y, int w, int h) {
		super(model,"RainCloud",x,y,w,h);
		this.canMoveStrategy = new CanMoveEverywhereStrategy();
		this.canMoveStrategy.registerMob(this);
	}

	public void update() {
		super.update();
		currentTick++;
		if (currentTick>tickToChangePic){
			currentTick = 0;
			picIndex++;
			if (picIndex == pics){
				picIndex = 0;
			}
		}
	}

	public void paint(Graphics g){
		g.drawImage(assets.getRainCloud(picIndex,true),x*assets.getScale(), y*assets.getScale(), null);
		g.drawImage(assets.getRainCloud(picIndex,false),(x+model.getTileSize())*assets.getScale(), y*assets.getScale(), null);
	}


	public void touched(Entity touchingEntity){
	}
	
	public int getGraphLayer() {
		return 6;
	}
	
}
