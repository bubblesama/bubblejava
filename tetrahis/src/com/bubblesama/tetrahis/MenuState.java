package com.bubblesama.tetrahis;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.bubblesama.tetrahis.Highscore.Score;

public class MenuState extends BasicGameState{

	private TextRenderer mainTextRenderer;
	private TextRenderer highscoreTextRenderer;
	private int index = 1;

	private final static int MODE_MAIN = 1;
	private final static int MODE_HIGHSCORE = 2;
	private int mode = MODE_MAIN;

	private boolean launchGame = false;

	public MenuState(){
		// titre principal
		this.mainTextRenderer = new TextRenderer();
		// title
		TextShow title = new TextShow("tetrahis");
		title.color = Palette.COLOR_03_DARK_BLUE;
		title.x0 = 50;
		title.y0 = 4;
		title.zoom = 3;
		mainTextRenderer.shows.add(title);
		// new game
		TextShow newGame = new TextShow("new game");
		newGame.color = Palette.COLOR_07_DARK_PINK;
		newGame.x0 = 70;
		newGame.y0 = 60;
		newGame.zoom = 2;
		mainTextRenderer.shows.add(newGame);
		// highscore
		TextShow highscore = new TextShow("highscore");
		highscore.color = Palette.COLOR_16_WHITEY;
		highscore.x0 = 70;
		highscore.y0 = 90;
		highscore.zoom = 2;
		mainTextRenderer.shows.add(highscore);
		// highscore
		TextShow quit = new TextShow("quit");
		quit.color = Palette.COLOR_16_WHITEY;
		quit.x0 = 70;
		quit.y0 = 120;
		quit.zoom = 2;
		mainTextRenderer.shows.add(quit);
		// highscore texts

	}

	public void init(GameContainer gc, StateBasedGame game) throws SlickException {}


	public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
		if (mode == MODE_MAIN){
			mainTextRenderer.renderMenu(gc, g);
		}else if (mode == MODE_HIGHSCORE){
			highscoreTextRenderer.renderMenu(gc, g);
		}
	}

	public void update(GameContainer gc, StateBasedGame game, int lastUpdate) throws SlickException {
		if (launchGame){
			launchGame = false;
			game.enterState(StateManager.STATE_GAME);
		}
	}

	public void executeMenu(){
		if (mode == MODE_MAIN){
			if (index == 3){ // quit
				System.exit(0);
			}else if (index == 2){// highscore
				// chargement des highscore
				mode = MODE_HIGHSCORE;
				Highscore highscore = Highscore.load();
				this.highscoreTextRenderer = new TextRenderer();
				int currentIndex = 0;
				TextShow title = new TextShow("HIGHSCORE");
				title.color = Palette.COLOR_03_DARK_BLUE;
				title.x0 = 50;
				title.y0 = 4;
				title.zoom = 3;
				highscoreTextRenderer.shows.add(title);
				for (Score score: highscore.scores){
					TextShow scoreNameText = new SimpleTextShow(50,(currentIndex+2)*30,score.name);
					TextShow scoreValueText = new SimpleTextShow(300,(currentIndex+2)*30," "+score.value);
					scoreNameText.color = Palette.COLOR_16_WHITEY;
					scoreValueText.color = Palette.COLOR_16_WHITEY;
					scoreNameText.zoom = 2;
					scoreValueText.zoom = 2;
					highscoreTextRenderer.shows.add(scoreNameText);
					highscoreTextRenderer.shows.add(scoreValueText);
					currentIndex++;
				}
			}else if (index == 1){//new game
				// launch game
				launchGame = true;
			}
		}else{


		}
	}


	public void escapeMenu(){
		if (mode == MODE_HIGHSCORE){
			mode = MODE_MAIN;
		}else if (mode == MODE_MAIN){
			System.exit(0);
		}
	}

	public void selectNextItem(){
		//System.out.println("MenuState#selectNextItem IN");
		mainTextRenderer.shows.get(index).color = Palette.COLOR_16_WHITEY;
		index++;
		index = mainTextRenderer.shows.size() == index? 1:index;
		mainTextRenderer.shows.get(index).color = Palette.COLOR_07_DARK_PINK;
	}

	public void selectPreviousItem(){
		mainTextRenderer.shows.get(index).color = Palette.COLOR_16_WHITEY;
		index--;
		index = 0 == index? mainTextRenderer.shows.size()-1:index;
		mainTextRenderer.shows.get(index).color = Palette.COLOR_07_DARK_PINK;
	}

	public int getID() {
		return StateManager.STATE_MENU;
	}
	
	
	public void setInput(Input input) {}
	public boolean isAcceptingInput() {return true;}
	public void inputStarted() {}
	public void inputEnded() {}
	public void keyReleased(int keyCode, char keyChar) {}
	public void keyPressed(int keyCode, char keyChar) {
		//System.out.println("MenuState#keyReleased IN keyCode="+keyCode+" keyChar="+keyChar);
		if (keyCode == Input.KEY_ENTER){
			executeMenu();
		}else if (keyCode == Input.KEY_ESCAPE){
			escapeMenu();
		}else if (keyCode == Input.KEY_DOWN){
			selectNextItem();
		}else if (keyCode == Input.KEY_UP){
			selectPreviousItem();
		}
	}

}
