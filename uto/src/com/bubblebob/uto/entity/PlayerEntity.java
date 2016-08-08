package com.bubblebob.uto.entity;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.bubblebob.uto.BuildingType;
import com.bubblebob.uto.BuyableType;
import com.bubblebob.uto.MovMode;
import com.bubblebob.uto.Period;
import com.bubblebob.uto.PlayerType;
import com.bubblebob.uto.Square;
import com.bubblebob.uto.SquareType;
import com.bubblebob.uto.UtoModel;
import com.bubblebob.uto.entity.move.CanMoveEverywhereStrategy;

public class PlayerEntity extends MovingEntity{

	private List<BuildingEntity> buildings;
	public List<PlayerBoatEntity> boats;
	public BoatEntity currentBoat = null;
	private int score = 0;
	private int pop = 300;
	public MovMode mode;
	private PlayerType type;
	private int wealth;
	private int tick;
	public BuyableType buyable = BuyableType.FORT;
	public boolean readyToBuy = false;
	public boolean showScoreThisTurn = false;

	public PlayerEntity(UtoModel model, int x, int y, int w, int h, PlayerType type) {
		super(model, "player:"+type,x, y, w, h,new CanMoveEverywhereStrategy());
		this.mode = MovMode.CURSOR;
		this.type = type;
		this.wealth = 100;
		this.boats = new ArrayList<PlayerBoatEntity>();
		this.buildings = new ArrayList<BuildingEntity>();
	}

	public boolean canMove(int newX, int newY){
		if (mode == MovMode.BOAT){
			return !model.collideType(SquareType.LAND, x+dx, y+dy,w,h);
		}else if (mode == MovMode.CURSOR){
			return super.canMove(newX, newY);
		}else{
			return false;
		}
	}
	
	public int getWealth(){
		return this.wealth;
	}
	
	public void creditWealth(int credit){
		this.wealth += credit;
	}

	public boolean canPayBuilding(BuildingEntity b) {
		return (wealth >= b.type.price);
	}

	public boolean removeBuilding(BuildingEntity b) {
		return buildings.remove(b);
	}

	public void paint(Graphics g){
		tick = (tick+1)%20;
		if (mode == MovMode.CURSOR){
			g.drawImage(assets.getCursor(type),x*assets.getScale(), y*assets.getScale(), null);
		}
		if (readyToBuy && tick <10){
			g.drawImage(assets.getBuyable(buyable, type),type==PlayerType.RED?10:795,440, null);
		}
		if (showScoreThisTurn){
			assets.paintNumbers(g, score, 5, false, type == PlayerType.RED?11:134, 89);
			showScoreThisTurn = false;
		}else{
			if (model.time.finished){
				assets.paintNumbers(g, score, 5, false, type == PlayerType.RED?11:134, 89);
			}else{
				if (model.time.getPeriod() == Period.SHOW_POP){
					assets.paintNumbers(g, pop, 3, false, type == PlayerType.RED?11:134, 89);
				}else if (model.time.getPeriod() == Period.SHOW_SCORE){
					assets.paintNumbers(g, score, 3, false, type == PlayerType.RED?11:134, 89);
				}else{
					assets.paintNumbers(g, wealth, 3, false, type == PlayerType.RED?11:134, 89);
				}
			}
		}

	}

	public PlayerType getType() {
		return type;
	}
	
	public MovMode getMode() {
		return mode;
	}

	public BoatEntity getMyBoat(){
		BoatEntity result = null;
		for (BoatEntity boat: boats){
			if (!boat.changingState && (boat.x - x)*(boat.x - x)<16 && (boat.y - y)*(boat.y - y)<16){
				result = boat;
			}
		}
		return result;
	}

	public void switchBuy(){
		if (readyToBuy){
			tick = 0;
			buyable = buyable.getSwitch();
		}else{
			readyToBuy = true;
		}
	}

	public void cancelBuy(){
		readyToBuy = false;
		buyable = BuyableType.FORT;
	}

	public void buy(){
		if (readyToBuy && buyable.getCost() <= wealth){
			boolean bought= false;
			switch (buyable) {
			case PATROL:
			case FISHING:
				bought = buyBoat(buyable);
				break;
			case FACTORY:
			case FARM:
			case FORT:
			case HOSPITAL:
			case HOUSE:
			case SCHOOL:
				bought = buyBuilding(buyable);
				break;
			case REBEL:
				bought = true;
				model.spawnFreeRebel(type == PlayerType.RED?PlayerType.GREEN:PlayerType.RED);
			default:
				break;
			}
			if (bought){
				wealth -= buyable.getCost();
			}
			readyToBuy = false;
		}
	}

	public boolean buyBoat(BuyableType buyable){
		boolean bought = true;
		PlayerBoatEntity boat = null;
		switch (buyable) {
		case FISHING:
			boat = new FishBoatEntity(model, (type==PlayerType.RED?model.map.redBoatPoint[0]:model.map.greenBoatPoint[0]), (type==PlayerType.RED?model.map.redBoatPoint[1]:model.map.greenBoatPoint[1]), this);
			break;
		case PATROL:
			boat = new PatrolBoatEntity(model, (type==PlayerType.RED?model.map.redBoatPoint[0]:model.map.greenBoatPoint[0]), (type==PlayerType.RED?model.map.redBoatPoint[1]:model.map.greenBoatPoint[1]), this);
			break;
		default:
			break;
		}
		boats.add(boat);
		model.add(boat);
		return bought;
	}

