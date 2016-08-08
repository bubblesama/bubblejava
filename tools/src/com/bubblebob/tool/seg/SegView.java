package com.bubblebob.tool.seg;

public class SegView extends AbstractSegAlgo {

	@Override
	public void trace(int x, int y) {
		System.out.println("("+x+","+y+")");
	}
	
	public static void main(String[] args) {
		new SegView().trace(0, 0, 6, 1);
	}
	
}
