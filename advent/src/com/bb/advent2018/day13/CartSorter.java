package com.bb.advent2018.day13;

import java.util.Comparator;

public class CartSorter implements Comparator<Cart>{

	@Override
	public int compare(Cart cart1, Cart cart2) {
		int result = 0;
		if (cart1.j<cart2.j) {
			result = -1; 
		}else if (cart1.j>cart2.j) {
			result = 1;
		}else if (cart1.i < cart2.i) {
			result = -1;
		}else if (cart1.i > cart2.i){
			result = 1;
		}
		return result;
	}

}
