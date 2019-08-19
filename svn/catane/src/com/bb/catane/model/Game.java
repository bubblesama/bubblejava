package com.bb.catane.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.bb.catane.control.Action;
import com.bb.catane.control.ActionDoRoll;
import com.bb.catane.control.ActionDropResources;
import com.bb.catane.control.ActionEndTurn;
import com.bb.catane.control.ActionMoveRobber;
import com.bb.catane.control.ActionPlaceCity;
import com.bb.catane.control.ActionPlaceColony;
import com.bb.catane.control.ActionPlaceFreeColony;
import com.bb.catane.control.ActionPlaceFreeRoad;
import com.bb.catane.control.ActionPlaceRoad;
import com.bb.catane.control.ActionRobSpecific;
import com.bb.catane.control.ActionType;

public class Game {

	private static final int MAX_COLONIES = 5;
	private static final int MAX_CITIES = 4;
	private static final int MAX_ROADS = 15;

	public GameBoard board;
	public Map<PlayerType,Player> players;
	public TurnManager turnManager;
	public PlayerType currentPlayer;
	public boolean robberMoved = true;
	public int[] lastDiceRoll;
	public int lastDiceTotal;
	public boolean diceRolled;
	public boolean playerShouldBeRobbed = false;

	public Game(int playersNumber){
		// joueurs
		this.players = new HashMap<PlayerType,Player>();
		this.players.put(PlayerType.BLUE, new Player(PlayerType.BLUE));
		this.players.put(PlayerType.RED, new Player(PlayerType.RED));
		if (playersNumber >2){
			this.players.put(PlayerType.WHITE, new Player(PlayerType.WHITE));
		}
		if (playersNumber >3){
			this.players.put(PlayerType.YELLOW, new Player(PlayerType.YELLOW));
		}
		// gestion des tours
		//   creation de la liste
		List<PlayerType> playerOrder = new ArrayList<PlayerType>(playersNumber);
		playerOrder.add(PlayerType.BLUE);
		playerOrder.add(PlayerType.RED);
		if (playersNumber >2){
			playerOrder.add(PlayerType.WHITE);
		}
		if (playersNumber >3){
			playerOrder.add(PlayerType.YELLOW);
		}
		Collections.shuffle(playerOrder, new Random());
		turnManager = new TurnManager(playerOrder);
		// board
		this.board = GameBoard.createBoard();
		// des
		this.lastDiceRoll = new int[2];
		this.diceRolled = false;
		this.currentPlayer = PlayerType.BLUE;
	}

	public boolean canPlaceFreeColony(Player player){
		return hisTurn(player) && players.get(player.type).remainingFreeColonies >0 && ((players.get(player.type).remainingFreeColonies == (players.get(player.type).remainingFreeRoads)));
	}

	public Action placeFreeColony(Player player, Vertex vertex) throws ImpossibleActionException{
		actOnTurn(player);
		// controle
		// apres avoir place
		if (!(players.get(player.type).remainingFreeColonies == (players.get(player.type).remainingFreeRoads))){
			throw new ImpossibleActionException("Place route before!");
		}
		// colonies restants
		if (players.get(player.type).remainingFreeColonies <= 0){
			throw new ImpossibleActionException("No more free colony!");
		}
		// sommet vide
		if (vertex.colony != null){
			throw new ImpossibleActionException("Colony here!");
		}
		// pas de ville voisine
		if (vertex.oneNeighbouringVertexIsNotEmpty()){
			throw new ImpossibleActionException("Another colony too close!");
		}
		//do the action
		Colony newColony = new Colony(vertex,player);
		vertex.colony = newColony;
		players.get(player.type).colonies.add(newColony);
		players.get(player.type).remainingFreeColonies--;
		return new ActionPlaceFreeColony(player.type, vertex.name);
	}

	public boolean canPlaceColony(Player player){
		// action simple
		if (iSimpleActionOK(player)){
			return false;
		}
		// reste des colonies
		if (players.get(player.type).colonies.size() >= MAX_COLONIES){
			return false;
		}
		// ressources
		if (players.get(player.type).resources.get(ResourceType.CLAY) < 1 ||
				players.get(player.type).resources.get(ResourceType.WOOD) < 1 ||
				players.get(player.type).resources.get(ResourceType.WOOL) < 1 ||
				players.get(player.type).resources.get(ResourceType.WHEAT) < 1
				){
			return false;
		}
		// sommet disponible
		boolean availableVertex = false;
		for (Road road : players.get(player.type).roads){
			for (Vertex vertex: road.side.vertexes){
				if (vertex.colony == null && !vertex.oneNeighbouringVertexIsNotEmpty()){
					availableVertex = true;
				}
			}
		}
		return availableVertex;
	}

