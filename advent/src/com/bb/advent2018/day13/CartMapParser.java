package com.bb.advent2018.day13;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CartMapParser {
	
	private static final String INPUT_FILE_NAME = "res/day13-input.txt";
	
	public static CartMap parseInputFile() {
		int w = 0;
		int h = 0;
		//reading input file to initiate cart map size
		BufferedReader br = null;
		FileReader fr = null;
		try {
			fr = new FileReader(INPUT_FILE_NAME);
			br = new BufferedReader(fr);
			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null) {
				h++;
				w = sCurrentLine.length();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
				if (fr != null) {
					fr.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		CartMap map = new CartMap(w, h);
		
		// deep analysis
		try {
			fr = new FileReader(INPUT_FILE_NAME);
			br = new BufferedReader(fr);
			String sCurrentLine;
			int currentJ = 0;
			int nexCartId = 0;
			while ((sCurrentLine = br.readLine()) != null) {
				//TODO parsing chars for cells and carts
				for (int i=0;i<sCurrentLine.length();i++) {
					char currentChar = sCurrentLine.charAt(i);
					TrackType track = TrackType.NONE;
					Direction direction = null;
					//possible -\|/+v^<>
					switch (currentChar) {
					case '-':
						track = TrackType.RIGHTLEFT;
						break;
					case '\\':
						track = TrackType.UPRIGHT;
						break;
					case '|':
						track = TrackType.UPDOWN;
						break;
					case '/':
						track = TrackType.RIGHTDOWN;
						break;
					case '+':
						track = TrackType.CROSS;
						break;
					case 'v':
						track = TrackType.UPDOWN;
						direction = Direction.DOWN;
						break;
					case '^':
						track = TrackType.UPDOWN;
						direction = Direction.UP;
						break;
					case '>':
						track = TrackType.RIGHTLEFT;
						direction = Direction.RIGHT;
						break;
					case '<':
						track = TrackType.RIGHTLEFT;
						direction = Direction.LEFT;
						break;
					default:
						break;
					}
					map.getCell(i, currentJ).setTrack(track);
					if (direction != null) {
						//adding a cart
						map.add(new Cart(nexCartId, i, currentJ, direction));
						nexCartId++;
					}
				}
				currentJ++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
				if (fr != null) {
					fr.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		System.out.println("map parsed: w="+map.w+" h="+map.h+" carts="+map.carts.size());
		
		return map;
		
	}
	
	
	
	
	
	
	

}
