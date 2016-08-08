package com.bbsama.krole.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LevelGenerator {

	// taille de base des salles
	private static int roomBaseSize = 9;
	// variation max sur la base
	private static int maxRoomSizeDelta = 3; 
	// nombre salles
	private static int roomBaseNumber = 15;
	private static int maxRoomNumberDelta = 4; 
	
	
	public static Level generateLevel(int w, int h){
		Random random = new Random();
		Level level = new Level(0,w, h);
		for (int i=0;i<w;i++){
			for (int j=0;j<h;j++){
				level.getCell(i, j).type = CellType.VOID;
			}
		}
		List<Room> addedRooms = new ArrayList<Room>();
		int roomsNumber = roomBaseNumber+random.nextInt(maxRoomNumberDelta*2)-maxRoomNumberDelta;
//		System.out.println("LevelGenerator#generateLevel roomsNumber="+roomsNumber);
		int addedRoomsNumber = 0;
		int tries = 0;
		while (addedRoomsNumber<roomsNumber && tries <100){
			tries++;
			int width = roomBaseSize+random.nextInt(maxRoomSizeDelta*2)-maxRoomSizeDelta;
			int height = roomBaseSize+random.nextInt(maxRoomSizeDelta*2)-maxRoomSizeDelta;
			int i0=random.nextInt(w-width-2)+1;
			int j0=random.nextInt(h-height-2)+1;
			// creation de la salle
			Room room = new Room(i0,j0,width,height);
			// verification de la superposition
			boolean adding = true;
			for (Room otherRoom: addedRooms){
				if (otherRoom.overlaps(room)){
					adding = false;
//					System.out.println("overlapse");
				}
			}
			if (adding){
				level.rooms.add(room);
				addedRoomsNumber++;
				addedRooms.add(room);
				for (int i=0;i<width;i++){
					for (int j=0;j<height;j++){
						if ((i0+i<w)&&(j+j0<h)){
							level.getCell(i0+i, j0+j).type = CellType.WAY;
						}
					}
				}
			}
		}
		// liste des groupes de salles: n group d'une salle au depart
		List<List<Room>> roomsGroups = new ArrayList<List<Room>>();
		for (Room room: addedRooms){
			ArrayList<Room> singleRoomGroup = new ArrayList<Room>();
			singleRoomGroup.add(room);
			roomsGroups.add(singleRoomGroup);
		}
		// groupement des salles par des couloirs
		while (roomsGroups.size()>1){
			List<Room> firstGroup = roomsGroups.get(0);
			List<Room> secondGroup = roomsGroups.get(1);
			Room roomA = firstGroup.get(firstGroup.size()-1);
			Room roomB = secondGroup.get(0);
			
			// en face sur i
			boolean easyLobby = false;
			Room roomLeft =  roomA.i<roomB.i?roomA:roomB;
			Room roomRight =  roomA.i<roomB.i?roomB:roomA;
			Room roomUp =  roomA.j<roomB.j?roomA:roomB;
			Room roomDown =  roomA.j<roomB.j?roomB:roomA;
			if ((roomA.i <= roomB.i && roomA.i+roomA.w >= roomB.i)){
				for (int lobbyJ = roomUp.j;lobbyJ<=roomDown.j;lobbyJ++){
					level.getCell(roomB.i, lobbyJ).type = CellType.WAY;
				}
				easyLobby = true;
				
			}
			if ((roomB.i <= roomA.i && roomB.i+roomB.w >= roomA.i)){
				for (int lobbyJ = roomUp.j;lobbyJ<=roomDown.j;lobbyJ++){
					level.getCell(roomA.i, lobbyJ).type = CellType.WAY;
				}
				easyLobby = true;
			}
			// en face sur j
			if ((roomA.j <= roomB.j && roomA.j+roomA.h >= roomB.j)){
				for (int lobbyI = roomLeft.i;lobbyI<=roomRight.i;lobbyI++){
					level.getCell(lobbyI,roomB.j).type = CellType.WAY;
				}
				easyLobby = true;
			}
			if ((roomB.j <= roomA.j && roomB.j+roomB.h >= roomA.j)){
				for (int lobbyI = roomLeft.i;lobbyI<=roomRight.i;lobbyI++){
					level.getCell(lobbyI,roomA.j).type = CellType.WAY;
				}
				easyLobby = true;
			}
			if (!easyLobby){
//				System.out.println("roomA: "+roomA);
//				System.out.println("roomB: "+roomB);
				int fixedJ = roomUp == roomLeft?roomUp.j+roomUp.h:roomUp.j+roomUp.h;
				int fixedI = roomUp == roomLeft?roomRight.i:roomLeft.i;
				
				// marche bien pour rightup leftDown
				for (int j=roomUp.j+roomUp.h;j<=roomDown.j;j++){
					level.getCell(fixedI,j).type = CellType.WAY;
				}
				for (int i=roomLeft.i;i<roomRight.i+1;i++){
					level.getCell(i,fixedJ).type = CellType.WAY;
				}
			}
			// fusion des groupes
			firstGroup.addAll(secondGroup);
			roomsGroups.remove(secondGroup);
		}
//		System.out.println("LevelGenerator#generateLevel roomsNumber="+roomsNumber+ " tries="+tries);
		// transformation de toutes les cases vides autour des pièces en murs
		for (int i=0;i<w;i++){
			for (int j=0;j<h;j++){
				if (level.getCell(i, j).type == CellType.WAY){
					System.out.println("LevelGenerator#generateLevel way!");
					// parcours des voisins pour passage en mur des vides
					for (int k=-1;k<2;k++){
						for (int l=-1;l<2;l++){
							if (i+k>=0 && i+k<w && j+l>=0 && j+l<h && level.getCell(i+k, j+l).type == CellType.VOID){
								System.out.println("LevelGenerator#generateLevel wall!");
								level.getCell(i+k, j+l).type = CellType.WALL;
							}
						}
					}
				}
			}
		}
		// creation ransom de door
		
		
		
		return level;
	}
	
}
