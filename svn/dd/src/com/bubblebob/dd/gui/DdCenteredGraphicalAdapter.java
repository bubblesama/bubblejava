package com.bubblebob.dd.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.bubblebob.dd.model.DdGameModel;
import com.bubblebob.dd.model.Direction;
import com.bubblebob.dd.model.GameMode;
import com.bubblebob.dd.model.dungeon.Shoot;
import com.bubblebob.dd.model.dungeon.item.AxeItem;
import com.bubblebob.dd.model.dungeon.item.BoatItem;
import com.bubblebob.dd.model.dungeon.item.FirstCrownPartItem;
import com.bubblebob.dd.model.dungeon.item.Item;
import com.bubblebob.dd.model.dungeon.item.KeyItem;
import com.bubblebob.dd.model.dungeon.item.LadderItem;
import com.bubblebob.dd.model.dungeon.item.QuiverItem;
import com.bubblebob.dd.model.dungeon.item.RandomItem;
import com.bubblebob.dd.model.dungeon.item.SecondCrownPartItem;
import com.bubblebob.dd.model.dungeon.map.DungeonTile;
import com.bubblebob.dd.model.dungeon.map.DungeonTileType;
import com.bubblebob.dd.model.dungeon.mob.Monster;
import com.bubblebob.dd.model.dungeon.mob.Puff;
import com.bubblebob.dd.model.world.map.WorldTile;
import com.bubblebob.tool.ggame.gui.GraphicalModel;
import com.bubblebob.tool.image.MonoSizedBundledImage;

public class DdCenteredGraphicalAdapter implements GraphicalModel{

	private DdGameModel model;
	private MonoSizedBundledImage dungeonTileSet;
	private MonoSizedBundledImage worldTileSet;

	// TAILLE DE LA FENETRE
	private int width;
	private int height;

	// DUNGEON: le nombre de caes affichees en large et en travers
	public int shownWidth;
	public int shownHeight;

	// WORLD: le coin superieur droit initial d'affichage de la carte du monde
	private int startWorldMapX;
	private int startWorldMapY;


	private Color backGroundGreen = new Color(38, 127, 0);

	private Color worldGreen = new Color(117, 204, 128);

	private int playerX;
	private int playerY;

	public DdCenteredGraphicalAdapter(DdGameModel model, MonoSizedBundledImage dungeonTileSet, MonoSizedBundledImage worldTileSet, int shownWidth, int shownHeight){
		this.model = model;
		this.dungeonTileSet = dungeonTileSet;
		this.worldTileSet = worldTileSet;
		this.shownWidth = shownWidth;
		this.shownHeight = shownHeight;
		this.width = dungeonTileSet.getImageWidth()*shownWidth;
		this.height = dungeonTileSet.getImageHeight()*shownHeight;

		this.coordsGrid = new int[shownWidth+1][shownHeight+1][2];
		for (int i=0; i<shownWidth+1; i++){
			for (int j=0; j<shownHeight+1; j++){
				int imageX = i*dungeonTileSet.getImageWidth();
				int imageY = j*dungeonTileSet.getImageHeight();
				coordsGrid[i][j][0] =  imageX;
				coordsGrid[i][j][1] =  imageY;
			}
		}
		playerX = (shownWidth-1)*dungeonTileSet.getImageWidth()/2;
		playerY = (shownHeight-1)*dungeonTileSet.getImageHeight()/2;

		startWorldMapX =  (shownWidth*dungeonTileSet.getImageWidth()-model.getWorld().getMap().getWidth()*worldTileSet.getImageWidth())/2;
		startWorldMapY = (shownHeight*dungeonTileSet.getImageHeight()-model.getWorld().getMap().getHeight()*worldTileSet.getImageHeight())/2;

	}

	// ACCESSEURS
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	// mise en cache des cordonnees des images pour eviter les recalculs
	private int[][][] coordsGrid;


