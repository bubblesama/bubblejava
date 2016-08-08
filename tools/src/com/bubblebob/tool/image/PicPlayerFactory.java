package com.bubblebob.tool.image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PicPlayerFactory {

	private static String properties = "player.properties";

	public static SimpleAnimatedPicPlayer getPlayer(){
		SimpleAnimatedPicPlayer result = null;
		Properties conf = new Properties();
		try {
			conf.load(new FileInputStream(properties));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		String filePath = conf.getProperty("player.file");
		if (filePath == null || filePath.equals("")){
			System.out.println("No valid file path conf found (player.file)");
		}else{
			int width = -1;
			String rawWidth = conf.getProperty("player.width");
			if (rawWidth != null){
				try{
					width = Integer.parseInt(rawWidth);
				} catch (NumberFormatException nfe){
					nfe.printStackTrace();
				}
			}
			if (width <= 0){
				System.out.println("No valid width (player.width");
			}else{
				MonoSizedBundledImage pics = null;
				try{
					pics = new MonoSizedBundledImage(width, 1, filePath);
				}catch (Exception e){
					e.printStackTrace();
				}
				if (pics == null){
					System.out.println("Invalid file found (player.file)");
				}else{
					String sequence = conf.getProperty("player.sequence");
					int[] ids = null; 
					if (sequence != null){
						String[] sequenceIds = sequence.split(";");
						ids = new int[sequenceIds.length];
						boolean sequenceParsingOk = true;
						try{
							for (int i=0;i<sequenceIds.length;i++){
								ids[i] = Integer.parseInt(sequenceIds[i]);
								if (ids[i] >= sequenceIds.length){
									sequenceParsingOk = false;
								}
							}
						}catch(NumberFormatException nfe){
							nfe.printStackTrace();
							sequenceParsingOk = false;
						}
						if (!sequenceParsingOk){
							System.out.println("Invalid sequence conf (player.sequence)");
						}else{
							int delay = -1;
							String rawDelay = conf.getProperty("player.delay");
							if (rawDelay != null){
								try{
									delay = Integer.parseInt(rawDelay);
								} catch (NumberFormatException nfe){
									nfe.printStackTrace();
								}
							}
							if (delay <= 0){
								System.out.println("Invalid delay conf (player.delay)");
							}else{
								int zoom = -1;
								String rawZoom = conf.getProperty("player.zoom");
								if (rawZoom != null){
									try{
										zoom = Integer.parseInt(rawZoom);
									} catch (NumberFormatException nfe){
										nfe.printStackTrace();
									}
								}
								if (zoom <= 0){
									System.out.println("Invalid zoom conf (player.zoom)");
								}else{
									pics = pics.zoom(zoom);
									result = new SimpleAnimatedPicPlayer(pics,delay,filePath,ids);
								}
							}
						}
					}
				}
			}
		}
		return result;
	}
	
	public static void main(String[] args) {
		SimpleAnimatedPicPlayer player = PicPlayerFactory.getPlayer();
		player.run();
	}

}
