package com.bubblebob.dd.editor.dungeon;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import com.bubblebob.dd.model.dungeon.item.RandomItem;
import com.bubblebob.dd.model.dungeon.map.DungeonTile;
import com.bubblebob.dd.model.dungeon.map.DungeonTileType;

public class DungeonEditorMouseListener implements MouseListener{

	private DungeonEditorModel model;

	public DungeonEditorMouseListener(DungeonEditorModel model){
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

		DungeonTile clickedTile = model.getMap().getTile(currentX, currentY);
		if (clickedTile != null){

			switch (model.getMode()) {
			case PATTERN:
				if (mouseEvent.getButton() == MouseEvent.BUTTON1){
					// clic gauche: on parcourt les types de cases
					clickedTile.setType(clickedTile.getType().next());
				}else if (mouseEvent.getButton() == MouseEvent.BUTTON3){
					// clic droit: on switch mur/vide
					if (clickedTile.getType() == DungeonTileType.EMPTY){
						clickedTile.setType(DungeonTileType.WALL);
					}else{
						clickedTile.setType(DungeonTileType.EMPTY);
					}
				}
				break;
			case GROUP:
				if (mouseEvent.getButton() == MouseEvent.BUTTON1){
					// clic gauche: on ajoute 1 au groupe de la case
					clickedTile.setGroup(clickedTile.getGroup()+1);
				}else if (mouseEvent.getButton() == MouseEvent.BUTTON3){
					// clic droit: on reninitialise le groupe
					clickedTile.setGroup(0);
				}
				break;
			case ITEM:
				if (mouseEvent.getButton() == MouseEvent.BUTTON1){
					// clic gauche: on ajoute un item aleatoire
					clickedTile.addItem(new RandomItem(null));
				}else if (mouseEvent.getButton() == MouseEvent.BUTTON3){
					// clic droit: on detruit tous les items
					clickedTile.getItems().clear();
				}
				break;
			}
		}
	}

	public void mouseReleased(MouseEvent arg0) {
	}







}
