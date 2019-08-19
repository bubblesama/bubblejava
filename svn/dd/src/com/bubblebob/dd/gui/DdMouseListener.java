package com.bubblebob.dd.gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import com.bubblebob.dd.model.dungeon.DungeonModel;

public class DdMouseListener implements MouseListener{

	private DungeonModel model;
	private DdCenteredGraphicalAdapter adapter;
	
	
	public DdMouseListener(DungeonModel model, DdCenteredGraphicalAdapter adapter){
		this.model = model;
		this.adapter = adapter;
	}
	
	public void mouseClicked(MouseEvent arg0) {
	}

	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent arg0) {
		
//		int x = arg0.getX();
//		int y = arg0.getY();
//		int currentX = (x+ model.getPlayer().getX())/model.getTileWidth() - adapter.shownWidth/2;
//		int currentY = (y+ model.getPlayer().getY())/model.getTileHeight() - adapter.shownHeight/2;
//		
//		String type = "???";
//		DungeonTile clickedTile = model.getMap().getTile(currentX, currentY);
//		if (clickedTile != null){
//			type = clickedTile.getType()+"";
//		}
	//	model.debug = "("+currentX+","+currentY+") "+type;
		
		
		
		
	}

	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	
	

}
