package com.bubblebob.uto;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.bubblebob.tool.ggame.Game;
import com.bubblebob.tool.ggame.GameModel;
import com.bubblebob.tool.ggame.gui.GraphicalModel;
import com.bubblebob.uto.control.InputManager;
import com.bubblebob.uto.control.InputType;
import com.bubblebob.uto.entity.BuildingEntity;
import com.bubblebob.uto.entity.Entity;
import com.bubblebob.uto.entity.FishEntity;
import com.bubblebob.uto.entity.MovingEntity;
import com.bubblebob.uto.entity.PirateBoatEntity;
import com.bubblebob.uto.entity.PlayerBoatEntity;
import com.bubblebob.uto.entity.PlayerEntity;
import com.bubblebob.uto.entity.RainCloudEntity;
import com.bubblebob.uto.graph.Assets;
import com.bubblebob.uto.graph.Drawable;
import com.bubblebob.uto.setting.GameConfigurator;
import com.bubblebob.uto.setting.Setting;
import com.bubblebob.uto.setting.SettingsManager;
import com.bubblebob.uto.setting.SimpleSetting;

public class UtoModel implements GameModel, GraphicalModel,Drawable{

	public Map map;
	public Timer time;
	public List<MovingEntity> mobs;
	public List<BuildingEntity> buildings;
	private Assets assets;
	private int tileSize;
	public PlayerEntity red;
	public PlayerEntity green;
	private InputManager inputManager;
	private Random random = new Random(System.currentTimeMillis());
	private List<Entity>[][] entities;
	private GraphOrderManager graphManager;
	private WildLifeManager wildLifeManager;
	
	private Setting settings = SettingsManager.getInstance();
	
	// ajout de la configuration
	private boolean configuratorOn = true;
	private GameConfigurator configurator;
	
	// la mise-a-jour des joueurs a-t-ell ete faire ce tour
	private boolean turnUpdateDone = false;

	public UtoModel(Map map, Assets assets){
		this.map = map;
		this.assets = assets;
		this.mobs = new ArrayList<MovingEntity>();
		this.buildings = new ArrayList<BuildingEntity>();
		this.graphManager = new GraphOrderManager();
		this.tileSize = map.tileSize;
		//System.out.println("[UtoModel#new] tileSize="+tileSize);
		this.inputManager = new InputManager();
		this.entities = new List[map.width][map.height];
		for (int i=0;i<map.width;i++){
			for (int j=0;j<map.height;j++){
				this.entities[i][j] = new ArrayList<Entity>();
			}
		}
		this.red = new PlayerEntity(this, map.redBoatPoint[0],  map.redBoatPoint[1], map.tileSize-1, map.tileSize-1,PlayerType.RED);
		this.green = new PlayerEntity(this, map.greenBoatPoint[0],  map.greenBoatPoint[1], map.tileSize-1, map.tileSize-1,PlayerType.GREEN);
		add(red);
		add(green);
		this.wildLifeManager = new WildLifeManager(this);
		this.configurator = new GameConfigurator(this,new Assets(6));
	}
	
	public void paint(Graphics g) {
		if (configuratorOn){
			configurator.paint(g);
		}else{
			map.paint(g);
			graphManager.paintInOrder(g);
			time.paint(g);
		}
	}
	
	public int getWidth() {
		return map.width*map.tileSize*assets.getScale()+6;
	}

	public int getHeight() {
		return map.height*map.tileSize*assets.getScale()+78;
	}
	
