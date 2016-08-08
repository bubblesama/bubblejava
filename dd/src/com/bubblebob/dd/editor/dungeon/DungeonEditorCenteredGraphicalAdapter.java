package com.bubblebob.dd.editor.dungeon;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.bubblebob.dd.gui.DdCenteredGraphicalAdapter;
import com.bubblebob.dd.model.dungeon.item.AxeItem;
import com.bubblebob.dd.model.dungeon.item.BoatItem;
import com.bubblebob.dd.model.dungeon.item.Item;
import com.bubblebob.dd.model.dungeon.item.KeyItem;
import com.bubblebob.dd.model.dungeon.item.LadderItem;
import com.bubblebob.dd.model.dungeon.item.QuiverItem;
import com.bubblebob.dd.model.dungeon.item.RandomItem;
import com.bubblebob.dd.model.dungeon.map.DungeonTile;
import com.bubblebob.dd.model.dungeon.mob.Monster;
import com.bubblebob.tool.ggame.gui.GraphicalModel;
import com.bubblebob.tool.image.MonoSizedBundledImage;

public class DungeonEditorCenteredGraphicalAdapter implements GraphicalModel{

	// le modele rendu
	private DungeonEditorModel model;
	// le modele graphique des cases rendues
	private MonoSizedBundledImage tileSet;

	private int width;
	private int height;

	// le nombre de cases montrees
	// en largeur
	public int shownWidth;
	// en hauteur
	public int shownHeight;
	// mise en cache des cordonnees des images pour eviter les recalculs
	private int[][][] coordsGrid;
	
	
	public DungeonEditorCenteredGraphicalAdapter(DungeonEditorModel model, MonoSizedBundledImage tileSet, int shownWidth, int shownHeight){
		this.model = model;
		this.tileSet = tileSet;
		this.shownWidth = shownWidth;
		this.shownHeight = shownHeight;
		this.width = tileSet.getImageWidth()*shownWidth+6;
		this.height = tileSet.getImageHeight()*shownHeight+12;
		//<mise en cache des coordonnees...
		this.coordsGrid = new int[shownWidth][shownHeight][2];
		for (int i=0; i<shownWidth; i++){
			for (int j=0; j<shownHeight; j++){
				int imageX = i*tileSet.getImageWidth();
				int imageY = j*tileSet.getImageHeight();
				coordsGrid[i][j][0] =  imageX;
				coordsGrid[i][j][1] =  imageY;
			}
		}
		//...mise en cache des coordonnees>
	}

	// ACCESSEURS
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	// METHODES METIER
	public void paint(Graphics g) {
		//System.out.println("[DdCenteredGraphicalAdapter#paint] IN");
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		//<dessin des cases...
		g.setColor(Color.BLACK);
		for (int i=0; i<shownWidth; i++){
			for (int j=0; j<shownHeight; j++){
				DungeonTile tile = model.getMap().getTileWithoutModulo(i,j);
				if (tile != null){
					g.drawImage(getImageForTile(tile),coordsGrid[i][j][0],coordsGrid[i][j][1], null);
//					if (model.getMode() == EditorMode.ITEM){
					if (!tile.getItems().isEmpty()){
						g.drawImage(getImageForItem(tile.getItems().get(0)),coordsGrid[i][j][0],coordsGrid[i][j][1], null);
					}
//				}
					if (model.getMode() == DungeonEditorMode.GROUP){
						g.drawString(tile.getGroup()+"",coordsGrid[i][j][0]+5,coordsGrid[i][j][1]+12);
					}

				}else{
					//System.out.println("[DdEditorCenteredGraphicalAdapter#paint] tile null: x="+(i+model.upperLeftShownTilePosition.getTileX())+" y="+(j+model.upperLeftShownTilePosition.getTileY()));
				}
			}
		}
		//...dessin des cases>
	}

	public BufferedImage getImageForTile(DungeonTile t){
		int x = -1;
		int y = 0;
		switch (t.getType()) {
		case EMPTY:
			x = 0;
			break;
		case WALL:
			x = 1;
			break;
		case UP_RIGHT_CORNER:
			x = 2;
			break;
		case DOWN_RIGHT_CORNER:
			x = 3;
			break;
		case DOWN_LEFT_CORNER:
			x = 4;
			break;
		case UP_LEFT_CORNER:
			x = 5;
			break;
		default:
			x = 0;
			break;
		}
		return tileSet.getImage(x, y);
	}

	public BufferedImage getImageForItem(Item item){
		int y = 5;
		int x = 1;
		if (item instanceof AxeItem) {
			x = 2;
		}else if (item instanceof BoatItem) {
			x = 5;
		}else if (item instanceof KeyItem) {
			x = 4;
		}else if (item instanceof LadderItem) {
			x = 3;
		}else if (item instanceof QuiverItem) {
			x = 0;
		}else if (item instanceof RandomItem) {
			x = 6;
		}
		return tileSet.getImage(x, y);
	}

	public BufferedImage getImageForMonster(Monster monster){
		int baseX = 0;
		int baseY = 0;
		switch (monster.getType()) {
		case SNAKE:
			baseX = 0;
			baseY = 3;
			break;
		default:
			break;
		}

		int monsterY = baseY;
		int monsterX = baseX + monster.getConsecutiveSteps()%2;
		if (monster.facesLeft()){
			monsterX +=2;
		}
		return tileSet.getImage(monsterX, monsterY);
	}

}
