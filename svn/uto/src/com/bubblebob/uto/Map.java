package com.bubblebob.uto;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.bubblebob.tool.image.MonoSizedBundledImage;
import com.bubblebob.uto.graph.Drawable;

public class Map implements Drawable{

	// generation a partir d'un fichier: les couleurs
	private final static int LAND = new Color(255, 216, 0).getRGB();
	private final static int SEA = new Color(0, 38, 255).getRGB();
	private final static int RED = new Color(255, 0, 0).getRGB();
	private final static int GREEN = new Color(0, 255, 0).getRGB();

	// donnees de base
	public int width;
	public int height;
	public int tileSize;
	public Square[][] grid;
	public int[] redBoatPoint;
	public int[] greenBoatPoint;
	
	public List<Square> redLand;
	public List<Square> greenLand;
	
	// donnees graphiques
	private BufferedImage mapPic;
	
	private Map(int width, int height, int gWidth, BufferedImage map){
		this.width = width;
		this.height = height;
		this.tileSize = gWidth;
		this.mapPic = map;
		this.grid = new Square[width][height];
		for (int i=0;i<width;i++){
			for (int j=0;j<height;j++){
				grid[i][j] = new Square(i,j,i*tileSize,j*tileSize);
			}
		}
		this.greenLand = new ArrayList<Square>();
		this.redLand = new ArrayList<Square>();
	}

	/**
	 * generateur de carte, a partir d'une image fonctionnelle et d'une image graphique
	 */
	public static Map getMap(String mapPath, String mapPicPath, int scale){
		int width = 0;
		int height = 0;
		int gWidth = 0;
		File mapFile = new File(mapPath);
		BufferedImage mapRaw = null;
		try {
			mapRaw = ImageIO.read(mapFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		width = mapRaw.getWidth();
		height = mapRaw.getHeight();
		BufferedImage mapPic = new MonoSizedBundledImage(1, 1, mapPicPath).zoom(1).getImage(0, 0);
		gWidth = mapPic.getWidth()/width;
		mapPic = new MonoSizedBundledImage(1, 1, mapPicPath).zoom(scale).getImage(0, 0);
		Map result = new Map(width, height, gWidth, mapPic);
		int middleWidth = width/2;
		for (int i=0;i<width;i++){
			for (int j=0;j<height;j++){
				Color color = new Color(mapRaw.getRGB(i, j));
				if (color.getRGB() == LAND){
					result.grid[i][j].type = SquareType.LAND;
					if (i>=middleWidth){
						result.grid[i][j].owner = PlayerType.GREEN;
						result.greenLand.add(result.grid[i][j]);
					}else{
						result.grid[i][j].owner = PlayerType.RED;
						result.redLand.add(result.grid[i][j]);
					}
				} else if (color.getRGB() == RED){
					result.redBoatPoint = new int[2];
					result.redBoatPoint[0] = i*gWidth;
					result.redBoatPoint[1] = j*gWidth;
					System.out.println("[Map#getMap] redBoatPoint=("+i+","+j+")");
				} else if (color.getRGB() == GREEN){
					result.greenBoatPoint = new int[2];
					result.greenBoatPoint[0] = i*gWidth;
					result.greenBoatPoint[1] = j*gWidth;
					System.out.println("[Map#getMap] greenBoatPoint=("+i+","+j+")");
				}
			}
		}
		return result;
	}

	public void paint(Graphics g) {
		g.drawImage(mapPic, 0, 0, null);
	}

	public boolean collideBox(SquareType type, int x, int y){
		return collideType(type, x, y, tileSize-1, tileSize-1);
	}

	public boolean collideType(SquareType type, int x, int y, int w, int h){
		int firstX = x/tileSize;
		int lastX = (x+w)/tileSize;
		int firstY = y/tileSize;
		int lastY = (y+h)/tileSize;
		if (firstX<0 || firstY<0 || lastX >= width || lastY >= height){
			//System.out.println("[Map#collideType] collide on boundaries");
			return true;
		}
		for (int i=firstX;i<=lastX;i++){
			for (int j=firstY;j<=lastY;j++){
				if (grid[i][j].type == type){
//					System.out.println("[Map#collideType] collide on ("+i+","+j+")");
					return true;
				}
			}
		}
		return false;
	}
	
	public Square getTile(int x, int y){
		int i=x/tileSize;
		int dx = x-i*tileSize;
		if (dx > (tileSize/2)){
			i++;
		}
		int j=y/tileSize;
		int dy = y-j*tileSize;
		if (dy > (tileSize/2)){
			j++;
		}
		if ( i>=0 && i<width && j>=0 && j< height){
			return grid[i][j];
		}else{
			return null;
		}
	}
	
}
