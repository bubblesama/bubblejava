package com.bubblebob.dd.model.dungeon;

import java.security.SecureRandom;

import com.bubblebob.dd.DdConstants;
import com.bubblebob.dd.model.DdGameModel;
import com.bubblebob.dd.model.PlayerInfos;
import com.bubblebob.dd.model.Position;
import com.bubblebob.dd.model.dungeon.item.AxeItem;
import com.bubblebob.dd.model.dungeon.item.BoatItem;
import com.bubblebob.dd.model.dungeon.item.FirstCrownPartItem;
import com.bubblebob.dd.model.dungeon.item.Item;
import com.bubblebob.dd.model.dungeon.item.KeyItem;
import com.bubblebob.dd.model.dungeon.item.LadderItem;
import com.bubblebob.dd.model.dungeon.item.QuiverItem;
import com.bubblebob.dd.model.dungeon.item.SecondCrownPartItem;
import com.bubblebob.dd.model.dungeon.map.DungeonMap;
import com.bubblebob.dd.model.dungeon.mob.HitMonster;
import com.bubblebob.dd.model.dungeon.mob.Monster;
import com.bubblebob.dd.model.dungeon.mob.MonsterType;
import com.bubblebob.dd.model.dungeon.mob.Player;
import com.bubblebob.dd.model.world.map.WorldTileType;
import com.bubblebob.tool.ggame.GameModel;

/**
 * Conserve toutes les informations 
 * @author bubblebob
 */
public class DungeonModel implements GameModel{

	private DungeonMap map;
	private MonsterManager monsterManager;
	private ShootManager shootManager;
	private PuffManager puffManager;
	private Player player;

	private int tileWidth;
	private int tileHeight;

	private boolean paused;
	private int ticker;

	public boolean lost = false;

	private DdGameModel game;

	public DungeonModel(int tileWidth, int tileHeight){
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
	}



	public void subscribeToGame(DdGameModel game){
		//System.out.println("[DungeonModel#subscribeToGame] game="+game);
		this.game = game;
	}

	public DungeonMap getMap() {
		return map;
	}

	public void setMap(DungeonMap map) {
		this.map = map;
	}

	public MonsterManager getMonsterManager() {
		return monsterManager;
	}

	public ShootManager getShootManager() {
		return shootManager;
	}

	public PuffManager getPuffManager() {
		return puffManager;
	}

	public Player getPlayer() {
		return player;
	}

	public void update() {
		if (!paused){
			this.player.update();
			this.shootManager.update();	
			this.monsterManager.update();
			this.puffManager.update();
			if (this.player.getHitPoints()<= 0){
				//		debug = "MORT!";
			}
		}
		ticker++;
	}


