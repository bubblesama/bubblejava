package com.bb.citadel;

public enum DistrictType {

	ROYAL, RELIGIOUS, BUSINESS, MILITARY, MARVEL;
	
	public static DistrictType parse(String desc){
		for (DistrictType type: DistrictType.values()){
			if (type.toString().toLowerCase().equals(desc.toLowerCase())){
				return type;
			}
		}
		return null;
	}
	
}
