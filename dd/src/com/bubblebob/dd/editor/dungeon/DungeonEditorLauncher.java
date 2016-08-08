package com.bubblebob.dd.editor.dungeon;

import com.bubblebob.dd.DdConstants;
import com.bubblebob.dd.model.dungeon.map.DungeonMap;
import com.bubblebob.tool.ggame.Game;
import com.bubblebob.tool.image.MonoSizedBundledImage;

public class DungeonEditorLauncher{
	
	public static int FRAME_DELAY = 200;
	
	public static void launchGame(){
		MonoSizedBundledImage tileSet = new MonoSizedBundledImage(12, 7, "assets/pics/dd-dungeon.png");
		DungeonMap blankMap = new DungeonMap("blank",11, 11);
		DungeonEditorModel model = new DungeonEditorModel(blankMap,tileSet.getImageWidth(), tileSet.getImageHeight());
		DungeonEditorCenteredGraphicalAdapter graphicalAdapter = new DungeonEditorCenteredGraphicalAdapter(model, tileSet, DdConstants.EDITOR_VIEW_WINDOW_SIZE_WIDTH, DdConstants.EDITOR_VIEW_WINDOW_SIZE_HEIGHT);
		DungeonEditorController controller = new DungeonEditorController(model);
		
		DungeonEditorControlPanel buttonsPanel = new DungeonEditorControlPanel(controller);
		
		Game game = new Game("DD - Editor", model, graphicalAdapter, buttonsPanel, FRAME_DELAY, new DungeonEditorMouseListener(model), null,new DungeonEditorKeyListener(controller));
		game.launch();
	}
	
	public static void main(String[] args) {
		launchGame();
	}

}
