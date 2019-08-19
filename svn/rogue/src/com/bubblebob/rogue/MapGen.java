package com.bubblebob.rogue;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

///**
// * Generateur de cartes
// * @author bubblebob
// *
// */
public class MapGen {
//
//	// carte proprement dite
//	public int[][] map;
//	
//	//les identifiants de chaque salle
//	private int[][] ids;
//	// les cases par identifiant
//	
//	
//	// generation: conf
//	// largeur de la plage des largeur/longueur des salles
//	private int roomWidthRange=8;
//	// largeur/longueur maximale des salles
//	private int roomMinWidth=3;
//	// surface maximale d'une salle donnee
//	private int roomMaxArea = 30;
//	// surface minimale d'une salle
//	private int roomMinArea = 20;
//	// nombre de salles
//	private int roomAmount = 3;
//	// largeur en cases
//	private int width = 40;
//	// hauteur en cases
//	private int height = 30;
//	// liste de toutes les salles
//	public List<Room> allRooms;
//	// liste des salles
//	public List<Room> rooms;
//	//wip: pseudo aléatoire
//	private int randomIndex;
//	private String seed = "mySeedisAtoughtone,toujdibwbdsbwjdhkwjhsdkjhwshdkjwhhkjdhzajkhkdjhakjazhd";
//	private byte[] hashedSeed;
//	private SecureRandom random = new SecureRandom(("toto"+System.currentTimeMillis()).getBytes());
//	private int nextRoomId = 1;
//	
//	/**
//	 * constructeur, lance la creation
//	 */
//	public MapGen() {
//		createMap();
//	}
//
//	public Floor getFloor(){
//		FloorSimple result = new FloorSimple(width, height);
//		for (Room room: rooms){
//			result.addRoom(room);
//		}
//		result.initCells();
//		return result;
//	}
//	
//	public MapGen(int roomWidthRange, int roomMinWidth, int roomMaxArea,int roomMinArea, int roomAmount, int width, int height) {
//		super();
//		this.roomWidthRange = roomWidthRange;
//		this.roomMinWidth = roomMinWidth;
//		this.roomMaxArea = roomMaxArea;
//		this.roomMinArea = roomMinArea;
//		this.roomAmount = roomAmount;
//		this.width = width;
//		this.height = height;
//		createMap();
//	}
//
//
//	/**
//	 * Algorithme de creation d'une salle
//	 */
//	private void createMap(){
//		int tries = 0;
//		allRooms = new ArrayList<Room>();
//		rooms = new ArrayList<Room>();
//		for (int i=0;i<roomAmount;i++){
//			boolean ok = false;
//			RoomSimple room = null;
//			// boucle de creation d'une salle
//			while (!ok){
//				// choix de la salle
//				int topLeftI = getNextInt(this.width-roomMinWidth-roomWidthRange);
//				int topLeftJ = getNextInt(this.height-roomMinWidth-roomWidthRange);
//				int roomWidth = roomMinWidth+getNextInt(roomWidthRange);
//				int roomHeight = roomMinWidth+getNextInt(roomWidthRange);
//				while(roomHeight*roomWidth > roomMaxArea || roomHeight*roomWidth<roomMinArea){
//					roomHeight = roomMinWidth+getNextInt(roomWidthRange);
//				}
//				room = new RoomSimple(nextRoomId,topLeftI, topLeftJ, roomWidth, roomHeight, false);
//				//tagRoom(2, room);
//				tries++;
//				// test de mise en place: superposee?
//				boolean overlapping = false;
//				for (Room otherRoom: allRooms){
//					if (overlapping(room,otherRoom)){
//						overlapping = true;
//					}
//				}
//				if (!overlapping){
//					ok = true;
//					nextRoomId++;
//				}
//			}
//			allRooms.add(room);
//			rooms.add(room);
//		}
//		// liaison des salles
//		linkAllMap();
//		//System.out.println("[MapGen#createMap] OUT");
//	}
//
//	private int getNextInt(int range){
//		return random.nextInt(range);
//	}
//
//
//	private void linkAllMap(){
//		List<List<Room>> groups = new ArrayList<List<Room>>();
//		// creation de la liste initiale: une salle par groupe
//		for (Room room: rooms){
//			List<Room> group = new ArrayList<Room>();
//			group.add(room);
//			groups.add(group);
//		}
//		// lien groupe par groupe
//		while(groups.size() > 1){
//			System.out.println("MapGen#linkAllMap linking: groups="+groups);
//			//choix de 2 groupes
//			List<Room> firstGroup = groups.get(getNextInt(groups.size()));
//			List<Room> secondGroup = groups.get(getNextInt(groups.size()));
//			while (secondGroup == firstGroup) {
//				secondGroup = groups.get(getNextInt(groups.size()));
//			}
//			// choix de deux salles
//			Room firstRoom = firstGroup.get(getNextInt(firstGroup.size()));
//			Room secondRoom = secondGroup.get(getNextInt(secondGroup.size()));
//			link(firstRoom,secondRoom,firstGroup,secondGroup,groups,nextRoomId++);
//		}
//	}
//
//
//	private void link(Room firstRoom, Room secondRoom, List<Room> firstGroup, List<Room> secondGroup, List<List<Room>> groups, int lobbyId){
//		System.out.println("MapGen#link IN 1="+firstRoom+" 2="+secondRoom+" L="+lobbyId);
//		
//		// determination des valeurs communes
//		int commonI = getMinCommonI(firstRoom,secondRoom);
//		int commonJ = getMinCommonJ(firstRoom,secondRoom);
//		List<Room> lobbies = new ArrayList<Room>();
//		
//		
//		
//		// cas de valeurs communes
//		if (commonI != -1 || commonJ != -1){
//			Room lobby = null;
//			if (commonI != -1){
//				// sur la meme colonne
//				if (firstRoom.getJ0() < secondRoom.getJ0()){
//					lobby = new RoomSimple(lobbyId,commonI,firstRoom.getJ0()+firstRoom.getHeight(),1,secondRoom.getJ0()-firstRoom.getJ0()-firstRoom.getHeight(),false);
//				}else{
//					lobby = new RoomSimple(lobbyId,commonI,secondRoom.getJ0()+secondRoom.getHeight(),1,firstRoom.getJ0()-secondRoom.getJ0()-secondRoom.getHeight(),false);
//				}
//			}else{
//				// sur la meme ligne
//				if (firstRoom.getI0() < secondRoom.getI0()){
//					lobby = new RoomSimple(lobbyId,firstRoom.getI0()+firstRoom.getWidth(),commonJ,secondRoom.getI0()-firstRoom.getI0()-firstRoom.getWidth(),1,false);
//				}else{
//					lobby = new RoomSimple(lobbyId,secondRoom.getI0()+secondRoom.getWidth(),commonJ,firstRoom.getI0()-secondRoom.getI0()-secondRoom.getWidth(),1,false);
//				}
//			}
//			// ajout du couloir à la carte
//			lobbies.add(lobby);
//			// ajout du/des couloir(s) au groupe et à la carte
//			for (Room aLobby: lobbies){
//				allRooms.add(lobby);
//				firstGroup.addAll(secondGroup);
//				groups.remove(secondGroup);
////				// test de superposition du couloir avec les salles des autres groupes, pour creer eventuellement une liaison imprevue
//				List<List<Room>> alienGroupsToRemove = new ArrayList<List<Room>>();
//				for (List<Room> alienGroup: groups){
//					if (!alienGroup.contains(firstRoom)){
//						System.out.println("MapGen#link alienGroup="+alienGroup);
//						for (Room alienRoom: alienGroup){
//							if (overlapping(aLobby, alienRoom)){
//								System.out.println("MapGen#link overlapping="+aLobby.getId()+" "+alienRoom.getId());
//								// superposition
//								firstGroup.addAll(alienGroup);
//								alienGroupsToRemove.add(alienGroup);
//							}
//						}
//					}
//				}
//				// suppression des groupes traverses par le couloir
//				for (List<Room> alienGroup: alienGroupsToRemove){
//					groups.remove(alienGroup);
//				}
//			}
//		}else{
//			// pas de valeurs communes: un coude
//			System.out.println("MapGen#linkAllMap bend needed");
//			Room lobbyBend = new RoomSimple(lobbyId,firstRoom.getI0(),secondRoom.getJ0(),1,1,false);
//			lobbies.add(lobbyBend);
//			List<Room> lobbyGroup = new ArrayList<Room>();
//			lobbyGroup.add(lobbyBend);
//			groups.add(lobbyGroup);
//			allRooms.add(lobbyBend);
//			link(firstRoom, lobbyBend, firstGroup, lobbyGroup, groups,lobbyId);
//			link(lobbyBend, secondRoom, lobbyGroup, secondGroup, groups,lobbyId);
//		}
//	}
//
//
//	private static int getMinCommonI(Room room, Room otherRoom){
//		int result = -1;
//		if (room.getI0()<=otherRoom.getI0()&&room.getI0()+room.getWidth()>=otherRoom.getI0()){
//			result = otherRoom.getI0();
//		}else if (room.getI0()>=otherRoom.getI0()&&room.getI0()<=otherRoom.getI0()+otherRoom.getWidth()){
//			result = room.getI0();
//		}
//		return result;
//	}
//
//	private static int getMinCommonJ(Room room,Room otherRoom){
//		int result = -1;
//		if (room.getJ0()<=otherRoom.getJ0()&&room.getJ0()+room.getHeight()>=otherRoom.getJ0()){
//			result = otherRoom.getJ0();
//		}else if (room.getJ0()>=otherRoom.getJ0()&&room.getJ0()<=otherRoom.getJ0()+otherRoom.getHeight()){
//			result = room.getJ0();
//		}
//		return result;
//	}
//
//
//	private static boolean overlapping(Room room, Room otherRoom){
//		boolean result = ((room.getI0()<=otherRoom.getI0()&&room.getI0()+room.getWidth()>=otherRoom.getI0())||(room.getI0()>=otherRoom.getI0()&&room.getI0()<=otherRoom.getI0()+otherRoom.getWidth()))
//				&&((room.getJ0()<=otherRoom.getJ0()&&room.getJ0()+room.getHeight()>=otherRoom.getJ0())||(room.getJ0()>=otherRoom.getJ0()&&room.getJ0()<=otherRoom.getJ0()+otherRoom.getHeight()));
//		return result;
//
//	}


<<<<<<< .mine
=======
	// generation: conf
	// largeur de la plage des largeur/longueur des salles
	private int roomWidthRange=8;
	// largeur/longueur maximale des salles
	private int roomMinWidth=3;
	// surface maximale d'une salle donnee
	private int roomMaxArea = 30;
	// surface minimale d'une salle
	private int roomMinArea = 20;
	// nombre de salles
	private int roomAmount = 3;
	// largeur en cases
	private int width = 40;
	// hauteur en cases
	private int height = 30;
	// liste de toutes les salles
	public List<Room> allRooms;
	// liste des couloirs
	public List<Room> lobbies;
	// liste des couloirs
	public List<Room> rooms;
	//wip: pseudo aléatoire
	private int randomIndex;
	private String seed = "mySeedisAtoughtone,toujdibwbdsbwjdhkwjhsdkjhwshdkjwhhkjdhzajkhkdjhakjazhd";
	private byte[] hashedSeed;
	private SecureRandom random = new SecureRandom(("toto"+System.currentTimeMillis()).getBytes());
	private int nextRoomId = 1;
	
