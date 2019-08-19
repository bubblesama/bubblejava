package com.bubblebob.uto.setting;

import java.awt.Color;
import java.awt.Graphics;

import org.w3c.dom.Text;

import com.bubblebob.tool.font.BaseFont;
import com.bubblebob.tool.font.BaseText;
import com.bubblebob.uto.UtoModel;
import com.bubblebob.uto.control.InputManager;
import com.bubblebob.uto.control.InputType;
import com.bubblebob.uto.graph.Assets;

/**
 * Classe de configuration des valeurs de cycle (longueur, nombre)
 * @author bubble
 *
 */
public class GameConfigurator {

	private UtoModel game;
	
	// etape de configuration
	private ConfigStep step = ConfigStep.START;
	// prise de chiffre courante
	private String currentInput;
	
	// valeurs a configurees 
	public int cycleLength = -1;
	public int cycleCount = -1;
	
	// limites des valeurs a configurer
	private static final int CYCLE_LENGTH_MIN = 20;
	private static final int CYCLE_LENGTH_MAX = 99;
	private static final int CYCLE_COUNT_MIN = 5;
	private static final int CYCLE_COUNT_MAX = 80;

	// les textes
	private BaseText textCycleLength;
	private BaseText textCycleCount;
	
	private Assets assets;
	
	public GameConfigurator(UtoModel game, Assets assets){
		BaseFont font = com.bubblebob.tool.font.BaseFont.getZoomedFont("assets/alphabet.png", 8);
		this.textCycleCount = new BaseText("CYCLES COUNT", font, 10, 80, 300, 100, 2);
		this.textCycleLength = new BaseText("CYCLE PERIOD", font, 10, 200, 300, 100, 2);
		this.currentInput = "";
		this.assets = assets;
		this.game = game;
	}
	
	private enum ConfigStep{
		START,CYCLE_COUNT_CHOSEN
	}
	
	public void paint(Graphics g) {
		textCycleLength.setGraphics(g);
		textCycleCount.setGraphics(g);
		g.setColor(Color.BLUE);
		g.fillRect(0, 0, 1200, 800);
		textCycleCount.paintWithOffset(0, 0);
		if (cycleCount > -1){
			assets.paintNumbers(g, cycleCount, 2, false, 70, 14);
		}
		if (step == ConfigStep.CYCLE_COUNT_CHOSEN){
			textCycleLength.paintWithOffset(0, 0);
			if (cycleLength > -1){
				assets.paintNumbers(g, cycleLength, 2, false, 70, 34);
			}
		}
		g.setColor(Color.BLACK);
	}
	
	public void update(InputManager inputManager){
		if (inputManager.getInput(InputType.INT_0)){
			currentInput+= 0;
			inputManager.resetInput(InputType.INT_0);
		}
		if (inputManager.getInput(InputType.INT_1)){
			currentInput+= 1;
			inputManager.resetInput(InputType.INT_1);
		}
		if (inputManager.getInput(InputType.INT_2)){
			currentInput+= 2;
			inputManager.resetInput(InputType.INT_2);
		}
		if (inputManager.getInput(InputType.INT_3)){
			currentInput+= 3;
			inputManager.resetInput(InputType.INT_3);
		}
		if (inputManager.getInput(InputType.INT_4)){
			currentInput+= 4;
			inputManager.resetInput(InputType.INT_4);
		}
		if (inputManager.getInput(InputType.INT_5)){
			currentInput+= 5;
			inputManager.resetInput(InputType.INT_5);
		}
		if (inputManager.getInput(InputType.INT_6)){
			currentInput+= 6;
			inputManager.resetInput(InputType.INT_6);
		}
		if (inputManager.getInput(InputType.INT_7)){
			currentInput+= 7;
			inputManager.resetInput(InputType.INT_7);
		}
		if (inputManager.getInput(InputType.INT_8)){
			currentInput+= 8;
			inputManager.resetInput(InputType.INT_8);
		}
		if (inputManager.getInput(InputType.INT_9)){
			currentInput+= 9;
			inputManager.resetInput(InputType.INT_9);
		}
		if (step == ConfigStep.START){
			if (currentInput.length() >0){
				if (currentInput.length() <3){
					cycleCount = Math.min(CYCLE_COUNT_MAX,Integer.parseInt(currentInput));
					
				}else{
					cycleCount = CYCLE_COUNT_MAX;
				}
			}
			if (inputManager.getInput(InputType.KEY_ENTER)){
				currentInput = "";
				step = ConfigStep.CYCLE_COUNT_CHOSEN;
				inputManager.resetInput(InputType.KEY_ENTER);
				cycleCount = Math.max(CYCLE_COUNT_MIN,cycleCount);
			}
		}else{
			if (currentInput.length() >0){
				if (currentInput.length() <3){
					cycleLength = Math.min(CYCLE_LENGTH_MAX,Integer.parseInt(currentInput));
					
				}else{
					cycleLength = CYCLE_LENGTH_MAX;
				}
			}
			if (inputManager.getInput(InputType.KEY_ENTER)){
				inputManager.resetInput(InputType.KEY_ENTER);
				cycleLength = Math.max(CYCLE_LENGTH_MIN,cycleLength);
				game.startGameAfterConfig(this);
			}
		}
	}
	
}
