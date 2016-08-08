package com.bubblebob.tool.image;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

/**
 * Gere un groupe d'image detaille dans un fichier de la forme
 * #commentaire1
 * nom_image1=chemin_image1
 * nom_image2=chemin_image2
 * #commentaire2
 * nom_image3=chemin_image3
 * 
 * @author bubblebob
 *
 */
public class ImageBundle {
	
	private Map<String,BufferedImage> images;
	
	public ImageBundle(){
		this.images = new HashMap<String,BufferedImage>();
	}
	
	/**
	 * Cree un ImageBundle a partir d'un fichier ".properties" disponible dans le classpath
	 * @param imageBundleFilePath
	 * @return
	 */
	public static ImageBundle getImageBundleFromClassPath(String imageBundleFileName){
		ImageBundle result = new ImageBundle();
		ResourceBundle imageBundleProperties = ResourceBundle.getBundle(imageBundleFileName);
		Enumeration<String> imageNames = imageBundleProperties.getKeys();
		
		while (imageNames.hasMoreElements()){
			String imageName = imageNames.nextElement();
			String imageFilePath = imageBundleProperties.getString(imageName);
			result.loadImage(imageName, imageFilePath);
		}
		return result;
	}
	
	/**
	 * Cree un ImageBundle a partir du chemin absolu d'un fichier de propriété d'images
	 * @param imageBundleFileName
	 * @return
	 */
	public static ImageBundle getImageBundleFromFile(String imageBundleFilePath){
		ImageBundle result = new ImageBundle();
		try {
			BufferedReader imagesFileReader = new BufferedReader(new InputStreamReader(new FileInputStream(imageBundleFilePath)));
			boolean continueLineReading = true;	
			while (continueLineReading){
				String imageConfLine = imagesFileReader.readLine();
				if (imageConfLine == null){
					continueLineReading = false;
				}else{
					if (!imageConfLine.equals("") && !imageConfLine.startsWith("#") ){
						String[] imageConf = imageConfLine.split("=");
						if (imageConf.length == 2){
							String imageName = imageConf[0];
							String imageFilePath = imageConf[1];
							result.loadImage(imageName, imageFilePath);
						}
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public void loadImage(String name, String filePath){
		try {
			File imageFile = new File(filePath);
			BufferedImage image = ImageIO.read(imageFile);
			images.put(name,image);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Image getImage(String name){
		Image result = images.get(name);
		if (result == null){
			
		}
		return result;
	}
	
}
