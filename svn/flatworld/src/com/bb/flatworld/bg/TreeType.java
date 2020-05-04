package com.bb.flatworld.bg;

public enum TreeType {

	TREE_1,TREE_2,TREE_3;
	public static TreeType getTree(int treeId){
		switch (treeId) {
		case 0:
			return TREE_1;
		case 1:
			return TREE_2;
		default:
			return TREE_3;
		}
	}
	
}
