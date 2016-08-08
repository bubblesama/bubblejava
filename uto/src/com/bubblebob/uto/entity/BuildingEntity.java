package com.bubblebob.uto.entity;

import java.awt.Graphics;

import com.bubblebob.uto.BuildingType;
import com.bubblebob.uto.UtoModel;
import com.bubblebob.uto.PlayerType;

public abstract class BuildingEntity extends Entity{

	public BuildingEntity(UtoModel model, PlayerEntity player, BuildingType type, int x, int y) {
		super(model, "b:"+type+":"+player,(x/model.getTileSize())*model.getTileSize(), (y/model.getTileSize())*model.getTileSize(), model.getTileSize(), model.getTileSize());
		this.player = player;
		this.type = type;
	}

	public PlayerEntity player;
	public BuildingType type;

	public void paint(Graphics g){
		if (type != BuildingType.NO_BUILDING){
//			System.out.println("[BuildingEntity#paint] IN");
			g.drawImage(assets.getBuilding(type), x*assets.getScale(), y*assets.getScale(), null);
		}
	}
	
	public static BuildingEntity getBuilding(UtoModel model, PlayerEntity player, BuildingType type, int x, int y){
		int X = (x/model.getTileSize())*model.getTileSize();
		int Y = (y/model.getTileSize())*model.getTileSize();
		switch (type) {
		case FACTORY:
			return new BuildingFactoryEntity(model, player, X, Y);
		case FARM:
			return new BuildingFactoryEntity(model, player, X, Y);
		case FORT:
			return new BuildingFactoryEntity(model, player, X, Y);
		case HOSPITAL:
			return new BuildingFactoryEntity(model, player, X, Y);
		case HOUSE:
			return new BuildingFactoryEntity(model, player, X, Y);
		case SCHOOL:
			return new BuildingFactoryEntity(model, player, X, Y);
		default:
			return null;
		}
	}
	
	public int getGraphLayer(){
		return 0;
	}
	
	
}
