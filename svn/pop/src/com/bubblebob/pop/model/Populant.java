package com.bubblebob.pop.model;

import com.bubblebob.pop.LengthView;
import com.bubblebob.pop.LengthView.Square;

public class Populant{
	
	
		private LengthView model;
	
		public int x;
		public int y;
		public int type=1;
		public Square square = null;
		
		public void update(){
			// square 
			if (square == null || square.x!=x || square.y != y){
				  square = model.getSquare(x, y);
				  square.populant = this;
			}
			// AI
			
			
			
			
		}
		public Populant(LengthView model, int x, int y) {
			super();
			this.x = x;
			this.y = y;
			this.model = model;
		}
		
		
		
	}