	/**
	 * constructeur, lance la creation
	 */
	public MapGen() {
		createMap();
	}

	public Floor getFloor(){
		FloorSimple result = new FloorSimple(width, height);
		for (Room room: rooms){
			result.addRoom(room);
		}
		for (Room room: lobbies){
			result.addLobby(room);
		}
		result.initCells();
		return result;
	}
	
	public MapGen(int roomWidthRange, int roomMinWidth, int roomMaxArea,int roomMinArea, int roomAmount, int width, int height) {
		super();
		this.roomWidthRange = roomWidthRange;
		this.roomMinWidth = roomMinWidth;
		this.roomMaxArea = roomMaxArea;
		this.roomMinArea = roomMinArea;
		this.roomAmount = roomAmount;
		this.width = width;
		this.height = height;
		createMap();
	}


	/**
	 * Algorithme de creation d'une salle
	 */
	private void createMap(){
		int tries = 0;
		allRooms = new ArrayList<Room>();
		rooms = new ArrayList<Room>();
		lobbies = new ArrayList<Room>();
		
		for (int i=0;i<roomAmount;i++){
			boolean ok = false;
			RoomSimple room = null;
			// boucle de creation d'une salle
			while (!ok){
				// choix de la salle
				int topLeftI = getNextInt(this.width-roomMinWidth-roomWidthRange);
				int topLeftJ = getNextInt(this.height-roomMinWidth-roomWidthRange);
				int roomWidth = roomMinWidth+getNextInt(roomWidthRange);
				int roomHeight = roomMinWidth+getNextInt(roomWidthRange);
				while(roomHeight*roomWidth > roomMaxArea || roomHeight*roomWidth<roomMinArea){
					roomHeight = roomMinWidth+getNextInt(roomWidthRange);
				}
				room = new RoomSimple(nextRoomId,topLeftI, topLeftJ, roomWidth, roomHeight, false);
				//tagRoom(2, room);
				tries++;
				if (tries%10000 == 0){
					//					System.out.println("[MapGen#createMap] tries="+tries/10000+"0 K");
				}
				// test de mise en place: superposee?
				boolean overlapping = false;
				for (Room otherRoom: allRooms){
					if (overlapping(room,otherRoom)){
						overlapping = true;
					}
				}
				if (!overlapping){
					ok = true;
					nextRoomId++;
				}
			}
			allRooms.add(room);
			rooms.add(room);
		}
		// liaison des salles
		linkAllMap();
	}

