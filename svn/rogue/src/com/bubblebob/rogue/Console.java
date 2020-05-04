package com.bubblebob.rogue;

import java.util.ArrayList;
import java.util.List;

public class Console {
	
	private List<String> lines;
	private static Console instance = new Console();
	
	private Console(){
		lines = new ArrayList<String>();
	}
	
	public static void log(String line){
		instance.lines.add(line);
	}
	
	public static void clear(){
		instance.lines.clear();
	}
	
	public static List<String> getLines(){
		return instance.lines;
	}
	
	
}
