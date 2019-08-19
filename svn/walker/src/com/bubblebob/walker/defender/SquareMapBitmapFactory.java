package com.bubblebob.walker.defender;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SquareMapBitmapFactory{

	private static int BLACK_INT = Color.BLACK.getRGB();

	private static boolean[][] getMap(BufferedImage image){
		int width = image.getWidth();
		int height = image.getHeight();
		boolean[][] result = new boolean[width][height];
		for (int i=0;i<width;i++){
			for (int j=0;j<height;j++){
//				result[i][height-j-1] = false;
				result[i][j] = false;
				if (BLACK_INT == image.getRGB(i,j)){
//					result[i][height-j-1] = true;
					result[i][j] = true;
				}
			}
		}
		return result;
	}

	public static boolean[][] getMap(String fileName){
		try {
			File imageFile = new File(fileName);
			BufferedImage image = ImageIO.read(imageFile);
			return getMap(image);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
