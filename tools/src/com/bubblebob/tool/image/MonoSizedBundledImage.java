package com.bubblebob.tool.image;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

/**
 * Crée un groupe d'images a a partir d'une image unique
 * @author bubblebob
 */
public class MonoSizedBundledImage {

	private int widthNumber;
	private int heightNumber;
	private int width;
	private int height;

	public BufferedImage[][] images;

	/**
	 * Cree un "bundle" d'image a partir d'une image unique
	 * @param widthNumber: le nombre d'images en largeur
	 * @param heightNumber: le nombre d'images en hauteur
	 * @param bundleFilePath: le chemin du fichier image
	 */
	public MonoSizedBundledImage(int widthNumber, int heightNumber, String bundleFilePath ){
		this.widthNumber = widthNumber;
		this.heightNumber = heightNumber;
		this.images = new BufferedImage[widthNumber][heightNumber];
		this.initBundle(bundleFilePath);
	}

	public MonoSizedBundledImage(int widthNumber, int heightNumber){
		this.widthNumber = widthNumber;
		this.heightNumber = heightNumber;
		this.images = new BufferedImage[widthNumber][heightNumber];
	}

	public MonoSizedBundledImage(List<BufferedImage> pics){
		this.height = pics.get(0).getHeight();
		this.width = pics.get(0).getWidth();
		this.heightNumber = 1;
		this.widthNumber = pics.size();
		this.images = new BufferedImage[widthNumber][1];
		for (int i=0;i<widthNumber;i++ ){
			images[i][0] = pics.get(i);
		}
	}

	private MonoSizedBundledImage(){}

	public BufferedImage getImage(int i, int j){
		return images[i][j];
	}

	public int getWidth() {
		return widthNumber;
	}

	public void setWidth(int widthNumber) {
		this.widthNumber = widthNumber;
	}

	public int getHeight() {
		return heightNumber;
	}

	public void setHeight(int heightNumber) {
		this.heightNumber = heightNumber;
	}

	/**
	 * Fournit la largeur d'une image
	 * @return
	 */
	public int getImageWidth(){
		return width;
	}

	/**
	 * Fournit la hauteur d'une image
	 * @return
	 */
	public int getImageHeight(){
		return height;
	}

	public void initBundle(String bundleFilePath){
		//System.out.println("[MonoSizedBundledImage#initBundle] IN widthNumber="+widthNumber+" heightNumber="+heightNumber);
		BufferedImage rawBundleImage = null;
		try {
			File imageFile = new File(bundleFilePath);
			rawBundleImage = ImageIO.read(imageFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		int xSize = rawBundleImage.getWidth();
		if (xSize%widthNumber == 0){
			int ySize = rawBundleImage.getHeight();
			if (ySize%heightNumber == 0){
				width = xSize/widthNumber;
				height = ySize/heightNumber;
				for (int i=0; i<widthNumber; i++){
					for (int j=0; j<heightNumber; j++){
						BufferedImage singleImage = rawBundleImage.getSubimage(i*width, j*height, width, height);
						images[i][j] = singleImage;
					}
				}
			}
			//System.out.println("[MonoSizedBundledImage#initBundle] OUT imageWidth="+width+" imageHeight="+height);
		}else{
			//System.out.println("[MonoSizedBundledImage#initBundle] wrong size!");
		}
	}

	public MonoSizedBundledImage zoom(int scale){
		MonoSizedBundledImage result = new MonoSizedBundledImage();
		result.widthNumber = widthNumber;
		result.heightNumber = heightNumber;
		result.width = width*scale;
		result.height = height*scale;
		result.images = new BufferedImage[widthNumber][heightNumber];
		for (int i=0;i<widthNumber;i++){
			for (int j=0;j<heightNumber;j++){
				BufferedImage newPic = new BufferedImage( result.width, result.height, BufferedImage.TYPE_INT_ARGB);
				Graphics2D graphics = newPic.createGraphics();
				graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
				graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
				graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
				graphics.drawImage(images[i][j], 0, 0, result.width, result.height, null);
				graphics.dispose();
				result.images[i][j] = newPic;
			}
		}
		return result;
	}

}
