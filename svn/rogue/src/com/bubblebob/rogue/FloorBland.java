package com.bubblebob.rogue;

public class FloorBland implements Floor{

	private Cell[][] cells;
	
	public FloorBland(int w, int h){
		this.cells = new Cell[w][h];
		for (int i=0;i<w;i++){
			for (int j=0;j<h;j++){
				this.cells[i][j]=new Cell(i,j);
			}
		}
		for (int i=0;i<w;i++){
			this.cells[i][0].type = CellType.Wall;
			this.cells[i][h-1].type = CellType.Wall;
		}
		for (int j=0;j<h;j++){
			this.cells[0][j].type = CellType.Wall;
			this.cells[w-1][j].type = CellType.Wall;
		}
	}
	
	public Cell[][] getCells() {
		return cells;
	}

	public int getWidth() {
		return cells.length;
	}

	public int getHeight() {
		return cells[0].length;
	}

}
