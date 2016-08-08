package com.bubblebob.tool.seg;

import java.util.List;
import java.util.Vector;

public class SegAlgo extends AbstractSegAlgo{

	private boolean traced;
	
	private int x1;
	private int x2;
	private int y1;
	private int y2;
	
	private List<int[]> coords;
	
	public SegAlgo(int x1, int x2, int y1, int y2) {
		super();
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
		this.traced = false;
		int width = x1-x2>0?x1-x2:x2-x1;
		int height = y1-y2>0?y1-y2:y2-y1;
		int size = width>height?width:height;
		this.coords = new Vector<int[]>(size);
	}
	
	@Override
	public void trace(int x, int y) {
		int[] coord = new int[2];
		coord[0] = x;
		coord[1] = y;
		coords.add(coord);
//		System.out.println("[SegTrace#trace] coords.size="+coords.size());
	}

	public List<int[]> getCalculatedCoords(){
		if (!traced){
			calculate();
			if ((x1!=x2)&&(y1!=y2)){
				int[] coord = new int[2];
				coord[0] = x2;
				coord[1] = y2;
				coords.add(coord);
			}
		}
		return coords;
	}
	                                        
	private void calculate(){
		trace(x1, y1, x2, y2);
		traced = true;
	}
	
}