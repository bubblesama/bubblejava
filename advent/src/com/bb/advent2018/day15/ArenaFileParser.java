package com.bb.advent2018.day15;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ArenaFileParser {
	
	private static final String INPUT_FILE_NAME = "res/day15-input.txt";
	
	public static Arena parseInputFile() {
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
		Arena arena = new Arena(w, h);
		// deep analysis
		try {
			fr = new FileReader(INPUT_FILE_NAME);
			br = new BufferedReader(fr);
			String sCurrentLine;
			int currentJ = 0;
			while ((sCurrentLine = br.readLine()) != null) {
				// parsing chars for map and mobs
				for (int i=0;i<sCurrentLine.length();i++) {
					char currentChar = sCurrentLine.charAt(i);
					MobType mobType = null;
					CellType cellType = null;
					//possible #.GE
					switch (currentChar) {
					case '#':
						cellType = CellType.ROCK;
						break;
					case '.':
						cellType = CellType.SPACE;
						break;
					case 'G':
						cellType = CellType.SPACE;
						mobType = MobType.GOBLIN;
						break;
					case 'E':
						cellType = CellType.SPACE;
						mobType = MobType.ELF;
						break;
					default:
						break;
					}
					if (mobType != null) {
						arena.pop(mobType, i, currentJ);
					}
					arena.changeCell(cellType, i, currentJ);
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
		
		return arena;
		
	}
}
