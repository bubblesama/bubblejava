package com.bubblebob.dd.open;

import com.bubblebob.tool.ggame.Game;
import com.bubblebob.tool.image.MonoSizedBundledImage;

/**
 * Interface generant une carte ouverte et l'affichant
 * @author Bubblebob
 *
 */
public class OpenLauncher{
	
	public static int FRAME_DELAY = 1000;
	
	public static void main(String[] args) {
		launchGame();
	}
	
	public static void launchGame(){
		MonoSizedBundledImage openTileSet = new MonoSizedBundledImage(5, 1, "D:\\workspace\\DOING-dd\\assets\\pics\\dd-open.png");
		MapBitsMap bitmap = MapBitsMap.getRandomMap(18, 18);
		OpenBitMapAdapter graphicalAdapter = new OpenBitMapAdapter(openTileSet, bitmap);
		Game game = new Game("3-Way-out-per-tile maze", bitmap, graphicalAdapter, null, FRAME_DELAY,null,null);
		game.launch();
	}

}