	public void update() {
		if (configuratorOn){
			configurator.update(inputManager);
		}else{
			if (!(time.getCycles() == -1)){
				if (!time.getPeriod().isFreezed()){
					// input...
					if (inputManager.getInput(InputType.GREEN_SWITCH_MOVE_MODE)){
						inputManager.resetInput(InputType.GREEN_SWITCH_MOVE_MODE);
						green.toggleMove();
					}
					if (inputManager.getInput(InputType.RED_SWITCH_MOVE_MODE)){
						inputManager.resetInput(InputType.RED_SWITCH_MOVE_MODE);
						red.toggleMove();
					}
					if (inputManager.getInput(InputType.RED_SWITCH_MOVE_MODE)){
						System.out.println("[UtoModel#update] RED_SWITH_MOV_MODE");
						inputManager.resetInput(InputType.RED_SWITCH_MOVE_MODE);
						red.toggleMove();
					}
					if (inputManager.getInput(InputType.GREEN_SWITCH_MOVE_MODE)){
						inputManager.resetInput(InputType.GREEN_SWITCH_MOVE_MODE);
						
					}
					if (inputManager.getInput(InputType.GREEN_BUY_SWITCH)){
						inputManager.resetInput(InputType.GREEN_BUY_SWITCH);
						green.switchBuy();
					}
					if (inputManager.getInput(InputType.GREEN_BUY)){
						inputManager.resetInput(InputType.GREEN_BUY);
						green.buy();
					}
					if (inputManager.getInput(InputType.RED_BUY)){
						inputManager.resetInput(InputType.RED_BUY);
						red.buy();
					}
					if (inputManager.getInput(InputType.RED_BUY_SWITCH)){
						inputManager.resetInput(InputType.RED_BUY_SWITCH);
						red.switchBuy();
					}
					if (inputManager.getInput(InputType.GREEN_BUY_CANCEL)){
						inputManager.resetInput(InputType.GREEN_BUY_CANCEL);
						green.cancelBuy();
					}
					if (inputManager.getInput(InputType.RED_BUY_CANCEL)){
						inputManager.resetInput(InputType.RED_BUY_CANCEL);
						red.cancelBuy();
					}

					if (inputManager.getInput(InputType.GREEN_BUY_CANCEL)){
						inputManager.resetInput(InputType.GREEN_BUY_CANCEL);
						green.cancelBuy();
					}
					if (inputManager.getInput(InputType.RED_BUY_CANCEL)){
						inputManager.resetInput(InputType.RED_BUY_CANCEL);
						red.cancelBuy();
					}
					if (inputManager.getInput(InputType.GREEN_SHOW_SCORE)){
						green.showScoreThisTurn();
					}
					if (inputManager.getInput(InputType.RED_SHOW_SCORE)){
						red.showScoreThisTurn();
					}
					
					if (inputManager.getInput(InputType.DEBUG_SPAWN_FISH)){
						inputManager.resetInput(InputType.DEBUG_SPAWN_FISH);
						boolean found = false;
						int fishI = random.nextInt(map.width);
						int fishJ = random.nextInt(map.height);
						while (!found) {
							fishI = random.nextInt(map.width);
							fishJ = random.nextInt(map.height);
							if (map.grid[fishI][fishJ].type == SquareType.SEA){
								found = true;
							}
						}
						add(new FishEntity(this, fishI*map.tileSize, fishJ*map.tileSize, map.tileSize-1, map.tileSize-1));
					}
					if (inputManager.getInput(InputType.DEBUG_SPAWN_PIRATE)){
						inputManager.resetInput(InputType.DEBUG_SPAWN_PIRATE);
						boolean found = false;
						int i = random.nextInt(map.width);
						int j = random.nextInt(map.height);
						while (!found) {
							i = random.nextInt(map.width);
							j = random.nextInt(map.height);
							if (map.grid[i][j].type == SquareType.SEA){
								found = true;
							}
						}
						add(new PirateBoatEntity(this, i*map.tileSize, j*map.tileSize, map.tileSize-1, map.tileSize-1));
					}
					
					if (inputManager.getInput(InputType.DEBUG_SPAWN_CLOUD)){
						inputManager.resetInput(InputType.DEBUG_SPAWN_CLOUD);
						boolean found = false;
						int cloudI = random.nextInt(map.width);
						int cloudJ = random.nextInt(map.height);
						while (!found) {
							cloudI = random.nextInt(map.width);
							cloudJ = random.nextInt(map.height);
							found = true;
						}
						add(new RainCloudEntity(this, cloudI*map.tileSize, cloudJ*map.tileSize, map.tileSize-1, map.tileSize-1));
					}
					green.dx = 0;
					green.dy = 0;
					red.dx = 0;
					red.dy = 0;
					if (inputManager.getInput(InputType.GREEN_MOVE_LEFT)) green.dx--;
					if (inputManager.getInput(InputType.RED_MOVE_LEFT)) red.dx--;
					if (inputManager.getInput(InputType.GREEN_MOVE_RIGHT)) green.dx++;
					if (inputManager.getInput(InputType.RED_MOVE_RIGHT)) red.dx++;
					if (inputManager.getInput(InputType.GREEN_MOVE_UP)) green.dy--;
					if (inputManager.getInput(InputType.RED_MOVE_UP)) red.dy--;
					if (inputManager.getInput(InputType.GREEN_MOVE_DOWN)) green.dy++;
					if (inputManager.getInput(InputType.RED_MOVE_DOWN)) red.dy++;
					// ...input
					for (MovingEntity mob: mobs){
						mob.update();
					}
					for (int i=0;i<mobs.size();i++){
						if (mobs.get(i).toKill){
							remove(mobs.get(i));
						}
					}
					for (MovingEntity mob: mobs){
						List<Entity> tileMobs =(List<Entity>) entities[mob.x/tileSize][mob.y/tileSize];
						for (Entity tileMob:tileMobs){
							if (tileMob != mob){
								tileMob.touched(mob);
							}
						}
					}
					for (MovingEntity mob: mobs){
						mob.updateMovement();
					}
					wildLifeManager.udpate();
				}
				time.update();
				// mise a jour des populations, score et richesses du au changement de tour
				if (time.getPeriod() != Period.FREE){
					if (!turnUpdateDone){
						turnUpdate();
						turnUpdateDone = true;
					}
				}else{
					turnUpdateDone = false;
				}
			}
		}
	}

