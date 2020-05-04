package com.bubblebob.tool.font;

import java.awt.Graphics;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractText{
	
	// attributs
	private static Pattern WORD_PATTERN = Pattern.compile("[a-zA-Z]+",Pattern.DOTALL+Pattern.CASE_INSENSITIVE);
	// la police de caracteres
	public Font font;
	// le texte
	private String text;
	// le coin haut gauche du cadre
	private int x;
	private int y;
	// le nombre de caracteres en large
	private int width;
	// le nombre de caracteres en hauteur
	private int height;
	// ecrit en horizontal?
	private boolean horizontal;
	// sort du cadre ou disparait si ca deborde
	private boolean leakOut;
	// sort du cadre ou disparait si les mots depassent une ligne
	private boolean lineLeakOut;
	// l'interligne
	private int interline;
	// les lignes ont-elles ete calculees?
	private boolean splitWords;
	// les lignes
	private List<String> lines;
	
	//constructeur
	public AbstractText(String text, Font font, int x, int y, int width, int height, int interline){
		this.font = font;
		this.text = text;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.interline = interline;
		this.horizontal = true;
		this.leakOut = false;
		this.lineLeakOut = true;
		this.splitWords = false;
		cut();
	}
	
	// accesseurs
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	// methodes
	public void setInterline(int interline) {
		this.interline = interline;
	}
	private void cut(){
		lines = new Vector<String>();
		//System.out.println("[Text#cut] text: "+text);
		Matcher wordMatcher = WORD_PATTERN.matcher(text);
		int currentLine = 0;
		int currentLength = 0;
		StringBuffer currentBuffer = new StringBuffer();
		while (wordMatcher.find()) {
			String word = wordMatcher.group(0);
			int nextLength = currentLength+word.length();
			if (nextLength > width){
				currentLine++;
				currentLength = 0;
				lines.add(currentBuffer.toString());
				currentBuffer = new StringBuffer(word);
			}else{
				currentBuffer.append(" "+word);
				currentLength += word.length()+1;
			}
		}
		lines.add(currentBuffer.toString());
	}
	
//	public void paint(Graphics g){
//		int yOffset = 0;
//		for (String line: lines){
//			int xOffset = 0;
//			for (char c: line.toCharArray()){
//				g.drawImage(font.getChar(c), x+xOffset, y+yOffset,null);
//				xOffset += font.getWidth()+1;
//			}
//			yOffset += font.getHeight()+interline;
//		}
//	}
	
	public void paint(){
		int yOffset = 0;
		for (String line: lines){
			int xOffset = 0;
			for (char c: line.toCharArray()){
				//g.drawImage(font.getChar(c), x+xOffset, y+yOffset,null);
				doSpecificPaintStuff(c,x+xOffset, y+yOffset);
				xOffset += font.getWidth()+1;
			}
			yOffset += font.getHeight()+interline;
		}
	}
	
	public abstract void doSpecificPaintStuff(char c, int x, int y);
	
//	public void paintWithOffset(Graphics g, int dx, int dy){
//		int yOffset = 0;
////		System.out.println("[Text#paintWithOffset] x="+(x-dx)+" y="+(y-dy));
//		for (String line: lines){
//			int xOffset = 0;
//			for (char c: line.toCharArray()){
//				g.drawImage(font.getChar(c), x+xOffset-dx, y+yOffset-dy,null);
//				xOffset += font.getWidth()+1;
//			}
//			yOffset += font.getHeight()+interline;
//		}
//	}
	
	
	public void paintWithOffset(int dx, int dy){
		int yOffset = 0;
//		System.out.println("[Text#paintWithOffset] x="+(x-dx)+" y="+(y-dy));
		for (String line: lines){
			int xOffset = 0;
			for (char c: line.toCharArray()){
//				g.drawImage(font.getChar(c), x+xOffset-dx, y+yOffset-dy,null);
				doSpecificPaintStuff(c, x+xOffset-dx, y+yOffset-dy);
				xOffset += font.getWidth()+1;
			}
			yOffset += font.getHeight()+interline;
		}
	}
}
