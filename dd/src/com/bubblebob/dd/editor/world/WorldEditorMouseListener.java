package com.bubblebob.dd.editor.world;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import com.bubblebob.dd.model.dungeon.map.DungeonTileType;
import com.bubblebob.dd.model.world.map.WorldTile;

public class WorldEditorMouseListener implements MouseListener{

	private WorldEditorModel model;

	public WorldEditorMouseListener(WorldEditorModel model){
		this.model = model;
	}

	public void mouseClicked(MouseEvent arg0) {
	}

	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mousePressed(MouseEvent mouseEvent) {
		int x = mouseEvent.getX();
		int y = mouseEvent.getY();
		int currentX = (x/model.getTileWidth()+ model.upperLeftShownTilePosition.getTileX());
		int currentY = (y/model.getTileHeight()+ model.upperLeftShownTilePosition.getTileY());

		WorldTile clickedTile = model.getMap().getTile(currentX, currentY);
		if (clickedTile != null){

			if (mouseEvent.getButton() == MouseEvent.BUTTON1){
				// clic gauche: on parcourt les types de cases
				System.out.println("[WorldEditorMouseListener#mousePressed] before: "+clickedTile.getType()+" after: "+clickedTile.getType().next());
				clickedTile.setType(clickedTile.getType().next());
			}else if (mouseEvent.getButton() == MouseEvent.BUTTON3){
				// clic droit: on switch mur/vide
//				if (clickedTile.getType() == DungeonTileType.EMPTY){
//					clickedTile.setType(DungeonTileType.WALL);
//				}else{
//					clickedTile.setType(DungeonTileType.EMPTY);
//				}
			}
		}
	}

	public void mouseReleased(MouseEvent arg0) {
	}







}
