package com.bubblebob.dd.editor.world;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.bubblebob.dd.model.world.map.WorldTile;
import com.bubblebob.tool.ggame.gui.GraphicalModel;
import com.bubblebob.tool.image.MonoSizedBundledImage;

public class WorldEditorCenteredGraphicalAdapter implements GraphicalModel{

	// le modele rendu
	private WorldEditorModel model;
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



	public WorldEditorCenteredGraphicalAdapter(WorldEditorModel model, MonoSizedBundledImage tileSet, int shownWidth, int shownHeight){
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
				WorldTile tile = model.getMap().getTile(i+model.upperLeftShownTilePosition.getTileX(), j+model.upperLeftShownTilePosition.getTileY());
				if (tile != null){
					g.drawImage(getImageForWorldTile(tile),coordsGrid[i][j][0],coordsGrid[i][j][1], null);
				}else{
					//System.out.println("[DdEditorCenteredGraphicalAdapter#paint] tile null: x="+(i+model.upperLeftShownTilePosition.getTileX())+" y="+(j+model.upperLeftShownTilePosition.getTileY()));
				}
			}
		}
		//...dessin des cases>
	}

	public BufferedImage getImageForWorldTile(WorldTile tile){
		int baseX = 0;
		int baseY = 0;
		switch (tile.getType()) {
		case EMPTY:
			baseX = 7;
			baseY = 0;
			break;
		case RIVER_UP_DOWN:
			baseX = 4;
			baseY = 2;
			break;
		case RIVER_UP_RIGHT:
			baseX = 0;
			baseY = 2;
			break;
		case RIVER_RIGHT_DOWN:
			baseX = 1;
			baseY = 2;
			break;
		case RIVER_DOWN_LEFT:
			baseX = 2;
			baseY = 2;
			break;
		case RIVER_LEFT_UP:
			baseX = 3;
			baseY = 2;
			break;
		case MOUTAIN_BLANK:
			baseX = 1;
			baseY = 0;
			break;
		case MOUTAIN_BLACK:
			baseX = 0;
			baseY = 0;
			break;
		case MOUTAIN_GREY:
			baseX = tile.isDiscovered()?2:0;
			baseY = 0;
			break;
		case MOUTAIN_BLUE:
			baseX = tile.isDiscovered()?3:0;
			baseY = 0;
			break;
		case MOUTAIN_RED:
			baseX = tile.isDiscovered()?4:0;
			baseY = 0;
			break;
		case MOUTAIN_PURPLE:
			baseX = tile.isDiscovered()?5:0;
			baseY = 0;
			break;
		case WALL_DOOR_UP_DOWN:
			baseX = 0;
			baseY = 3;
			break;
		case WALL_DOOR_LEFT_RIGHT:
			baseX = 1;
			baseY = 3;
			break;
		case WALL_UP_DOWN:
			baseX = 3;
			baseY = 3;
			break;
		case WALL_LEFT_RIGHT:
			baseX = 2;
			baseY = 3;
			break;
		case FOREST:
			baseX = 0;
			baseY = 1;
			break;
		case HOUSE:
			baseX = 2;
			baseY = 1;
			break;
		default:
			break;
		}
		return tileSet.getImage(baseX, baseY);
	}

}
