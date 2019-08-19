package com.bubblebob.tool.tilemap.viewer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.bubblebob.tool.ggame.gui.GraphicalModel;
import com.bubblebob.tool.image.MonoSizedBundledImage;
import com.bubblebob.tool.tilemap.model.SimpleMap;


public class MapView implements GraphicalModel{
	
	private SimpleMap map;
	private int width;
	private int height;

	
	private MonoSizedBundledImage tileSet;
	
	public MapView(SimpleMap map, MonoSizedBundledImage tileSet){
		this.map = map;
		this.tileSet = tileSet;
		this.width = tileSet.getImageWidth()*map.getWidth();
		this.height = tileSet.getImageHeight()*map.getHeight();
	}
	
	public void paint(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		for (int i=0; i<map.getWidth(); i++){
			for (int j=0; j<map.getHeight(); j++){
				BufferedImage tilePic = tileSet.getImage(map.getTile(i,j).getType(), 0);
				g.drawImage(tilePic, i*tileSet.getImageWidth(), j*tileSet.getImageHeight(), tilePic.getWidth(null), tilePic.getHeight(null), null);
			}
		}
		g.setColor(Color.BLUE);
	}

	public int getWidth() {
		return width+6;
	}

	public int getHeight() {
		return height+8;
	}
}