	public Action placeColony(Player player, Vertex vertex) throws ImpossibleActionException{
		// controle
		//action simple
		doSimpleAction(player);
		// trop de colonies
		if (players.get(player.type).colonies.size() >= MAX_COLONIES){
			throw new ImpossibleActionException("No more colony to place!");
		}
		// sommet vide
		if (vertex.colony != null){
			throw new ImpossibleActionException("Colony here!");
		}
		// pas de ville voisine
		if (vertex.oneNeighbouringVertexIsNotEmpty()){
			throw new ImpossibleActionException("Another colony too close!");
		}
		// une route arrivante
		boolean comingRoad = false;
		for (Side side: vertex.sides){
			if (side.road != null && side.road.player.type == player.type){
				comingRoad = true;
			}
		}
		if (!comingRoad){
			throw new ImpossibleActionException("No coming road!");
		}
		// les ressources
		if (players.get(player.type).resources.get(ResourceType.CLAY) < 1 ||
				players.get(player.type).resources.get(ResourceType.WOOD) < 1 ||
				players.get(player.type).resources.get(ResourceType.WOOL) < 1 ||
				players.get(player.type).resources.get(ResourceType.WHEAT) < 1){
			throw new ImpossibleActionException("Not enough resources!");
		}
		// action
		Colony newColony = new Colony(vertex,player);
		vertex.colony = newColony;
		players.get(player.type).colonies.add(newColony);
		players.get(player.type).resources.put(ResourceType.CLAY, players.get(player.type).resources.get(ResourceType.CLAY)-1);
		players.get(player.type).resources.put(ResourceType.WOOD, players.get(player.type).resources.get(ResourceType.WOOD)-1);
		players.get(player.type).resources.put(ResourceType.WOOL, players.get(player.type).resources.get(ResourceType.WOOL)-1);
		players.get(player.type).resources.put(ResourceType.WHEAT, players.get(player.type).resources.get(ResourceType.WHEAT)-1);
		return new ActionPlaceColony(player.type, vertex.name);
	}

	public boolean canPlaceFreeRoad(Player player){
		return 	hisTurn(player)&&(players.get(player.type).remainingFreeRoads>0)&&(players.get(player.type).remainingFreeColonies == players.get(player.type).remainingFreeRoads-1);
	}

	public Action placeFreeRoad(Player player,Side side) throws ImpossibleActionException{
		// controle
		actOnTurn(player);
		// apres avoir place une colonie
		if (!(players.get(player.type).remainingFreeColonies == (players.get(player.type).remainingFreeRoads)-1)){
			throw new ImpossibleActionException("Place colony before!");
		}
		// route gratuite restante
		if (players.get(player.type).remainingFreeRoads<=0){
			throw new ImpossibleActionException("No more free road!");
		}
		// pas de route
		if (side.road != null){
			throw new ImpossibleActionException("Road here!");
		}
		// une ville ou une route avoisinante
		boolean nextCityOrRoad = false;
		for (Vertex vertex: side.vertexes){
			// ville avoisinante
			if (vertex.colony != null && vertex.colony.player.type == player.type){
				nextCityOrRoad = true;
			}
		}
		if (!nextCityOrRoad){
			throw new ImpossibleActionException("No city or road next!");
		}
		// action
		Road newRoad = new Road(side,players.get(player.type));
		side.road = newRoad;
		players.get(player.type).roads.add(newRoad);
		players.get(player.type).remainingFreeRoads--;
		// changement de tour
		endFreeTurn(player);
		return new ActionPlaceFreeRoad(player.type, side.name);
	}

	public boolean canPlaceRoad(Player player){
		// action simple
		if (iSimpleActionOK(player)){
			return false;
		}
		// reste des routes
		if (players.get(player.type).roads.size() >= MAX_ROADS){
			return false;
		}
		// ressources
		if (players.get(player.type).resources.get(ResourceType.CLAY) < 1 ||
				players.get(player.type).resources.get(ResourceType.WOOD) < 1){
			return false;
		}
		// cote disponible
		boolean availableSide = false;
		// cote d'une colonie
		for (Colony colony: players.get(player.type).colonies){
			for (Side testedSide: colony.place.sides){
				if (testedSide.road == null){
					availableSide = true;
				}
			}
		}
		// cote d'une route
		for (Road road: players.get(player.type).roads){
			for (Vertex roadVertex: road.side.vertexes){
				for (Side testedSide: roadVertex.sides){
					if (testedSide.road == null){
						availableSide = true;
					}
				}
			}
		}
		return availableSide;
	}

