package com.bubblebob.rogue;


public class FloorSimple implements Floor{

	public Cell[][] getCells() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getHeight() {
		// TODO Auto-generated method stub
		return 0;
	}
	
//	private List<Room> allRooms;
//	private List<Room> lobbies;
//	private List<Room> rooms;
//	private int w;
//	private int h;
//	private CellType[][] cells;
//	
//	public FloorSimple(int w, int h) {
//		super();
//		this.allRooms = new ArrayList<Room>();
//		this.lobbies = new ArrayList<Room>();
//		this.rooms = new ArrayList<Room>();
//		this.w = w;
//		this.h = h;
//		this.cells = new CellType[w][h];
//		for (int i=0;i<w;i++){
//			for (int j=0;j<h;j++){
//				this.cells[i][j] = CellType.Void;
//			}
//		}
//	}
//	
//	public void initCells(){
//		for(Room room: getRooms()){
//			for (int i=-1;i<room.getWidth()+1;i++){
//				if (i+room.getI0()>=0 && i+room.getI0()<this.cells.length){
//					if (room.getJ0()-1>=0){
//						this.cells[i+room.getI0()][room.getJ0()-1] = CellType.Wall;
//					}
//					if (room.getJ0()+room.getHeight()<this.cells[0].length){
//						this.cells[i+room.getI0()][room.getJ0()+room.getHeight()] = CellType.Wall;
//					}
//				}
//			}
//			for (int j=-1;j<room.getHeight()+1;j++){
//				if (room.getJ0()+j>=0 && room.getJ0()+j<this.cells[0].length){
//					if (room.getI0()-1>=0){
//						this.cells[room.getI0()-1][room.getJ0()+j] = CellType.Wall;
//					}
//					if (room.getI0()+room.getWidth()<this.cells.length){
//						this.cells[room.getI0()+room.getWidth()][room.getJ0()+j] = CellType.Wall;
//					}
//				}
//			}
//		}
//		for(Room room: getRooms()){
//			for (int i=0;i<room.getWidth();i++){
//				for (int j=0;j<room.getHeight();j++){
//					this.cells[room.getI0()+i][room.getJ0()+j] = CellType.Floor;
//				}
//			}
//		}
//		for (Room lobby: getLobbies()){
//			// pour eviter des portes dans tout un mur
//			boolean previousIsDoor = false;
//			// gestion d'un couloir de 2 de longs: deux portes
//			boolean shortLobby = false;
//			if (lobby.getWidth()== 2 || lobby.getHeight() == 2){
//				shortLobby = true;
//			}
//			for (int i=0;i<lobby.getWidth();i++){
//				for (int j=0;j<lobby.getHeight();j++){
//					if (this.cells[lobby.getI0()+i][lobby.getJ0()+j] == CellType.Wall){
//						if (!previousIsDoor||shortLobby){
//							this.cells[lobby.getI0()+i][lobby.getJ0()+j] = CellType.Door;
//							previousIsDoor = true;
//						}
//					}else{
//						if (this.cells[lobby.getI0()+i][lobby.getJ0()+j] != CellType.Door){
//							this.cells[lobby.getI0()+i][lobby.getJ0()+j] = CellType.Floor;	
//						}
//						previousIsDoor = false;
//					}
//				}
//			}
//			
//			
//			
//		}
//	}
//	
//	
//	public List<Room> getAllRooms() {
//		return allRooms;
//	}
//
//	public boolean canWalk(int i, int j) {
//		return false;
//	}
//
//	public int getWidth() {
//		return w;
//	}
//
//	public int getHeight() {
//		return h;
//	}
//	
//	public void addRoom(Room r){
//		allRooms.add(r);
//		rooms.add(r);
//	}
//	
//	public void addLobby(Room r){
//		allRooms.add(r);
//		lobbies.add(r);
//	}
//	
//	public List<Room> getLobbies() {
//		return lobbies;
//	}
//
//	public List<Room> getRooms() {
//		return rooms;
//	}
//
//	public CellType[][] getCells() {
//		return cells;
//	}

}
