package com.bubblesama.tetrahis;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Piece {

	public PieceType type;
	public int[] center;
	public int piecePos;

	public boolean deltaAndCollideGrid(int dx, int dy, int[][] grid){
		boolean collide = false;
		//System.out.println("Piece#deltaAndCollideGrid type="+type);
		for (int[] cell: type.getPos(piecePos)){
			int newCellI = cell[0]+center[0]+dx;
			int newCellJ = cell[1]+center[1]+dy;
			if (newCellI<0||newCellI>=grid.length||newCellJ<0||newCellJ>=grid[0].length){
				collide = true;
			}else if (grid[newCellI][newCellJ] > 0){
				collide = true;
			}
		}
		if (!collide){
			center[0] += dx;
			center[1] += dy;
		}
		return collide;	
	}
	
	public List<int[]> getCells(){
		List<int[]> result = new ArrayList<int[]>();
		for (int[] cell: type.getPos(piecePos)){
			int[] newCell = {cell[0]+center[0],cell[1]+center[1]};
			result.add(newCell);
		}
		return result;
	}

	public Piece(PieceType type, int centerI, int centerJ){
		this.type = type;
		int[] center = {centerI,centerJ};
		this.center = center;
		this.piecePos = 0;
	}
	
	
	public void rotateRight(int[][] grid){
		boolean collide = false;
		for (int[] cell: type.getPos((piecePos+1)%type.getP())){
			int newCellI = cell[0]+center[0];
			int newCellJ = cell[1]+center[1];
			if (newCellI<0||newCellI>=grid.length||newCellJ<0||newCellJ>=grid[0].length){
				collide = true;
			}else if (grid[newCellI][newCellJ] == GameState.GRID_FULL){
				collide = true;
			}
		}
		if (!collide){
			this.piecePos = (piecePos+1)%type.getP();
		}
	}
	
	public void randomizePosition(){
		Random random = new Random(System.currentTimeMillis());
		this.piecePos = random.nextInt(type.getP());
	}
	
}
