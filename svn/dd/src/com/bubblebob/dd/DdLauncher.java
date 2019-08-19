package com.bubblebob.dd;

import com.bubblebob.dd.control.DungeonController;
import com.bubblebob.dd.control.WorldController;
import com.bubblebob.dd.gui.DdCenteredGraphicalAdapter;
import com.bubblebob.dd.gui.DdKeyListener;
import com.bubblebob.dd.gui.DdMouseListener;
import com.bubblebob.dd.model.DdGameModel;
import com.bubblebob.dd.model.dungeon.DungeonModel;
import com.bubblebob.dd.model.world.WorldModel;
import com.bubblebob.dd.model.world.WorldPlayer;
import com.bubblebob.dd.model.world.map.WorldMapFactory;
import com.bubblebob.tool.ggame.Game;
import com.bubblebob.tool.image.MonoSizedBundledImage;

public class DdLauncher {
	
	public static int FRAME_DELAY = 35;
	
	public static void main(String[] args){
		launchGame();
	}
	
	public static void launchGame(){
//		MonoSizedBundledImage dungeonTileSet = new MonoSizedBundledImage(12, 7, "D:\\workspace\\dd\\assets\\dd-dungeon-big.png");
		MonoSizedBundledImage dungeonTileSet = new MonoSizedBundledImage(12, 7, "assets/pics/dd-dungeon-big.png");
//		MonoSizedBundledImage worldTileSet = new MonoSizedBundledImage(10, 4, "D:\\workspace\\dd\\assets\\dd-world-big.png");
		MonoSizedBundledImage worldTileSet = new MonoSizedBundledImage(10, 4, "assets/pics/dd-world-big.png");
		DungeonModel dungeon = new DungeonModel(dungeonTileSet.getImageWidth(), dungeonTileSet.getImageHeight());
		WorldModel world = new WorldModel(WorldMapFactory.loadTextMap("aa"),WorldPlayer.getDefaultPlayer());
		DdGameModel gameModel = new DdGameModel(world, dungeon);
		DdCenteredGraphicalAdapter graphicalAdapter = new DdCenteredGraphicalAdapter(gameModel, dungeonTileSet, worldTileSet, DdConstants.VIEW_WINDOW_SIZE_WIDTH, DdConstants.VIEW_WINDOW_SIZE_HEIGHT);
		DungeonController dungeonController = new DungeonController(gameModel);
		WorldController worldController = new WorldController(gameModel);
		Game game = new Game("DD", gameModel, graphicalAdapter, null, FRAME_DELAY, new DdMouseListener(dungeon,graphicalAdapter), null,new DdKeyListener(worldController,dungeonController,gameModel));
		gameModel.setGame(game);
		game.launch();
	}

}
