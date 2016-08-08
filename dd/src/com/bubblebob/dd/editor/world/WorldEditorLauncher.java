package com.bubblebob.dd.editor.world;

import com.bubblebob.dd.DdConstants;
import com.bubblebob.dd.model.world.map.WorldMap;
import com.bubblebob.tool.ggame.Game;
import com.bubblebob.tool.image.MonoSizedBundledImage;

public class WorldEditorLauncher{
	
	public static int FRAME_DELAY = 200;
	
	public static void launchGame(){
		MonoSizedBundledImage tileSet = new MonoSizedBundledImage(10, 4, "assets\\pics\\dd-world-small.png");
		WorldMap blankMap = WorldMap.getDefaultMap();
		WorldEditorModel model = new WorldEditorModel(blankMap,tileSet.getImageWidth(), tileSet.getImageHeight());
		WorldEditorCenteredGraphicalAdapter graphicalAdapter = new WorldEditorCenteredGraphicalAdapter(model, tileSet, DdConstants.EDITOR_VIEW_WINDOW_SIZE_WIDTH, DdConstants.EDITOR_VIEW_WINDOW_SIZE_HEIGHT);
		WorldEditorController controller = new WorldEditorController(model);
		
		WorldEditorControlPanel buttonsPanel = new WorldEditorControlPanel(controller);
		
		Game game = new Game("DD - Editor", model, graphicalAdapter, buttonsPanel, FRAME_DELAY, new WorldEditorMouseListener(model), null,new WorldEditorKeyListener(controller));
		game.launch();
	}
	
	public static void main(String[] args) {
		launchGame();
	}

}
