package com.bubblebob.dd;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import com.bubblebob.dd.control.DungeonController;
import com.bubblebob.dd.control.WorldController;
import com.bubblebob.dd.model.DdGameModel;
import com.bubblebob.dd.model.Direction;
import com.bubblebob.dd.model.GameMode;
import com.bubblebob.dd.model.dungeon.DungeonModel;
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
import com.bubblebob.dd.model.dungeon.mob.Monster;
import com.bubblebob.dd.model.world.WorldModel;
import com.bubblebob.dd.model.world.WorldPlayer;
import com.bubblebob.dd.model.world.map.WorldMapFactory;
import com.bubblebob.dd.model.world.map.WorldTile;
public class SlickGame extends BasicGame {


	private static AppGameContainer app;

	private DdGameModel model;
	private static final long TIME_MODEL_REFRESH = 30;
	private long lastRefreshStamp = System.currentTimeMillis();

	// controle
	private DungeonController dungeonController;
	private WorldController worldController;
	private Input input;
	private Map<Integer,Long> keysMap;


	// graphiques
	private static final int WINDOW_WIDTH = 640;
	private static final int WINDOW_HEIGHT = 480;

	private SpriteSheet dungeonSprites;
	private SpriteSheet worldSprites;
	private Color backGroundGreen = new Color(38, 127, 0);
	private Color worldGreen = new Color(117, 204, 128);
	// WORLD: le coin superieur droit initial d'affichage de la carte du monde
	private int startWorldMapX = 15;
	private int startWorldMapY = 60;
	private final static int DUNGEON_SHOWN_WIDTH = 9;
	private final static int DUNGEON_SHOWN_HEIGHT = 4;
	private static final int SCALE = 4;
	private static final int DUNGEON_SCALE = 4;

	
	public SlickGame() {
		super("Slick DD");
	}

	public void init(GameContainer gc) throws SlickException {
		// modele
		DungeonModel dungeon = new DungeonModel(9, 13);
		WorldModel world = new WorldModel(WorldMapFactory.loadTextMap("aa"),WorldPlayer.getDefaultPlayer());
		this.model = new DdGameModel(world, dungeon);
		// controle
		this.keysMap = new HashMap<Integer,Long>();
		this.dungeonController = new DungeonController(model);
		this.worldController = new WorldController(model);
		this.input = gc.getInput();
		// graphique
		this.dungeonSprites = new SpriteSheet("assets/pics/dd-dungeon-small.png", 9, 13);
		this.worldSprites = new SpriteSheet("assets/pics/dd-world.png", 8, 8);
	}

	public void update(GameContainer gc, int lastUpdate) throws SlickException {
		// recuperation des commandes
		if (model.getMode() == GameMode.WORLD){
			if (checkKey(Input.KEY_RIGHT)){
				worldController.goEast();
			}
			if (checkKey(Input.KEY_LEFT)){
				worldController.goWest();
			}
			if (checkKey(Input.KEY_UP)){
				worldController.goNorth();
			}
			if (checkKey(Input.KEY_DOWN)){
				worldController.goSouth();
			}
		}else{
			// DUNGEON
			if (checkKey(Input.KEY_RIGHT,100,false)){
				dungeonController.goRight();
			}else{
				dungeonController.stopRight();
			}
			if (checkKey(Input.KEY_LEFT,100,false)){
				dungeonController.goLeft();
			}else{
				dungeonController.stopLeft();
			}
			if (checkKey(Input.KEY_UP,100,false)){
				dungeonController.goUp();
			}else{
				dungeonController.stopUp();
			}
			if (checkKey(Input.KEY_DOWN,100,false)){
				dungeonController.goDown();
			}else{
				dungeonController.stopDown();
			}
		}
		if (System.currentTimeMillis()-lastRefreshStamp > TIME_MODEL_REFRESH){
			lastRefreshStamp = System.currentTimeMillis();
			// maj modele
			model.update();
		}
	}


	private boolean checkKey(int keyCode){
		return checkKey(keyCode,500,true);
	}

	private boolean checkKey(int keyCode, long lag, boolean resetOnUp){
		boolean doAction = false;
		if (input.isKeyDown(keyCode)){
			if (keysMap.containsKey(keyCode)){
				long lastTime = keysMap.get(keyCode);
				if (System.currentTimeMillis()-lastTime>lag){
					doAction = true;
					keysMap.put(keyCode, System.currentTimeMillis());
				}
			}else{
				doAction = true;
			}
		}else{// keyUp
			keysMap.remove(keyCode);
		}
		if (doAction){
			keysMap.put(keyCode, System.currentTimeMillis());
		}
		return doAction;
	}