	private int getNextInt(int range){
		return random.nextInt(range);
	}


	private void linkAllMap(){
		List<List<Room>> groups = new ArrayList<List<Room>>();
		// creation de la liste initiale: une salle par groupe
		for (Room room: rooms){
			List<Room> group = new ArrayList<Room>();
			group.add(room);
			groups.add(group);
		}
		// lien groupe par groupe
		while(groups.size() > 1){
			System.out.println("MapGen#linkAllMap linking: groups="+groups);
			//choix de 2 groupes
			List<Room> firstGroup = groups.get(getNextInt(groups.size()));
			List<Room> secondGroup = groups.get(getNextInt(groups.size()));
			while (secondGroup == firstGroup) {
				secondGroup = groups.get(getNextInt(groups.size()));
			}
			// choix de deux salles
			Room firstRoom = firstGroup.get(getNextInt(firstGroup.size()));
			Room secondRoom = secondGroup.get(getNextInt(secondGroup.size()));
			link(firstRoom,secondRoom,firstGroup,secondGroup,groups,nextRoomId++);
		}
	}


	private void link(Room firstRoom, Room secondRoom, List<Room> firstGroup, List<Room> secondGroup, List<List<Room>> groups, int lobbyId){
		System.out.println("MapGen#link IN 1="+firstRoom+" 2="+secondRoom+" L="+lobbyId);
		
		// determination des valeurs communes
		int commonI = getMinCommonI(firstRoom,secondRoom);
		int commonJ = getMinCommonJ(firstRoom,secondRoom);
		List<Room> lobbies = new ArrayList<Room>();
		// cas de valeurs communes
		if (commonI != -1 || commonJ != -1){
			Room lobby = null;
			if (commonI != -1){
				// sur la meme colonne
				if (firstRoom.getJ0() < secondRoom.getJ0()){
					lobby = new RoomSimple(lobbyId,commonI,firstRoom.getJ0()+firstRoom.getHeight(),1,secondRoom.getJ0()-firstRoom.getJ0()-firstRoom.getHeight(),false);
				}else{
					lobby = new RoomSimple(lobbyId,commonI,secondRoom.getJ0()+secondRoom.getHeight(),1,firstRoom.getJ0()-secondRoom.getJ0()-secondRoom.getHeight(),false);
				}
			}else{
				// sur la meme ligne
				if (firstRoom.getI0() < secondRoom.getI0()){
					lobby = new RoomSimple(lobbyId,firstRoom.getI0()+firstRoom.getWidth(),commonJ,secondRoom.getI0()-firstRoom.getI0()-firstRoom.getWidth(),1,false);
				}else{
					lobby = new RoomSimple(lobbyId,secondRoom.getI0()+secondRoom.getWidth(),commonJ,firstRoom.getI0()-secondRoom.getI0()-secondRoom.getWidth(),1,false);
				}
			}
			// ajout du couloir à la carte
			lobbies.add(lobby);
			// ajout du/des couloir(s) au groupe et à la carte
			for (Room aLobby: lobbies){
				this.lobbies.add(aLobby);
				allRooms.add(lobby);
				firstGroup.addAll(secondGroup);
				groups.remove(secondGroup);
//				// test de superposition du couloir avec les salles des autres groupes, pour creer eventuellement une liaison imprevue
				List<List<Room>> alienGroupsToRemove = new ArrayList<List<Room>>();
				for (List<Room> alienGroup: groups){
					if (!alienGroup.contains(firstRoom)){
						System.out.println("MapGen#link alienGroup="+alienGroup);
						for (Room alienRoom: alienGroup){
							if (overlapping(aLobby, alienRoom)){
								System.out.println("MapGen#link overlapping="+aLobby.getId()+" "+alienRoom.getId());
								// superposition
								firstGroup.addAll(alienGroup);
								alienGroupsToRemove.add(alienGroup);
							}
						}
					}
				}
				// suppression des groupes traverses par le couloir
				for (List<Room> alienGroup: alienGroupsToRemove){
					groups.remove(alienGroup);
				}
			}
		}else{
			// pas de valeurs communes: un coude
			System.out.println("MapGen#linkAllMap bend needed");
			Room lobbyBend = new RoomSimple(lobbyId,firstRoom.getI0(),secondRoom.getJ0(),1,1,false);
			lobbies.add(lobbyBend);
			List<Room> lobbyGroup = new ArrayList<Room>();
			lobbyGroup.add(lobbyBend);
			groups.add(lobbyGroup);
			allRooms.add(lobbyBend);
			this.lobbies.add(lobbyBend);
			link(firstRoom, lobbyBend, firstGroup, lobbyGroup, groups,lobbyId);
			link(lobbyBend, secondRoom, lobbyGroup, secondGroup, groups,lobbyId);
		}
	}


