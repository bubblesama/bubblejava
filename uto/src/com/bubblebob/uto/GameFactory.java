package com.bubblebob.uto;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

public class GameFactory {

	private static final Color SEA = new Color(0,38,255);
	private static final Color LAND = new Color(255,216,0);

	private static final Color GREEN = new Color(0,255,0);
	private static final Color RED = new Color(255,216,0);

	public static BufferedImage getZoomed(BufferedImage image, int zoom){
		int width = (int) (image.getWidth() * zoom);
		int height = (int) (image.getHeight() * zoom);
		BufferedImage result = new BufferedImage( width, height, image.getType());
		Graphics2D graphics = image.createGraphics();
		graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
		graphics.drawImage(image, 0, 0, width, height, null);
		graphics.dispose();
		return result;
	}
	
}


