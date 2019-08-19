package com.bb.flatworld.bg;

public enum CloudType { 
	FAT1, FAT2, LONG;
	public static CloudType getCloud(int cloudId){
		switch (cloudId) {
		case 0:
			return FAT1;
		case 1:
			return LONG;
		default:
			return FAT2;
		}
	}
}