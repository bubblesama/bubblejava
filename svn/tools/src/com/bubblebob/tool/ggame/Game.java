package com.bubblebob.tool.ggame;

import java.awt.BorderLayout;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.bubblebob.tool.ggame.gui.FrameGui;
import com.bubblebob.tool.ggame.gui.GraphicalModel;

/**
 * Jeu abstrait gerant toute la partie generique d'un jeu, et parametre par:<br/>
 *  - le nom du jeu <br/>
 *  - le modele du jeu <br/>
 *  - l'adaptateur graphique du modele de jeu <br/>
 *  - l'adaptateur graphique du controleur <br/>
 *  - la periode de rafraichissement du jeu <br/>
 *  - le <i>listener</i> de surveillance des clics souris <br/>
 *  - le <i>listener</i> de surveillance des mouvements de la  souris<br/>
 *  
 * @author bubblebob
 *
 */
public class Game {

	private JFrame frame;
	private GameModel gameModel;
	private GraphicalModel graphicalModelAdapter;
	private JPanel controlerGui;
	private String name;
	private MouseListener mouseListener;
	private MouseMotionListener mouseMotionListener;
	private KeyListener keyListener;
	private int frameDelay;
	private GameLoop gameLoop;

	private String title;
	
	public Game(String name, GameModel gameModel, GraphicalModel graphicalModelAdapter, JPanel controlerGui, int frameDelay, MouseListener mouseListener, MouseMotionListener mouseMotionListener) {
		this.gameModel = gameModel;
		this.graphicalModelAdapter = graphicalModelAdapter;
		this.mouseListener = mouseListener;
		this.frameDelay = frameDelay;
		this.controlerGui = controlerGui;
		this.mouseMotionListener = mouseMotionListener;
		this.name = name;
	}

	public Game(String name, 
			GameModel gameModel, 
			GraphicalModel graphicalModelAdapter, 
			JPanel controlerGui, 
			int frameDelay, 
			MouseListener mouseListener, 
			MouseMotionListener mouseMotionListener,
			KeyListener keyListener) {
		this.gameModel = gameModel;
		this.graphicalModelAdapter = graphicalModelAdapter;
		this.mouseListener = mouseListener;
		this.frameDelay = frameDelay;
		this.controlerGui = controlerGui;
		this.mouseMotionListener = mouseMotionListener;
		this.name = name;
		this.keyListener = keyListener;
	}

	public String getTitle(){
		return title;
	}

	public void setTitle(String title){
		frame.setTitle(title);
	}
	
	public String getName() {
		return name;
	}

	public void launch(){
		frame = new JFrame(name);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addWindowListener(new WindowListener() {
			public void windowOpened(WindowEvent e) {}
			public void windowIconified(WindowEvent e) {}
			public void windowDeiconified(WindowEvent e) {}
			public void windowDeactivated(WindowEvent e) {}
			public void windowClosing(WindowEvent e) {
				System.out.println("Thanks for playing!");
			}
			public void windowClosed(WindowEvent e) {}
			public void windowActivated(WindowEvent e) {}
		});
		frame.setLocation(50, 50);
		FrameGui modelGui = new FrameGui(graphicalModelAdapter); // create our canvas object that has custom rendering in it.
		if (mouseListener != null){
			modelGui.addMouseListener(mouseListener);
		}
		if (mouseMotionListener != null){
			modelGui.addMouseMotionListener(mouseMotionListener);
		}
		if (keyListener != null){
			modelGui.addKeyListener(keyListener);
		}
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(modelGui,BorderLayout.CENTER);
		if (controlerGui != null){
			frame.getContentPane().add(controlerGui,BorderLayout.EAST);
		}
		//<calcul de la largeur du GUI du controler...
		int controlerGuiWidth = 0;
		if (controlerGui != null){
			controlerGuiWidth = controlerGui.getWidth();
		}
		//...calcul de la largeur du GUI du controler>
		System.out.println("width: "+(graphicalModelAdapter.getWidth()+controlerGuiWidth));
		frame.setSize(graphicalModelAdapter.getWidth()+controlerGuiWidth, graphicalModelAdapter.getHeight()+20);
		this.gameLoop = new GameLoop(frameDelay,modelGui,gameModel,this);
		Thread gameThread = new Thread(gameLoop);
		gameThread.setPriority(Thread.MIN_PRIORITY);
		gameThread.start(); 
		frame.setVisible(true);
	}

	
}