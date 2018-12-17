package com.bb.advent2018.day13;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CartMapParser {
	
	private static final String INPUT_FILE_NAME = "res/day13-input.txt";
	
	public CartMap parseInputFile() {
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
			
			while ((sCurrentLine = br.readLine()) != null) {
				int currentI;

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
		
		
		
		
	}
	
	
	
	
	
	
	

}