	public Action placeRoad(Player player, Side side) throws ImpossibleActionException{
		// controle
		//action simple
		doSimpleAction(player);
		// des joues
		actAfterDice();
		// routes restantes
		if (players.get(player.type).roads.size() >= MAX_ROADS){
			throw new ImpossibleActionException("No more road!");
		}
		// resources disponibles
		if (players.get(player.type).resources.get(ResourceType.CLAY) < 1 ||
				players.get(player.type).resources.get(ResourceType.WOOD) < 1){
			throw new ImpossibleActionException("Not enough resources!");
		}
		// emplacement vide
		if (side.road != null){
			throw new ImpossibleActionException("Road here!");
		}
		//route amie menant au cote
		boolean friendlyRoad = false;
		for (Vertex vertex: side.vertexes){
			for (Side testedSide: vertex.sides){
				if (testedSide.road != null && testedSide.road.player.type == player.type){
					friendlyRoad = true;
				}
			}
		}
		if (!friendlyRoad){
			throw new ImpossibleActionException("No incoming road!");
		}
		//action
		Road newRoad = new Road(side,players.get(player.type));
		side.road = newRoad;
		players.get(player.type).roads.add(newRoad);
		players.get(player.type).resources.put(ResourceType.CLAY, players.get(player.type).resources.get(ResourceType.CLAY)-1);
		players.get(player.type).resources.put(ResourceType.WOOD, players.get(player.type).resources.get(ResourceType.WOOD)-1);
		return new ActionPlaceRoad(player.type, side.name);
	}

	public boolean canPlaceCity(Player player){
		// action simple
		if (iSimpleActionOK(player)){
			return false;
		}
		if (players.get(player.type).cities.size()>= MAX_CITIES){
			return false;
		}
		if (players.get(player.type).colonies.size()< 1){
			return false;
		}
		if (players.get(player.type).resources.get(ResourceType.WHEAT) < 2 ||
				players.get(player.type).resources.get(ResourceType.STONE) < 3){
			return false;
		}else{
			return true;
		}
	}

	public Action placeCity(Player player,Vertex vertex) throws ImpossibleActionException{
		// controle
		//action simple
		doSimpleAction(player);
		// cite restante
		if (players.get(player.type).cities.size()>= MAX_CITIES){
			throw new ImpossibleActionException("No more city!");
		}
		// pas de colonie sur place
		if (vertex.colony == null || vertex.colony instanceof City || vertex.colony.player.type != player.type){
			throw new ImpossibleActionException("No friendly colony here!");
		}
		// resources
		if (players.get(player.type).resources.get(ResourceType.WHEAT) < 2 ||
				players.get(player.type).resources.get(ResourceType.STONE) < 3){
			throw new ImpossibleActionException("Not enough resources!");
		}
		//action
		players.get(player.type).colonies.remove(vertex.colony);
		City city = new City(vertex, player);
		players.get(player.type).cities.add(city);
		vertex.colony = city;
		players.get(player.type).resources.put(ResourceType.WHEAT, players.get(player.type).resources.get(ResourceType.WHEAT)-2);
		players.get(player.type).resources.put(ResourceType.STONE, players.get(player.type).resources.get(ResourceType.STONE)-3);
		return new ActionPlaceCity(player.type, vertex.name);
	}

	public boolean canRollDice(Player player){
		return canDoRoll(player);
	}

	public Action rollDice(Player player) throws ImpossibleActionException{
		// jes des des
		Random random = new Random(System.currentTimeMillis());
		int firstRoll = random.nextInt(6)+1;
		int secondRoll = random.nextInt(6)+1;
		// action
		return doRoll(player, firstRoll, secondRoll);
	}

	public boolean canEndTurn(Player p){
		return iSimpleActionOK(p);
	}

	public Action endTurn(Player p) throws ImpossibleActionException{
		//controle
		doSimpleAction(p);
		//action
		currentPlayer = turnManager.nextPlayer();
		diceRolled = false;
		return new ActionEndTurn(p.type);
	}

	private void endFreeTurn(Player p){
		p.remainingFreeTurns--;
		currentPlayer = turnManager.nextPlayer();
	}

	public boolean canMoveRobber(Player p){
		return hisTurn(p) && noPlayerMustDrop() && !robberMoved;
	}

