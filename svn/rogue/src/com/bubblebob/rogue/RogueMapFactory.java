package com.bubblebob.rogue;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class RogueMapFactory {

	private static int BLACK_INT = Color.BLACK.getRGB();

	private static RogueMap getMap(BufferedImage image){
		int width = image.getWidth();
		int height = image.getHeight();
		RogueMap result = new RogueMap(width, height);
		for (int i=0;i<width;i++){
			for (int j=0;j<height;j++){
				if (BLACK_INT == image.getRGB(i,j)){
					result.setWall(i, j, true);
				}
			}
		}
		return result;
	}

	public static RogueMap getMap(String fileName){
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
