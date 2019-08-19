package com.bubblebob.tool.ggame.gui;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.image.VolatileImage;

public class FrameGui extends Canvas {

	private static final long serialVersionUID = -3265894049850325525L;

	private VolatileImage volatileImg;

	private GraphicalModel model;

	public FrameGui(GraphicalModel model){
		super();
		this.model = model;
	}

	public void paint(Graphics g) {
		// create the hardware accelerated image.
		createBackBuffer();

		// Main rendering loop. Volatile images may lose their contents. 
		// This loop will continually render to (and produce if necessary) volatile images
		// until the rendering was completed successfully.
		do {
			// Validate the volatile image for the graphics configuration of this 
			// component. If the volatile image doesn't apply for this graphics configuration 
			// (in other words, the hardware acceleration doesn't apply for the new device)
			// then we need to re-create it.
			GraphicsConfiguration gc = this.getGraphicsConfiguration();
			int valCode = volatileImg.validate(gc);

			// This means the device doesn't match up to this hardware accelerated image.
			if(valCode==VolatileImage.IMAGE_INCOMPATIBLE){
				createBackBuffer(); // recreate the hardware accelerated image.
			}

			Graphics offscreenGraphics = volatileImg.getGraphics();   
			doPaint(offscreenGraphics); // call core paint method.

			// paint back buffer to main graphics
			g.drawImage(volatileImg, 0, 0, this);
			// Test if content is lost   
		} while(volatileImg.contentsLost());
		volatileImg.flush();
	}

	/**
	 * Methode de visualisation du modele
	 * @param g: le graphique du rendu visuel
	 */
	private void doPaint(Graphics g) {
		model.paint(g);
	}

	/**
	 * Methode de creation de l'image volatile de buffer utilisee pour le rendu
	 */
	private void createBackBuffer() {
		GraphicsConfiguration gc = getGraphicsConfiguration();
		volatileImg = gc.createCompatibleVolatileImage(getWidth(), getHeight());
	}

	/**
	 * Methode de mise a jour de l'interface graphique
	 * @param g: le graphique du rendu visuel
	 */
	public void update(Graphics g) {
		paint(g);
	}
}