	public Action moveRobber(Player player, Tile tile) throws ImpossibleActionException{
		//controle
		actOnTurn(player);
		if (robberMoved){
			throw new ImpossibleActionException("Robber already moved or cannot moved!");
		}
		// déja sur la case
		if (board.robbers == tile){
			throw new ImpossibleActionException("Robber already here!");
		}
		//action
		board.robbers = tile;
		// on verifie si un joueur doit etre vole
		for (Vertex vertex: tile.vertexes){
			if (vertex.colony != null){
				for (Integer resourceAmount: vertex.colony.player.resources.values()){
					if (resourceAmount > 0){
						playerShouldBeRobbed = true;
					}
				}
			}
		}
		robberMoved = true;
		return new ActionMoveRobber(player.type, tile.name);
	}

	public boolean canDropResource(Player p){
		return players.get(p.type).mustDropResource;
	}

	public Action dropResource(Player p, Map<ResourceType,Integer> drops)throws ImpossibleActionException{
		// controle
		if (!canDropResource(p)){
			throw new ImpossibleActionException("No resource to drop!");
		}
		// controle de la quantite
		int totalResources = 0;
		int currentDrops = 0;
		for (ResourceType type: ResourceType.values()){
			int currentPossessed = players.get(p.type).resources.get(type);
			int tryToDrop = drops.get(type);
			if (currentPossessed<tryToDrop){
				throw new ImpossibleActionException("Not enough resource "+type+" to drop!");
			}
			currentDrops += tryToDrop;
		}
		if (currentDrops != p.resourcesToDrop){
			throw new ImpossibleActionException("Wrong drop quantity!");
		}
		// action
		for (ResourceType type: ResourceType.values()){
			int tryToDrop = drops.get(type);
			if (tryToDrop > 0){
				players.get(p.type).resources.put(type, players.get(p.type).resources.get(type)-tryToDrop);
			}
		}
		return new ActionDropResources(p.type, drops);
	}


	public boolean canRobPlayerAndSpecificResource(Player robber){
		return hisTurn(robber)&& playerShouldBeRobbed;
	}

	public Action robPlayerAndSpecificResource(Player robber,Player robbed, ResourceType robbedResource) throws ImpossibleActionException{
		// control
		actOnTurn(robber);
		if (!playerShouldBeRobbed){
			throw new ImpossibleActionException("Not time to rob!");
		}
		// joueur lie a la case
		boolean playerLinkedToCell = false;
		for (Vertex vertex: board.robbers.vertexes){
			if (vertex.colony != null && vertex.colony.player.type == robbed.type){
				playerLinkedToCell = true;
			}
		}
		if (!playerLinkedToCell){
			throw new ImpossibleActionException("Not robbable!");
		}
		// joueur a cette ressource
		if (players.get(robbed.type).resources.get(robbedResource) < 0){
			throw new ImpossibleActionException("Not this resource to rob!");
		}
		// action
		players.get(robbed.type).resources.put(robbedResource, players.get(robbed.type).resources.get(robbedResource)-1);
		playerShouldBeRobbed = true;
		return new ActionRobSpecific(robber.type, robbed.type, robbedResource);
	}

	public boolean canRobPlayer(Player robber){
		return canRobPlayerAndSpecificResource(robber);
	}

	public Action robPlayer(Player robber,Player robbed) throws ImpossibleActionException{
		// control
		actOnTurn(robber);
		if (!playerShouldBeRobbed){
			throw new ImpossibleActionException("Not time to rob!");
		}
		// joueur lie a la case
		boolean playerLinkedToCell = false;
		for (Vertex vertex: board.robbers.vertexes){
			if (vertex.colony != null && vertex.colony.player.type == robbed.type){
				playerLinkedToCell = true;
			}
		}
		if (!playerLinkedToCell){
			throw new ImpossibleActionException("Not robbable!");
		}
		// joueur a des ressources
		boolean hasResources = false;
		for (ResourceType type:ResourceType.values()){
			if (players.get(robbed.type).resources.get(type) > 0){
				hasResources = true;
			}
		}
		if (!hasResources){
			throw new ImpossibleActionException("No resource to rob!");
		}
		// action
		// construction de la liste des ressources
		List<ResourceType> resourceList = new ArrayList<ResourceType>();
		for (ResourceType type:ResourceType.values()){
			for (int i=0;i<players.get(robbed.type).resources.get(type);i++){
				resourceList.add(type);
			}
		}
		// recuperation d'une ressource
		ResourceType robbedResource = resourceList.get(new Random(System.currentTimeMillis()).nextInt(resourceList.size()));
		return robPlayerAndSpecificResource(robber, robbed, robbedResource);
	}

	public boolean canDoRoll(Player player){
		return hisTurn(player) && !diceRolled && players.get(player.type).remainingFreeTurns == 0;
	}

