package com.bubblesama.tetrahis;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;

public class PieceType {

	private static Map<String,PieceType> typesByName;
	
	private String name;
	// le nombre de positions
	private int p;
	private List<List<int[]>> positions;
	public int colorId;
	private static int BLACK_INT = Color.BLACK.getRGB();
	
	private PieceType(String name, int positions,int colorId) {
		super();
		this.name = name;
		this.colorId = colorId;
		this.p = positions;
		this.positions = new ArrayList<List<int[]>>();
	}

	public String getName() {
		return name;
	}

	public int getP() {
		return p;
	}

	public List<int[]> getPos(int index){
		return positions.get(index);
	}	

	private static PieceType generateType(String name, String piecePath, int colorId){
		PieceType result = null;
		try {
			File pieceFile = new File(piecePath);
			BufferedImage image = ImageIO.read(pieceFile);
			int width = image.getWidth();
			int height = image.getHeight();
			int p = width/height;
			//System.out.println("PieceType#generatePiece p="+p);
			result = new PieceType(name, p,colorId);
			for (int index=0;index<p;index++){
				List<int[]> position = new ArrayList<int[]>();
				//System.out.println("PieceType#generatePiece position="+index);
				for (int i=0;i<height;i++){
					for (int j=0;j<height;j++){
						//System.out.println("PieceType#generatePiece scan="+(i+index*height)+" "+j);
						if (BLACK_INT == image.getRGB(i+index*height,j)){
							int[] point = {i,j};
							position.add(point);
						}
					}
				}
				result.positions.add(position);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}


	public static void main(String[] args) {
		PieceType type = generateType("L", "assets/pieceL.png",7);
		for (List<int[]> position: type.positions){
			System.out.println("PieceType#main position");
			for (int[] piece: position){
				System.out.println("PieceType#main piece: "+piece[0]+" "+piece[1]);
			}
		}
	}

	public static void initPieces(){
		typesByName = new HashMap<String,PieceType>();
		typesByName.put("L", generateType("L", "assets/pieceL.png",7));
		typesByName.put("O", generateType("O", "assets/pieceO.png",8));
		typesByName.put("I", generateType("I", "assets/pieceI.png",9));
		typesByName.put("S", generateType("S", "assets/pieceS.png",10));
		typesByName.put("Z", generateType("Z", "assets/pieceZ.png",11));
		typesByName.put("J", generateType("J", "assets/pieceJ.png",12));
		typesByName.put("T", generateType("T", "assets/pieceT.png",13));
	}
	
	
	public static PieceType getRandomType(){
		initPieces();
		Random random = new Random(System.currentTimeMillis());
		int randomPieceIndex = random.nextInt(typesByName.size());
		return (PieceType)typesByName.get(typesByName.keySet().toArray()[randomPieceIndex]);
	}

	public static PieceType getTypeByName(String name){
		initPieces();
		return typesByName.get(name);
	}
	
}