	/**
	 * Ajoute les monstres et les objets aleatoirement en fonction du type de donjon
	 * @param dungeonType
	 */
	private void fillMap(WorldTileType dungeonType ){
		System.out.println("[DungeonModel#fillMap] IN dungeonType="+dungeonType);
		String time = System.currentTimeMillis()+"-seed";
		SecureRandom random = new SecureRandom(time.getBytes());
		MonsterType specificType = null;
		Item specificItem = null;
		switch (dungeonType) {
		case MOUTAIN_GREY:
			specificType = MonsterType.RAT;
			specificItem = new QuiverItem(this.game);
			break;
		case MOUTAIN_BLUE:
			specificType = MonsterType.TROLL;
			specificItem = new BoatItem(this.game);
			break;
		case MOUTAIN_RED:
			specificType = MonsterType.SNAKE;
			specificItem = new AxeItem(this.game);
			break;
		case MOUTAIN_PURPLE:
			specificType = MonsterType.DRAGON;
			specificItem = new KeyItem(this.game);
			break;
		case MOUTAIN_BIG:
			specificType = MonsterType.DRAGON;
			specificItem = new FirstCrownPartItem(this.game);
			break;
		default:
			break;
		}
		//<ajout de l'echelle ou de la deuxieme partie de couronne...
		int ladderBitX = random.nextInt(map.getWidth()/9)*9+4;
		int ladderBitY = random.nextInt(map.getHeight()/9)*9+4;
		// l'echelle est ajoutee au milieu du morceau de carte
		if (dungeonType != WorldTileType.MOUTAIN_BIG){
			map.getTile(ladderBitX, ladderBitY).addItem(new LadderItem(game));
		}else{
			map.getTile(ladderBitX, ladderBitY).addItem(new SecondCrownPartItem(game));
		}
		//<ajout du monstre de l'echelle...
		Monster specificLadderMonster = new HitMonster(this,ladderBitX*tileWidth, ladderBitY*tileHeight,specificType);
		monsterManager.addMonster(specificLadderMonster);
		//...ajout du monstre de l'echelle>
		//...ajout de l'echelle ou de la deuxieme partie de couronne>
		//<ajout de carquois aleatoires...
		int quivers = random.nextInt(3);
		for (int n=0;n<quivers;n++){
			boolean done = false;
			while (!done){
				int quiverBitX = random.nextInt(map.getWidth()/9)*9+4;
				int quiverBitY = random.nextInt(map.getHeight()/9)*9+4;
				if (map.getTile(quiverBitX,quiverBitY).getItems().isEmpty()){
					done = true;
					map.getTile(quiverBitX,quiverBitY).addItem(new QuiverItem(game));
				}
			}
		}
		//...ajout de carquois aleatoires>
		//<ajout de l'objet specifique et du monstre gardien...
		boolean done = false;
		while (!done){
			int specificItemX = random.nextInt(map.getWidth()/9)*9+4;
			int specificItemY = random.nextInt(map.getHeight()/9)*9+4;
			if (map.getTile(specificItemX,specificItemY).getItems().isEmpty()){
				done = true;
				map.getTile(specificItemX,specificItemY).addItem(specificItem);
				Monster specificItemMonster = new HitMonster(this,specificItemX*tileWidth, specificItemY*tileHeight,specificType);
				monsterManager.addMonster(specificItemMonster);
			}
		}
		//...ajout de l'objet specifique et du monstre gardien>
		// ajout de rats, serpents, etc... en fonction de la carte
		// TODO
	}




	/**
	 * Fournit l'element subissant une eventuelle collision sur une case donnee
	 * @param tileX
	 * @param tyleY
	 * @return
	 */
	public Collideable getCollided(int tileX, int tyleY){
		for (Monster monster: monsterManager.getMonsters()){
			if (monster.getXTile() == tileX && monster.getYTile() == tyleY){
				return monster;
			}
		}
		if (player.getXTile() == tileX && player.getYTile() == tyleY){
			return player;
		}
		return null;
	}

	public void pause(){
		paused = true;
	}

	public boolean isPaused(){
		return paused;
	}

	public void resume(){
		paused = false;
	}

	public int getTileWidth() {
		return tileWidth;
	}

	public int getTileHeight() {
		return tileHeight;
	}

	public int getPixelWidth(){
		return tileWidth*map.getWidth();
	}

	public int getPixelHeight(){
		return tileHeight*map.getHeight();
	}

	public int getTicker() {
		return ticker;
	}


	/**
	 * Initialise un donjon avec une nouvelle carte, les informations de base du joueur et remplit le donjon de monstres et d'items en fonction du type de donjon
	 * @param map
	 * @param infos
	 * @param dungeonType
	 */
	public void launch(DungeonMap map, PlayerInfos infos,WorldTileType dungeonType){
		System.out.println("[DungeonModel#launch] IN dungeonType="+dungeonType);
		this.map = map;
		// le joueur est positionne sur la premiete case vide
		Position startTile = map.getFirstEmptyTile();
		int playerX = startTile.getTileX()*tileWidth+1;
		int playerY = startTile.getTileY()*tileHeight+1;
		this.player = new Player(this, playerX, playerY, DdConstants.PLAYER_TICKS_TO_MOVE, Player.DEFAULT_HIT_POINTS,infos);
		this.ticker = 0;
		this.monsterManager = new MonsterManager(this);
		this.puffManager = new PuffManager();
		this.shootManager = new ShootManager(this, player, DdConstants.SHOOT_SPAN, DdConstants.SHOOT_TICKS_TO_MOVE, infos);
		this.lost = false;
		this.paused = false;
		fillMap(dungeonType);
	}

}
