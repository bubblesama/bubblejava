package com.bubblebob.dd.open;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.bubblebob.tool.ggame.gui.GraphicalModel;
import com.bubblebob.tool.image.MonoSizedBundledImage;

/**
 * Interface graphique de rendu des cartes a bord ouvert
 * @author Bubblebob
 *
 */
public class OpenBitMapAdapter implements GraphicalModel{

	private MonoSizedBundledImage tileSet;
	private MapBitsMap model;
	
	
	public OpenBitMapAdapter(MonoSizedBundledImage tileSet, MapBitsMap model){
		this.model = model;
		this.tileSet = tileSet;
	}
	
	
	public void paint(Graphics g) {
		int x=0;
		for (int i=0;i<model.getWidth();i++){
			int y=0;
			for (int j=0;j<model.getHeight();j++){
				g.drawImage(getImageFromMapBit(model.getMapBit(i, j)),x,y, null);
				y += tileSet.getImageHeight();
			}
			x += tileSet.getImageWidth();
		}
	}

	public int getWidth() {
		return model.getWidth()*tileSet.getImageWidth()+10;
	}

	public int getHeight() {
		return model.getHeight()*tileSet.getImageHeight()+20;
	}
	
	
	private BufferedImage getImageFromMapBit(MapBit bit){
		int y = 0;
		int x = 0;
		switch (bit.getOpenWay()) {
		case TOP:
			x=1;
			break;
		case BOTTOM:
			x=2;
			break;
		case LEFT:
			x=3;
			break;
		case RIGHT:
			x=4;
			break;
		default:
			x=0;
			break;
		}
		return tileSet.getImage(x, y);
	}

}
