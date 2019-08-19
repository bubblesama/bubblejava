package com.bubblebob.tool.font;

import java.awt.Graphics;

import com.bubblebob.tool.ggame.Game;
import com.bubblebob.tool.ggame.GameModel;
import com.bubblebob.tool.ggame.gui.GraphicalModel;

public class BaseTest extends BaseText implements GameModel, GraphicalModel {
	
	public BaseTest(String text, BaseFont font, int x, int y, int width, int height, int interline) {
		super(text, font, x, y, width, height, interline);
	}

	public void paint(Graphics g) {
		super.setGraphics(g);
		super.paint();
	}

	public int getWidth() {
		return 500;
	}
	public int getHeight() {
		return 400;
	}

	public void update() {
		
	}
	
	public static void main(String[] args) {
		BaseTest test = new BaseTest("Heureux qui comme Ulysse a fait un beau voyage, ou comme cestui la qui conquit la Toison", BaseFont.getZoomedFont("D:\\workspace\\bubblebobtools\\tools\\assets\\alphabet.png",2), 10, 10, 10,100, 3);
		int frameDelay = 25;
		Game game = new Game("Font", test, test, null, frameDelay, null, null);
		game.launch();
	}

}
