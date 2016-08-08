package com.bubblebob.tool.phildefair;

import java.util.List;

import org.ejml.simple.SimpleMatrix;

public class SimpleSolver {
	
	
	//Point Of View
	private double[] pov;
	private List<double[]> points;
	
	private double w;
	private double h;
	
	private int wRes;
	private int hRes;
	
	
	
	/*
	 ax+by+cz=d
	 VM=k*VA
	 
	 x		-xV=k*(xA-xV)
	 y-yV=k*(yA-yV)
	 z-zV=k*(zA-zV)
	 ax		+by+cz=d
	 
	 
	 x		  +k(xV-xA)	= xV
	 	  y	  +k(yV-yA)	= yV
			 z+k(zV-zA)	= zV
	 ax	+by+cz          = d
	 	
	 	*/
	// Ax=b
	public static SimpleMatrix main(double x, double y, double z) {
		
		double a = 0;
		double b = 0;
		double c = 1;
		double d= 0;
		double xV= 0;
		double yV= 0;
		double zV=-1;
		double xA= x;
		double yA= y;
		double zA= z;
		
		
		SimpleMatrix A = new SimpleMatrix(4,4);
		A.set(0, 0, 1);
		A.set(0, 1, 0);
		A.set(0, 2, 0);
		A.set(0, 3, xV-xA);
		
		A.set(1, 0, 0);
		A.set(1, 1, 1);
		A.set(1, 2, 0);
		A.set(1, 3, yV-yA);
		
		A.set(2, 0, 0);
		A.set(2, 1, 0);
		A.set(2, 2, 0);
		A.set(2, 3, zV-zA);
		
		A.set(3, 0, a);
		A.set(3, 1, b);
		A.set(3, 2, c);
		A.set(3, 3, 0);
		
		SimpleMatrix B = new SimpleMatrix(4,1);
		
		B.set(0,0,xV);
		B.set(1,0,yV);
		B.set(2,0,zV);
		B.set(3,0,d);
		
		
		SimpleMatrix X = A.solve(B);
		
//		System.out.println(X.get(0, 0));
//		System.out.println(X.get(1, 0));
//		System.out.println(X.get(2, 0));
//		System.out.println(X.get(3, 0));
		
		return X;
	}
	
	
	
	
	
	
	
	
}
