package com.bubblesama.tetrahis;

import java.util.ArrayList;

public class SimpleTextShow extends TextShow{

	public SimpleTextShow(int x0, int y0, String message) {
		super(x0, y0, message);
	}
	
	public String getMessage(){
		return lines.get(0);
	}
	
	public void setMessage(String message){
		lines = new ArrayList<String>();
		lines.add(message);
	}

}
