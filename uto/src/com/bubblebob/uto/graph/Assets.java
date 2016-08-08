package com.bubblebob.uto.graph;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.bubblebob.tool.image.MonoSizedBundledImage;
import com.bubblebob.uto.BoatType;
import com.bubblebob.uto.BuildingType;
import com.bubblebob.uto.BuyableType;
import com.bubblebob.uto.PlayerType;

public class Assets {

	private int scale;
	private MonoSizedBundledImage sprites;
	private MonoSizedBundledImage maps;
	private MonoSizedBundledImage numbersFont;

	private static String picSourceFolder = "assets/";
	
	
	public Assets(int gScale){
		this.scale = gScale;
		sprites = new MonoSizedBundledImage(11, 4, picSourceFolder+"sprites.png").zoom(gScale);
		maps = new MonoSizedBundledImage(1, 1, picSourceFolder+"map.png").zoom(gScale);
		numbersFont = new MonoSizedBundledImage(10, 1, picSourceFolder+"numbers_white.png").zoom(gScale);
	}

	public BufferedImage getMap(){
		return maps.getImage(0,0);
	}

	public BufferedImage getBuilding(BuildingType building){
		switch (building) {
		case FORT:
			return sprites.getImage(0, 0);
		case FACTORY:
			return sprites.getImage(1, 0);
		case FARM:
			return sprites.getImage(2, 0);
		case SCHOOL:
			return sprites.getImage(0, 1);
		case HOSPITAL:
			return sprites.getImage(1, 1);
		case HOUSE:
			return sprites.getImage(2, 1);
		case REBEL:
			return sprites.getImage(0, 2);
		default:
			return null;
		}
	}


	public BufferedImage getCursor(PlayerType playerType){
		switch (playerType) {
		case GREEN:
			return sprites.getImage(1, 3);
		case RED:
			return sprites.getImage(0, 3);
		default:
			return null;
		}
	}

	public BufferedImage getBoat(PlayerType playerType,BoatType boatType, int state){
		switch (boatType) {
		case FISHING:
			return sprites.getImage(playerType == PlayerType.RED?6:7, state);
		case PATROL:
			return sprites.getImage(playerType == PlayerType.RED?4:5,state);
		case PIRATE:
			return sprites.getImage(8, state);
		default:
			return null;
		}
	}

	public BufferedImage getNumber(int number){
		return numbersFont.getImage(number, 0);
	}

	public BufferedImage getFish(boolean otherFish){
		return sprites.getImage(3, otherFish?0:1);
	}
	
	public BufferedImage getRainCloud(int picIndex,boolean firstPart){
		return sprites.getImage(9+(firstPart?0:1), picIndex);
	}

	public int getScale(){
		return scale;
	}

	public void paintNumbers(Graphics g,int number, int size, boolean showLeftZeros, int x0, int y0){
		int keep = number;
		int pow = 1;
		for (int i=0;i<size;i++){
			pow *= 10;
		}
		int dx = 6;
		int x = x0;
		while (pow >=1){
			if (showLeftZeros || number>=pow || (number ==0 && pow==1)){
				int currentFigure = keep/pow;
				keep -= (keep/pow)*pow;
				g.drawImage(getNumber(currentFigure), x*scale, y0*scale, null);
			}
			x+= dx;
			pow /= 10;
		}
	}

	public BufferedImage getBuyable(BuyableType type, PlayerType player){
		switch (type) {
		case FORT:
			return sprites.getImage(0, 0);
		case FACTORY:
			return sprites.getImage(1, 0);
		case FARM:
			return sprites.getImage(2, 0);
		case SCHOOL:
			return sprites.getImage(0, 1);
		case HOSPITAL:
			return sprites.getImage(1, 1);
		case HOUSE:
			return sprites.getImage(2, 1);
		case REBEL:
			return sprites.getImage(0, 2);
		case FISHING:
			return sprites.getImage(6+(player == PlayerType.RED?0:1), 0);
		case PATROL:
			return sprites.getImage(4+(player == PlayerType.RED?0:1), 0);
		default:
			return null;
		}
	}
	
}