	public Action doRoll(Player player, int firstDice, int secondDice) throws ImpossibleActionException{
		//controle
		actOnTurn(player);
		//des deja joues
		if (diceRolled){
			throw new ImpossibleActionException("Dice rolled!");
		}
		// tours gratuits
		if (players.get(player.type).remainingFreeTurns > 0 ){
			throw new ImpossibleActionException("Remaining free road or colony!");
		}
		//action
		lastDiceRoll[0] = firstDice;
		lastDiceRoll[1] = secondDice;
		lastDiceTotal = lastDiceRoll[0]+lastDiceRoll[1];
		diceRolled = true;
		if (lastDiceTotal == 7){
			// cas du voleur
			robberMoved = false;
			//retrait de la moitie des resources
			for (Player robbedPlayer: players.values()){
				if (robbedPlayer.totalResource() >= 8){
					robbedPlayer.mustDropResource = true;
					robbedPlayer.resourcesToDrop = robbedPlayer.totalResource()/2;
				}
			}
		}else{
			// distribution des ressources
			for (Tile tile: board.tiles){
				if (tile.resourceScore == lastDiceTotal){
					for (Vertex vertex: tile.vertexes){
						if (vertex.colony != null){
							if (vertex.colony instanceof Colony){
								players.get(vertex.colony.player.type).resources.put(tile.type.getResource(),players.get(vertex.colony.player.type).resources.get(tile.type.getResource())+1);
							}else{
								players.get(vertex.colony.player.type).resources.put(tile.type.getResource(),players.get(vertex.colony.player.type).resources.get(tile.type.getResource())+2);
							}
						}
					}
				}
			}
		}
		return new ActionDoRoll(player.type, firstDice, secondDice);
	}

	private boolean noPlayerMustDrop(){
		boolean result = true;
		for (Player p: players.values()){
			if (p.mustDropResource){
				result = false;
			}
		}
		return result;
	}

	private boolean hisTurn(Player p){
		return  p.type == currentPlayer;
	}

	private void actOnTurn(Player p) throws ImpossibleActionException{
		if (!hisTurn(p)){
			throw new ImpossibleActionException("Not his turn!");
		}
	}

	private void actAfterDice() throws ImpossibleActionException{
		if (!diceRolled){
			throw new ImpossibleActionException("Dice not rolled!");
		}
	}

	private boolean iSimpleActionOK(Player p){
		return !playerShouldBeRobbed&&robberMoved&&hisTurn(p) && diceRolled && players.get(p.type).remainingFreeColonies == 0 && players.get(p.type).remainingFreeRoads == 0;
	}

	private void doSimpleAction(Player player) throws ImpossibleActionException{
		actOnTurn(player);
		actAfterDice();
		if (playerShouldBeRobbed){
			throw new ImpossibleActionException("Player should be robbed!");
		}
		if (!robberMoved){
			throw new ImpossibleActionException("Robber not moved!");
		}
		if (players.get(player.type).remainingFreeColonies == 0 && players.get(player.type).remainingFreeRoads == 0){
			throw new ImpossibleActionException("Remaining free colony or road!");
		}
	}

	public List<ActionType> getActionList(Player p){
		List<ActionType> result = new ArrayList<ActionType>();
		if (canPlaceFreeColony(p)){ result.add(ActionType.PLACE_FREE_COLONY);}
		if (canPlaceFreeRoad(p)){ result.add(ActionType.PLACE_FREE_ROAD);}
		if (canRollDice(p)){ result.add(ActionType.ROLL_DICE);}
		if (canEndTurn(p)){result.add(ActionType.END_TURN);}
		if (canDropResource(p)){result.add(ActionType.DROP_RESOURCES);}
		if (canMoveRobber(p)){result.add(ActionType.MOVE_ROBBER);}
		if (canPlaceColony(p)){ result.add(ActionType.PLACE_COLONY);}
		if (canPlaceCity(p)){ result.add(ActionType.PLACE_CITY);}
		if (canPlaceRoad(p)){ result.add(ActionType.PLACE_ROAD);}
		return result;
	}

	public Player getPlayer(PlayerType type){
		return players.get(type);
	}

	public Tile getTile(int tileId){
		return board.tiles.get(tileId);
	}

	public Vertex getVertex(int vertexId){
		return board.vertexes.get(vertexId);
	}

	public Side getSide(int sideId){
		return board.sides.get(sideId);
	}

	public PlayerType getCurrentPlayer(){
		return currentPlayer;
	}
	
	public List<PlayerType> getPlayers(){
		return new ArrayList<PlayerType>(players.keySet());
	}
}
