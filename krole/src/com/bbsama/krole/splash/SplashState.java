package com.bbsama.krole.splash;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.bbsama.krole.KrolLauncher;
import com.bubblebob.tool.font.SlickFont;

public class SplashState extends BasicGameState{
	
	private List<TimedText> texts;
	private int currentTextIndex = 0;
	private long startCurrentText = 0;
	
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		this.texts = new ArrayList<TimedText>();
		SlickFont font = SlickFont.getFont("assets/alphabet_white.png",2);
		this.texts.add(new TimedText("KROLE", font, 10, 10, 30, 5, 2,300));
		this.texts.add(new TimedText("by bb", font, 10, 10, 30, 5, 2,300));
		startCurrentText = System.currentTimeMillis();
	}

	public void render(GameContainer container, StateBasedGame game, Graphics graphics) throws SlickException {
		graphics.setColor(Color.green);
		texts.get(currentTextIndex).setGraphics(graphics);
		texts.get(currentTextIndex).paint();
	}

	public void update(GameContainer container, StateBasedGame game, int lastUpdate) throws SlickException {
		long currentDuration = System.currentTimeMillis()-startCurrentText;
		if (currentDuration>texts.get(currentTextIndex).getDuration()){
			//changement de texte
			if (currentTextIndex < texts.size()-1){
				currentTextIndex++;
				startCurrentText = System.currentTimeMillis();
			}else{
				game.enterState(KrolLauncher.STATE_GAME);
			}
		}
	}

	public int getID() {return KrolLauncher.STATE_SPLASH;}

}