	public boolean buyBuilding(BuyableType buyable){
		Square tile = model.map.getTile(x, y);
		boolean bought = false;	
		if (tile != null && tile.getType() == SquareType.LAND && tile.getOwner() == type && tile.buildingType == BuildingType.NO_BUILDING){
			BuildingEntity entityToBuy = null;
			switch (buyable) {
			case FACTORY:
				entityToBuy = new BuildingFactoryEntity(model, this, tile.x, tile.y);
				break;
			case FARM:
				entityToBuy = new BuildingFarmEntity(model, this, tile.x, tile.y);
				break;
			case FORT:
				entityToBuy = new BuildingFortEntity(model, this, tile.x, tile.y);
				break;
			case HOSPITAL:
				entityToBuy = new BuildingHospitalEntity(model, this, tile.x, tile.y);
				break;
			case HOUSE:
				entityToBuy = new BuildingHouseEntity(model, this, tile.x, tile.y);
				break;
			case SCHOOL:
				entityToBuy = new BuildingSchoolEntity(model, this, tile.x, tile.y);
				break;
			default:
				break;
			}
			buildings.add(entityToBuy);
			model.add(entityToBuy);
			tile.buildingType = entityToBuy.type;
			bought = true;
		}
		return bought;
	}

	public void toggleMove(){
		System.out.println("[PlayerEntity#toggleMove] IN");
		if (mode == MovMode.BOAT){
			mode = MovMode.CURSOR;
			currentBoat = null;
			this.movingTicksToMove = 1;
		}else{
			BoatEntity thereBoat = getMyBoat();
			if (thereBoat != null){
				currentBoat = thereBoat;
				mode = MovMode.BOAT;
				this.x = currentBoat.x;
				this.newX = currentBoat.x;
				this.y = currentBoat.y;
				this.newY = currentBoat.y;
				this.movingTicksToMove = 1;
				System.out.println("movingTicksToMove="+movingTicksToMove);
			}
		}
	}

	public void update(){
		super.update();
		if (currentBoat != null){
			currentBoat.newX = newX;
			currentBoat.newY = newY;
		}
	}

	public int getGraphLayer() {
		return 9;
	}

	public void showScoreThisTurn(){
		showScoreThisTurn = true;
	}

	public void turnUpdate(){
		System.out.println("[PlayerEntity#turnUpdate] IN");
		int factories = 0;
		int schools = 0;
		int farms = 0;
		int hospitals = 0;
		int houses = 0;
		int fishboats = 0;
		int rebels = 0;
		for (int i=0;i<buildings.size();i++){
			BuildingEntity building = buildings.get(i);
			if (building instanceof BuildingFactoryEntity) {
				factories++;
			}
			if (building instanceof BuildingSchoolEntity) {
				schools++;
			}
			if (building instanceof BuildingFarmEntity) {
				BuildingFarmEntity farm = (BuildingFarmEntity)building;
				farms++;
				if (!farm.cashThisTurn){
					farm.cycles--;
					if (farm.cycles <= 0){
						model.remove(farm);
					}
				}
				farm.cashThisTurn = false;
			}
			if (building instanceof BuildingHospitalEntity) {
				hospitals++;
			}
			if (building instanceof BuildingHouseEntity) {
				houses++;
			}
			if (building instanceof BuildingRebelEntity) {
				rebels++;
			}
		}
		for (BoatEntity boat: boats){
			if (boat instanceof FishBoatEntity){
				fishboats++;
			}
		}
		int popWorkOrFeedUnits = pop/200;
		score += pop;
		// richesse
		//usines aides par les ecoles
		int fullProdFactories = Math.min(popWorkOrFeedUnits, Math.min(2*schools,factories));
		int prodFactories = Math.min(popWorkOrFeedUnits, factories) - fullProdFactories;
		wealth += 15*fullProdFactories + 10*prodFactories;
		//bateaux de peche
		wealth += 3 * fishboats;
		//population
		int foodUnits = farms+2*fishboats;
		boolean starvation = popWorkOrFeedUnits>foodUnits;
		int healthMod = 3*hospitals+houses-factories-(starvation?2:0);
		if (starvation){
			System.out.println("[PlayerEntity#turnUpdate] "+type+": starvation! popWorkOrFeedUnits="+popWorkOrFeedUnits+" foodUnits="+foodUnits);

			spawnRebel();
			pop = Math.max(300, pop-100*(popWorkOrFeedUnits+foodUnits));
		}
		if (healthMod<-2 ||  healthMod<-5){
			System.out.println("[PlayerEntity#turnUpdate] low health!");
			spawnRebel();
		}
		if (!starvation){
			pop = Math.max(300,Math.min(pop+100*(foodUnits-popWorkOrFeedUnits+healthMod),600+500*houses));
			if (rebels>0){
				BuildingRebelEntity rebel = null;
				for (BuildingEntity b: buildings){
					if (b instanceof BuildingRebelEntity) {
						rebel = (BuildingRebelEntity) b;
					}
				}
				model.remove(rebel);
			}
		}
	}
	public void spawnRebel(){
		System.out.println("[PlayerEntity#spawnRebel] IN");
		Random random = new Random(System.currentTimeMillis());
		List<Square> lands = (type == PlayerType.RED)?model.map.redLand:model.map.greenLand;
		boolean found = false;
		while (!found) {
			Square square = lands.get(random.nextInt(lands.size()));
			found = true;
			for (BuildingEntity building: buildings){
				if (building instanceof BuildingFortEntity) {
					if (Math.abs((square.x-building.x)/model.map.tileSize)<=1 && 
							Math.abs((square.y-building.y)/model.map.tileSize)<=1
					){
						found = false;
					}
				}
			}
			if (found){
				BuildingRebelEntity rebel = new BuildingRebelEntity(model, this, square.x, square.y);
				model.clearBuildingFromSquare(square.i, square.j);
				model.add(rebel);
				buildings.add(rebel);
				square.buildingType = BuildingType.REBEL;
			}
		}
	}

}
