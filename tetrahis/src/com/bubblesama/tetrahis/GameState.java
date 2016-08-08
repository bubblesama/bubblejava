package com.bubblesama.tetrahis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class GameState extends BasicGameState{

	private static final int width = 10;
	private static final int height = 22;
	private int[][] grid;
	public static final int GRID_EMPTY = -1;
	public static final int GRID_FULL = 3;

	private static final int RENDER_CELL_SIZE = 20;
	private static final int RENDER_MARGIN_LEFT = 13;
	private static final int RENDER_MARGIN_HIGH = 3;
	private boolean blinkOff = false;
	private long lastBlink = System.currentTimeMillis();

	private TextRenderer linesTextRenderer;
	private TextRenderer gameOverRenderer;
	private TextShow linesText;

	private Piece currentPiece;
	private Piece nextPiece;

	private long lastDown = System.currentTimeMillis();
	//	private long lastRight = System.currentTimeMillis();
	//	private long lastLeft = System.currentTimeMillis();
	private long lastGhost = System.currentTimeMillis();
	private long startDeleting = 0;
	private List<Integer> linesToDelete = new ArrayList<Integer>();
	private Set<Integer> ghosts;
	private Set<Integer> done;

	private static final int STATE_PLAYING = 0;
	private static final int STATE_DELETING_LINE = 1;
	private static final int STATE_GAME_OVER = 3;
	private int currentState = STATE_PLAYING;

	private int lines = 0;

	Synthesizer synth;

	public void init(GameContainer gc, StateBasedGame game) throws SlickException {
		// affichage
		this.linesTextRenderer = new TextRenderer();
		this.linesText = new SimpleTextShow(420, 30, ""+lines);
		linesText.zoom = 2;
		linesText.color = Palette.COLOR_03_DARK_BLUE;
		this.linesTextRenderer.shows.add(linesText);
		this.gameOverRenderer = new TextRenderer();
		TextShow gameOverText = new SimpleTextShow(270, 500, "game over");
		gameOverText.zoom = 2;
		gameOverText.color = Palette.COLOR_07_DARK_PINK;
		this.gameOverRenderer.shows.add(gameOverText);
		// son
		try {
			synth = MidiSystem.getSynthesizer();
			synth.loadInstrument(synth.getDefaultSoundbank().getInstruments()[90]);
		} catch (MidiUnavailableException e) {
			e.printStackTrace();
		}
		// jeu
		this.ghosts = new TreeSet<Integer>();
		this.done = new TreeSet<Integer>();
		this.grid = new int[width][height];
		for (int i=0;i<width;i++){
			for (int j=0;j<height;j++){
				grid[i][j] = GRID_EMPTY;
			}
		}
		loadNextPiece();
	}

	public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
		g.setColor(Palette.COLOR_04_DARK_GRAY);
		// grille
		for (int i=0;i<width;i++){
			for (int j=0;j<height;j++){
				if (grid[i][j] == GRID_EMPTY){
					g.fillRect((i+RENDER_MARGIN_LEFT)*RENDER_CELL_SIZE, (j+RENDER_MARGIN_HIGH)*RENDER_CELL_SIZE, RENDER_CELL_SIZE, RENDER_CELL_SIZE);
				}
			}
		}
		//g.setColor(Palette.COLOR_03_DARK_BLUE);
		for (int i=0;i<width;i++){
			for (int j=0;j<height;j++){
				if (grid[i][j] != GRID_EMPTY){
					g.setColor(Palette.getColor(grid[i][j]));
					g.fillRect((i+RENDER_MARGIN_LEFT)*RENDER_CELL_SIZE, (j+RENDER_MARGIN_HIGH)*RENDER_CELL_SIZE, RENDER_CELL_SIZE, RENDER_CELL_SIZE);
				}
			}
		}
		// pieces en cours
		//g.setColor(Palette.COLOR_14_LIGHT_CYAN);
		g.setColor(Palette.getColor(currentPiece.type.colorId));
		for (int[] cell: currentPiece.getCells()){
			g.fillRect((cell[0]+RENDER_MARGIN_LEFT)*RENDER_CELL_SIZE, (cell[1]+RENDER_MARGIN_HIGH)*RENDER_CELL_SIZE, RENDER_CELL_SIZE, RENDER_CELL_SIZE);
		}
		g.setColor(Palette.getColor(nextPiece.type.colorId));
		for (int[] cell: nextPiece.getCells()){
			g.fillRect((cell[0]+RENDER_MARGIN_LEFT+width)*RENDER_CELL_SIZE, (1+cell[1]+RENDER_MARGIN_HIGH)*RENDER_CELL_SIZE, RENDER_CELL_SIZE, RENDER_CELL_SIZE);
		}
		// line en suppression
		g.setColor(Color.black);
		for (int i=0;i<width;i++){
			if (!blinkOff){
				for (int index: linesToDelete){
					g.fillRect((i+RENDER_MARGIN_LEFT)*RENDER_CELL_SIZE, (index+RENDER_MARGIN_HIGH)*RENDER_CELL_SIZE, RENDER_CELL_SIZE, RENDER_CELL_SIZE);
				}
			}
		}
		// score
		this.linesText.lines.clear();
		this.linesText.lines.add(""+lines);
		this.linesTextRenderer.renderMenu(gc, g);
		if (currentState == STATE_GAME_OVER && !blinkOff){
			this.gameOverRenderer.renderMenu(gc, g);
		}
	}

	public void update(GameContainer gc, StateBasedGame game, int lastUpdate) throws SlickException {
		if (currentState == STATE_PLAYING){
			if (System.currentTimeMillis()-lastGhost>150){
				lastGhost = System.currentTimeMillis();
				for (int ghost: ghosts){
					keyPressed(ghost,' ');
				}
			}
			if (System.currentTimeMillis()-lastDown>500){
				synth.getChannels()[5].noteOn(60, 600);
				lastDown = System.currentTimeMillis();
				moveAndNext(0, 1);
			}
		}else if(currentState == STATE_DELETING_LINE){
			// gestion du clignotement
			if (System.currentTimeMillis()-lastBlink > 60){
				lastBlink = System.currentTimeMillis();
				blinkOff = !blinkOff;
			}
			if (System.currentTimeMillis()-startDeleting > 600){
				// reset blink
				lastBlink = System.currentTimeMillis();
				// suppression effective
				for (int index: linesToDelete){
					lines++;
					for (int i=0;i<width;i++){
						grid[i][index] = GRID_EMPTY;
					}
				}
				//chute
				for (int index: linesToDelete){
					for (int i=0;i<width;i++){
						for (int j=index-1;j>=0;j--){
							grid[i][j+1] = grid[i][j];
						}
					}
				}
				checkLinesToClear();
				// passage en effacement
				if (!linesToDelete.isEmpty()){
					//System.out.println("GameState#moveAndNext lines: "+linesToDelete.size());
					this.currentState = STATE_DELETING_LINE;
					this.startDeleting = System.currentTimeMillis();
				}else{
					loadNextPiece();
					currentState = STATE_PLAYING;
				}
			}

		}else if(currentState == STATE_GAME_OVER){
			if (System.currentTimeMillis()-lastBlink > 1000){
				lastBlink = System.currentTimeMillis();
				blinkOff = !blinkOff;
			}
			if (ghosts.contains(Input.KEY_SPACE)){
				resetLevel();
			}
		}
	}

	public int getID() {
		return StateManager.STATE_GAME;
	}

	public void setInput(Input input) {}
	public boolean isAcceptingInput() {return true;}
	public void inputStarted() {}
	public void inputEnded() {}
	public void keyReleased(int keyCode, char keyChar) {
		ghosts.remove(keyCode);
		done.remove(keyCode);
	}
	public void keyPressed(int keyCode, char keyChar) {
		ghosts.add(keyCode);
		//System.out.println("GameState.keyPressed IN keyCode="+keyCode+" keyChar="+keyChar);
		if (keyCode == Input.KEY_ENTER){

		}else if (keyCode == Input.KEY_ESCAPE){
		}else if (keyCode == Input.KEY_DOWN){
			lastDown = System.currentTimeMillis();
			moveAndNext(0, 1);
		}else if (keyCode == Input.KEY_UP){
		}else if (keyCode == Input.KEY_LEFT){
			currentPiece.deltaAndCollideGrid(-1, 0, grid);
		}else if (keyCode == Input.KEY_RIGHT){
			currentPiece.deltaAndCollideGrid(1, 0, grid);
		}else if (keyCode == Input.KEY_SPACE){
			if (!done.contains(Input.KEY_SPACE)){
				currentPiece.rotateRight(grid);
				done.add(Input.KEY_SPACE);
			}
		}
	}

	public void moveAndNext(int dx, int dy){
		boolean collide = currentPiece.deltaAndCollideGrid(dx, dy, grid);
		if (collide){
			boolean tooHigh = false;
			for (int[] cell: currentPiece.getCells()){
				if (cell[1]<=0){
					tooHigh = true;
				}
				grid[cell[0]][cell[1]] = currentPiece.type.colorId;
			}
			if (tooHigh){
				// TODO game Over
				this.currentState = STATE_GAME_OVER;
				this.blinkOff = false;
				this.lastBlink= System.currentTimeMillis();
			}else{
				checkLinesToClear();
				// passage en effacement
				if (!linesToDelete.isEmpty()){
					//System.out.println("GameState#moveAndNext lines: "+linesToDelete.size());
					this.currentState = STATE_DELETING_LINE;
					this.startDeleting = System.currentTimeMillis();
				}else{
					loadNextPiece();
				}
			}
		}
	}

	public void loadNextPiece(){
		if (nextPiece != null){
			this.currentPiece = nextPiece;
		}else{
			this.currentPiece = new Piece(PieceType.getRandomType(),4,0);
		}
		this.nextPiece =  new Piece(PieceType.getRandomType(),4,0);
		this.nextPiece.randomizePosition();
	}

	public void checkLinesToClear(){
		// controle de la grille
		linesToDelete.clear();
		for (int j=0;j<height;j++){
			boolean toDelete = true;
			for (int i=0;i<width;i++){
				toDelete = toDelete && grid[i][j] > GRID_EMPTY;
			}
			if (toDelete){
				linesToDelete.add(j);
			}
		}
	}

	private void resetLevel(){
		//System.out.println("GameState.resetLevel IN");
		lines = 0;
		for (int i=0;i<width;i++){
			for (int j=0;j<height;j++){
				grid[i][j] = GRID_EMPTY;
			}
		}
		nextPiece = null;
		loadNextPiece();
		currentState = STATE_PLAYING;
	}

}
