package com.bubblebob.tool.font.autofont;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import com.bubblebob.tool.font.Font;
import com.bubblebob.tool.font.SlickAutoText;

public class SlickAutoFont extends BasicGame implements Font {

	private Map<Character,Glyph> glyphByChar;
	
	public SlickAutoFont(){
		super("Autoglyph");
		glyphByChar = new HashMap<Character,Glyph>();
	}

	private int scale = 1;
	
	
	/**
	 * Creer une liste de glyphes par caractère br />
	 * Parcours deux fichier contenant: <br/>
	 * - une matrice de caracteres pour affecter à chaque caractere une place, au format rectangulaire, chaque caractère une seule fois <br/>
	 * - une ficher image de même dimensions contenant les glyphes
	 * @param charMapFilePath: le fichier des caractères
	 * @param picMapFilePath: le fichier des glyphes
	 * @param monoWidth: boolean: chque glyphe aussi large?
	 */
	public void initGlyphMap(String charMapFilePath, String picMapFilePath, boolean monoWidth){
		try {
			// premier scan du fichier des caractères pour compter les lignes et colonnes
			int rows = 0;
			int columns = 0;
			BufferedReader charMapFileReader = null;
			charMapFileReader = new BufferedReader(new InputStreamReader(new FileInputStream(charMapFilePath)));
			try{
				int lineIndex = 0;
				String line = charMapFileReader.readLine();
				while (line != null) {
					rows++;
					columns=  line.toCharArray().length;
					line = charMapFileReader.readLine();
				}
			}catch (IOException e){
				e.printStackTrace();
			}	
			// deuxieme parcours avec le fichier de glyphes
			Image glyphsMapImage = new Image(picMapFilePath);

			System.out.println("glyphImage: width="+glyphsMapImage.getWidth());

			//calcul des dimensions de base de chaque glyphe
			int glyphBaseHeight = glyphsMapImage.getHeight()/rows;
			int glyphBaseWidth = glyphsMapImage.getWidth()/columns;
			SpriteSheet glyphsBaseSheet = new SpriteSheet(picMapFilePath,glyphBaseWidth,glyphBaseHeight);
			// nouveau scan du fichier pour creations des pictos des glyphs
			charMapFileReader = new BufferedReader(new InputStreamReader(new FileInputStream(charMapFilePath)));
			int lineIndex = 0;
			String line = charMapFileReader.readLine();
			while (line != null) {
				int charIndex = 0;
				for (char c: line.toCharArray()){
					glyphByChar.put(c, new Glyph(c,charIndex, lineIndex,monoWidth, glyphsBaseSheet.getSprite(charIndex, lineIndex)));
					charIndex++;
				}
				line = charMapFileReader.readLine();
				lineIndex++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public int getWidth(char c) {
		return glyphByChar.get(c).w*scale;	
	}

	@Override
	public int getHeight() {
		return glyphByChar.get(' ').h*scale;	
	}

	public Image getChar(char c){
		if (glyphByChar.get(c)!= null && glyphByChar.get(c).pic != null){
			return glyphByChar.get(c).pic.getScaledCopy(scale);
		}else{
			return glyphByChar.get('?').pic.getScaledCopy(scale);
		}
	}

	public Glyph getGlyph(char c){
		return glyphByChar.get(c);
	}
	
	
	
	
	// TESTS
	
	// pour les tests
	private SlickAutoText text;
	
	// A des fins de tests
	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		g.setColor(Color.blue);
		text.setGraphics(g);
		text.paint();
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		initGlyphMap("assets/ascii.txt", "assets/ascii.png", false);
//		System.out.println("test de place de w: i="+glyphByChar.get('w').i+" j="+glyphByChar.get('w').j);
		this.text = new SlickAutoText("TTTThe quick brown fo?x !,#jumps over the lazy dog! QUICK and BROWN, the fox @#?", this, 20, 30, 20, 20, 1);
	}

	@Override
	public void update(GameContainer gc, int lastUpdate) throws SlickException {

	}

	public static void main(String[] args) {
		AppGameContainer app;
		try {
			app = new AppGameContainer(new SlickAutoFont());
			app.setDisplayMode(800,600,false);
			app.setMinimumLogicUpdateInterval(1000/60);
			app.isVSyncRequested();
			app.setTargetFrameRate(60);
			app.setShowFPS(false);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}
