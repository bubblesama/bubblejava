package com.bubblebob.pop;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Vector;

import com.bubblebob.pop.model.Populant;
import com.bubblebob.tool.ggame.Game;
import com.bubblebob.tool.ggame.GameModel;
import com.bubblebob.tool.ggame.gui.GraphicalModel;
import com.bubblebob.tool.image.MonoSizedBundledImage;

public class LengthView implements GraphicalModel,GameModel {
	public LengthView(){
		this.squaresGrid = new Square[squareWidth][squareHeight];
		for (int i=0;i<squareWidth;i++){
			for (int j=0;j<squareHeight;j++){
				Square square = new Square(i,j,0);
				squaresGrid[i][j] = square;
			}
		}
		squaresGrid[0][2].h = -5;
		this.populants = new Vector<Populant>();
		Populant pop = new Populant(this,8, 8);
		populants.add(pop);
		pop.update();
		
	}
	public int getHeight() {
		return 400;
	}
	public int getWidth() {
		return 600;
	}
	public void paint(Graphics g) {
		for (int i=0;i<squareWidth;i++){
			for (int j=squareHeight-1;j>=0;j--){
				Square square = squaresGrid[i][j];
				square.draw(g);
			}
		}
	}
	public void update() {
		// screen movment
		if(turn % 2 == 0){
			x0+= dx0;
			y0+= dy0;
		}
		if (overTile != null){
			overTile.over = false;
		}
		Square over = getProjectedSquare(mouseX, mouseY);
		if (over != null){
			over.over = true;
		}
		overTile = over;
		turn++;
		// maj populant
		for (Populant p:populants){
			p.update();
		}
		
	}
	public Square[][] squaresGrid;
	private int squareWidth = 40;
	private int squareHeight = squareWidth;
	private int x0 = 0;
	private int y0 = 100;
	public int scaleX = 20;
	public int scaleY = 10;
	private int dxx = scaleX;
	private int dxy = scaleY;
	private int dyx = scaleX;
	private int dyy = -scaleY;
	private int dh = 10;
	private int minH = -2;
	private int maxH= +4;
	public int dx0 = 0;
	public int dy0 = 0;
	public int ddx0 = 5;
	public int ddy0 = 5;
	public int mouseX;
	public int mouseY;
	private int turn = 0;
	private Square overTile = null;
	private static final String PROJECT_ROOT = "D:/perso/work";
	private MonoSizedBundledImage tiles = new MonoSizedBundledImage(2, 1, PROJECT_ROOT+"/SVN/pop/assets/pop-tiles.png");
	private BufferedImage tile = tiles.getImage(0, 0);
	private BufferedImage selectedTile = tiles.getImage(1, 0);
	private List<Populant> populants;
	private MonoSizedBundledImage populantTiles = new MonoSizedBundledImage(2, 1, PROJECT_ROOT+"/SVN/pop/assets/pop.png").zoom(2);
	private int populantDWidth = 15;
	private int populantDHeight = 20;
	
	private void drawLine(Graphics g, int xA,int yA, int hA, int xB, int yB, int hB){
		g.drawLine(x0+xA*dxx+yA*dyx,y0+xA*dxy+yA*dyy-dh*hA,x0+xB*dxx+yB*dyx,y0+xB*dxy+yB*dyy-dh*hB);
	}
	private void drawCenter(Graphics g, int xM,int yM, int hM){
		g.fillRect(x0+xM*dxx+yM*dyx+(dxx+dyx)/2,y0+xM*dxy+yM*dyy+(dxy+dyy)/2-hM*dh,1,1);
	}
	public int[] project(int x, int y){
		int xPrime = x-x0;
		int yPrime = y-y0;
		int xFinal = (xPrime*dyy-yPrime*dyx)/(dxx*dyy-dxy*dyx);
		int yFinal = (xPrime*dxy-yPrime*dxx)/(dyx*dxy-dyy*dxx);
		// System.out.println("[LengthView#project] xFinal="+xFinal+" yFinal="+yFinal);
		int[] result = {xFinal,yFinal};
		return result;
	}
	public Square getProjectedSquare(int x, int y){
		int xPrime = x-x0;
		int yPrime = y-y0;
		int xM = (xPrime*dyy-yPrime*dyx)/(dxx*dyy-dxy*dyx);
		int yM = (xPrime*dxy-yPrime*dxx)/(dyx*dxy-dyy*dxx);
		if (xM>=0 && xM<squareWidth&&yM>=0&&yM<squareHeight){
			return squaresGrid[xM][yM];
		}else{
			return null;
		}
	}
	public Square getSquare(int i, int j){
		if (i<0 || i>= squareWidth || j<0 || j>=squareHeight){
			return null;
		}else{
			return squaresGrid[i][j];
		}
	}
	public class Square{
		public boolean over = false;
		public int x;
		public int y;
		public int h;
		public Populant populant;
		public Square(int x, int y, int h){
			this.x = x;
			this.y = y;
			this.h = h;
		}
		public void draw(Graphics g){
			if (over){
				g.drawImage(selectedTile,x0+x*dxx+y*dyx,y0+x*dxy+y*dyy-h*dh-scaleY,null);
			}else{
				g.drawImage(tile,x0+(x)*dxx+y*dyx,y0+x*dxy+y*dyy-h*dh-scaleY,null);
			}
			if (populant != null){
				drawPopulant(g,populant);
			}
		}
		public void drawPopulant(Graphics g, Populant p){
			g.drawImage(populantTiles.getImage(p.type, 0),x0+x*dxx+y*dyx+populantDWidth,y0+x*dxy+y*dyy-h*dh-populantDHeight,null);
		}
		public void drawHeight(Graphics g){
			drawCenter(g, x, y, h);
		}
		public void up(){
			if (h<maxH){
				h++;
				for (Square square: getNeighbours()){
					if (square.h < h-1){
						square.up();
					}
				}
			}
		}
		public void down(){
			if (h> minH){
				h--;
				for (Square square: getNeighbours()){
					if (square.h > h+1){
						square.down();
					}
				}
			}
		}
		public List<Square> getNeighbours(){
			List<Square> result = new Vector<Square>();
			int[][] coords = {{x-1,y},{x+1,y},{x,y-1},{x,y+1}};
			for (int[] coord : coords){
				int xM = coord[0];
				int yM = coord[1];
				if (xM>=0 && xM<squareWidth&&yM>=0&&yM<squareHeight){
					result.add(squaresGrid[xM][yM]);
				}
			}
			return result;
		}
	}
	
	public static void main(String[] args) {
		LengthView model = new LengthView();
		LengthListener listener = new LengthListener(model);
		Game game = new Game("length",model,model,null,120,listener,listener,listener);
		game.launch();
	}
}