	public int getTileSize(){
		return map.tileSize;
	}

	public int getMapWidth() {
		return map.width;
	}

	public int getMapHeight() {
		return map.height;
	}

	public boolean collideBox(SquareType type, int x, int y) {
		return map.collideBox(type, x, y);
	}

	public boolean collideType(SquareType type, int x, int y, int w, int h) {
		return map.collideType(type, x, y, w, h);
	}

	public void add(Entity entity){
		if (entity instanceof MovingEntity) {
			MovingEntity mob = (MovingEntity) entity;
			mobs.add(mob);
		}else if (entity instanceof BuildingEntity){
				BuildingEntity building = (BuildingEntity) entity;
				buildings.add(building);
		}
		entities[entity.x/tileSize][entity.y/tileSize].add(entity);
		graphManager.addEntity(entity);
	}
	
	public void remove(Entity entity){
		if (entity instanceof MovingEntity) {
			MovingEntity mob = (MovingEntity) entity;
			mobs.remove(mob);
			if (entity instanceof PlayerBoatEntity) {
				PlayerBoatEntity boat = (PlayerBoatEntity) entity;
				boat.player.boats.remove(boat);
			}
		}else if (entity instanceof BuildingEntity){
			BuildingEntity building = (BuildingEntity) entity;
			buildings.remove(building);
			building.player.removeBuilding(building);
			map.grid[entity.x/tileSize][entity.y/tileSize].buildingType = BuildingType.NO_BUILDING;
		}
		entities[entity.x/tileSize][entity.y/tileSize].remove(entity);
		graphManager.removeEntity(entity);
	}
	
	public void registerNewMovingEntityPosition(MovingEntity mob, int oldX, int oldY, int newX, int newY){
		entities[oldX/tileSize][oldY/tileSize].remove(mob);
		entities[newX/tileSize][newY/tileSize].add(mob);
	}
	
	public void clearBuildingFromSquare(int i, int j){
		List<BuildingEntity> squareBuildings = new ArrayList<BuildingEntity>();
		for (Entity e: entities[i][j]){
			if (e instanceof BuildingEntity) {
				BuildingEntity b = (BuildingEntity) e;
				squareBuildings.add(b);
			}
		}
		for (BuildingEntity b: squareBuildings){
			remove(b);
		}
	}

	private void turnUpdate(){
		red.turnUpdate();
		green.turnUpdate();
	}
	
	public void spawnFreeRebel(PlayerType player){
		switch (player) {
		case RED:
			red.spawnRebel();
			break;
		case GREEN:
			green.spawnRebel();
			break;
		default:
			break;
		}
	}
	
	public void startGameAfterConfig(GameConfigurator config){
		this.configuratorOn = false;
		this.settings = new SimpleSetting(config.cycleCount, config.cycleLength);
		this.time = new Timer(this,assets,map.tileSize*(map.width/2-3),map.tileSize*(map.height)+5);
	}
	
	
	public static void main(String[] args) {
		int scale = 5;
		Assets assets = new Assets(scale);
		String assetsFolder = "assets/";
		Map map = Map.getMap(assetsFolder+"map-gen.png", assetsFolder+"map-gen-pic.png",scale);
		UtoModel model = new UtoModel(map,assets);
		Game g = new Game("uto+", model, model, null, 50, null, null,model.inputManager);
		g.launch();
	}

	public Setting getSettings() {
		return settings;
	}

}
