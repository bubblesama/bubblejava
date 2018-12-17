package com.bb.advent2018.day13;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CartCrasher {

	private static final String INPUT_FILE_NAME = "res/day13-input.txt";
	
	private void initFromFile() {
		
		//reading input file to initiate cart map size
		BufferedReader br = null;
		FileReader fr = null;
		try {
			fr = new FileReader(INPUT_FILE_NAME);
			br = new BufferedReader(fr);
			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null) {
			
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
		//log("init cluster initiated, stars="+cluster.size());
		
		
		
		
	}
	
	
	
	
	
	
	
	
}