	private static int getMinCommonI(Room room, Room otherRoom){
		int result = -1;
		if (room.getI0()<=otherRoom.getI0()&&room.getI0()+room.getWidth()>=otherRoom.getI0()){
			result = otherRoom.getI0();
		}else if (room.getI0()>=otherRoom.getI0()&&room.getI0()<=otherRoom.getI0()+otherRoom.getWidth()){
			result = room.getI0();
		}
		return result;
	}

	private static int getMinCommonJ(Room room,Room otherRoom){
		int result = -1;
		if (room.getJ0()<=otherRoom.getJ0()&&room.getJ0()+room.getHeight()>=otherRoom.getJ0()){
			result = otherRoom.getJ0();
		}else if (room.getJ0()>=otherRoom.getJ0()&&room.getJ0()<=otherRoom.getJ0()+otherRoom.getHeight()){
			result = room.getJ0();
		}
		return result;
	}


	private static boolean overlapping(Room room, Room otherRoom){
		boolean result = ((room.getI0()<=otherRoom.getI0()&&room.getI0()+room.getWidth()>=otherRoom.getI0())||(room.getI0()>=otherRoom.getI0()&&room.getI0()<=otherRoom.getI0()+otherRoom.getWidth()))
				&&((room.getJ0()<=otherRoom.getJ0()&&room.getJ0()+room.getHeight()>=otherRoom.getJ0())||(room.getJ0()>=otherRoom.getJ0()&&room.getJ0()<=otherRoom.getJ0()+otherRoom.getHeight()));
		return result;

	}


>>>>>>> .r84
}
