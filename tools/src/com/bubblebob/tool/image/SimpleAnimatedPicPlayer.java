package com.bubblebob.tool.image;

import java.awt.Graphics;

import com.bubblebob.tool.ggame.Game;
import com.bubblebob.tool.ggame.GameModel;
import com.bubblebob.tool.ggame.gui.GraphicalModel;

public class SimpleAnimatedPicPlayer implements GraphicalModel,GameModel {
	private Game playingGame;
	private MonoSizedBundledImage imageBundle;
	private int[] sequence;
	private int currentIndex;
	//<methodes du GraphicalModel...
	public void paint(Graphics g) {
//		g.clearRect(0, 0, getWidth(), getHeight());
		g.drawImage(imageBundle.getImage(sequence[currentIndex],0),0,0,null);
	}
	public int getWidth() {
		return imageBundle.getImageWidth();
	}
	public int getHeight() {
		return imageBundle.getImageHeight()+50;
	}
	//...methodes du GraphicalModel>
	//<methodes du modele...
	public void update() {
		currentIndex = currentIndex == sequence.length-1? 0: currentIndex+1;
	}
	//...methodes du modele>
	public SimpleAnimatedPicPlayer(MonoSizedBundledImage imageBundle, int frameDelay, String frameName, int[] sequence) {
		this.imageBundle = imageBundle;
		this.currentIndex = 0;
		this.sequence = sequence;
		playingGame = new Game("Player: "+frameName, this, this, null, frameDelay, null, null,null);
	}
	public void run(){
		playingGame.launch();
	}
	public static void main(String[] args) {
		//MonoSizedBundledImage testPics = new MonoSizedBundledImage(5, 1, "D:\\workspace\\tools\\src\\test\\image\\test-sequence.png");
		int[] sequence = {2,1,2,0};
		MonoSizedBundledImage testPics = new MonoSizedBundledImage(3, 1, "D:\\tmp\\walk.png");
		MonoSizedBundledImage zoom = testPics.zoom(3);
		SimpleAnimatedPicPlayer player = new SimpleAnimatedPicPlayer(zoom, 150, "", sequence);
		player.run();
	}
}
