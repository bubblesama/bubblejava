package com.bb.catane.model;

public enum ResourceType {
	
	CLAY,WOOD,STONE,WOOL,WHEAT;

	public static ResourceType parse(String resourceString) {
		for (ResourceType result: ResourceType.values()){
			if (result.toString().equalsIgnoreCase(resourceString)){
				return result;
			}
		}
		return null;
	}
}