	// LANCEMENT
	public static void main(String[] args) {
		//AppGameContainer app;
		try {
			app = new AppGameContainer(new SlickGame());
			app.setDisplayMode(WINDOW_WIDTH,WINDOW_HEIGHT,false);
			app.setMinimumLogicUpdateInterval(1000/60);
			app.isVSyncRequested();
			app.setTargetFrameRate(60);
			app.setShowFPS(false);
			app.start();

		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	// GRAPHIQUE

	public void render(GameContainer gc, Graphics g) throws SlickException {

		if (model.getMode() == GameMode.DUNGEON){
			g.setColor(backGroundGreen);
			g.fillRect(0, 0, 1000, 1000);
			int spriteWidth = dungeonSprites.getSprite(0, 0).getWidth();
			int spriteHeight = dungeonSprites.getSprite(0, 0).getHeight();
			//<dessin des cases et des items...
			//<le decalage du joueur par rapport a l'origine...

			//...le decalage du joueur par rapport a l'origine>

			//			//<le decalage du joueur par rapport a la case
			//			int playerDeltaX = model.getDungeon().getPlayer().getX() % spriteWidth;
			//			int playerDeltaY = model.getDungeon().getPlayer().getY() % spriteHeight;
			//			//...le decalage du joueur par rapport a l'origine>
			//			int widthIndex = playerTileDeltaX - DUNGEON_SHOWN_WIDTH/2;
			//			int tileWidth = model.getDungeon().getMap().getWidth();
			//			int tileHeight = model.getDungeon().getMap().getHeight();
			//			for (int i=0; i<DUNGEON_SHOWN_WIDTH+1; i++){
			//				int heightIndex = playerTileDeltaY - DUNGEON_SHOWN_HEIGHT/2;
			//				for (int j=0; j<DUNGEON_SHOWN_HEIGHT+1; j++){
			//					DungeonTile tile = model.getDungeon().getMap().getTile((widthIndex+tileWidth)%tileWidth,(heightIndex+tileHeight)%tileHeight);
			//					if (tile != null && tile.isDiscovered() && tile.getType() != DungeonTileType.WALL){
			//						g.drawImage(getImageForTile(tile),i*spriteWidth-playerDeltaX,j*spriteHeight-playerDeltaY, null);
			//						if (!tile.getItems().isEmpty()){
			//							Item item = tile.getItems().get(0);
			//							g.drawImage(getImageForItem(item),i*spriteWidth-playerDeltaX,j*spriteHeight-playerDeltaY, null);
			//						}
			//					}
			//					heightIndex++;
			//				}
			//				widthIndex++;
			//			}


			for (int i=-DUNGEON_SHOWN_WIDTH;i<DUNGEON_SHOWN_WIDTH;i++){
				for (int j=-DUNGEON_SHOWN_HEIGHT;j<DUNGEON_SHOWN_HEIGHT;j++){
					DungeonTile tile = model.getDungeon().getMap().getTile(model.getDungeon().getPlayer().getXTile()+i, model.getDungeon().getPlayer().getYTile()+j);
					g.drawImage(getImageForTile(tile).getScaledCopy(DUNGEON_SCALE), (DUNGEON_SHOWN_WIDTH+i)*DUNGEON_SCALE*spriteWidth, (DUNGEON_SHOWN_HEIGHT+j)*DUNGEON_SCALE*spriteHeight);
				}
			}
			//...dessin des cases et des items>

			//<dessin du joueur...
			if (!model.getDungeon().getPlayer().isDeathStarted()){
				g.drawImage(getImageForPlayer().getScaledCopy(DUNGEON_SCALE), DUNGEON_SHOWN_WIDTH*DUNGEON_SCALE*spriteWidth, DUNGEON_SHOWN_HEIGHT*DUNGEON_SCALE*spriteHeight, null);
			}
			//...dessin du joueur>
			/*
			//<dessin des fleches...
			for (Shoot shoot: model.getDungeon().getShootManager().getShoots()){
				//System.out.println("[DdCenteredGraphicalAdapter#paint] "+shoot.getX()/tileSet.getImageWidth());
				g.drawImage(getImageForArrow(shoot.getDirection()),shoot.getTileX()*dungeon.getSpriteWidth()-model.getDungeon().getPlayer().getX()+shownWidth/2*dungeon.getSpriteWidth(),shoot.getTileY()*dungeon.getSpriteHeight()-model.getDungeon().getPlayer().getY()+shownHeight/2*dungeon.getSpriteHeight(), null);
			}
			//...dessin des fleches>
			//<dessin des monstres...
			for (Monster monster: model.getDungeon().getMonsterManager().getMonsters()){
				if (monster.getCurrentTile().isDiscovered()|| monster.getType().isAlwaysVisible()){
					g.drawImage(getImageForMonster(monster),monster.getX()-model.getDungeon().getPlayer().getX()+shownWidth/2*dungeon.getSpriteWidth(),monster.getY()-model.getDungeon().getPlayer().getY()+shownHeight/2*dungeon.getSpriteHeight(), null);
				}
			}
			//...dessin des monstres>
			//<dessin des fumees...
			for (Puff puff: model.getDungeon().getPuffManager().getPuffs()){
				g.drawImage(dungeon.getSprite(7, 5),puff.getX()-model.getDungeon().getPlayer().getX()+shownWidth/2*dungeon.getSpriteWidth(),puff.getY()-model.getDungeon().getPlayer().getY()+shownHeight/2*dungeon.getSpriteHeight(), null);
			}
			//...dessin des fumees>
//			g.setColor(Color.BLACK);
//			g.drawString(""+(model.getDungeon().getTicker()/10), 10, 10);

			if (model.getDungeon().isPaused()){
				g.setColor(Color.GREEN);
				g.fillRect(shownWidth/2*dungeon.getSpriteWidth()-25,shownHeight/2*dungeon.getSpriteHeight()+2, 67, 16);
				g.setColor(Color.RED);
				g.drawString("* PAUSE *", shownWidth/2*dungeon.getSpriteWidth()-20,shownHeight/2*dungeon.getSpriteHeight()+14);
			}
			if (model.getDungeon().lost){
				g.setColor(Color.GREEN);
				g.fillRect(shownWidth/2*dungeon.getSpriteWidth()-25,shownHeight/2*dungeon.getSpriteHeight()+2, 87, 16);
				g.setColor(Color.RED);
				g.drawString("*GAME OVER*", shownWidth/2*dungeon.getSpriteWidth()-20,shownHeight/2*dungeon.getSpriteHeight()+14);
			}
			//			System.out.println("[DdCenteredGraphicalAdapter#paint] OUT duree="+(System.currentTimeMillis()-begin));


			 */
		}else if (model.getMode() == GameMode.WORLD){
			g.setColor(worldGreen);
			g.fillRect(0, 0, 1000, 1000);
			g.setColor(Color.white);

			int worldTileScaledWidth = worldSprites.getSprite(0, 0).getWidth()*SCALE;
			int worldTileScaledHeight = worldSprites.getSprite(0, 0).getHeight()*SCALE;

			//<affichage des cases...
			int x = startWorldMapX;
			for (int i=0; i<model.getWorld().getMap().getTileWidth(); i++){
				int y = startWorldMapY;
				for (int j=0; j<model.getWorld().getMap().getTileHeight(); j++){
					g.drawImage(getImageForWorldTile(model.getWorld().getMap().getTile(i,j)).getScaledCopy(SCALE),x,y, null);
					y += worldTileScaledHeight;
				}
				x += worldTileScaledWidth;
			}

			//<affichage du donjon principal...
			g.drawImage(worldSprites.getSprite(5, 2).getScaledCopy(SCALE),model.getWorld().getMap().getLowLeftLastDungeonX()*worldTileScaledWidth+startWorldMapX,model.getWorld().getMap().getLowLeftLastDungeonY()*worldTileScaledHeight+startWorldMapY, null);
			g.drawImage(worldSprites.getSprite(6, 2).getScaledCopy(SCALE),(model.getWorld().getMap().getLowLeftLastDungeonX()+1)*worldTileScaledWidth+startWorldMapX,model.getWorld().getMap().getLowLeftLastDungeonY()*worldTileScaledHeight+startWorldMapY, null);
			g.drawImage(worldSprites.getSprite(5, 1).getScaledCopy(SCALE),model.getWorld().getMap().getLowLeftLastDungeonX()*worldTileScaledWidth+startWorldMapX,(model.getWorld().getMap().getLowLeftLastDungeonY()-1)*worldTileScaledHeight+startWorldMapY, null);
			g.drawImage(worldSprites.getSprite(6, 1).getScaledCopy(SCALE),(model.getWorld().getMap().getLowLeftLastDungeonX()+1)*worldTileScaledWidth+startWorldMapX,(model.getWorld().getMap().getLowLeftLastDungeonY()-1)*worldTileScaledHeight+startWorldMapY, null);

			//<affichage des nuages...
			g.drawImage(worldSprites.getSprite(6, 1).getScaledCopy(SCALE),(model.getWorld().getMap().getLowLeftLastDungeonX()+1)*worldTileScaledWidth+startWorldMapX,(model.getWorld().getMap().getLowLeftLastDungeonY()-1)*worldTileScaledHeight+startWorldMapY, null);
			int deltaY = (model.getWorld().getTicker()%120)/40;
			g.drawImage(worldSprites.getSprite(7, 1+deltaY).getScaledCopy(SCALE),model.getWorld().getMap().getLowLeftLastDungeonX()*worldTileScaledWidth+startWorldMapX,(model.getWorld().getMap().getLowLeftLastDungeonY()-1)*worldTileScaledHeight+startWorldMapY, null);
			g.drawImage(worldSprites.getSprite(8, 1+deltaY).getScaledCopy(SCALE),(model.getWorld().getMap().getLowLeftLastDungeonX()+1)*worldTileScaledWidth+startWorldMapX,(model.getWorld().getMap().getLowLeftLastDungeonY()-1)*worldTileScaledHeight+startWorldMapY, null);
			//...affichage des nuages>
			//...affichage du donjon principal>
			//...affichage des cases>
			// affichage de l'eventuel item de la traversee de case du joueur
			g.drawImage(getItemImageForWorldTile(model.getWorld().getMap().getTile(model.getWorld().getPlayer().getX(),model.getWorld().getPlayer().getY())),model.getWorld().getPlayer().getX()*worldTileScaledWidth+startWorldMapX,model.getWorld().getPlayer().getY()*worldTileScaledHeight+startWorldMapY, null);


			//<affichage du joueur...
			if (model.getWorld().isWon()){
				g.drawImage(worldSprites.getSprite(6, 0).getScaledCopy(SCALE),model.getWorld().getPlayer().getX()*worldTileScaledWidth+startWorldMapX,model.getWorld().getPlayer().getY()*worldTileScaledHeight+startWorldMapY, null);
			}else{
				boolean displayPlayer = (model.getWorld().getTicker()%20)/10 == 0;
				if (displayPlayer){
					g.drawImage(worldSprites.getSprite(9, 0).getScaledCopy(SCALE),model.getWorld().getPlayer().getX()*worldTileScaledWidth+startWorldMapX,model.getWorld().getPlayer().getY()*worldTileScaledHeight+startWorldMapY, null);
					if (model.getWorld().getPlayer().getInfos().getLives()>1){
						g.drawImage(worldSprites.getSprite(9, 1).getScaledCopy(SCALE),model.getWorld().getPlayer().getX()*worldTileScaledWidth+startWorldMapX,model.getWorld().getPlayer().getY()*worldTileScaledHeight+startWorldMapY, null);
						if (model.getWorld().getPlayer().getInfos().getLives()>2){
							g.drawImage(worldSprites.getSprite(9, 2).getScaledCopy(SCALE),model.getWorld().getPlayer().getX()*worldTileScaledWidth+startWorldMapX,model.getWorld().getPlayer().getY()*worldTileScaledHeight+startWorldMapY, null);
						}
					}
				}
			}
			//...affichage du joueur>
		}

	}

	public Image getImageForTile(DungeonTile t){
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
		return dungeonSprites.getSprite(x, y);
	}

	public Image getImageForItem(Item item){
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
		return dungeonSprites.getSprite(x, y);
	}

	public Image getImageForArrow(Direction direction){
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
		return dungeonSprites.getSprite(x, y);
	}

	public Image getImageForPlayer(){
		int playerSpriteY = 2;
		if (model.getDungeon().getPlayer().facesRight()){
			playerSpriteY = 1;
		}
		int playerSpriteX =(model.getDungeon().getPlayer().getConsecutiveSteps() %8)/2;
		if (model.getDungeon().getPlayer().getHitPoints() <= 1){
			playerSpriteX+=4;
		}
		return dungeonSprites.getSprite(playerSpriteX, playerSpriteY);
	}

	public Image getImageForMonster(Monster monster){
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
		case BLOB:
			baseX = 8;
			baseY = 4;
			break;
		default:
			break;
		}
		int monsterY = baseY;
		int monsterX = baseX + (monster.getConsecutiveSteps()%8/4);
		if (monster.facesLeft()){
			monsterX +=2;
		}
		return dungeonSprites.getSprite(monsterX, monsterY);
	}


	/**
	 * Fournit l'image de l'item necessaire a la traversee de la case, si necessaire
	 * @param tile
	 * @return
	 */
	public Image getItemImageForWorldTile(WorldTile tile){
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
		return worldSprites.getSprite(baseX, baseY);
	}

	public Image getImageForWorldTile(WorldTile tile){
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
		return worldSprites.getSprite(baseX, baseY);
	}

}