	// METHODES METIER
	public void paint(Graphics g) {
		//System.out.println("[DdCenteredGraphicalAdapter#paint] IN");
		if (model.getMode() == GameMode.DUNGEON){
			g.setColor(backGroundGreen);
			g.fillRect(0, 0, width, height);
			g.setColor(Color.BLACK);
			//<dessin des cases et des items...
			//<le decalage du joueur par rapport a l'origine...
			int playerTileDeltaX = model.getDungeon().getPlayer().getX()/dungeonTileSet.getImageWidth();
			int playerTileDeltaY = model.getDungeon().getPlayer().getY()/dungeonTileSet.getImageHeight();
			//...le decalage du joueur par rapport a l'origine>
			//<le decalage du joueur par rapport a la case
			int playerDeltaX = model.getDungeon().getPlayer().getX() % dungeonTileSet.getImageWidth();
			int playerDeltaY = model.getDungeon().getPlayer().getY() % dungeonTileSet.getImageHeight();
			//...le decalage du joueur par rapport a l'origine>
			int widthIndex = playerTileDeltaX - shownWidth/2;
			// System.out.println("x:"+playerDeltaX+" y:"+playerDeltaY);
			int tileWidth = model.getDungeon().getMap().getWidth();
			int tileHeight = model.getDungeon().getMap().getHeight();
			for (int i=0; i<shownWidth+1; i++){
				int heightIndex = playerTileDeltaY - shownHeight/2;
				for (int j=0; j<shownHeight+1; j++){
					DungeonTile tile = model.getDungeon().getMap().getTile((widthIndex+tileWidth)%tileWidth,(heightIndex+tileHeight)%tileHeight);
					if (tile != null && /**/tile.isDiscovered() &&/**/ tile.getType() != DungeonTileType.WALL){
						g.drawImage(getImageForTile(tile),coordsGrid[i][j][0]-playerDeltaX,coordsGrid[i][j][1]-playerDeltaY, null);
						if (!tile.getItems().isEmpty()){
							Item item = tile.getItems().get(0);
							g.drawImage(getImageForItem(item),coordsGrid[i][j][0]-playerDeltaX,coordsGrid[i][j][1]-playerDeltaY, null);
						}
					}
					//g.drawString(tile.getGroup()+"",coordsGrid[i][j][0]+5,coordsGrid[i][j][1]+12);
					heightIndex++;
				}
				widthIndex++;
			}
			//...dessin des cases et des items>
			//<dessin du joueur...
			if (!model.getDungeon().getPlayer().isDeathStarted()){
				g.drawImage(getImageForPlayer(), playerX, playerY, null);
			}
			//...dessin du joueur>
			//<dessin des fleches...
			for (Shoot shoot: model.getDungeon().getShootManager().getShoots()){
				//System.out.println("[DdCenteredGraphicalAdapter#paint] "+shoot.getX()/tileSet.getImageWidth());
				g.drawImage(getImageForArrow(shoot.getDirection()),shoot.getTileX()*dungeonTileSet.getImageWidth()-model.getDungeon().getPlayer().getX()+shownWidth/2*dungeonTileSet.getImageWidth(),shoot.getTileY()*dungeonTileSet.getImageHeight()-model.getDungeon().getPlayer().getY()+shownHeight/2*dungeonTileSet.getImageHeight(), null);
			}
			//...dessin des fleches>
			//<dessin des monstres...
			for (Monster monster: model.getDungeon().getMonsterManager().getMonsters()){
				if (monster.getCurrentTile().isDiscovered()){
					g.drawImage(getImageForMonster(monster),monster.getX()-model.getDungeon().getPlayer().getX()+shownWidth/2*dungeonTileSet.getImageWidth(),monster.getY()-model.getDungeon().getPlayer().getY()+shownHeight/2*dungeonTileSet.getImageHeight(), null);
				}
			}
			//...dessin des monstres>
			//<dessin des fumees...
			for (Puff puff: model.getDungeon().getPuffManager().getPuffs()){
				g.drawImage(dungeonTileSet.getImage(7, 5),puff.getX()-model.getDungeon().getPlayer().getX()+shownWidth/2*dungeonTileSet.getImageWidth(),puff.getY()-model.getDungeon().getPlayer().getY()+shownHeight/2*dungeonTileSet.getImageHeight(), null);
			}
			//...dessin des fumees>
//			g.setColor(Color.BLACK);
			//g.drawString(""+(model.getDungeon().getTicker()/10), 10, 10);

			if (model.getDungeon().isPaused()){
				g.setColor(Color.GREEN);
				g.fillRect(shownWidth/2*dungeonTileSet.getImageWidth()-25,shownHeight/2*dungeonTileSet.getImageHeight()+2, 67, 16);
				g.setColor(Color.RED);
				g.drawString("* PAUSE *", shownWidth/2*dungeonTileSet.getImageWidth()-20,shownHeight/2*dungeonTileSet.getImageHeight()+14);
			}
			if (model.getDungeon().lost){
				g.setColor(Color.GREEN);
				g.fillRect(shownWidth/2*dungeonTileSet.getImageWidth()-25,shownHeight/2*dungeonTileSet.getImageHeight()+2, 87, 16);
				g.setColor(Color.RED);
				g.drawString("*GAME OVER*", shownWidth/2*dungeonTileSet.getImageWidth()-20,shownHeight/2*dungeonTileSet.getImageHeight()+14);
			}
			//			System.out.println("[DdCenteredGraphicalAdapter#paint] OUT duree="+(System.currentTimeMillis()-begin));
		}else if (model.getMode() == GameMode.WORLD){
			g.setColor(worldGreen);
			g.fillRect(0, 0, width, height);
			//<affichage des cases...
			int x = startWorldMapX;
			for (int i=0; i<model.getWorld().getMap().getTileWidth(); i++){
				int y = startWorldMapY;
				for (int j=0; j<model.getWorld().getMap().getTileHeight(); j++){
					g.drawImage(getImageForWorldTile(model.getWorld().getMap().getTile(i,j)),x,y, null);
					y += worldTileSet.getImageHeight();
				}
				x += worldTileSet.getImageWidth();
			}
			//<affichage du donjon principal...
			g.drawImage(worldTileSet.getImage(5, 2),model.getWorld().getMap().getLowLeftLastDungeonX()*worldTileSet.getImageWidth()+startWorldMapX,model.getWorld().getMap().getLowLeftLastDungeonY()*worldTileSet.getImageHeight()+startWorldMapY, null);
			g.drawImage(worldTileSet.getImage(6, 2),(model.getWorld().getMap().getLowLeftLastDungeonX()+1)*worldTileSet.getImageWidth()+startWorldMapX,model.getWorld().getMap().getLowLeftLastDungeonY()*worldTileSet.getImageHeight()+startWorldMapY, null);
			g.drawImage(worldTileSet.getImage(5, 1),model.getWorld().getMap().getLowLeftLastDungeonX()*worldTileSet.getImageWidth()+startWorldMapX,(model.getWorld().getMap().getLowLeftLastDungeonY()-1)*worldTileSet.getImageHeight()+startWorldMapY, null);
			g.drawImage(worldTileSet.getImage(6, 1),(model.getWorld().getMap().getLowLeftLastDungeonX()+1)*worldTileSet.getImageWidth()+startWorldMapX,(model.getWorld().getMap().getLowLeftLastDungeonY()-1)*worldTileSet.getImageHeight()+startWorldMapY, null);
			//<affichage des nuages...
			g.drawImage(worldTileSet.getImage(6, 1),(model.getWorld().getMap().getLowLeftLastDungeonX()+1)*worldTileSet.getImageWidth()+startWorldMapX,(model.getWorld().getMap().getLowLeftLastDungeonY()-1)*worldTileSet.getImageHeight()+startWorldMapY, null);
			int deltaY = (model.getWorld().getTicker()%120)/40;
			g.drawImage(worldTileSet.getImage(7, 1+deltaY),model.getWorld().getMap().getLowLeftLastDungeonX()*worldTileSet.getImageWidth()+startWorldMapX,(model.getWorld().getMap().getLowLeftLastDungeonY()-1)*worldTileSet.getImageHeight()+startWorldMapY, null);
			g.drawImage(worldTileSet.getImage(8, 1+deltaY),(model.getWorld().getMap().getLowLeftLastDungeonX()+1)*worldTileSet.getImageWidth()+startWorldMapX,(model.getWorld().getMap().getLowLeftLastDungeonY()-1)*worldTileSet.getImageHeight()+startWorldMapY, null);
			//...affichage des nuages>
			//...affichage du donjon principal>
			//...affichage des cases>
			// affichage de l'eventuel item de la traversee de case du joueur
			g.drawImage(getItemImageForWorldTile(model.getWorld().getMap().getTile(model.getWorld().getPlayer().getX(),model.getWorld().getPlayer().getY())),model.getWorld().getPlayer().getX()*worldTileSet.getImageWidth()+startWorldMapX,model.getWorld().getPlayer().getY()*worldTileSet.getImageHeight()+startWorldMapY, null);

			//<affichage du joueur...
			if (model.getWorld().isWon()){
				g.drawImage(worldTileSet.getImage(6, 0),model.getWorld().getPlayer().getX()*worldTileSet.getImageWidth()+startWorldMapX,model.getWorld().getPlayer().getY()*worldTileSet.getImageHeight()+startWorldMapY, null);
			}else{
				boolean displayPlayer = (model.getWorld().getTicker()%20)/10 == 0;
				if (displayPlayer){
					g.drawImage(worldTileSet.getImage(9, 0),model.getWorld().getPlayer().getX()*worldTileSet.getImageWidth()+startWorldMapX,model.getWorld().getPlayer().getY()*worldTileSet.getImageHeight()+startWorldMapY, null);
					if (model.getWorld().getPlayer().getInfos().getLives()>1){
						g.drawImage(worldTileSet.getImage(9, 1),model.getWorld().getPlayer().getX()*worldTileSet.getImageWidth()+startWorldMapX,model.getWorld().getPlayer().getY()*worldTileSet.getImageHeight()+startWorldMapY, null);
						if (model.getWorld().getPlayer().getInfos().getLives()>2){
							g.drawImage(worldTileSet.getImage(9, 2),model.getWorld().getPlayer().getX()*worldTileSet.getImageWidth()+startWorldMapX,model.getWorld().getPlayer().getY()*worldTileSet.getImageHeight()+startWorldMapY, null);
						}
					}
				}
			}
			//...affichage du joueur>
		}
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
		return dungeonTileSet.getImage(x, y);
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
		}else if (item instanceof FirstCrownPartItem) {
			x = 8;
		}else if (item instanceof SecondCrownPartItem) {
			x = 9;
		}
		return dungeonTileSet.getImage(x, y);
	}

	public BufferedImage getImageForArrow(Direction direction){
		int x = -1;
		int y = 4;
		switch (direction) {
		case NONE:
			x = 0;
			break;
		case NORTH:
			x = 0;
			break;
		case SOUTH:
			x = 1;
			break;
		case EAST:
			x = 2;
			break;
		case WEST:
			x = 3;
			break;
		case SOUTH_WEST:
			x = 4;
			break;
		case SOUTH_EAST:
			x = 5;
			break;
		case NORTH_WEST:
			x = 6;
			break;
		case NORTH_EAST:
			x = 7;
			break;
		default:
			x = 0;
			break;
		}
		return dungeonTileSet.getImage(x, y);
	}

	public BufferedImage getImageForPlayer(){
		int playerSpriteY = 2;
		if (model.getDungeon().getPlayer().facesRight()){
			playerSpriteY = 1;
		}
		int playerSpriteX =(model.getDungeon().getPlayer().getConsecutiveSteps() %8)/2;
		if (model.getDungeon().getPlayer().getHitPoints() <= 1){
			playerSpriteX+=4;
		}
		return dungeonTileSet.getImage(playerSpriteX, playerSpriteY);
	}

	public BufferedImage getImageForMonster(Monster monster){
		int baseX = 0;
		int baseY = 0;
		switch (monster.getType()) {
		case SNAKE:
			baseX = 0;
			baseY = 3;
			break;
		case TROLL:
			baseX = 4;
			baseY = 3;
			break;
		case RAT:
			baseX = 8;
			baseY = 1;
			break;
		case SPIDER:
			baseX = 8;
			baseY = 2;
			break;
		case DRAGON:
			baseX = 8;
			baseY = 3;
			break;
		default:
			break;
		}
		int monsterY = baseY;
		int monsterX = baseX + (monster.getConsecutiveSteps()%8/4);
		if (monster.facesLeft()){
			monsterX +=2;
		}
		return dungeonTileSet.getImage(monsterX, monsterY);
	}


	/**
	 * Fournit l'image de l'item necessaire a la traversee de la case, si necessaire
	 * @param tile
	 * @return
	 */
	public BufferedImage getItemImageForWorldTile(WorldTile tile){
		int baseX = 0;
		int baseY = 0;
		switch (tile.getType()) {
		case RIVER_UP_DOWN:
		case RIVER_UP_RIGHT:
		case RIVER_RIGHT_DOWN:
		case RIVER_DOWN_LEFT:
		case RIVER_LEFT_UP:
			baseX = 5;
			baseY = 3;
			break;
		case WALL_DOOR_UP_DOWN:
		case WALL_DOOR_LEFT_RIGHT:
			baseX = 6;
			baseY = 3;
			break;
		case FOREST:
			baseX = 4;
			baseY = 3;
			break;
		default:
			// cette image est un vide transparent: rien d'affiche
			baseX = 4;
			baseY = 1;
			break;
		}
		return worldTileSet.getImage(baseX, baseY);
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
		return worldTileSet.getImage(baseX, baseY);
	}



}
